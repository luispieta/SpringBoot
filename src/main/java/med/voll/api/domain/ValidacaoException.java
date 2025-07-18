package med.voll.api.domain;

//Personalização de mensagem a ser exibida na regra de negócios
public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem);

    }
}
