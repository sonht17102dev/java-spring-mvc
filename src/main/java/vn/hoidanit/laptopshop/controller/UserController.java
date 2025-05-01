package vn.hoidanit.laptopshop.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;



@Controller
public class UserController {

    private final UserService userService;
   

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = userService.getAllUsersByEmail("thanhson3900@gmail.com");
        System.out.println(arrUsers);
        return "hello";
    }
    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> arrUsers = userService.getAllUsers();
        model.addAttribute("users", arrUsers);
        return "admin/user/table-user";
    }
    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/show";
    }
    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }
    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable Long id) {
        User user = new User();
        user.setId(id);
        model.addAttribute("newUser", user);
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        System.out.println(newUser);
        userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }
    @PostMapping("/admin/user/update")
    public String updateUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        User currentUser = userService.getUserById(newUser.getId());
        if(currentUser != null) {
            currentUser.setAddress(newUser.getAddress());
            currentUser.setFullName(newUser.getFullName());
            currentUser.setPhone(newUser.getPhone());
            userService.handleSaveUser(currentUser);
            
        }
        return "redirect:/admin/user";
    }
    @PostMapping("/admin/user/delete")
    public String deleteUser(Model model, @ModelAttribute("newUser") User newUser) {
        userService.deleteUserById(newUser.getId());
        return "redirect:/admin/user";
    }
}
