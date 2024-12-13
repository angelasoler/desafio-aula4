package acc.br.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

/**
 * Controller for handling home and login routes.
 */
@Controller
public class HomeController {

    /**
     * Handle GET requests to /home.
     * @return View name for home page
     */
    @GetMapping("/home")
    public String home() {
        return "home"; // Maps to home.html
    }

    /**
     * Handle GET requests to /login.
     * @return View name for login page
     */
    @GetMapping("/login")
    public String login(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login"; // Maps to login.html
    }
}