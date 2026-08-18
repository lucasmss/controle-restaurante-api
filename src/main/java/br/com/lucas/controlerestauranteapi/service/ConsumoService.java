package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumoService{
    private final ConsumoRepository consumoRepository;
    private final MesaRepository mesaRepository;

    public ConsumoService(ConsumoRepository consumoRepository, MesaRepository mesaRepository) {
        this.consumoRepository = consumoRepository;
        this.mesaRepository = mesaRepository;
    }

    public boolean mesaEstaOcupada(Long mesaId){
        return consumoRepository
                .findByMesaIdAndStatus(mesaId, StatusConsumo.ABERTO)
                .isPresent();
    }

    public List<Mesa> listarMesasDisponiveis(){
       List<Mesa> mesas = mesaRepository.findAll();

        return mesas.stream()
                .filter(mesa -> !mesaEstaOcupada(mesa.getId()))
                .toList();

    }

//    public List<Consumo> listarConsumosAbertos(){
//        return consumoRepository.findAll();
//    }

    public Consumo iniciarConsumo(Consumo consumo){
        return consumoRepository.save(consumo);
    }

    public void excluirConsumo(Long id){
        consumoRepository.deleteById(id);
    }
}
