package mvc.springBoot.controller;


import mvc.springBoot.entity.Role;
import mvc.springBoot.entity.User;
import mvc.springBoot.repository.UserRepository;
import mvc.springBoot.service.RoleService;
import mvc.springBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("")
public class RestUserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public RestUserController(UserService userService, UserRepository userRepository,  RoleService roleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> apiGetAllUsers() {
        final List<User> users = userService.allUsers();
        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> apiGetUserById(@PathVariable("id") int id) {
        User userById = userService.findUserById(id);
        return userById != null
                ? new ResponseEntity<>(userById, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("username") String username,  Model model) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/admin/users/{id}")
    public ResponseEntity<User> updateAll(@RequestBody User user, @PathVariable("id") int id) {
        userService.updateAll(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        User user = userService.findUserById(id);
        userService.deleteUser(user);
        return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
    }
}
