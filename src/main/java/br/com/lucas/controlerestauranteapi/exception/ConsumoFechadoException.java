package br.com.lucas.controlerestauranteapi.exception;

public class ConsumoFechadoException extends RuntimeException {
    public ConsumoFechadoException(String message) {
        super(message);
    }
}
