package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "Campo logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "Campo bairro é obrigatório")
        String bairro,
        @NotBlank(message = "Campo CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "Formato inválido do CEP")
        String cep,
        @NotBlank(message = "Campo cidade é obrigatório")
        String cidade,
        @NotBlank(message = "Campo UF é obrigatório")
        String uf,
        String complemento,
        String numero) {
}
