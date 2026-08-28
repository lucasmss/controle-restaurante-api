package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.Pagamento;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.ConsumoNaoExisteException;
import br.com.lucas.controlerestauranteapi.exception.PagamentoJaRealizadoException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Pagamento fazerPagamento(Long consumoId, Boolean taxaServico){
        Consumo consumo = consumoRepository.findById(consumoId).orElseThrow(() ->
                new ConsumoNaoExisteException("Consumo Não Existe!"));

        if(consumo.getStatus().equals(StatusConsumo.FECHADO)){
            throw new PagamentoJaRealizadoException("Pagamento Realizado!");
        }

        Pagamento pagamento = new Pagamento();

        pagamento.setConsumo(consumo);
        pagamento.setDataPagamento(LocalDateTime.now());

        if (Boolean.TRUE.equals(taxaServico)) {
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
