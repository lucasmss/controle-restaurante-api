package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.MesaIndisponivelException;
import br.com.lucas.controlerestauranteapi.exception.MesaJaExisteException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {
    private final MesaRepository mesaRepository;
    private final ConsumoRepository consumoRepository;

    public MesaService(MesaRepository mesaRepository, ConsumoRepository consumoRepository){
        this.mesaRepository = mesaRepository;
        this.consumoRepository = consumoRepository;
    }

    public List<Mesa> listarTodas(){
        return mesaRepository.findAll();
    }

    public Mesa buscarMesaId(Long id){
        return mesaRepository.findById(id).orElseThrow();
    }

    public Mesa novaMesa(Mesa mesa){
        if(mesaRepository.existsByNumero(mesa.getNumero())){
            throw new MesaJaExisteException("Mesa Já Cadastrada, Favor escolher Outro Número!");
        }
        return mesaRepository.save(mesa);
    }

    public Mesa atualizarMesa(Long id,Mesa mesa){
        var mesaExistente = buscarMesaId(id);
        mesaExistente.setNumero(mesa.getNumero());
        return mesaRepository.save(mesaExistente);
    }

    public Mesa buscarMesaNumero(Integer numeroMesa){
        if (mesaRepository.existsByNumero(numeroMesa)) {
            return mesaRepository.findMesaByNumero(numeroMesa);
        }else {
            throw new MesaIndisponivelException("Número Incorreto ou Mesa Não Existe");
        }
    }

    public void excluirMesa(Long id){

      Optional<Consumo> mesaComConsumoAberto = consumoRepository.findByMesaIdAndStatus(id, StatusConsumo.ABERTO);

      if (mesaComConsumoAberto.isEmpty()) {
          mesaRepository.deleteById(id);
      }else {
          throw new MesaIndisponivelException("Mesa Com Consumo Aberto");
      }

    }
}
