package mx.ivanaranda.literalura.service;

public interface IConvierteDatos {
    <T> T obtenerDatosJson(String json, Class<T> clase);
}
