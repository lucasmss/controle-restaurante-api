package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.exception.MesaJaExisteException;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MesaServiceTeste {

    @Mock
    private MesaRepository mesaRepository;

    @InjectMocks
    private MesaService mesaService;

    @Test
    void deveRetornarMesaCriada(){
        Mesa mesa = new Mesa();

        mesa.setNumero(1);

        when(mesaRepository.existsByNumero(1))
                .thenReturn(false);

        when(mesaService.novaMesa(mesa))
                .thenReturn(mesa);

        Mesa mesaCriada = mesaService.novaMesa(mesa);

        verify(mesaRepository).save(mesaCriada);

    }


    @Test
    void deveLancarExcecaoMesaNaoCriada(){
        Mesa mesa = new Mesa();

        mesa.setNumero(1);

        when(mesaRepository.existsByNumero(1))
                .thenReturn(true);

        assertThrows(MesaJaExisteException.class, () -> mesaService.novaMesa(mesa));

        verify(mesaRepository, never()).save(any(Mesa.class));

    }
}
