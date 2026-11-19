import { useState } from "react";
import "./CadastroLivros.css";

export default function CadastroLivros() {
  const [titulo, setTitulo] = useState("");
  const [autor, setAutor] = useState("");
  const [anoPublicacao, setAnoPublicacao] = useState("");
  const [livros, setLivros] = useState([]);
  const [idEdicao, setIdEdicao] = useState(null);

  function adicionar() {
    const novoLivro = {
      id: crypto.randomUUID(),
      titulo: titulo.trim(),
      autor: autor.trim(),
      anoPublicacao: Number(anoPublicacao),
    };
    setLivros((anterior) => [...anterior, novoLivro]);
  }

  function remover(id) {
    setLivros((anterior) => anterior.filter((livro) => livro.id !== id));
    if (id === idEdicao) {
      limpar();
    }
  }

  function editar(livro) {
    setTitulo(livro.titulo);
    setAutor(livro.autor);
    setAnoPublicacao(String(livro.anoPublicacao));
    setIdEdicao(livro.id);
  }

  function salvar(e) {
    e.preventDefault(); // Evita recarregar a página ao enviar o formulário

    if (
      titulo.trim() === "" ||
      autor.trim() === "" ||
      anoPublicacao === "" ||
      Number(anoPublicacao) < 0
    ) {
      return;
    }

    if (idEdicao === null) {
      adicionar();
    } else {
      setLivros((anterior) =>
        anterior.map((livro) =>
          livro.id === idEdicao
            ? {
                ...livro,
                titulo: titulo.trim(),
                autor: autor.trim(),
                anoPublicacao: Number(anoPublicacao),
              }
            : livro
        )
      );
    }

    limpar();
  }

  function limpar() {
    setTitulo("");
    setAutor("");
    setAnoPublicacao("");
    setIdEdicao(null);
  }

  return (
    <main className="container">
      <header className="header">
        <h1>Controle de Livros</h1>
        <span className="badge">
          {livros.length} {livros.length === 1 ? "livro" : "livros"}
        </span>
      </header>

      <form onSubmit={salvar}>
        <div className="form-grid">
          <input
            className="input-field"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            placeholder="Título do livro"
            required
          />
          <input
            className="input-field"
            value={autor}
            onChange={(e) => setAutor(e.target.value)}
            placeholder="Nome do autor"
            required
          />
          <input
            className="input-field"
            type="number"
            value={anoPublicacao}
            onChange={(e) => setAnoPublicacao(e.target.value)}
            placeholder="Ano"
            min="0"
            required
          />
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary">
            {idEdicao === null ? "Adicionar Livro" : "Salvar Alterações"}
          </button>

          {idEdicao !== null && (
            <button
              type="button"
              onClick={limpar}
              className="btn btn-secondary"
            >
              Cancelar Edição
            </button>
          )}
        </div>
      </form>

      {livros.length === 0 ? (
        <div className="empty-state">
          <p>Nenhum livro cadastrado ainda. Preencha os campos acima.</p>
        </div>
      ) : (
        <ul className="book-list">
          {livros.map((livro) => (
            <li
              key={livro.id}
              className={`book-card ${idEdicao === livro.id ? "editing" : ""}`}
            >
              <div className="book-info">
                <h3>{livro.titulo}</h3>
                <p>
                  {livro.autor} • Publicado em {livro.anoPublicacao}
                </p>
              </div>

              <div className="book-actions">
                <button
                  type="button"
                  onClick={() => editar(livro)}
                  className="btn btn-sm btn-edit"
                >
                  Editar
                </button>
                <button
                  type="button"
                  onClick={() => remover(livro.id)}
                  className="btn btn-sm btn-delete"
                >
                  Remover
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}
