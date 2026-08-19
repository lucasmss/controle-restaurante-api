package br.com.lucas.controlerestauranteapi.exception;

public class MesaIndisponivelException extends RuntimeException {
    public MesaIndisponivelException(String message) {
        super(message);
    }
}
