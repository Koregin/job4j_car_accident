package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Stream;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        List<String> names = List.of("Petr Arsentev", "Ivan Petrov", "Tom Cruise", "Bruce Willis", "Linux Torwalds");
        model.addAttribute("names", names);
        return "index";
    }
}
