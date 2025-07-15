package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DTOCadastroPaciente dadosPaciente, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente();
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DTODetalhamentoPaciente(paciente));
    }

    @GetMapping
    public Page<DTOListagemPaciente> listarPacientes(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DTOListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizacaoPacientes(@RequestBody @Valid DTOAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DTODetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirPacientes (@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inativarPaciente(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPacientes (@PathVariable Long id) {
        var pacientes = repository.getReferenceById(id);
        return ResponseEntity.ok(new DTODetalhamentoPaciente(pacientes));
    }

}
