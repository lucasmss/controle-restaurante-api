package br.com.lucas.controlerestauranteapi.exception;

public class MesaJaExisteException extends RuntimeException {
    public MesaJaExisteException(String message) {
        super(message);
    }
}
