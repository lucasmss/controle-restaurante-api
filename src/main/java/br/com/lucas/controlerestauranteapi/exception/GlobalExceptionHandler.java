package br.com.lucas.controlerestauranteapi.exception;

import jakarta.servlet.annotation.HandlesTypes;
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

    @ExceptionHandler(MesaJaExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String mesaJaExiste(MesaJaExisteException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(ConsumoNaoExisteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String consumoNaoExiste(ConsumoNaoExisteException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(MesaSemConsumoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String mesaSemConsumo(MesaSemConsumoException exception){
        return exception.getMessage();
    }


}
