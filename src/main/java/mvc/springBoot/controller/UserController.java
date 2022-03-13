package mvc.springBoot.controller;

import mvc.springBoot.entity.Role;
import mvc.springBoot.repository.UserRepository;
import mvc.springBoot.service.RoleService;
import mvc.springBoot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import mvc.springBoot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping("/")
//    public String StartPage(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        model.addAttribute("userAuth" , userService.loadUserByUsername(auth.getName()));
//        return "index";
//    }

    @GetMapping("/")
    public String allUsers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userAuth" , userService.loadUserByUsername(auth.getName()));
        return "/users";
    }

//    @GetMapping(value = "user/lk")
//    public String getUserPage2(Model model, Principal principal) {
//        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
//
//        return "user";
//    }

//    @PostMapping("/admin/add")
////    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("roles") ArrayList<Long> roles) {
//    public String addUser(@ModelAttribute("newUser") User user) {
//        userService.saveUser(user);
////        user.setRoles(roles.stream().map(roleService::getRole).collect(Collectors.toSet()));
//        return "redirect:/admin/users";
//    }

//    @PatchMapping("/admin/edit/{id}")
//    public String updateUser(@ModelAttribute("user") User user,  @RequestParam List<Long> roles)  {
//        Set<Role> userRoles = new HashSet<>();
//        for(Long role: roles){
//            userRoles.add(roleService.getRole(role));
//        }
//        user.setRoles(userRoles);
//        userService.updateAll(user);
//        return "redirect:/admin/users";
//    }

//    @PostMapping(value = "/admin/delete/{id}")
//    public String deleteUser(@PathVariable("id") int id) {
//        User user = userService.findUserById(id);
//        userService.deleteUser(user);
//        return "redirect:/admin/users";
//    }


}
