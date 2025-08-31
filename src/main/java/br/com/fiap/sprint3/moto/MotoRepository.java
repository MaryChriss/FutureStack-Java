package br.com.fiap.sprint3.moto;

import br.com.fiap.sprint3.patio.Patio;
import br.com.fiap.sprint3.zona.TipoZona;
import br.com.fiap.sprint3.zona.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByModeloContainingIgnoreCase(String modelo, Pageable pageable);
    Page<Moto> findByPlacaContainingIgnoreCase(String placa, Pageable pageable);
    Page<Moto> findByModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(String modelo, String placa, Pageable pageable);
    Page<Moto> findAll(Pageable pageable);
    int countByPatioAndZona(Patio patio, Zona zona);
}