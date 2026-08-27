package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.exception.MesaJaExisteException;
import br.com.lucas.controlerestauranteapi.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {
    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository){
        this.mesaRepository = mesaRepository;
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

    public void excluirMesa(Long id){
        mesaRepository.deleteById(id);
    }
}
