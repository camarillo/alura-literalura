package mx.ivanaranda.literalura.principal;

import mx.ivanaranda.literalura.model.Autor;
import mx.ivanaranda.literalura.model.Datos;
import mx.ivanaranda.literalura.model.DatosLibro;
import mx.ivanaranda.literalura.model.Libro;
import mx.ivanaranda.literalura.service.ConsumoAPI;
import mx.ivanaranda.literalura.service.ConvierteDatos;
import mx.ivanaranda.literalura.service.IConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private IConvierteDatos convierteDatos = new ConvierteDatos();
    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

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
                    case 2:
                        this.buscarLibrosRegistrados();
                        break;
                    case 3:
                        this.buscarAutoresRegistrados();
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
            Optional<Libro> buscarLibro = libroRepository.findByTituloContainsIgnoreCase(libroBuscado.get().titulo());
            if (buscarLibro.isEmpty()){
                Libro libro = new Libro(libroBuscado.get());
                List<Autor> autores = libroBuscado.get().autores().stream()
                        .map(Autor::new)
                        .toList();
                libro.setAutores(autores);

                libroRepository.save(libro);
                System.out.println("---- LIBRO ENCONTRADO -----");
                System.out.println("Titulo: " + libroBuscado.get().titulo());
                System.out.println("Autor: " + libroBuscado.get().autores().get(0).nombre());
                System.out.println("Idioma: " + libroBuscado.get().idiomas().get(0));
                System.out.println("Descargas: " + libroBuscado.get().numeroDeDescargas());
                System.out.println("----- LIBRO GUARDADO ------");
            } else {
                System.out.println("---- El libro " + libroBuscado.get().titulo() + " ya fue guardado anteriormente");
            }
        } else {
            System.out.println("XXXXX LIBRO NO ENCONTRADO XXXXX");
        }
    }

    private void buscarLibrosRegistrados() {
        System.out.println(".:: Libros registrados ::.");
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros){
            System.out.println("---- LIBRO -----");
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutores().get(0).getNombre());
            System.out.println("Idioma: " + libro.getIdiomas());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("----------------");
        }
    }

    private void buscarAutoresRegistrados() {
        System.out.println(".:: Autores registrados ::.");
        List<String> autores = autorRepository.findDistinctByNombre();
        autores.stream().forEach(System.out::println);
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
