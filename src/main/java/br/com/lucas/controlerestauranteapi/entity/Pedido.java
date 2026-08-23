package br.com.lucas.controlerestauranteapi.entity;

import br.com.lucas.controlerestauranteapi.enums.StatusPedido;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consumo_id")
    private Consumo consumo;

    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.FEITO;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Consumo getConsumo() {
        return consumo;
    }

    public void setConsumo(Consumo consumo) {
        this.consumo = consumo;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
