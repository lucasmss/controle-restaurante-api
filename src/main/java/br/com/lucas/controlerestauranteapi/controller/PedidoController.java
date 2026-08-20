package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.ItemPedido;
import br.com.lucas.controlerestauranteapi.entity.Pedido;
import br.com.lucas.controlerestauranteapi.service.PedidoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/consumos/{consumoId}/pedidos")
    public Pedido fazerPedido(@PathVariable Long consumoId, @RequestBody Pedido pedido){
        return pedidoService.fazerPedido(consumoId, pedido);
    }
}
