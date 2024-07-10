package mx.ivanaranda.literalura.principal;

import mx.ivanaranda.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
}
