package br.com.senai.teste.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.teste.Service.EmprestimoService;
import br.com.senai.teste.dto.EmprestimoRequest;
import br.com.senai.teste.model.Emprestimo;

@RestController 
@RequestMapping ("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;
    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(emprestimo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping 
    public ResponseEntity<List<Emprestimo>> listar(){
        List<Emprestimo> emprestimos = emprestimoService.listar();
        return ResponseEntity.ok(emprestimos);
    }

    @PatchMapping("/{id}/devolucao")
    public ResponseEntity<Emprestimo> devolver(@PathVariable Integer id) {
        Optional<Emprestimo> emprestimo = emprestimoService.devolver(id);
        if (emprestimo.isPresent()) {
            return ResponseEntity.ok(emprestimo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping 
    public ResponseEntity<Emprestimo> cadastrar(@RequestBody EmprestimoRequest dados){
        Integer alunoId = dados.getAlunoId();
        Integer livroId = dados.getLivroId();
        if (alunoId == null || livroId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Emprestimo> emprestimo = emprestimoService.cadastrar(alunoId, livroId);
        
        if(emprestimo.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo.get());
    }

    
}
