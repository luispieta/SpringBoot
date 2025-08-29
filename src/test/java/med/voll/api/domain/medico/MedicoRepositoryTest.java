package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DTOEndereco;
import med.voll.api.domain.paciente.DTOCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest                                                                    //É utilizada para testar uma interface Repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //Serve para não substítuir as configurações do banco de dados, não sendo em memória
@ActiveProfiles("test")                                                         //Vai ler a validação do application_test.properties
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        //given ou arrange (DADO)
        //Utilizado para agendar a consulta na próxima segunda as 10h
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@gmail.com", "666666", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "000.000.000-00");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act (QUANDO)
        //Irá buscar no banco de dados e ver se tem consulta marcada
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert (ENTÃO)
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        //given ou arrange (DADO)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        //when ou act (QUANDO)
        var medico = cadastrarMedico("Medico", "medico@gmail.com", "666666", Especialidade.CARDIOLOGIA);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert (ENTÃO)
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele deveria estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario3() {

        //given ou arrange (DADO)
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        //when ou act (QUANDO)
        var medico = cadastrarMedico("Medico", "medico@gmail.com", "666666", Especialidade.CARDIOLOGIA);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert (ENTÃO)
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Deveria devolver o Medico, pois está ativo")
    void findAtivoById() {

        //given ou arrange (DADO)
        var medico = cadastrarMedico("Medico", "medico@gmail.com", "666666", Especialidade.CARDIOLOGIA);

        //when ou act (QUANDO)
        var ativo = medico.getAtivo();

        //then ou assert (ENTÃO)
        assertThat(ativo).isTrue();
    }

    //Será utilizado para reutilizar os dados
    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DTOCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DTOCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DTOCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DTOCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DTOEndereco dadosEndereco() {
        return new DTOEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}