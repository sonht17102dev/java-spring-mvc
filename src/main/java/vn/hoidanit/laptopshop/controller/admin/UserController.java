package vn.hoidanit.laptopshop.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UploadService uploadService;

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, UploadService uploadService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // @GetMapping("/")
    // public String getHomePage(Model model) {
    //     List<User> arrUsers = userService.getAllUsersByEmail("thanhson3900@gmail.com");
    //     System.out.println(arrUsers);
    //     return "hello";
    // }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> arrUsers = userService.getAllUsers();
        model.addAttribute("users", arrUsers);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
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
    public String createUserPage(@Valid @ModelAttribute("newUser") User newUser, BindingResult result,
    @RequestParam("avatarFile") MultipartFile file, Model model ) {
        
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if(result.hasErrors()) {

            return "admin/user/create";
        }

        String avatarFileName = uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setAvatar(avatarFileName);
        newUser.setPassword(hashPassword);
        // Set role
        newUser.setRole(userService.getRoleByName(newUser.getRole().getName()));

        userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    @PostMapping("/admin/user/update")
    public String updateUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        User currentUser = userService.getUserById(newUser.getId());
        if (currentUser != null) {
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
