package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarAgendamento(@RequestBody @Valid DTOCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
