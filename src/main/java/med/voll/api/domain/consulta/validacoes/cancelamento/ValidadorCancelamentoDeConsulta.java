package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.DTOCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DTOCancelamentoConsulta dados);
}
