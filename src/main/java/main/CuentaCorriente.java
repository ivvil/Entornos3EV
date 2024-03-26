package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CuentaCorriente implements Comparable<CuentaCorriente> {
	private String numCuenta;
	private Double saldo;
	private List<Cliente> clientes;
	private static Integer numSiguiente = 100;

	public CuentaCorriente(Cliente cliente) throws ObjetoErroneo {
		numCuenta = String.format("CC%03d", numSiguiente);
		if (cliente == null) {
			throw new ObjetoErroneo();
		}
		saldo = 0.0;
		clientes = new ArrayList<>();
		clientes.add(cliente);
		numSiguiente++;
	}

	public boolean buscaCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}

	public String agregaCliente(Cliente cliente) {
		if (cliente == null)
			return "El cliente est� a null)";
		if (clientes.contains(cliente))
			return "El cliente ya est� en la cuenta";
		clientes.add(cliente);
		return "Cliente a�adido correctamente";
	}

	public String quitaCliente(Cliente cliente) {
		if (cliente == null)
			return "El cliente est� a null)";
		if (!clientes.contains(cliente))
			return "El cliente no est� en la cuenta";
		if (clientes.size() == 1)
			return "Es el �nico cliente de la cuenta. No se puede eliminar";
		clientes.remove(cliente);
		return "Cliente eliminado correctamente";
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public static Integer getNumSiguiente() {
		return numSiguiente;
	}

	@Override
	public String toString() {
		String texto = "";
		for (Cliente c : clientes) {
			texto += c.toString() + "/";
		}
		return "main.CuentaCorriente n�mero de cuenta=" + numCuenta + ", saldo=" + String.format("%9.2f", saldo)
				+ "\n clientes:=" + texto;
	}

	public String visualiza() {
		return numCuenta + " : saldo = " + saldo;
	}

	public Movimiento ingresar(Calendar fecha, double importe) throws ObjetoErroneo {
		Movimiento m = null;
		if (importe >= 0.0) {
			m = new Movimiento(fecha, importe, true, this);
			saldo = saldo + importe;
		}
		return m;
	}

	public Movimiento retirar(Calendar fecha, double importe) throws ObjetoErroneo {
		Movimiento m = null;
		if (importe >= 0.0 && importe < saldo) {
			m = new Movimiento(fecha, importe, false, this);
			saldo = saldo - importe;
		}
		return m;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CuentaCorriente)) {
			return false;
		}
		CuentaCorriente other = (CuentaCorriente) obj;
		if (numCuenta == null) {
			if (other.numCuenta != null) {
				return false;
			}
		} else if (!numCuenta.equals(other.numCuenta)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(CuentaCorriente o) {
		// TODO Auto-generated method stub
		return numCuenta.compareTo(o.getNumCuenta());
	}

}
