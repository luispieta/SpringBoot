package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//O objetivo é para dizer para o spring para carregar esse serviço, podendo ser injetado no controller
@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repositoryConsulta;

    @Autowired
    private MedicoRepository repositoryMedico;

    @Autowired
    private PacienteRepository repositoryPaciente;

    @Autowired
    //O Spring vai procurar todas as classes que implementam essa inferface, injetando um por um nesse atributo
    private List<ValidadorAgendamentoDeConsulta> validadores;

    //A classe Service executa as regras de negócio e validação da aplicação
    public DTODetalhamentoConsulta agendar(DTOAgendamentoConsulta dados) {
        //O existsById() é um metodo boolean para dizer se tem o ID ou não
        if(!repositoryPaciente.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado não existe");

        }

        //Não é obrigatório o médico
        if(dados.idMedico() != null && !repositoryMedico.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do médico informado não existe");
        }

        //Irá percorrer um por um, dessa forma irá injetar todos os validadores
        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);

        if(medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");

        }

        var paciente = repositoryPaciente.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        repositoryConsulta.save(consulta);

        return new DTODetalhamentoConsulta(consulta);

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

    public void cancelar(DTOCancelamentoConsulta dados) {
        if(!repositoryConsulta.existsById(dados.idConsulta())) {
            throw new ValidacaoException("ID da consulta informado não existe");

        }
        var consulta = repositoryConsulta.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());

    }
}
