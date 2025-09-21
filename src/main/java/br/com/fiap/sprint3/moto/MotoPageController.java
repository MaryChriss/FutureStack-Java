package br.com.fiap.sprint3.moto;

import br.com.fiap.sprint3.moto.Moto;
import br.com.fiap.sprint3.moto.MotoDTO;
import br.com.fiap.sprint3.moto.MotoRepository;
import br.com.fiap.sprint3.patio.PatioRepository;
import br.com.fiap.sprint3.zona.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/motos/html")
public class MotoPageController {

    @Autowired
    MotoRepository motoRepository;

    @Autowired
    ZonaRepository zonaRepository;

    @Autowired
    PatioRepository patioRepository;

    @Autowired
    MotoService motoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("motos", motoRepository.findAll());
        return "moto-list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("moto", new MotoDTO());
        model.addAttribute("patios", patioRepository.findAll());
        model.addAttribute("zonas", List.of());
        return "moto-form";
    }


    @RequestMapping(value = "/form/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String editar(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        MotoDTO dto = motoService.toDTO(moto);
        if (moto.getPatio() != null) dto.patioId = moto.getPatio().getId();
        if (moto.getZona()  != null) dto.zonaId  = moto.getZona().getId();

        model.addAttribute("moto", dto);
        model.addAttribute("patios", patioRepository.findAll());
        model.addAttribute("zonas",
                dto.patioId != null ? zonaRepository.findByPatioId(dto.patioId) : List.of());
        return "moto-form";
    }

    @PostMapping("/form")
    public String salvar(@Valid @ModelAttribute("moto") MotoDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patios", patioRepository.findAll());
            model.addAttribute("zonas",
                    dto.patioId != null ? zonaRepository.findByPatioId(dto.patioId) : List.of());
            return "moto-form";
        }

        if (dto.id != null) {
            motoService.updateMoto(dto.id, dto);
        } else {
            if (dto.patioId == null) {
                result.rejectValue("patioId", "NotNull", "Selecione o pátio");
                model.addAttribute("patios", patioRepository.findAll());
                model.addAttribute("zonas", List.of());
                return "moto-form";
            }
            motoService.createMoto(dto.patioId, dto);
        }
        return "redirect:/motos/html";
    }

    @GetMapping("/delete/{id}")
    public String deletar(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos/html";
    }

}
