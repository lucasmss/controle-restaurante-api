package br.com.lucas.controlerestauranteapi.service;

import br.com.lucas.controlerestauranteapi.entity.*;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import br.com.lucas.controlerestauranteapi.exception.MesaIndisponivelException;
import br.com.lucas.controlerestauranteapi.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsumoService{
    private final ConsumoRepository consumoRepository;
    private final MesaRepository mesaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public ConsumoService(ConsumoRepository consumoRepository, MesaRepository mesaRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.consumoRepository = consumoRepository;
        this.mesaRepository = mesaRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public boolean mesaEstaOcupada(Long mesaId){
        return consumoRepository
                .findByMesaIdAndStatus(mesaId, StatusConsumo.ABERTO)
                .isPresent();
    }

    public List<Mesa> listarMesasDisponiveis(){
       List<Mesa> mesas = mesaRepository.findAll();

        return mesas.stream()
                .filter(mesa -> !mesaEstaOcupada(mesa.getId()))
                .toList();

    }

    public Consumo buscarConsumo(Long id){
        return consumoRepository.findById(id).orElseThrow();
    }

    public Consumo iniciarConsumo(Long mesaId){
        if(mesaEstaOcupada(mesaId)){
            throw new MesaIndisponivelException("Mesa Indisponível");
        }

        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow();

        Consumo abrirConsumo = new Consumo();
        abrirConsumo.setMesa(mesa);
        abrirConsumo.setDataAbertura(LocalDateTime.now());
        abrirConsumo.setStatus(StatusConsumo.ABERTO);
        abrirConsumo.setTaxaServicoAceita(true);

        return consumoRepository.save(abrirConsumo);
    }

    public List<Pedido> buscarPedidosDoConsumo(Long consumoId){
        return pedidoRepository.findByConsumoId(consumoId);
    }

    public Produto buscarProdutoId(Long produtoId){
        return produtoRepository.findById(produtoId).orElseThrow();
    }

    public ItemPedido buscarItemPedidos(Long pedidoId){
        return itemPedidoRepository.findById(pedidoId).orElseThrow();
    }

    public Consumo fecharConsumo(Long mesaId) {
        Consumo consumo = consumoRepository
                .findByMesaIdAndStatus(mesaId, StatusConsumo.ABERTO)
                .orElseThrow();

        var pedidos = buscarPedidosDoConsumo(consumo.getId());

        for (Pedido pedido : pedidos) {
            for (ItemPedido item : pedido.getItens()) {
                System.out.println(item.getQuantidade());
                System.out.println(item.getPrecoUnitario());
            }
        }

        consumo.setDataFechamento(LocalDateTime.now());
        consumo.setStatus(StatusConsumo.FECHADO);

        return consumoRepository.save(consumo);
    }

    public void excluirConsumo(Long id){
        consumoRepository.deleteById(id);
    }
}
