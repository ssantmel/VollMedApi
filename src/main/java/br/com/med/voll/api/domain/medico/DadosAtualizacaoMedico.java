package br.com.med.voll.api.domain.medico;

import br.com.med.voll.api.domain.endereco.Endereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
        @NotNull
        long id,
        String telefone,
        String nome,
        Endereco endereco) {
}
