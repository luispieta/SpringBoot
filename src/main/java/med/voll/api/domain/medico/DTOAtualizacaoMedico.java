package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DTOEndereco;

//RECORD para realizar atualização, assim solicitando os parâmetros que precisam ser mudados
public record DTOAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DTOEndereco endereco) {


}
