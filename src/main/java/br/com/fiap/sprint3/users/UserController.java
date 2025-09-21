package br.com.fiap.sprint3.users;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {

    @ModelAttribute("user")
    public User userModel() {
        return new User();
    }
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @GetMapping("/form")
    public String form(org.springframework.ui.Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/form")
    public String create(@Valid @ModelAttribute("user") User user,
                         BindingResult result,
                         org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "register"; // volta exibindo os erros
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        ra.addFlashAttribute("msg", "Cadastro realizado com sucesso!");
        return "redirect:/login?registered";
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid UserUpdateDTO dto) {
    User user = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
        user.setEmail(dto.getEmail());
    }

    if (dto.getNomeUser() != null && !dto.getNomeUser().isBlank()) {
         user.setNomeUser(dto.getNomeUser());
    }

    if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
        user.setPhone(dto.getPhone());
    }

    return repository.save(user);
}

}
