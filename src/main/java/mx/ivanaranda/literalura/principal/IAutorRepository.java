package mx.ivanaranda.literalura.principal;

import mx.ivanaranda.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT DISTINCT a.nombre FROM Autor a")
    List<String> findDistinctByNombre();

}
