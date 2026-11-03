package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.*;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.MesaIndisponivelException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.ItemPedidoRepository;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import br.com.lucas.controlerestauranteapi.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsumoServiceTeste {
    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ConsumoService consumoService;

    @Test
    void deveIniciarConsumo(){

        Mesa mesa = new Mesa();
        mesa.setNumero(2);

        when(mesaRepository.findById(1L))
                .thenReturn(Optional.of(mesa));

        when(consumoRepository.findByMesaIdAndStatus(1L, StatusConsumo.ABERTO))
                .thenReturn(Optional.empty());

        when(consumoRepository.save(any(Consumo.class)))
                .thenAnswer(i -> i.getArgument(0));

        Consumo novoConsumo = consumoService.iniciarConsumo(1L);

        assertNotNull(novoConsumo);
        assertSame(mesa, novoConsumo.getMesa());
        assertEquals(StatusConsumo.ABERTO, novoConsumo.getStatus());
        assertTrue(novoConsumo.getTaxaServicoAceita());
        assertNotNull(novoConsumo.getDataAbertura());

        verify(consumoRepository).save(any(Consumo.class));
    }

    @Test
    void deveLancarExcecaoQuandoMesaJaEstaOcupada(){

        Consumo consumoAberto = new Consumo();

        when(consumoRepository.findByMesaIdAndStatus(2L, StatusConsumo.ABERTO))
                .thenReturn(Optional.of(consumoAberto));

        assertThrows(MesaIndisponivelException.class, () -> consumoService.iniciarConsumo(2L));

        verify(consumoRepository).findByMesaIdAndStatus(2L, StatusConsumo.ABERTO);
        verify(mesaRepository, never()).findById(anyLong());
        verify(consumoRepository, never()).save(any(Consumo.class));

    }

    @Test
    void deveAtualizarValorConsumo() {

        // Arrange: preparar consumo existente
        Consumo consumoExistente = new Consumo();

        consumoExistente.setStatus(StatusConsumo.ABERTO);
        consumoExistente.setDataAbertura(LocalDateTime.now());
        consumoExistente.setMesa(new Mesa());
        consumoExistente.setTaxaServicoAceita(true);

        // Valores antigos, que serão recalculados
        consumoExistente.setValorConsumido(new BigDecimal("15.00"));
        consumoExistente.setValorTaxaServico(new BigDecimal("1.50"));
        consumoExistente.setValorTotal(new BigDecimal("16.50"));

        // Simular ID gerado pelo banco
        ReflectionTestUtils.setField(consumoExistente, "id", 7L);

        // Simular busca do consumo aberto pela mesa
        when(consumoRepository.findByMesaIdAndStatus(
                5L, StatusConsumo.ABERTO))
                .thenReturn(Optional.of(consumoExistente));

        // Arrange: preparar item do pedido
        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setPrecoUnitario(new BigDecimal("15.00"));
        itemPedido.setQuantidade(2);

        // Arrange: preparar pedido
        Pedido pedidoExistente = new Pedido();

        pedidoExistente.setConsumo(consumoExistente);
        pedidoExistente.setItens(List.of(itemPedido));

        // Simular busca dos pedidos pelo ID do consumo
        when(pedidoRepository.findByConsumoId(7L))
                .thenReturn(List.of(pedidoExistente));

        // Simular salvamento do consumo
        when(consumoRepository.save(any(Consumo.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act: executar atualização
        Consumo resultadoAtualizacao =
                consumoService.atualizarValorConsumo(5L);

        // Assert: verificar resultado
        assertNotNull(resultadoAtualizacao);

        assertSame(consumoExistente, resultadoAtualizacao);

        assertEquals(StatusConsumo.ABERTO, resultadoAtualizacao.getStatus());

        assertEquals(
                0,
                new BigDecimal("30.00")
                        .compareTo(resultadoAtualizacao.getValorConsumido())
        );

        assertEquals(
                0,
                new BigDecimal("3.00")
                        .compareTo(resultadoAtualizacao.getValorTaxaServico())
        );

        assertEquals(
                0,
                new BigDecimal("33.00")
                        .compareTo(resultadoAtualizacao.getValorTotal())
        );

        // Verify: verificar chamadas aos repositórios
        verify(consumoRepository)
                .findByMesaIdAndStatus(5L, StatusConsumo.ABERTO);

        verify(pedidoRepository)
                .findByConsumoId(7L);

        verify(consumoRepository)
                .save(consumoExistente);

    }
}
