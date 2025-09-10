package lebib.team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @GetMapping("/api")
    public List<String> main() {
        return List.of("1", "2", "3");
    }
}
