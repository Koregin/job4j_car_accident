package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id -> rules.add(accidentService.findRuleById(Integer.parseInt(id))));
        }
        accident.setRules(rules);
        accidentService.create(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String edit(@RequestParam("id") int accidentId, Model model) {
        model.addAttribute("accident", accidentService.findById(accidentId));
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "accident/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        if (ids != null) {
            Arrays.stream(ids).forEach(id -> rules.add(accidentService.findRuleById(Integer.parseInt(id))));
        }
        accident.setRules(rules);
        accidentService.update(accident);
        return "redirect:/";
    }
}