package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.*;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.enums.StatusPedido;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.ItemPedidoRepository;
import br.com.lucas.controlerestauranteapi.repository.PedidoRepository;
import br.com.lucas.controlerestauranteapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTeste {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;


    @Test
    void deveAdicionarPedido() {

        Consumo consumo = new Consumo();

        when(consumoRepository.existsById(99L))
                .thenReturn(true);

        when(consumoRepository.findById(99L))
                .thenReturn(Optional.of(consumo));

        Produto produto = new Produto();

        produto.setAtivo(true);
        produto.setPreco(new BigDecimal("2.50"));
        produto.setName("Café Especial");
        produto.setCodigo(1);

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        Produto produtoInformado = mock(Produto.class);

        when(produtoInformado.getId())
                .thenReturn(1L);

        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setProduto(produtoInformado);
        itemPedido.setQuantidade(2);

        Pedido pedidoEntrada = new Pedido();
        pedidoEntrada.setItens(List.of(itemPedido));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(itemPedidoRepository.save(any(ItemPedido.class)))
                .thenAnswer(i -> i.getArgument(0));


        Pedido resultado = pedidoService.fazerPedido(99L, pedidoEntrada);


        assertNotNull(resultado);
        assertEquals(StatusConsumo.ABERTO,  consumo.getStatus());
        assertSame(consumo, resultado.getConsumo());
        assertNotNull(resultado.getDataPedido());

        assertEquals(1, resultado.getItens().size());


        ItemPedido itemCriado = resultado.getItens().get(0);

        assertSame(resultado, itemCriado.getPedido());
        assertSame(consumo, itemCriado.getConsumo());
        assertSame(produto, itemCriado.getProduto());

        assertEquals(2, itemCriado.getQuantidade());
        assertEquals(0, new BigDecimal("2.50").compareTo(itemCriado.getPrecoUnitario()));

        verify(consumoRepository).existsById(99L);
        verify(consumoRepository).findById(99L);
        verify(produtoRepository).findById(1L);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(itemPedidoRepository).save(any(ItemPedido.class));

    }
}
