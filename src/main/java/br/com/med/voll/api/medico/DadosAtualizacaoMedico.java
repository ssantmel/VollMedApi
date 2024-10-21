package br.com.med.voll.api.medico;

import br.com.med.voll.api.endereco.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
        @NotNull
        long id,
        String telefone,
        String nome,
        Endereco endereco) {
}
