package vn.hoidanit.laptopshop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;



@Controller
public class UserController {

    private final UserService userService;
   

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("test", test);
        return "hello";
    }
    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
    @PostMapping("/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        System.out.println(newUser);
        userService.handleSaveUser(newUser);
        return "admin/user/create";
    }
   
}
