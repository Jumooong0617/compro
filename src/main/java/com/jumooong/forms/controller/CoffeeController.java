package com.jumooong.forms.controller;

import com.jumooong.forms.models.CoffeeUser;
import com.jumooong.forms.models.Coffee;
import com.jumooong.forms.services.CoffeeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;

    private final String[] types = {"Affogato", "Espresso", "Americano", "Latte", "Cappuccino", "Mocha", "Flat White", "Iced Coffee"};
    private final String[] sizes = {"Small", "Medium", "Large"};
    private final String[] roastLevels = {"Light", "Medium", "Dark"};
    private final String[] brewMethods = {"Drip", "French Press", "Espresso", "Filter"};

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model, HttpSession session) {
        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }

        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id, HttpSession session) {
        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }

        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }
        model.addAttribute("coffee", new Coffee());
        model.addAttribute("types", types);
        model.addAttribute("sizes", sizes);
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
    }

    @PostMapping("/save")
    public String store(@ModelAttribute("coffee") @Valid Coffee coffee,
                        BindingResult bindingResult,
                        @RequestParam(value = "imageFile") MultipartFile coffeePicture, Model model, HttpSession session) {

        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "add";  // Make sure the 'add' template is loaded
        }

        // Assign ID BEFORE handling the image
        coffee.setId(coffeeService.getLastId() + 1);

        // Handle image upload
        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = UUID.randomUUID() + coffeePicture.getOriginalFilename().substring(coffeePicture.getOriginalFilename().lastIndexOf("."));
            try {
                coffeePicture.transferTo(new File(uploadFolder.getAbsolutePath() + File.separator + fileName));
                coffee.setCoffeePicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }

        coffeeService.addCoffee(coffee);  // Save the coffee with the new picture
        return "redirect:/";  // Redirect to the list page
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model, HttpSession session) {
        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }
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
    public String update(@ModelAttribute("coffee") @Valid Coffee coffee, BindingResult bindingResult, Model model, HttpSession session) {
        CoffeeUser currentUser = (CoffeeUser) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }
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

    @GetMapping("/coffee/{id}")
    public String view(@PathVariable int id, Model model) {
        Coffee coffee = coffeeService.getCoffee(id);
        model.addAttribute("coffee", coffee);
        return "coffee"; // This should point to a Thymeleaf template named coffee.html
    }

}