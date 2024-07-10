package mx.ivanaranda.literalura.principal;

import mx.ivanaranda.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query("SELECT DISTINCT l.idiomas FROM Libro l")
    List<String> findDistinctByIdiomas();

    @Query("SELECT l FROM Libro l WHERE l.idiomas ILIKE %:idioma%")
    List<Libro> findByIdiomas(String idioma);

}
