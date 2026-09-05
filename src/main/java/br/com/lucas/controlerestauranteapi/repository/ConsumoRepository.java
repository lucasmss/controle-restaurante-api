package br.com.lucas.controlerestauranteapi.repository;

import br.com.lucas.controlerestauranteapi.entity.Consumo;
import br.com.lucas.controlerestauranteapi.enums.StatusConsumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsumoRepository extends JpaRepository<Consumo, Long> {

    Optional<Consumo> findByMesaIdAndStatus(Long mesaId, StatusConsumo statusConsumo);
    Optional<Consumo> findByIdAndStatus(Long consumoId, StatusConsumo statusConsumo);
    List<Consumo> findAllByStatus(StatusConsumo statusConsumo);

}
