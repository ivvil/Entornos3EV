package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Guarda información sobre la cuenta corriente del cliente.
 */
public class CuentaCorriente implements Comparable<CuentaCorriente> {
	private String numCuenta;
	private Double saldo;
	private List<Cliente> clientes;
	private static Integer numSiguiente = 100;

    /**
     * Construye un nuevo objeto {@link CuentaCorriente} con un número de cuenta autogenerado, el saldo a 0
     * y una lista de clientes con el cliente introducido por el parametro.
     * Comprueba que el {@link Cliente} introducido por parametro no sea un objeto nulo.
     * @param cliente Cliente propietario de la cuenta
     * @throws ObjetoErroneo en caso de que el cliente sea {@code null}.
     */
	public CuentaCorriente(Cliente cliente) throws ObjetoErroneo {
        // Formatea una cadena de texto dado el atributo estático de numSiguiente
		numCuenta = String.format("CC%03d", numSiguiente);

        // Comprueba si el cliente no es null, en caso de que lo sea lanza la Exepcion ObjetoErroneo
		if (cliente == null) {
			throw new ObjetoErroneo();
		}

        // Inicializa el atributo saldo a 0
		saldo = 0.0;

        // Inicializa la lista de clientes sin un tamaño establecido y añade el cliente
		clientes = new ArrayList<>();
		clientes.add(cliente);

        // Aumenta en 1 el valor numSiguiente para crear el siguiente Objeto
		numSiguiente++;
	}

