package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table (name = "medicos")        // Indica que a classe é uma entidade JPA.
@Entity (name = "Medico")        // Define o nome da tabela no banco de dados.
@Getter                          // Gera automaticamente os métodos getters.
@AllArgsConstructor              // Gera um construtor com todos os atributos.
@NoArgsConstructor               // Gera um construtor sem argumentos (obrigatório para o JPA).
@EqualsAndHashCode(of = "id")    // Gera equals e hashCode baseados no campo "id".
public class Medico {

    @Id //Será chave primária
    @GeneratedValue (strategy = GenerationType.IDENTITY) //Auto incremento da chave primária
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated (EnumType.STRING)
    private Especialidade especialidade;

    @Embedded  //Será incorporado diretamente na tabela medicos
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());

    }

    //Construtor para realizar a alteração das informações do médico
    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        //IF para não deixar null os dados
        if (dados.nome() != null) {
            this.nome = dados.nome();

        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();

        }
        if(dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());

        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
