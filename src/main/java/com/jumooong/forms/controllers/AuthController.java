package com.jumooong.forms.controllers;


import com.jumooong.forms.models.AppUser;
import com.jumooong.forms.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute("user") @Valid AppUser user,
                               BindingResult bindingResult,
                               HttpSession session,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        AppUser foundUser = userService.findByUsername(user.getUsername());

        if(foundUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), foundUser.getPassword())) {
            session.setAttribute("LoggedInUser", user);
            return "redirect:/";
        }

        model.addAttribute("error", "Incorrect username or password! Please try again!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
