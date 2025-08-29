package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import med.voll.api.domain.consulta.DTODetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    //está simulando informações na classe
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DTOAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DTODetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver o código HTTP 400 quando informações estão inválidas.")

    // Simula que o usuário está logado
    @WithMockUser
    void agendarCenario1() throws Exception {

        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver o código HTTP 200 quando informações estão válidas.")

    // Simula que o usuário está logado
    @WithMockUser
    void agendarCenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamento = new DTODetalhamentoConsulta(null, 2L, 5L, data);
        //Irá devolver o DTODetalhamentoConsulta
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc.perform(post("/consultas")
                        //Diz para o sistema que vamos informar um JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJson.write(
                                new DTOAgendamentoConsulta(2L, 5L, data, especialidade)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}

// TESTE DE INTEGRAÇÃO: Vamos realizar de fato disparar uma requisição para a API, com isso, ela irá chamar o Service, Repository, Classes, e outros para a aplicação.

// TESTE DE UNIDADE: Irei utilizar o Mock para simular uma requisição e assim irei só realizar o teste no controller isolado, sem realizar a integração de Repository ou outras Classes.