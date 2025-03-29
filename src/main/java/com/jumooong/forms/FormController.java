package com.jumooong.forms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FormController {
    CoffeeService coffeeService;

    public FormController() {
        coffeeService = new CoffeeService();
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("coffees", coffeeService.getCoffees());
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String create(Model model) {
        List<String> roastLevels = List.of("Light", "Medium", "Dark");
        List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "create";
    }

    @PostMapping("/save")
    public String store(@RequestParam String name,
                        @RequestParam String type,
                        @RequestParam String size,
                        @RequestParam double price,
                        @RequestParam String roastLevel,
                        @RequestParam String origin,
                        @RequestParam boolean isDecaf,
                        @RequestParam int stock,
                        @RequestParam List<String> flavorNotes,
                        @RequestParam String brewMethod) {

        Coffee c = new Coffee(coffeeService.getLastId() + 1, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(c);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            List<String> roastLevels = List.of("Light", "Medium", "Dark");
            List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("coffee", c);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam List<String> flavorNotes,
                         @RequestParam String brewMethod) {

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            c.setName(name);
            c.setType(type);
            c.setSize(size);
            c.setPrice(price);
            c.setRoastLevel(roastLevel);
            c.setOrigin(origin);
            c.setDecaf(isDecaf);
            c.setStock(stock);
            c.setFlavorNotes(flavorNotes);
            c.setBrewMethod(brewMethod);

            coffeeService.updateCoffee(id, c);
        }
        return "redirect:/";
    }
}