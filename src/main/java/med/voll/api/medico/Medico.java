package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.endereco.Endereco;

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

    public Medico(DadosCadastroMedico dados) {

        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());

    }
}
