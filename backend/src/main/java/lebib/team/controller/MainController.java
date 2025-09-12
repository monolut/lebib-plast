package lebib.team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
public class MainController {

    @GetMapping("/")
    public List<String> main() {
        return List.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
    }
}
