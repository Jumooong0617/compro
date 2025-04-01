package com.smoshi.coffee;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CoffeeController {
    CoffeeService coffeeService;

    public CoffeeController() {
        coffeeService = new CoffeeService();
    }

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

    @GetMapping("/new")
    public String create(Model model) {
        List<String> roastLevels = List.of("Light", "Medium", "Dark");
        List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
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
                        @RequestParam String flavorNotes,
                        @RequestParam String brewMethod) {

        Coffee coffee = new Coffee(coffeeService.getLastId() + 1, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod);
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            List<String> roastLevels = List.of("Light", "Medium", "Dark");
            List<String> brewMethods = List.of("Drip", "Cold Brew", "Espresso", "French Press");
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("coffee", coffee);
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
                         @RequestParam String flavorNotes,
                         @RequestParam String brewMethod) {

        Coffee coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            coffee.setName(name);
            coffee.setType(type);
            coffee.setSize(size);
            coffee.setPrice(price);
            coffee.setRoastLevel(roastLevel);
            coffee.setOrigin(origin);
            coffee.setDecaf(isDecaf);
            coffee.setStock(stock);
            coffee.setFlavorNotes(flavorNotes);
            coffee.setBrewMethod(brewMethod);

            coffeeService.updateCoffee(id, coffee);
        }
        return "redirect:/";
    }
}