package com.jumooong.forms;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());

        return "login";
    }
    @PostMapping("/login")
    public String authenticate(@ModelAttribute("user") @Valid AppUser user, BindingResult bindingResult, HttpSession session, Model model){
        if(bindingResult.hasErrors()){
            return "login";
        }

        String dummyUserName ="ulyssespogi";
        String password ="ilovecompro";

        if(user.getUsername().equals(dummyUserName) && user.getPassword().equals(password)){

            session.setAttribute("user", user);

            return "redirect:/";
        }

        String error ="Incorrect username or password! Please try again!";
        model.addAttribute("error", error);

        return "login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