    /**
     * Devuelve el resultado de {@code ArrayList.contains} dado el {@link Cliente}.
     * @param cliente Cliente a buscar
     */
	public boolean buscaCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}

    /**
     * Dado un cliente se añade a la lista de clientes.
     * Comprueba que el {@link Cliente} no sea nulo y que no este en la lista.
     * @param cliente Cliente a añadir
     */
	public String agregaCliente(Cliente cliente) {
        // Comprobar que el cliente insertado por parametro no sea un cliente nulo
		if (cliente == null)
            // Devolver un mensaje de error y salir de la ejecución
			return "El cliente está a null)";

        // Comprobar que el cliente no este en la Lista de la cuenta
		if (clientes.contains(cliente))
            // Devolver un mensaje indicando que ya existe en la cuenta y salir de la ejecución
			return "El cliente ya está en la cuenta";

        // Insertar cliente en caso de que no sea nulo, ni ya exista en la cuenta
		clientes.add(cliente);

        // Devolver un mensaje indicando que se ha añadido correctamente
		return "Cliente añadido correctamente";
	}

    /**
     * Elimina el cliente de la lista de clientes.
     * Comprueba que el {@link Cliente} introducido por parametro exista en la cuenta,
     * no sea nulo y que en la cuenta haya más de 1 cliente.
     * @param cliente Cliente a eliminar.
     */
	public String quitaCliente(Cliente cliente) {
        // Comprobar que el cliente insertado por parametro no sea un cliente nulo
        if (cliente == null)
            // Devolver un mensaje de error
            return "El cliente está a null)";

        // Comprobar que el cliente ya exista en la cuenta
		if (!clientes.contains(cliente))
            // Si no es así devolver el mensaje de error correspondiente y salir de la ejecución
			return "El cliente no está en la cuenta";

        // Comprobar si hay mas de un cliente en la lista, para que esta lista no se quede vacía
		if (clientes.size() == 1)
            // Si solo existe un cliente devolver un mensaje de error y salir de la ejecución
			return "Es el único cliente de la cuenta. No se puede eliminar";

        // Después de hacer las comprobaciones elminar el cliente de la lista
		clientes.remove(cliente);
        // Devolver el mensaje de comprobación correspondiente
		return "Cliente eliminado correctamente";
	}

    /**
     * Devuelve un {@link String} conteniendo el número de cuenta.
     */
	public String getNumCuenta() {
		return numCuenta;
	}

    /**
     * Devuelve un {@link Double} conteniendo el saldo disponible de una cuenta
     */
	public Double getSaldo() {
		return saldo;
	}

    /**
     * Devuelve un {@link Integer} conteniendo el número para generar automaticamente el número de cuenta
     */
	public static Integer getNumSiguiente() {
		return numSiguiente;
	}

    /**
     * Devuelve un {@link String} conteniendo los atributos del objeto en una cadena de texto.
     */
	@Override
	public String toString() {
		String texto = "";
		for (Cliente c : clientes) {
			texto += c.toString() + "/";
		}
		return "CuentaCorriente número de cuenta=" + numCuenta + ", saldo=" + String.format("%9.2f", saldo)
				+ "\n clientes:=" + texto;
	}

    /**
     * Devuelve un {@link String} que contiene el número de cuenta y el saldo que contiene
     */
	public String visualiza() {
		return numCuenta + " : saldo = " + saldo;
	}

    /**
     * Dado una cantidad de dinero la ingresa en la cuenta bancaria y devuelve un movimiento indicando la fecha cuando se ha hecho.
     * @param fecha Fecha del movimiento.
     * @param importe Importe a ingresar.
     * @return {@code null} en el caso de que se hayan intruducido unos parametros invalidos y {@link Movimiento} en caso de que se introduzcan valores válidos.
     * @throws ObjetoErroneo en caso de que algunos de los parametros sea incorreto.
     */
	public Movimiento ingresar(Calendar fecha, double importe) throws ObjetoErroneo {
        // Crear un movimiento nulo
        Movimiento m = null;

        // En el caso de que el importe sea mayor
        if (importe >= 0.0) {
            // Instanciar un nuevo movimiento con la fecha y el importe introducida por parametro
            m = new Movimiento(fecha, importe, true, this);
            // Una vez instanciado añadir el importe al saldo de la cuenta
            saldo = saldo + importe;
        }

        // Devolver el movimiento creado
        return m;
	}

    /**
     * Dado la fecha y el importe crea un movimiento en caso de que los parametros introducidos sean valores adecueandos para la CuentaCorriente.
     * @param fecha Fecha del movimiento.
     * @param importe Importe a retrirar.
     * @return {@code null} en el caso de que se hayan intruducido unos parametros invalidos y {@link Movimiento} en caso de que se introduzcan valores válidos.
     * @throws ObjetoErroneo en caso de que algunos de los parametros sea incorreto.
     */
	public Movimiento retirar(Calendar fecha, double importe) throws ObjetoErroneo {
        // Creacion del movimiento nulo
		Movimiento m = null;

        // En caso de que el importe sea mayor o igual que 0 y sea menor que el saldo
		if (importe >= 0.0 && importe < saldo) {
            // Instanciar el movimiento con los parametros fecha e importe
			m = new Movimiento(fecha, importe, false, this);
            // Sustraer el importe al saldo de la cuenta
			saldo = saldo - importe;
		}

        // Devolver el movimiento creado
		return m;
	}

    /**
     * Comparara el objeto introducido por parametro con {@link CuentaCorriente}
     * @return Verdadero en caso de que el parametro sea equivalente a {@link CuentaCorriente}
     */
	@Override
	public boolean equals(Object obj) {
        // Comprueba si es examente el mismo objeto
		if (this == obj) {
			return true;
		}

        // Comprobar que el objeto no sea una instancia de CuentaCorriente
		if (!(obj instanceof CuentaCorriente)) {
			return false;
		}

        // Castear a CuentaCorriente y realizar las comparaciones usando el atributo numCuenta
		CuentaCorriente other = (CuentaCorriente) obj;
		if (numCuenta == null) {
			if (other.numCuenta != null) {
				return false;
			}{
				return false;
			}
		} else if (!numCuenta.equals(other.numCuenta)) {
			return false;
		}
		return true;
	}

    /**
     * Compara el parametro con este objeto
     * @param o El objeto a comparar
     * @return 0 si el objeto es igual,
     * un número positivo en caso de que el objeto comparado sea mayor
     * o un número negativo en caso de que el objeto comparado sea menor.
     */
	@Override
	public int compareTo(CuentaCorriente o) {
		// Llamada a compareTo de Integer para comparar el numCuenta
		return numCuenta.compareTo(o.getNumCuenta());
	}

}
