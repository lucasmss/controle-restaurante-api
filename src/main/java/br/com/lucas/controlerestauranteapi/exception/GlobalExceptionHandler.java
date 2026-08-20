package br.com.lucas.controlerestauranteapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MesaIndisponivelException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String mesaIndisponivel(MesaIndisponivelException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(ConsumoFechadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String consumoFechado(ConsumoFechadoException exception){
        return exception.getMessage();
    }


}
