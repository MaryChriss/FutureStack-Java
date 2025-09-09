package br.com.fiap.sprint3.moto;


import br.com.fiap.sprint3.patio.Patio;
import br.com.fiap.sprint3.patio.PatioRepository;
import br.com.fiap.sprint3.zona.Zona;
import br.com.fiap.sprint3.zona.ZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;
    private final PatioRepository patioRepository;

    public MotoDTO createMoto(Long patioId, MotoDTO dto) {
        // 1) valida pátio
        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        Moto moto = new Moto();
        moto.setModelo(dto.modelo);
        moto.setPlaca(dto.placa);
        moto.setStatus(dto.status);

        // 2) se vier zonaId, precisa ser do MESMO pátio
        if (dto.zonaId != null) {
            Zona zona = zonaRepository.findByIdAndPatioId(dto.zonaId, patio.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Zona não encontrada neste pátio"));
            moto.setZona(zona);
        } else {
            moto.setZona(null); // permite criar sem zona (seu domínio já permite)
        }

        return toDTO(motoRepository.save(moto));
    }

    public MotoDTO updateMoto(Long id, MotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        mapearMoto(moto, dto);
        return toDTO(motoRepository.save(moto));
    }

    private Moto mapearMoto(Moto moto, MotoDTO dto) {
        moto.setModelo(dto.modelo);
        moto.setPlaca(dto.placa);
        moto.setStatus(dto.status);

        if (dto.zonaId != null) {
            Zona zona = zonaRepository.findById(dto.zonaId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona não encontrada"));
            moto.setZona(zona);
        } else {
            moto.setZona(null);
        }
        return moto;
    }

    public MotoDTO toDTO(Moto moto) {
        return new MotoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getZona() != null ? moto.getZona().getId() : null,
                moto.getStatus()
        );
    }
}
