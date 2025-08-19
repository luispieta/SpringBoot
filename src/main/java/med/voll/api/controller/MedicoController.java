package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController                             //Indica que a classe é um controller REST, com os métodos retornando dados (JSON por padrão).
@RequestMapping("medicos")                  //Todos os endpoints dessa classe começarão com /medicos.
@SecurityRequirement(name = "bearer-key")   //Todos o métodos são restritos
public class MedicoController {

    @Autowired //Injeta automaticamente a dependência de MedicoRepository, que é um repositório Spring Data JPA para acessar o banco de dados.
    private MedicoRepository repository;

    //Serve para salvar os dados do médico
    @PostMapping    //Esse metodo será acionado por requisições HTTP POST em /medicos.
    @Transactional  //Garante que a operação seja executada dentro de uma transação do banco.
    public ResponseEntity cadastrar(@RequestBody            // Diz que os dados virão no corpo da requisição (JSON).
                          @Valid                            //Ativa a validação automática dos dados com base nas anotações em DadosCadastroMedico.
                                        DTOCadastroMedico dados,        //DTO via JavaRecord para representar os dados que estão chegando na API
                                    UriComponentsBuilder uriBuilder   // É uma classe para atualizar a URL quando realizar deploy ou alterar o mesmo
                          ){
        //Realiza o novo cadastro de médico
        var medico = new Medico(dados);
        repository.save(medico);

        //Será o caminho de sempre, mesmo trocando a URL do site
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //Irá receber o HTTP de criação (201)
        //Quando cadastrado o médico, será já chamado para a visualização do mesmo
        return ResponseEntity.created(uri).body(new DTODetalhamentoMedico(medico));
    }

    //Serve para listar os dados de médicos ou médico
    @GetMapping
    //ResponseEntity serve para colocar o status dos dados
    public ResponseEntity<Page<DTOListagemMedico>> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DTOListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    /*
    -- Metodo para listar médico, com paginação
    @GetMapping
    public Page<DadosListagemMedico> listarMedicos(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    -- Metodo para listar médico, sem paginação
    @GetMapping
    public List<DadosListagemMedico> listarMedicos() {
        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    }

    */

    //Serve para alterar dados específicos de um médico
    @PutMapping
    @Transactional
    //Utilizado para determinar que só usuários ADMIN podem atualizar o médico
    //@Secured("ROLE_ADMIN")
    public ResponseEntity atualizarMedico(@RequestBody @Valid DTOAtualizacaoMedico dados) {
        //Atualiza e devolve os dados
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        //Irá receber o HTTP de ok (200)
        return ResponseEntity.ok(new DTODetalhamentoMedico(medico));
    }

    /*
    //Serve para excluir definitivamente o registro no banco de dados
    //Mapear o id inserido na URL
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(
            //Serve para informar o id na URL
            @PathVariable Long id) {
        repository.deleteById(id);

    }
    */

    //Serve para realizar a exclusão lógica no banco de dados
    //Mapear o id inserido na URL
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(
            //Serve para informar o id na URL
            @PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        //Irá receber o HTTP de noContent (204)
        return ResponseEntity.noContent().build();
    }

    //Serve para detalhar o cadastro de um médico
    @GetMapping("/{id}")
    //Utilizado para determinar que só usuários ADMIN podem detalhar o médico
    //@Secured("ROLE_ADMIN")
    public ResponseEntity detalhar(
                                //Serve para informar o id na URL
                                @PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DTODetalhamentoMedico(medico));
    }

}
