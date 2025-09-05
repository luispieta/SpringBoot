package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.DTOEndereco;
import med.voll.api.domain.paciente.DTOCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Validação para verificar se o paciente está ativo quando cadastrado")
    void validarSePacienteEstaAtivo() {

        //given ou arrange (DADO)
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com", "000.000.000-00");

        //when ou act (QUANDO)
        var ativo = paciente.getAtivo();

        //then ou assert (ENTÃO)
        assertThat(ativo).isTrue();

    }

    //Será utilizado para reutilizar os dados
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
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