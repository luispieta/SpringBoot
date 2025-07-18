package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DTOAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        //Serve para informar um data e hora futura
        @Future
        //Serve para configurar o formato que irá no JSON, pois o formato padrão é yyyy-MM-ddTHH:mm
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        Especialidade especialidade) {

}
