package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Anotação que indica que a class irá tratar de erros
@RestControllerAdvice
public class TratadorDeErros {

    //Toda vez que um controller e dar erro, irá ser chamado esse metodo
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        //Irá retornar o HTTP Not Found (404)
        return ResponseEntity.notFound().build();
    }

    //Toda vez que um controller e dar erro, irá ser chamado esse metodo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {

        //Irá listar a mensagem de erros
        var erros = ex.getFieldErrors();
        //Irá retornar o HTTP Bad Request (400)
        return ResponseEntity.badRequest().body(erros);
    }

}