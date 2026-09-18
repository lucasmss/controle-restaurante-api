package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.ItemPedido;
import br.com.lucas.controlerestauranteapi.entity.Pedido;
import br.com.lucas.controlerestauranteapi.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/consumos/{consumoId}/pedidos")
    public Pedido fazerPedido(@Valid @PathVariable Long consumoId, @RequestBody Pedido pedido){
        return pedidoService.fazerPedido(consumoId, pedido);
    }

    @PutMapping("/pedidos/pedido")
    public Pedido atualizarPedido(@RequestBody Pedido pedido){
        return pedidoService.atualizarPedido(pedido);
    }

    @DeleteMapping("/pedidos/{pedidoId}")
    public void removerItem(@PathVariable Long pedidoId){
        pedidoService.removerPedido(pedidoId);
    }
}
