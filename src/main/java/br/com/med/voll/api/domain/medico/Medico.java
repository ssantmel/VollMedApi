package br.com.med.voll.api.domain.medico;

import br.com.med.voll.api.domain.endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table (name ="medicos")
@Entity(name ="Medico")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private  Especialidade especialidade;
    @Embedded
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

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
        if(this.nome != null){
            this.nome = dados.nome();
        }
     if (dados.telefone() != null){
         this.telefone = dados.telefone();
     }

     if (dados.endereco() != null){
         this.endereco.atualizarInformacoes(dados.endereco());
     }

    }

    public void excluir() {
        this.ativo = false;
    }
}
