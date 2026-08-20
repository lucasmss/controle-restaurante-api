package br.com.lucas.controlerestauranteapi.repository;

import br.com.lucas.controlerestauranteapi.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
