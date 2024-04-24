package main;

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class Leer {
    /**
     * Lee un número entero introducido por la consola
     * @param texto Texto a mostrar en pantalla
     * @return Número Entero
     */
	public static int entero(String texto) {
		int valor;
		String var;
		while (true) {
			var = cadena(texto);
			try {
				valor = Integer.parseInt(var);
				return valor;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("Error de datos");
			} 
		}
	}

    /**
     * Leer una cadena de texto introducida por consola
     * @param texto a mostrar por consola
     * @return cadena de texto
     */
	public static String cadena(String texto) {
		Scanner teclado = new Scanner(System.in);
		String valor;
		System.out.println(texto);
		valor = teclado.nextLine();
		return valor;
	}

    /**
     * Lee una cadena de texto y comprueba que esta siga un patron usando una expresión regular
     * @param regex Expresión regular a seguir
     * @param texto Texto a mostrar por pantalla
     * @return Cadena
     */
	public static String cadena(String regex, String texto) {
		Scanner teclado = new Scanner(System.in);
		String valor;
		System.out.println(texto);
		valor = teclado.nextLine();
		try {
			while (!valor.matches(regex)) {
				System.out.println(texto);
				valor = teclado.nextLine();
			}
		} catch (PatternSyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(regex + " No es una expresión regular");
			return null;
		}
		return valor;
	}

    /**
     * Lee por consola el número real introducido
     * @param texto a mostrar en la consola
     * @return número real leido por consola
     */
	public static float real(String texto) {
		float valor;
		String var;
		while (true) {
			var = cadena(texto);
			try {
				valor = Float.parseFloat(var);
				return valor;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("Error de datos reales");
			} 
		}
		
	}

    /**
     * Lee por consola el double introducido
     * @param texto a mostrar en la consola
     * @return double leido por consola
     */
	public static double doble(String texto) {
		double valor;
		String var;
		while (true) {
			var = cadena(texto);
			try {
				valor = Double.parseDouble(var);
				return valor;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en los datos tecleados");
			} 
		}
	}

    /**
     * Dado un array de cadenas de texto lo lee y muestra por pantalla la serie de opciones y lee la opción intruducida
     * @param menu Vector de cadenas de texto conteniendo las opciones del menu.
     * @return Número entero con la opción seleccionada
     */
	public static int menu(String[] menu) {
		int opcion = -1;
		for (int i = 1; i < menu.length; i++) {
			System.out.println(i + ".-" + menu[i]);
		}
		System.out.println("0.-" + menu[0]);
		while (opcion < 0 || opcion >= menu.length) {
			opcion = entero("Elije opción");
		}
		return opcion;
	}
}
