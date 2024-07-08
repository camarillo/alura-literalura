package mx.ivanaranda.literalura.principal;

import java.util.Scanner;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    public void menu(){
        var opcion = -1;
        var menu = """
                1 - Buscar libro por titulo.
                2 - Listar libros registrados.
                3 - Listar autores registrados.
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                .
                0 - Salir
                """;
        while(opcion != 0){
            System.out.println(menu);
            System.out.print("Selecciona una opcion: ");
            var opcionUsuario= teclado.nextLine();
            if (!esNumeroEntero(opcionUsuario) || Integer.parseInt(opcionUsuario) >5 || Integer.parseInt(opcionUsuario) < 0){
                    System.out.println("¡¡¡ Opción invalida !!! ");
            } else {
                opcion = Integer.parseInt(opcionUsuario);
            }
        }
        System.out.println(".:: A D I O S ::.");

    }

    public static boolean esNumeroEntero(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
