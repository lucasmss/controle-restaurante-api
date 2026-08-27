package br.com.lucas.controlerestauranteapi.exception;

public class MesaSemConsumoException extends RuntimeException {
    public MesaSemConsumoException(String message) {
        super(message);
    }
}
