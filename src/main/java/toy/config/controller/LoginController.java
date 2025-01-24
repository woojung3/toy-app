package toy.config.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.config.GlobalSecurityConfig.SecurityService;
import toy.config.service.LoginService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SecurityService securityService;

    @GetMapping("/login")
    public String login(Model model) throws IOException {
        return "login";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) throws IOException {

        try {
            loginService.save(securityService.createUser(username, password));
            return "login";
        } catch (Exception e) {
        } finally {
        }

        return "redirect:/login?duplicate";
    }
}
