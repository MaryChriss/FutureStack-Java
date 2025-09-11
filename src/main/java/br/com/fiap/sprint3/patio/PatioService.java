package br.com.fiap.sprint3.patio;

import br.com.fiap.sprint3.moto.MotoRepository;
import br.com.fiap.sprint3.zona.TipoZona;
import br.com.fiap.sprint3.zona.Zona;
import br.com.fiap.sprint3.zona.ZonaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PatioService {

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    public PatioDTO createPatio(PatioDTO patioDTO) {
        Patio patio = mapearPatio(new Patio(), patioDTO);
        patio = patioRepository.save(patio);

        criarZonaSeNaoExiste(patio, TipoZona.A, "Zona A", patioDTO.metragemZonaA);
        criarZonaSeNaoExiste(patio, TipoZona.B, "Zona B", patioDTO.metragemZonaB);

        return toDTO(patio);

    }

    @Transactional
    public PatioDTO updatePatio(Long id, PatioDTO patioDTO) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "patio não encontrado"));

        mapearPatio(patio, patioDTO);
        patioRepository.save(patio);

        if (patioDTO.metragemZonaA != null) {
            criarOuAtualizarZona(patio, TipoZona.A, "Zona A", patioDTO.metragemZonaA);
        }
        if (patioDTO.metragemZonaB != null) {
            criarOuAtualizarZona(patio, TipoZona.B, "Zona B", patioDTO.metragemZonaB);
        }

        return toDTO(patio);
    }

    private void criarZonaSeNaoExiste(Patio patio, TipoZona tipo, String nome, Double metragem) {
        if (!zonaRepository.existsByPatioIdAndTipoZona(patio.getId(), tipo)) {
            Zona z = new Zona();
            z.setNome(nome);
            z.setTipoZona(tipo);
            z.setMetragem(metragem);
            z.setPatio(patio);
            zonaRepository.save(z);
        }
    }

    private void criarOuAtualizarZona(Patio patio, TipoZona tipo, String nome, Double metragem) {
        Zona z = zonaRepository.findByTipoZonaAndPatio(tipo, patio)
                .orElseGet(() -> {
                    Zona nova = new Zona();
                    nova.setPatio(patio);
                    nova.setTipoZona(tipo);
                    nova.setNome(nome);
                    return nova;
                });
        z.setMetragem(metragem);
        zonaRepository.save(z);
    }

    public PatioDTO toDTO(Patio patio) {
        return new PatioDTO(
                patio.getId(),
                patio.getNome(),
                patio.getQuantidadeVagas(),
                patio.getMetragemZonaA(),
                patio.getMetragemZonaB()
        );
    }

    private Patio mapearPatio(Patio patio, PatioDTO dto) {
        patio.setNome(dto.nome);
        patio.setQuantidadeVagas(dto.quantidadeVagas);
        patio.setMetragemZonaA(dto.metragemZonaA);
        patio.setMetragemZonaB(dto.metragemZonaB);
        return patio;
    }

}
