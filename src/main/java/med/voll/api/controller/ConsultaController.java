package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import med.voll.api.domain.consulta.DTODetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//IMPORTANTE: Os Controllers não serve para colocar a regra de negócio, serve para controlar o fluxo de execução

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DTOAgendamentoConsulta dados) {

        agenda.agendar(dados);

        return ResponseEntity.ok(new DTODetalhamentoConsulta(null, null, null, null));
    }

    //formato da hora 2025-07-16T08:30

}
