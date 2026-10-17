package br.com.senai.teste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.senai.teste.model.Livro;
import br.com.senai.teste.service.LivroService;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/livro")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrar(@Valid @RequestBody Livro livro) {
        Livro livroCadastrado = livroService.cadastrar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCadastrado);
    }

    @PostMapping ("/{id}/capa")
    public ResponseEntity<Livro> enviarCapa(@PathVariable Integer id, @RequestParam("arquivo") MultipartFile arquivo ){
        Livro livroAtualizado=livroService.salvarCapa(id, arquivo);
        return ResponseEntity.ok(livroAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listar() {
        List<Livro> livro = livroService.listar();
        return ResponseEntity.ok(livro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarporid(@PathVariable Integer id) {
        Optional<Livro> livro = livroService.buscarPorId(id);
        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Livro novosdados) {

        Optional<Livro> livroAtualizado = livroService.atualizar(id, novosdados);

        if (livroAtualizado.isPresent()) {
            return ResponseEntity.ok(livroAtualizado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {
        boolean excluido = livroService.excluir(id);
        if (excluido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
