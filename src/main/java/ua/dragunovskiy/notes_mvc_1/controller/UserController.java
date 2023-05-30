package ua.dragunovskiy.notes_mvc_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.dragunovskiy.notes_mvc_1.entity.User;
import ua.dragunovskiy.notes_mvc_1.service.UserService;


@Controller
public class UserController {

    @Autowired
    private UserService<Integer, User> userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAll());
        return "/user/getAllUsers";
    }

    @GetMapping("/users/{id}")
    public String getUserById(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getById(id));
        return "/user/getUser";
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "/user/addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("newUser") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("editUser", userService.getById(id));
        return "/user/editUser";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@ModelAttribute("editUser") User user) {
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("/users/info/{id}")
    public String moreInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("userInfo", userService.getById(id));
        return "/user/infoUser";
    }

    @RequestMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
