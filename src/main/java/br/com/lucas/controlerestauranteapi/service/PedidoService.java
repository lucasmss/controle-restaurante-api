package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.entity.ItemPedido;
import br.com.lucas.controlerestauranteapi.entity.Pedido;
import br.com.lucas.controlerestauranteapi.entity.Produto;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.enums.StatusPedido;
import br.com.lucas.controlerestauranteapi.exception.ConsumoFechadoException;
import br.com.lucas.controlerestauranteapi.repository.ConsumoRepository;
import br.com.lucas.controlerestauranteapi.repository.ItemPedidoRepository;
import br.com.lucas.controlerestauranteapi.repository.PedidoRepository;
import br.com.lucas.controlerestauranteapi.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService{
    private final PedidoRepository pedidoRepository;
    private final ConsumoRepository consumoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ConsumoRepository consumoRepository, ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.consumoRepository = consumoRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Consumo buscarConsumoPorId(Long consumoId){
        return consumoRepository.findById(consumoId).orElseThrow();
    }

    public Produto buscarProdutoPorId(Long produtoId){
        return produtoRepository.findById(produtoId).orElseThrow();
    }

    @Transactional
    public Pedido fazerPedido(Long consumoId, Pedido pedido) {
        Consumo consumo = buscarConsumoPorId(consumoId);

        if (consumo.getStatus() != StatusConsumo.ABERTO) {
            throw new ConsumoFechadoException("Consumo Fechado");
        }

        Pedido fazerPedido = new Pedido();

        fazerPedido.setConsumo(consumo);
        fazerPedido.setStatus(StatusPedido.FEITO);
        fazerPedido.setDataPedido(LocalDateTime.now());

        fazerPedido = pedidoRepository.save(fazerPedido);

        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedido item : pedido.getItens()) {
            var produto = buscarProdutoPorId(item.getProduto().getId());

            ItemPedido itemPedido = new ItemPedido();

            itemPedido.setPedido(fazerPedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());

            itemPedidoRepository.save(itemPedido);

            itens.add(itemPedido);
        }

        fazerPedido.setItens(itens);
        return fazerPedido;
    }
}
