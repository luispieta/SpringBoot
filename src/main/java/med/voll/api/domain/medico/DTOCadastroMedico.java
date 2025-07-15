package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DTOEndereco;

//RECORD para o cadastro
public record DTOCadastroMedico(
        @NotBlank(message = "Campo nome é obrigatório") //Obriga inserir caracters de String, assim não podendo ser vazio
        String nome,
        @NotBlank(message = "Campo email é obrigatório")
        @Email(message = "Formato do email é inválido")//Ira conter o formato de email com @, .com e outros
        String email,
        @NotBlank(message = "Campo telefone é obrigatório")
        String telefone,
        @NotBlank(message = "Campo CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "Quantidade incorreto do CRM") //Será obrigatório de 4 a 6 digitos
        String crm,
        @NotNull(message = "Campo especialidade é obrigatório")//O campo será obrigatório
        Especialidade especialidade,
        @NotNull
        @Valid // Ira validar também o outro objeto
        DTOEndereco endereco) {
}
