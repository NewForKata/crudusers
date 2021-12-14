package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        return "home";
    }

    @GetMapping(value = "edit/{id}")
    public ModelAndView editUser(@PathVariable("id") int id) {
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "edit")
    public ModelAndView edit(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/userlist");
        userService.edit(user);
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public String addPage() {
        return "addUser";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/userlist";
    }

    @GetMapping(value = "/userlist")
    public ModelAndView userList() {
        List<User> users = userService.allUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userList");
        modelAndView.addObject("userList", users);
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/userlist";
    }

}