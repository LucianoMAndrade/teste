package br.com.senai.teste.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
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
}
