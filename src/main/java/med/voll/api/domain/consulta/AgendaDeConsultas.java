package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//O objetivo é para dizer para o spring para carregar esse serviço, podendo ser injetado no controller
@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repositoryConsulta;

    @Autowired
    private MedicoRepository repositoryMedico;

    @Autowired
    private PacienteRepository repositoryPaciente;

    //A classe Service executa as regras de negócio e validação da aplicação
    public void agendar(DTOAgendamentoConsulta dados) {
        //O existsById() é um metodo boolean para dizer se tem o ID ou não
        if(!repositoryPaciente.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado não existe");

        }

        //Não é obrigatório o médico
        if(dados.idMedico() != null && !repositoryMedico.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do médico informado não existe");
        }

        var medico = escolherMedico(dados);

        var paciente = repositoryPaciente.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data());
        repositoryConsulta.save(consulta);

    }

    private Medico escolherMedico(DTOAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return repositoryMedico.getReferenceById(dados.idMedico());

        }
        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");

        }
        return repositoryMedico.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }

}
