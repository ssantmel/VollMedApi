package br.com.med.voll.api.controller;

import br.com.med.voll.api.domain.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    // No metodo cadastrar boas ptraticas é detalhar o que eu cadastrei
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
                                    UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        medicoRepository.save(medico);

    /* a uri tem que ser o endereço da minha api ou seja a url http ...* coloquei
    mais um parametro no metodo a clsse URI que sabe criar esse http
    parametros dinamicos ficam entre {} conchetes. .toURI cria o objeto uri
     */
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
                 /* metodo createc , eu passo pra ele uma URI que representa o endereço
       e o spring cria automaticamente o cabeçalho location baseado na uri que eu passar
        paramentro. o .body() qual info quero passar no corpo da resposta*/
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);

        return ResponseEntity.ok(page);


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
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity Detalhar(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
