package acc.br.login.controller;

import acc.br.login.model.dto.UserDto;
import acc.br.login.model.entity.User;
import acc.br.login.service.UserValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserValidationService userValidationService;

    /**
     * Exibe a página de registro.
     * @param model Model
     * @return Nome da view de registro
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    /**
     * Processa o formulário de registro.
     * @param userDto Dados do usuário
     * @param bindingResult Resultados da validação
     * @param model Model
     * @return Redirecionamento ou exibição da página de registro com erros
     */
    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        // Verifica se há erros de validação
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Verifica se as senhas coincidem
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "As senhas não coincidem.");
            return "register";
        }

        try {
            User newUser = userValidationService.registerNewUser(
                userDto.getUsername(),
                userDto.getPassword()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Registro realizado com sucesso! Faça login agora.");
            return "redirect:/login"; // Redireciona para a página de login após o registro
        } catch (RuntimeException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }
    }
}
