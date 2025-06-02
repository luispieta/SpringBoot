package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController             //Indica que a classe é um controller REST, com os métodos retornando dados (JSON por padrão).
@RequestMapping("medicos")  //Todos os endpoints dessa classe começarão com /medicos.
public class MedicoController {

    @Autowired //Injeta automaticamente a dependência de MedicoRepository, que é um repositório Spring Data JPA para acessar o banco de dados.
    private MedicoRepository repository;

    //Serve para salvar os dados do médico
    @PostMapping    //Esse metodo será acionado por requisições HTTP POST em /medicos.
    @Transactional  //Garante que a operação seja executada dentro de uma transação do banco.
    public void cadastrar(@RequestBody              // Diz que os dados virão no corpo da requisição (JSON).
                          @Valid                    //Ativa a validação automática dos dados com base nas anotações em DadosCadastroMedico.
                          DadosCadastroMedico dados //DTO via JavaRecord para representar os dados que estão chegando na API
    ){
        repository.save(new Medico(dados));
    }

    //Serve para listar os dados de médicos ou médico
    @GetMapping
    public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
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
    public void atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

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
    public void excluir(
            //Serve para informar o id na URL
            @PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

    }

}
