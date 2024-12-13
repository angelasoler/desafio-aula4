package acc.br.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String login() {
        return "login"; // Maps to login.html
    }
}