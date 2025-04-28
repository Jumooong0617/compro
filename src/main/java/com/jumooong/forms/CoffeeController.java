package com.jumooong.forms;

import jakarta.servlet.http.HttpSession;
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
    private CoffeeService coffeeService;

    private final List<String> roastLevels = List.of("Light", "Medium", "Dark");
    private final List<String> brewMethods = List.of("Espresso", "French Press", "Drip", "Cold Brew");
    private final List<String> flavorOptions = List.of("Nutty", "Fruity", "Spicy", "Floral");
    private final List<String> coffeeTypes = List.of("Arabica", "Robusta", "Liberica", "Excelsa", "Geisha", "Bourbon", "Typica");

    @GetMapping("/")
    public String index(@RequestParam(required = false) String search, Model model, HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id, HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String create(Model model, HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Coffee newCoffee = new Coffee();
        model.addAttribute("newCoffee", newCoffee);
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        model.addAttribute("flavorOptions", flavorOptions);
        model.addAttribute("coffeeTypes", coffeeTypes);
        return "create";
    }

    @PostMapping("/save")
    public String store(@ModelAttribute("newCoffee") @Valid Coffee coffee,
                        BindingResult bindingResult,
                        Model model,
                        HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("flavorOptions", flavorOptions);
            model.addAttribute("coffeeTypes", coffeeTypes);
            return "create";
        }
        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model, HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Coffee c = coffeeService.getCoffee(id);
        if (c != null) {
            model.addAttribute("coffee", c);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("flavorOptions", flavorOptions);
            model.addAttribute("coffeeTypes", coffeeTypes);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee,
                         BindingResult bindingResult,
                         Model model,
                         HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            model.addAttribute("flavorOptions", flavorOptions);
            model.addAttribute("coffeeTypes", coffeeTypes);
            return "edit";
        }

        coffeeService.updateCoffee(coffee.getId(), coffee);
        return "redirect:/";
    }
}
