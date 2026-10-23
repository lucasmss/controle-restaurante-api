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
        Produto produtoExistente = new Produto();

        produtoExistente.setAtivo(true);
        produtoExistente.setName("Teste Item");
        produtoExistente.setCodigo(1);
        produtoExistente.setPreco(new BigDecimal("2.50"));

        Produto produtoAtualizado = new Produto();

        produtoAtualizado.setAtivo(true);
        produtoAtualizado.setName("Café Especial");
        produtoAtualizado.setCodigo(1);
        produtoAtualizado.setPreco(new BigDecimal("3.50"));

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produtoExistente));

        when(produtoRepository.save(any(Produto.class)))
                .thenAnswer(i -> i.getArgument(0));

        Produto resultado = produtoService.atualizarProduto(1L, produtoAtualizado);

        assertNotNull(resultado);
        assertSame(produtoExistente, resultado);
        assertEquals("Café Especial", resultado.getName());

        verify(produtoRepository).findById(1L);
        verify(produtoRepository).save(produtoExistente);
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