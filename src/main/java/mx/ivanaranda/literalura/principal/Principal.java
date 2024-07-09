package mx.ivanaranda.literalura.principal;

import mx.ivanaranda.literalura.model.Datos;
import mx.ivanaranda.literalura.model.DatosLibro;
import mx.ivanaranda.literalura.service.ConsumoAPI;
import mx.ivanaranda.literalura.service.ConvierteDatos;
import mx.ivanaranda.literalura.service.IConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private IConvierteDatos convierteDatos = new ConvierteDatos();

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    public void menu(){
        var opcion = -1;
        var menu = """
                
                =========== M E N U ===========
                1 - Buscar y guardar libro por titulo.
                2 - Listar libros registrados.
                3 - Listar autores registrados.
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                """;
        while(opcion != 0){
            System.out.print(menu);
            System.out.print("Selecciona una opcion: ");
            var opcionUsuario= teclado.nextLine();
            if (!esNumeroEntero(opcionUsuario) || Integer.parseInt(opcionUsuario) >5 || Integer.parseInt(opcionUsuario) < 0){
                    System.out.println("¡¡¡ Opción invalida !!! ");
            } else {
                opcion = Integer.parseInt(opcionUsuario);
                switch (opcion){
                    case 1:
                        this.buscarSeriePorNombreAPI();
                        break;
                    default:
                        System.out.println("¡¡¡ Opción invalida !!! ");
                        break;
                }
            }
        }
        System.out.println(".:: A D I O S ::.");

    }

    private void buscarSeriePorNombreAPI() {
        System.out.println(".:: Buscar Libro ::.");
        System.out.print("Que libro quieres buscar: ");
        var nombreLibroUsuario = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibroUsuario.replace(" ", "%20"));
        Datos datos = convierteDatos.obtenerDatosJson(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datos.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibroUsuario.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){

            System.out.println("---- LIBRO ENCONTRADO -----");
            System.out.println("Titulo: " + libroBuscado.get().titulo());
            System.out.println("Autor: " + libroBuscado.get().autores().get(0).nombre());
            System.out.println("Idioma: " + libroBuscado.get().idiomas().get(0));
            System.out.println("Descargas: " + libroBuscado.get().numeroDeDescargas());
            System.out.println("---------------------------");
        }
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
