package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    @Autowired
    public void validar(DTOAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        //Boolean para checar se é domingo ou não é domingo
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioDeAbertura = dataConsulta.getHour() < 7;
        var horarioDeFechamento = dataConsulta.getHour() > 18;

        if(domingo || horarioDeAbertura || horarioDeFechamento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");

        }
    }

}
