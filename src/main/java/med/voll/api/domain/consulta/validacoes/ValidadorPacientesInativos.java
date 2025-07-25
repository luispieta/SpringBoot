package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacientesInativos implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository repository;

    public void validar(DTOAgendamentoConsulta dados) {
        var pacienteAtivo = repository.findAtivoById(dados.idPaciente());

        if(!pacienteAtivo) {
            throw new ValidacaoException("O paciente está inativo");
        }

    }

}
