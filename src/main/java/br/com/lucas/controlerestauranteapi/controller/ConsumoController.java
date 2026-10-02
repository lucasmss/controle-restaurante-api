package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.ItemPedido;
import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.entity.Pedido;
import br.com.lucas.controlerestauranteapi.service.ConsumoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConsumoController {
    private final ConsumoService consumoService;

    public ConsumoController(ConsumoService consumoService) {
        this.consumoService = consumoService;
    }

    @GetMapping("/mesas/{mesaId}/consumo")
    public Consumo buscarConsumoPorMesaId(@PathVariable Long mesaId){return consumoService.buscarConsumoPorMesaId(mesaId);}

    @GetMapping("/mesas/disponiveis")
    public List<Mesa> listarMesasDisponiveis(){
        return consumoService.listarMesasDisponiveis();
    }

    @GetMapping("/mesas/{consumoId}/consumos")
    public Consumo buscarConsumoId(@PathVariable Long consumoId){
        return consumoService.buscarConsumo(consumoId);
    }

    @GetMapping("/mesas/consumos")
    public List<Consumo> listarConsumos(){
        return consumoService.listarConsumos();
    }

    @GetMapping("/mesas/{consumoId}/pedidos")
    public List<Pedido> buscarPedidosDoConsumo(@PathVariable Long consumoId){
        return consumoService.buscarPedidosDoConsumo(consumoId);
    }

    @PostMapping("/mesas/{id}/consumos")
    public Consumo adicionarConsumo(@PathVariable Long id){
        return consumoService.iniciarConsumo(id);
    }

    @PutMapping("/mesas/{mesaId}/consumo")
    public Consumo atualizarValorConsumo(@PathVariable Long mesaId){
        return consumoService.atualizarValorConsumo(mesaId);
    }

    @DeleteMapping("/consumos/{id}")
    public void excluirConsumo(@PathVariable Long id){
        consumoService.excluirConsumo(id);
    }
}
