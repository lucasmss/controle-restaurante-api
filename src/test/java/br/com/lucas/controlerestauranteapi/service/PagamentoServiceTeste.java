package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Pagamento;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTeste {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ConsumoRepository consumoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void deveRealizarTesteComTaxaDeServico(){

        Consumo consumo = new Consumo();

        consumo.setStatus(StatusConsumo.ABERTO);
        consumo.setValorConsumido(new BigDecimal("12.40"));
        consumo.setValorTotal(new BigDecimal("13.64"));

        when(consumoRepository.findById(1L))
                .thenReturn(Optional.of(consumo));

        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento pagamento = pagamentoService.fazerPagamento(1L, true);

        assertNotNull(pagamento);

        assertEquals(0, new BigDecimal("13.64").compareTo(pagamento.getValor()));

        assertEquals(StatusConsumo.FECHADO, consumo.getStatus());

        assertNotNull(pagamento.getDataPagamento());

        assertNotNull(consumo.getDataFechamento());

        verify(consumoRepository).save(consumo);

        verify(pagamentoRepository).save(any(Pagamento.class));
    }

    @Test
    void deveRealizarTesteSemTaxaDeServico(){
        Consumo consumo = new Consumo();

        consumo.setStatus(StatusConsumo.ABERTO);
        consumo.setValorConsumido(new BigDecimal("12.40"));
        consumo.setValorTotal(new BigDecimal("13.64"));

        when(consumoRepository.findById(1L))
                .thenReturn(Optional.of(consumo));

        when(pagamentoRepository.save(any(Pagamento.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pagamento pagamento = pagamentoService.fazerPagamento(1L, false);

        assertNotNull(pagamento);

        assertEquals(0, new BigDecimal("12.40").compareTo(pagamento.getValor()));

        assertEquals(StatusConsumo.FECHADO, consumo.getStatus());

        assertNotNull(pagamento.getDataPagamento());

        assertNotNull(consumo.getDataFechamento());

        verify(consumoRepository).save(consumo);

        verify(pagamentoRepository).save(any(Pagamento.class));
    }
}
