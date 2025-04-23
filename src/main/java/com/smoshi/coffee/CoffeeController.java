package com.smoshi.coffee;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    private final String[] types = {"Affogato", "Espresso", "Americano", "Latte", "Cappuccino", "Mocha", "Flat White", "Iced Coffee"};
    private final String[] sizes = {"Small", "Medium", "Large"};
    private final String[] roastLevels = {"Light", "Medium", "Dark"};
    private final String[] brewMethods = {"Drip", "French Press", "Espresso", "Filter"};

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("coffee", new Coffee());
        model.addAttribute("types", types);
        model.addAttribute("sizes", sizes);
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
    }

    @PostMapping("/save")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "add";
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
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "edit";
        }
        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
