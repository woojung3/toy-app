package toy.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import toy.config.GlobalSecurityConfig.SecurityService;
import toy.config.service.LoginService;

@Slf4j
@Controller
public class WebAppController {
    private final String appMode;
    private final LoginService loginService;
    private final SecurityService securityService;

    public WebAppController(
            Environment environment,
            LoginService loginService,
            SecurityService securityService) {
        appMode = environment.getProperty("app-mode");
        this.loginService = loginService;
        this.securityService = securityService;
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

    @GetMapping("/sample")
    public String sample(Model model) {
        return "sample";
    }

    @GetMapping("/about")
    public String layout(Model model) {
        return "about";
    }

    @GetMapping("/zlint")
    public String zlint(Model model) {
        List<String[]> sample = new ArrayList<>();
        sample.add(new String[] { "item", "value" });

        String certificate = """
                -----BEGIN CERTIFICATE-----
                MIIFpDCCA4ygAwIBAgIDQrHDMA0GCSqGSIb3DQEBCwUAMGsxEzARBgoJkiaJk/Is
                ZAEZFgNjb20xFzAVBgoJkiaJk/IsZAEZFgd2d2dyb3VwMRMwEQYKCZImiZPyLGQB
                GRYDcGtpMSYwJAYDVQQDEx1Wb2xrc3dhZ2VuIEFHIFJvb3QgQ0EgQUFMMSBHMTAe
                Fw0xOTA4MjExMDAwMDBaFw0zNTA4MjExMDAwMDBaMGsxEzARBgoJkiaJk/IsZAEZ
                FgNjb20xFzAVBgoJkiaJk/IsZAEZFgd2d2dyb3VwMRMwEQYKCZImiZPyLGQBGRYD
                cGtpMSYwJAYDVQQDEx1Wb2xrc3dhZ2VuIEFHIFJvb3QgQ0EgQUFMMSBHMTCCAiAw
                DQYJKoZIhvcNAQEBBQADggINADCCAggCggIBAM5oWZsegUK6/NRRVKmHkGyR0huK
                /Dpsh8eIfp98KKHkluzdKJU3WWBDmawTjMmQbvaKukefX4rYFAYuvKM28/YWMrjN
                KrvVyupNFpT6wMATYuW4hEx0giSS+TJMmT3bfG/4Z0cuyePJJgK7TiTE9T7iWkFL
                BjWlfZncugCzDQLQ7ZmtU6f9NS1LM899/kGToqovtp0G1JK7eo1Y6yOWpkuWOdT+
                VL1kQDZ+tXnPJz41LdDa9sPYmIEEJwwCWNec3sT7WWC4SP7uEGCBK7dTCn6fHDBs
                1lAcOQmKMyGj9iM3NdsGL3mP6sPWtjMREGLDN2JBur2navTljdGHXEkToT5ZVgh6
                sr4rR4GeO929gT8yAgnhY472tQ3q2TSRM5wbXp/IOlNTU6/wwF7GnSVY8Q98oCvU
                8VVbgw9xEOD3XwbKJZDdqDaQPIEG7TSHWTYhswHoJ0b5xzurpAHPu61nvyWlc+D2
                v0qhj8swYKuPhJgqe5nU2/WXJ8oUr75gZFIYcrNhuvmptzI4Fy+GviylDnZIDJeQ
                cI4B+CpcOlLYOhr3H6ieYh0bxO8N0n2nTBTglT3YWAuN2XRmIsBppzIA+ng4B8p5
                0iy4GGkOqk+CpZGFe+5wRyv4ClCB3SxQYAXaKQU780HOxWAldnBRkbVK51yttXCe
                Gv+V+K1qRJ9oLfAZAgERo1MwUTAPBgNVHRMBAf8EBTADAQH/MBEGA1UdDgQKBAhE
                UnHozqITODAeBgNVHSAEFzAVMBMGESsGAQQBmQqOWxQBAQIBAQEAMAsGA1UdDwQE
                AwIBBjANBgkqhkiG9w0BAQsFAAOCAgEAfaKoVVV80T5Z+XSfuiIbc9sLE+3yKuIb
                9dW9Irs7e0oB9KbCitDOT9VNeo66iVrkC9b3BO5zsKA39PgUillJJ1ieO1+LquUW
                6IzSEB0zRYHa+MTWCbQPgVGfm19oEJA6rwWOoXQIG6AyUeXHTAhundg/dUTnvlc+
                sNLWHs+2lCOb3YXmfNtz2HU1h7tpRHvm1gnja7fAbM0895DLs/x9jl9wLscnW9zD
                M8kpfH5gjkt6Vpav+JQvfQya1xLETvcn4tnuhUi0AIhtpH0UYxhXs4KbL5Ao457E
                0lOWHJxjOFLib5GKy/CO7FIQUwovbF+sY6CcM6zdJeqGGnvhozQbWpw+lW7xoQRv
                r/lDcgIzNyMQKDKLytm3RT0YyBb6LJ7VIyVOQY8qgyBgWzMzwhC7aukiuoFi9dD3
                dRT6lUUVZBjuJx5uEpFkOh9EJpnK2iz0AZy/VpfPiYF/OHWWuzqIb5QGswXybxJJ
                aYm98CkUPX2Vdx9sH6SQaNZEJiUYG1ufSkADXK1/l56sIZZWCukHIO4TZo0D0GA1
                pD+jDD+nr9ODMmC7FhhlQpMHXjtFYpPbb3o7ywj/kxHaj2QFwlLInqJoXx6yuex/
                iH0UqGC0nVHPA2OXcDX5h3icJufsztxKRPACxnA71wiCm6quNID+9+Q0YH57o4Vi
                BttIE9F/Ko8=
                -----END CERTIFICATE-----
                    """;

        model.addAttribute("certificate", certificate);
        model.addAttribute("result", sample);
        return "zlint";
    }

    @PostMapping("/zlint")
    public String zlintUpdate(@RequestParam("pem") String pem, Model model) throws IOException {
        var result = zlint(pem);

        model.addAttribute("result", result);
        return "zlint :: resultTable";
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

    List<String[]> zlint(String pem) throws IOException {
        List<Process> processes = ProcessBuilder.startPipeline(List.of(
                new ProcessBuilder("echo", pem)
                        .inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE),
                new ProcessBuilder("zlint")
                        .redirectError(ProcessBuilder.Redirect.INHERIT)));

        String json;
        try (Scanner s = new Scanner(processes.get(processes.size() - 1).getInputStream())) {
            json = s.nextLine();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        List<String[]> result = new ArrayList<String[]>();
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();

            var key = field.getKey();
            var val = field.getValue().get("result").asText();

            if (!Arrays.asList("NA", "pass").contains(val)) {
                result.add(new String[] { key, val });
            }
        }

        return result;
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
