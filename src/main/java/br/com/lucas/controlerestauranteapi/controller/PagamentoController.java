package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.Pagamento;
import br.com.lucas.controlerestauranteapi.service.PagamentoService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/consumos/{consumoId}/pagamento")
    public Pagamento fazerPagamento(@PathVariable Long consumoId, @RequestBody Boolean taxaServico){
        return pagamentoService.fazerPagamento(consumoId, taxaServico);
    }
}
