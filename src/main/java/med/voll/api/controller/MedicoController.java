package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController             //Indica que a classe é um controller REST, com os métodos retornando dados (JSON por padrão).
@RequestMapping("medicos")  //Todos os endpoints dessa classe começarão com /medicos.
public class MedicoController {

    @Autowired //Injeta automaticamente a dependência de MedicoRepository, que é um repositório Spring Data JPA para acessar o banco de dados.
    private MedicoRepository repository;

    @PostMapping    //Esse metodo será acionado por requisições HTTP POST em /medicos.
    @Transactional  //Garante que a operação seja executada dentro de uma transação do banco.
    public void cadastrar(@RequestBody  // Diz que os dados virão no corpo da requisição (JSON).
                          @Valid        //Ativa a validação automática dos dados com base nas anotações em DadosCadastroMedico.
                          DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

}