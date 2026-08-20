package br.com.lucas.controlerestauranteapi.entity;

import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @Enumerated(EnumType.STRING)
    private StatusConsumo status = StatusConsumo.ABERTO;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    private Boolean taxaServicoAceita = true;

    @OneToMany(mappedBy = "consumo")
    private List<Pagamento> pagamentos;

    @JsonIgnore
    @OneToMany(mappedBy = "consumo")
    private List<Pedido> pedidos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public StatusConsumo getStatus() {
        return status;
    }

    public void setStatus(StatusConsumo status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Boolean getTaxaServicoAceita() {
        return taxaServicoAceita;
    }

    public void setTaxaServicoAceita(Boolean taxaServicoAceita) {
        this.taxaServicoAceita = taxaServicoAceita;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
