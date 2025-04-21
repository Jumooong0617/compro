package com.jumooong.forms;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
        model.addAttribute("brewMethods", List.of("Espresso", "French Press", "Drip", "Cold Brew"));
        model.addAttribute("newCoffee", new Coffee());
        return "create";
    }

    @PostMapping("/save")
    public String store(@ModelAttribute("newCoffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            model.addAttribute("brewMethods", List.of("Espresso", "French Press", "Drip", "Cold Brew"));
            return "create";
        }
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            model.addAttribute("brewMethods", List.of("Espresso", "French Press", "Drip", "Cold Brew"));
            model.addAttribute("coffee", c);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", List.of("Light", "Medium", "Dark"));
            model.addAttribute("brewMethods", List.of("Espresso", "French Press", "Drip", "Cold Brew"));
            return "edit";
        }

        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
