package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Pagamento;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final ConsumoRepository consumoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, ConsumoRepository consumoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.consumoRepository = consumoRepository;
    }

    public Consumo buscarConsumoId(Long consumoId){
        return consumoRepository.findById(consumoId).orElseThrow();
    }

    public Pagamento fazerPagamento(Long consumoId, Boolean taxaServico){
        Consumo consumo = buscarConsumoId(consumoId);

        Pagamento pagamento = new Pagamento();

        pagamento.setConsumo(consumo);
        pagamento.setDataPagamento(LocalDateTime.now());

        if (taxaServico) {
            pagamento.setValor(consumo.getValorTotal());
        } else {
            pagamento.setValor(consumo.getValorConsumido());
            consumo.setTaxaServicoAceita(false);
        }

        consumo.setStatus(StatusConsumo.FECHADO);
        consumo.setDataFechamento(LocalDateTime.now());
        consumoRepository.save(consumo);

        return pagamentoRepository.save(pagamento);
    }
}
