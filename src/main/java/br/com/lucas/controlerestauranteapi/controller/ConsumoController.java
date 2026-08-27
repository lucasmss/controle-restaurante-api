package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.service.ConsumoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsumoController {
    private final ConsumoService consumoService;

    public ConsumoController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }

    @GetMapping("/mesas/disponiveis")
    public List<Mesa> listarMesasDisponiveis(){
        return consumoService.listarMesasDisponiveis();
    }

    @GetMapping("/mesas/{consumoId}/consumos")
    public Consumo buscarConsumoId(@PathVariable Long consumoId){
        return consumoService.buscarConsumo(consumoId);
    }

    @PostMapping("/mesas/{id}/consumos")
    public Consumo adicionarConsumo(@PathVariable Long id){
        return consumoService.iniciarConsumo(id);
    }

    @PutMapping("/mesas/{mesaId}/consumos")
    public Consumo fecharConsumo(@PathVariable Long mesaId){
        return consumoService.fecharConsumo(mesaId);
    }

    @DeleteMapping("/consumos/{id}")
    public void excluirConsumo(@PathVariable Long id){
        consumoService.excluirConsumo(id);
    }
}
