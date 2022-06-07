package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;

    public AccidentControl(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentService.findAllTypes());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int accidentId, Model model) {
        model.addAttribute("accident", accidentService.findById(accidentId));
        model.addAttribute("types", accidentService.findAllTypes());
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
