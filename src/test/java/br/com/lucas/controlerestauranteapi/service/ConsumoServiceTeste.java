package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.MesaIndisponivelException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
}
