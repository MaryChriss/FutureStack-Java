package br.com.fiap.sprint3.zona;

import br.com.fiap.sprint3.patio.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    Optional<Zona> findByTipoZonaAndPatio(TipoZona tipoZona, Patio patio);
}
