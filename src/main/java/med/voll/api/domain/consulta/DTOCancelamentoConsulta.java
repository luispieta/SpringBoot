package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DTOCancelamentoConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
