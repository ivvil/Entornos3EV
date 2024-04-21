package main;

/**
 * Guarda información sobre el cliente
 */
public class Cliente implements Comparable<Cliente> {
	private String nombre;
	private String apellidos;
	private String dni_pas;

	/**
	 * Construye un nuevo objeto {@link Cliente}, probando que el {@code nombre}, {@code apellidos} y
	 * el {@code dni_pas} no sean nulos.
	 * @param nombre Nombre del cliente.
	 * @param apellidos Apellidos del cliente.
	 * @param dni_pas DNI del cliente.
	 * @throws ObjetoErroneo en caso de que algun campo sea {@code null}.
	 */
	public Cliente(String nombre, String apellidos, String dni_pas) throws ObjetoErroneo {

		setNombre(nombre);
		setApellidos(apellidos);
		setDni_pas(dni_pas);
		if (isNull2()) {		// Prueba si alguno de los campos son nulos
			throw new ObjetoErroneo();
		}
	}

	/**
	 * Devuelve un {@link String} conteniendo el nombre del {@link Cliente}.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del {@link Cliente} a el especificado.
	 * Combrueba que no sea nulo o que este vacio.
	 * @param nombre Nombre al que cambiar.
	 */
	public void setNombre(String nombre) {
		if (nombre != null && nombre.trim().length() > 0) { // Comprueba que el nombre sea una String valida
			this.nombre = nombre;
		}
	}

	/**
	 * Devuelve un {@link String} conteniendo los apellidos del {@link Cliente}.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Cambia los apellidos del {@link Cliente} a el especificado.
	 * Combrueba que no sea nulo o que este vacio.
	 * @param apellidos Apellidos al que cambiar.
	 */
	public void setApellidos(String apellidos) {
		if (apellidos != null && apellidos.trim().length() > 0) {
			this.apellidos = apellidos;
		}
	}

	/**
	 * Devuelve un {@link String} conteniendo el DNI del {@link Cliente}.
	 */
	public String getDni() {
		return dni_pas;
	}

	/**
     * Establece el valor del dni si este es válido.
     * @param dni El dni que probar y guardar.
     * @return {@code true} si el dni es válido, guardándolo en el objeto, {@code false} si no es válido.
     */
	public boolean setDni_pas(String dni) {
		final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";// Letras ordenadas de la última posición del dni
		final String NUMEROS = "0123456789";// Números
		int n = 0;// Número convertido a partir del número en String
		String numero = "";// Para tratar la parte numérica
		if (dni == null || dni.length() != 9) {// comprueba la longitud del dni
			return false;
		}
		dni = dni.toUpperCase();
		if (!dni.matches("[0-9XYZxyz][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE]")) {
			return false;
		}
		if (NUMEROS.contains(dni.substring(0, 1))) {// Creo el número para hacer la validación de la letra
			numero = dni.substring(0, 8);// Esto para el dni
		} else {// Esto para el NIE
			switch (dni.substring(0, 1)) {// asigno primera posición nuérica en función de XYZ
			case "X":
				numero = dni.substring(1, 8);
				break;
			case "Y":
				numero = "1" + dni.substring(1, 8);
				break;
			case "Z":
				numero = "2" + dni.substring(1, 8);
				break;
			}
		}
		n = Integer.valueOf(numero);// Convierto el String en int
		if (!LETRAS.substring(n % 23, n % 23 + 1).equals(dni.substring(8))) {// Valido la letra correcta
			return false;
		}
		this.dni_pas = dni;
		return true;// devuelvo el mensaje
	}

    /**
     * Prueba si el objeto es correcto comprobando él {@code nombre}, {@code apellidos} y él {@code dni_pas}.
     * @return {@code true} si alguno de los campos comprobados son null, {@code false} si todos no son null.
     */
	public boolean isNull2() {
		if (nombre == null || apellidos == null || dni_pas == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Cliente)) {
			return false;
		}
		Cliente other = (Cliente) obj;
		if (dni_pas == null) {
			if (other.dni_pas != null) {
				return false;
			}
		} else if (!dni_pas.equals(other.dni_pas)) {
			return false;
		}
		return true;
	}

    /**
     * Compara el objeto actual con otro, comprobando que sus {@code apellidos} y {@code nombre} sean iguales.
     * @param t El objeto a comparar.
     * @return La comparación lexicográfica de los apellidos si no son iguales, y si lo son, la de los nombres.
     */
	public int compareTo(Cliente t) {
		if (apellidos.compareToIgnoreCase(t.getApellidos()) == 0) {
			return nombre.compareToIgnoreCase(t.getNombre());
		}
		return apellidos.compareToIgnoreCase(t.getApellidos());
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni_pas;
	}


}
