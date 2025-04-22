package com.smoshi.coffee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("coffee", new Coffee());
        return "add";
    }

    @PostMapping("/save")
    public String store(@Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("coffee", coffee);
            return "add";  // Show the form again with validation errors
        }
        coffee.setId(coffeeService.getLastId() + 1);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("coffee", coffee);
            return "edit";  // Show the form again with validation errors
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
