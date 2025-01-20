package toy.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebAppController {
    private final String appMode;

    @Autowired
    public WebAppController(Environment environment) {
        appMode = environment.getProperty("app-mode");
    }

    @GetMapping("/")
    public String index(Model model) throws IOException {
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "@hendisantika34");
        model.addAttribute("projectname", "WebApp");
        model.addAttribute("mode", appMode);

        return "sample";
    }

    @GetMapping("/login")
    public String login(Model model) throws IOException {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) throws IOException {
        return "register";
    }

    @GetMapping("/sample")
    public String sample(Model model) {
        return "sample";
    }

    @GetMapping("/about")
    public String layout(Model model) {
        return "about";
    }

    @GetMapping("/htmx")
    public String htmx(Model model) {
        model.addAttribute("username", "default");

        return "htmx";
    }

    @GetMapping("/contact/1")
    public String contactGet(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);

        return "htmx :: get";
    }

    @GetMapping("/contact/1/edit")
    public String contactEdit(Model model) {
        return "htmx :: edit";
    }

    @GetMapping("/chart")
    public String chart(Model model) {
        List<Integer> votes = new ArrayList<Integer>();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            votes.add(rand.nextInt(101));
        }

        model.addAttribute("votes", votes);
        return "chart";
    }

    @GetMapping("/chart/update")
    public String chartUpdate(Model model) {
        List<Integer> votes = new ArrayList<Integer>();
        Random rand = new Random();

        for (int i = 0; i < 6; i++) {
            votes.add(rand.nextInt(101));
        }

        model.addAttribute("votes", votes);
        return "chart :: chart";
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    String echo() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("echo", "babo", "monchongi");
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
        pb.directory(new File("./"));
        Process p = pb.start();

        return new String(p.getInputStream().readAllBytes());
    }

    // For testing errorprone

    // public class ShortSet {
    // public static void main(String[] args) {
    // Set<Short> s = new HashSet<>();
    // for (short i = 0; i < 100; i++) {
    // s.add(i);
    // s.remove(i - 1);
    // }
    // System.out.println(s.size());
    // }
    // }
}
