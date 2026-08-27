package br.com.lucas.controlerestauranteapi.exception;

public class ConsumoNaoExisteException extends RuntimeException {
    public ConsumoNaoExisteException(String message) {
        super(message);
    }
}
