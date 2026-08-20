package br.com.lucas.controlerestauranteapi.repository;

import br.com.lucas.controlerestauranteapi.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
