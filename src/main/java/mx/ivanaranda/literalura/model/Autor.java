package mx.ivanaranda.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private Integer nacimimiento;
    private Integer muerte;
    @ManyToOne
    private Libro libro;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.nacimimiento = datosAutor.nacimiento();
        this.muerte = datosAutor.muerte();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimimiento() {
        return nacimimiento;
    }

    public void setNacimimiento(Integer nacimimiento) {
        this.nacimimiento = nacimimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacimimiento=" + nacimimiento +
                ", muerte=" + muerte +
                ", libro=" + libro +
                '}';
    }
}
