package br.com.med.voll.api.domain.medico;

import br.com.med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String Telefone,
                                      Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico){

        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(),
                medico.getEspecialidade(), medico.getEndereco());

    };
}
