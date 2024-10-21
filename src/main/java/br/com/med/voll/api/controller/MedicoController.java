package br.com.med.voll.api.controller;

import br.com.med.voll.api.endereco.Endereco;
import br.com.med.voll.api.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(dados));

    }


    @GetMapping
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);



    /*
    metodo abaixo esta retornando uma lista de medicos porém sem paginação
    @GetMapping
    public List<DadosListagemMedicos> listar() {
        return medicoRepository.findAll().stream().map(DadosListagemMedicos::new).toList();*/
/*MedicoRepository recebe um medico como parametro e eu estou passando um
 DadosListagemMedicos então precisa fazer a conversão atraves do .stream.map
  .toList do final converte em uma lista*/
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico  = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico  = medicoRepository.getReferenceById(id);
        medico.excluir();

    }


}
