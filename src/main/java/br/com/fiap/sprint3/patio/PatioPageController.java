package br.com.fiap.sprint3.patio;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/patios/html")
@RequiredArgsConstructor
public class PatioPageController {

    private final PatioService patioService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lista", patioService.listAllDTO());
        return "patio-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new PatioDTO(null, "", 0, 0.0, 0.0));
        return "patio-form";
    }

    @PostMapping // criar
    public String salvar(@Valid @ModelAttribute("form") PatioDTO form,
                         BindingResult br, RedirectAttributes ra) {

        if (br.hasErrors()) return "patio-form";

        try {
            patioService.createPatio(form);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(v ->
                    br.addError(new FieldError("form", v.getPropertyPath().toString(), v.getMessage()))
            );
            return "patio-form";
        } catch (DataIntegrityViolationException e) {
            // 2) fallback caso o banco acuse UNIQUE mesmo assim
            br.rejectValue("nome", "patio.nome.unique", "Já existe um pátio com esse nome.");

            return "patio-form";
        }

        ra.addFlashAttribute("msgSucesso", "Pátio salvo.");
        return "redirect:/patios/html";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("form", patioService.getById(id));
        return "patio-form"; // <- corrigido (antes estava "patios-form")
    }

    @PostMapping("/{id}") // atualizar
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("form") PatioDTO form,
                            BindingResult br, RedirectAttributes ra) {


        if (br.hasErrors()) return "patio-form";

        try {
            patioService.updatePatio(id, form);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(v ->
                    br.addError(new FieldError("form", v.getPropertyPath().toString(), v.getMessage()))
            );
            return "patio-form";
        } catch (DataIntegrityViolationException e) {
            br.rejectValue("nome", "patio.nome.unique", "Já existe um pátio com esse nome.");
            return "patio-form";
        }

        ra.addFlashAttribute("msgSucesso", "Pátio atualizado.");
        return "redirect:/patios/html";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        patioService.delete(id);
        ra.addFlashAttribute("msgSucesso", "Pátio excluído.");
        return "redirect:/patios/html";
    }
}
