package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Produto;
import br.com.lucas.controlerestauranteapi.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto buscarProduto(Long id){
        return produtoRepository.findById(id).orElseThrow();
    }

    public Produto adicionarProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto){
        var produtoExistente = buscarProduto(id);
        produtoExistente.setCodigo(produto.getCodigo());
        produtoExistente.setName(produto.getName());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setAtivo(produto.isAtivo());
        return produtoRepository.save(produtoExistente);
    }

    public void excluirProduto(Long id){
        produtoRepository.deleteById(id);
    }
}
