package br.com.fiap.sprint3.moto;

import br.com.fiap.sprint3.moto.Moto;
import br.com.fiap.sprint3.moto.MotoDTO;
import br.com.fiap.sprint3.moto.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@Controller
@RequestMapping("/motos/html")
public class MotoPageController {

    @Autowired
    MotoRepository motoRepository;

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
        return "moto-form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("moto", motoService.toDTO(moto));
        return "moto-form";
    }

    @PostMapping("/form/{patioId}")
    public String salvar(@PathVariable Long patioId,
                         @Valid @ModelAttribute("moto") MotoDTO dto,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", dto);
            return "moto-form";
        }

        if (dto.id != null) {
            motoService.updateMoto(dto.id, dto);
        } else {
            motoService.createMoto(patioId, dto);
        }

        return "redirect:/motos/html";
    }

    @GetMapping("/delete/{id}")
    public String deletar(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos/html";
    }

}
