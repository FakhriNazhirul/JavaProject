package com.project.organix.controller;

import com.project.organix.model.User;
import com.project.organix.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return redirectByRole((String) session.getAttribute("userRole"));
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session, Model model) {
        if (session.getAttribute("userId") != null) {
            return redirectByRole((String) session.getAttribute("userRole"));
        }

        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password.equals(user.getPassword())) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("userName", user.getName());
                
                return redirectByRole(user.getRole());
            }
        }
        model.addAttribute("error", "Invalid email or password");
        return "auth/login";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute User userForm, HttpSession session, Model model) {
        if (userRepository.findByEmail(userForm.getEmail()).isPresent()) {
            model.addAttribute("user", userForm);
            model.addAttribute("error", "Email already registered");
            return "auth/register";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setRole("USER");
        user.setPoints(0);

        User saved = userRepository.save(user);
        session.setAttribute("userId", saved.getId());
        session.setAttribute("userRole", saved.getRole());
        session.setAttribute("userName", saved.getName());

        return "redirect:/user/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private String redirectByRole(String role) {
        if ("ADMIN".equals(role)) {
            return "redirect:/";
        }
        return "redirect:/user/dashboard";
    }
}
