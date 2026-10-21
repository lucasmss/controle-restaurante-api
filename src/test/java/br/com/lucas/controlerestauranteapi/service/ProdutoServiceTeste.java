package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Produto;
import br.com.lucas.controlerestauranteapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTeste {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveAtualizarProduto(){
        Produto produto = new Produto();

        produto.setAtivo(true);
        produto.setName("Teste Item");
        produto.setCodigo(1);
        produto.setPreco(new BigDecimal("2.50"));

        when(produtoRepository.save(produto))
                .thenReturn(produto);

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        assertNotNull(produto);

        assertEquals(produto, produtoService.atualizarProduto(1L, produto));

        verify(produtoRepository).save(produto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado(){
        Produto produto = new Produto();

        when(produtoRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, ()-> produtoService.atualizarProduto(99L, produto));

        verify(produtoRepository, never()).save(any(Produto.class));
    }
}
