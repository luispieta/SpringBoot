package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank //Obriga inserir caracters de String, assim não podendo ser vazio
        String nome,
        @NotBlank
        @Email //Ira conter o formato de email com @, .com e outros
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") //Será obrigatório de 4 a 6 digitos
        String crm,
        @NotNull //O campo será obrigatório
        Especialidade especialidade,
        @NotNull
        @Valid // Ira validar também o outro objeto
        DadosEndereco endereco) {
}
