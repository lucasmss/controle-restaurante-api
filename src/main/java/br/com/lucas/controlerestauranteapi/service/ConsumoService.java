package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.MesaIndisponivelException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Consumo buscarConsumo(Long id){
        return consumoRepository.findById(id).orElseThrow();
    }

    public Consumo iniciarConsumo(Long mesaId){
        if(mesaEstaOcupada(mesaId)){
            throw new MesaIndisponivelException("Mesa Indisponível");
        }

        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow();

        Consumo abrirConsumo = new Consumo();
        abrirConsumo.setMesa(mesa);
        abrirConsumo.setDataAbertura(LocalDateTime.now());
        abrirConsumo.setStatus(StatusConsumo.ABERTO);
        abrirConsumo.setTaxaServicoAceita(true);

        return consumoRepository.save(abrirConsumo);
    }

    public void excluirConsumo(Long id){
        consumoRepository.deleteById(id);
    }
}
