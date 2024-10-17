package br.com.med.voll.api.medico;

import br.com.med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm,
                                  Especialidade especialidade, DadosEndereco endereco) {
}
