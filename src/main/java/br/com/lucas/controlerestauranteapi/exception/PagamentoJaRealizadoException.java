package br.com.lucas.controlerestauranteapi.exception;

public class PagamentoJaRealizadoException extends RuntimeException {
    public PagamentoJaRealizadoException(String message) {
        super(message);
    }
}
