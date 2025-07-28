package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNaoAgendarNoMesmoDia implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DTOAgendamentoConsulta dados) {

        var horarioAbertura = dados.data().withHour(7);
        var horarioFechamento = dados.data().withHour(18);
        var pacienteComOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), horarioAbertura, horarioFechamento);
        if(pacienteComOutraConsulta) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada para esse dia");
        }

    }

}
