package br.com.senai.teste.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.senai.teste.model.Livro;
import br.com.senai.teste.repository.LivroRepository;

@Service 
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }
    public Livro cadastrar(Livro livro) {
        return livroRepository.save(livro);
    }
    public List<Livro> listar() {
        return livroRepository.findAll();
    }
    public Optional<Livro> buscarPorId(Integer id) {
        return livroRepository.findById(id);
    }

    public Optional<Livro> atualizar(Integer id, Livro novosdados) {
        Optional<Livro> livroEncontrado = livroRepository.findById(id);
        if (livroEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Livro livro = livroEncontrado.get();

        livro.setTitulo(novosdados.getTitulo());
        livro.setAutor(novosdados.getAutor());
        livro.setAnoPublicacao(novosdados.getAnoPublicacao());

        return Optional.of(livroRepository.save(livro));
    }

    public boolean excluir(Integer id){
        if(!livroRepository.existsById(id)){
            return false;
        }
        livroRepository.deleteById(id);
        return true;    
    }

    public Livro salvarCapa(Integer id, MultipartFile arquivo){
        Optional<Livro> livroencontrado=livroRepository.findById(id);
        if (livroencontrado.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Livro não encontrado.");
        }
        Livro livro = livroencontrado.get();

        String nomeArquivo=System.currentTimeMillis()+"-"+arquivo.getOriginalFilename();

        File pasta = new File("uploads/capa");
        
        if (!pasta.exists()){
            pasta.mkdirs();
        }

        File destino = new File(pasta,nomeArquivo).getAbsoluteFile();

        try{
            arquivo.transferTo(destino);
        } catch (IOException erro){
            erro.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Não foi possível salvar a capa.");
        }

        livro.setNomeArquivoCapa(nomeArquivo);
        return livroRepository.save(livro);

    }
}
