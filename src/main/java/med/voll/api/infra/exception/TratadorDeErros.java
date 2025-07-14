package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidadecao::new).toList());
    }

    //Serve para deixar mais específico e simplificado
    private record DadosErroValidadecao(String campo, String mensagem) {
        public DadosErroValidadecao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());

        }

    }

}

/*
Uma das maneiras de personalizar as mensagens de erro é adicionar o atributo message nas próprias anotações de validação:
Personalização das mensagens de erros

public record DadosCadastroMedico(
    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato do email é inválido")
    String email,

    @NotBlank(message = "Telefone é obrigatório")
    String telefone,

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM é inválido")
    String crm,

    @NotNull(message = "Especialidade é obrigatória")
    Especialidade especialidade,

    @NotNull(message = "Dados do endereço são obrigatórios")
    @Valid DadosEndereco endereco) {}

Outra maneira é isolar as mensagens em um arquivo de propriedades, que deve possuir o nome ValidationMessages.properties e ser criado no diretório src/main/resources:
    Em application.properties
    nome.obrigatorio=Nome é obrigatório
email.obrigatorio=Email é obrigatório
email.invalido=Formato do email é inválido
telefone.obrigatorio=Telefone é obrigatório
crm.obrigatorio=CRM é obrigatório
crm.invalido=Formato do CRM é inválido
especialidade.obrigatoria=Especialidade é obrigatória
endereco.obrigatorio=Dados do endereço são obrigatórios


E, nas anotações, indicar a chave das propriedades pelo próprio atributo message, delimitando com os caracteres { e }:
public record DadosCadastroMedico(
    @NotBlank(message = "{nome.obrigatorio}")
    String nome,

    @NotBlank(message = "{email.obrigatorio}")
    @Email(message = "{email.invalido}")
    String email,

    @NotBlank(message = "{telefone.obrigatorio}")
    String telefone,

    @NotBlank(message = "{crm.obrigatorio}")
    @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
    String crm,

    @NotNull(message = "{especialidade.obrigatoria}")
    Especialidade especialidade,

    @NotNull(message = "{endereco.obrigatorio}")
    @Valid DadosEndereco endereco) {}
 */