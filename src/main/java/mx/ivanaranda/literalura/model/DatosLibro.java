package mx.ivanaranda.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonProperty("title") String titulo
        , @JsonProperty("languages") List<String> idiomas
        , @JsonProperty("authors") List<DatosAutor> autores
        , @JsonProperty("download_count") Double numeroDeDescargas
) {
}
