package br.com.fiap.sprint3.patio;

import br.com.fiap.sprint3.moto.Moto;
import br.com.fiap.sprint3.moto.MotoDTO;
import br.com.fiap.sprint3.moto.MotoRepository;
import br.com.fiap.sprint3.moto.MotoService;
import br.com.fiap.sprint3.zona.TipoZona;
import br.com.fiap.sprint3.zona.Zona;
import br.com.fiap.sprint3.zona.ZonaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.fiap.sprint3.zona.TipoZona.A;
import static br.com.fiap.sprint3.zona.TipoZona.B;

@RestController
@RequestMapping("/patios")
@Slf4j
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private MotoService motoService;

    @GetMapping
    @Operation(summary = "Listar todos os pátios", tags = "Patio")
    public List<PatioDTO> listar() {
        log.info("Listando todos os pátios");
        return patioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pátio por id", tags = "Patio")
    public PatioDTO buscar(@PathVariable Long id) {
        log.info("Buscando pátio id {}", id);
        return getPatio(id).map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo pátio", tags = "Patio")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public PatioDTO criar(@RequestBody @Valid PatioDTO dto) {
        log.info("Criando pátio {}", dto.nome);
        Patio patio = new Patio();
        patio.setNome(dto.nome);
        patio.setQuantidadeVagas(dto.quantidadeVagas);
        patio.setMetragemZonaA(dto.metragemZonaA);
        patio.setMetragemZonaB(dto.metragemZonaB);
        return toDTO(patioRepository.save(patio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pátio", tags = "Patio")
    public PatioDTO atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO dto) {
        log.info("Atualizando pátio id {}", id);
        Patio patio = getPatio(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        patio.setNome(dto.nome);
        patio.setQuantidadeVagas(dto.quantidadeVagas);
        patio.setMetragemZonaA(dto.metragemZonaA);
        patio.setMetragemZonaB(dto.metragemZonaB);

        return toDTO(patioRepository.save(patio));
    }

    @GetMapping("/{patioId}/motos")
    @Operation(summary = "Listar motos de um pátio (com filtros)", tags = "Patio")
    public Page<MotoDTO> listarMotosDoPatio(@PathVariable Long patioId,
                                            @RequestParam(required = false) String modelo,
                                            @RequestParam(required = false) String placa,
                                            Pageable pageable) {
        String filtroModelo = (modelo != null && !modelo.isBlank()) ? modelo : "";
        String filtroPlaca  = (placa  != null && !placa.isBlank())  ? placa  : "";

        Page<Moto> page = motoRepository
                .findByZona_Patio_IdAndModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(
                        patioId, filtroModelo, filtroPlaca, pageable);

        return page.map(motoService::toDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir pátio", tags = "Patio")
    public void excluir(@PathVariable Long id) {
        log.info("Excluindo pátio id {}", id);
        Patio patio = getPatio(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
        patioRepository.delete(patio);
    }

    @GetMapping("/{id}/ocupacao")
    public ResponseEntity<OcupacaoDTO> getOcupacao(@PathVariable Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        Zona zonaA = zonaRepository.findByTipoZonaAndPatio(TipoZona.A, patio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona A não encontrada"));

        Zona zonaB = zonaRepository.findByTipoZonaAndPatio(TipoZona.B, patio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona B não encontrada"));

        int motosZonaA = motoRepository.countByPatioAndZona(patio, zonaA);
        int motosZonaB = motoRepository.countByPatioAndZona(patio, zonaB);
        int totalMotos = motosZonaA + motosZonaB;

        OcupacaoDTO ocupacao = new OcupacaoDTO(totalMotos, motosZonaA, motosZonaB, patio.getQuantidadeVagas());
        return ResponseEntity.ok(ocupacao);
    }



    private java.util.Optional<Patio> getPatio(Long id) {
        return patioRepository.findById(id);
    }

    private PatioDTO toDTO(Patio patio) {
        return new PatioDTO(
                patio.getId(),
                patio.getNome(),
                patio.getQuantidadeVagas(),
                patio.getMetragemZonaA(),
                patio.getMetragemZonaB()
        );
    }
}
