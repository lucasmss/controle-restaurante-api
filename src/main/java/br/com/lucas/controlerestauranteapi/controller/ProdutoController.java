package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.Produto;
import br.com.lucas.controlerestauranteapi.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos")
    public List<Produto> listarProdutos(){
        return produtoService.listarProdutos();
    }

    @GetMapping("/produtos/{id}")
    public Produto buscarProdutoId(@PathVariable Long id){
        return produtoService.buscarProduto(id);
    }

    @PostMapping("/produtos")
    public Produto adicionarProduto(@RequestBody Produto produto){
        return produtoService.adicionarProduto(produto);
    }

    @PutMapping("/produtos/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto){
        return produtoService.atualizarProduto(id, produto);
    }

    @DeleteMapping("/produtos/{id}")
    public void excluirProduto(@PathVariable Long id){
        produtoService.excluirProduto(id);
    }
}
