package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class Banco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Cliente> clientes = new ArrayList<>();
		List<CuentaCorriente> cuentas = new ArrayList<>();
		List<Movimiento> movimientos = new ArrayList<>();
		final String[] MENU = { "Salir", "Crear cliente", "Crear cuenta", "Añadir cliente a una cuenta",
				"Quitar cliente de una cuenta", "Ingresar en cuenta", "Sacar de una cuenta", "Cuentas de un cliente",
				"Movimientos entre fechas", "Movimientos de una cuenta", "Listado de cuentas" };
		Comparator<CuentaCorriente> porSaldo = (a, b) -> a.getSaldo().compareTo(b.getSaldo());
		Comparator<Movimiento> porFecha = (a, b) -> a.getFecha().compareTo(b.getFecha());
		Comparator<Movimiento> porCuenta = (a, b) -> a.getCuenta().compareTo(b.getCuenta());
		int opcion = -1;
		Cliente c = null;
		CuentaCorriente cc = null;
		Movimiento m = null, mm = null;
		double importe;
		Calendar fecha, fecha1;
		String nombre, apellidos, dni;
		while (opcion != 0) {
			switch (opcion) {
			case 1:
				nombre = Leer.cadena("Nombre del cliente");
				apellidos = Leer.cadena("Apellidos del cliente");
				dni = Leer.cadena("Dni del cliente");
				try {
					c = new Cliente(nombre, apellidos, dni);
					if(clientes.contains(c)) 
						System.out.println("El cliente ya existe");
					else
						clientes.add(c);
				} catch (ObjetoErroneo e2) {
					System.out.println("Datos erróneos al crear el cliente");
				}
				break;
			case 2:
				c = selecciona(clientes, "un cliente para abrir la cuenta");
				try {
					cc = new CuentaCorriente(c);
					cuentas.add(cc);
				} catch (ObjetoErroneo e1) {
					System.out.println("La cuenta no se pueda abrir");
				}
				break;
			case 3:
				cc = selecciona(cuentas, "una cuenta corriente");
				c = selecciona(clientes, "un cliente a añadir");
				if (cc != null)
					System.out.println(cc.agregaCliente(c));
				break;
			case 4:
				cc = selecciona(cuentas, "una cuenta corriente");
				c = selecciona(clientes, "un cliente a eliminar");
				if (cc != null)
					System.out.println(cc.quitaCliente(c));
				break;
			case 5:
				cc = selecciona(cuentas, "una cuenta corriente donde ingresar");
				if (cc == null)
					break;
				importe = Leer.doble("Importe a ingresar");
				fecha = leeFecha("Fecha del ingreso");
				mm = buscaUltimo(movimientos, cc);
				if (mm == null || mm.getFecha().before(fecha)) {
					try {
						m = cc.ingresar(fecha, importe);
						movimientos.add(m);
					} catch (ObjetoErroneo e) {
						System.out.println("El importe no se puede ingresar " + importe);
					}
				} else {
					System.out.println("Hay movimientos posteriores al propuesto");
				}
				break;
			case 6:
				cc = selecciona(cuentas, "una cuenta corriente de donde sacar");
				if (cc == null)
					break;
				importe = Leer.doble("Importe a retirar");
				fecha = leeFecha("Fecha del reintegro");
				mm = buscaUltimo(movimientos, cc);
				if (mm != null && mm.getFecha().before(fecha)) {
					try {
						m = cc.retirar(fecha, importe);
						movimientos.add(m);
					} catch (ObjetoErroneo e) {
						System.out.println("El importe no se puede retirar " + importe);
					}
				} else {
					System.out.println("No hay saldo para la retirada o hay movimientos posteriores al propuesto");
				}
				break;
			case 7:
				c = selecciona(clientes, "un cliente para localizar sus cuentas");
				cuentas.sort(porSaldo.reversed());
				for (CuentaCorriente ccor : cuentas) {
					if (ccor.buscaCliente(c)) {
						System.out.println(ccor);
					}
				}
				break;
			case 8:
				fecha = leeFecha("Fecha desde:");
				fecha.add(Calendar.DAY_OF_MONTH, -1);
				fecha1 = leeFecha("Fecha hasta");
				fecha1.add(Calendar.DAY_OF_MONTH, +1);
				if (fecha.after(fecha1)) {
					System.out.println("La fecha segunda es menor que la primera");
				} else {
					movimientos.sort(porFecha.thenComparing(porCuenta));
					for (Movimiento mov : movimientos) {
						if (mov.getFecha().after(fecha) && mov.getFecha().before(fecha1)) {
							System.out.println(mov + "  " + mov.getCuenta().getNumCuenta());
						}
					}
				}
				break;
			case 9:
				cc = selecciona(cuentas, "una cuenta corriente para ver sus movimientos");
				System.out.println(cc);
				for (Movimiento mov : movimientos) {
					if (mov.getCuenta().equals(cc)) {
						System.out.println(mov);
					}
				}
				break;
			case 10:
				cuentas.forEach(System.out::println);
				break;
			}
			opcion = Leer.menu(MENU);
		}
	}

	public static <T> T selecciona(List<T> lista, String texto) {
		int nele = -1;
		if (lista.size() == 0) {
			System.out.println("Lista vacía");
			return null;
		}
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(i + " " + lista.get(i).toString());
		}
		while (nele < 0 || nele >= lista.size()) {
			nele = Leer.entero("Escoge " + texto);
		}
		return lista.get(nele);
	}

	public static Calendar leeFecha(String texto) {
		String dia;
		dia = Leer.cadena("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$", texto);
		String[] d = dia.split("[\\/|-]");
		Calendar fecha = new GregorianCalendar(Integer.parseInt(d[2]), Integer.parseInt(d[1]) - 1,
				Integer.parseInt(d[0]));
		return fecha;
	}

	public static Movimiento buscaUltimo(List<Movimiento> m, CuentaCorriente cc) {
		Movimiento mm = null;
		for (Movimiento movimiento : m) {
			if (movimiento.getCuenta().equals(cc))
				mm = movimiento;
		}
		return mm;
	}

}
