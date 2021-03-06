package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentService accidentService;

    public AccidentControl(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(accidentService.findRulesForAccident(ids));
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int accidentId, Model model) {
        model.addAttribute("accident", accidentService.findAccidentById(accidentId));
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(accidentService.findRulesForAccident(ids));
        accidentService.update(accident);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        accidentService.delete(id);
        return "redirect:/";
    }
}
