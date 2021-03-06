package mvc.springBoot.service;

import mvc.springBoot.entity.Role;
import mvc.springBoot.entity.User;
import mvc.springBoot.repository.RoleRepository;
import mvc.springBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("userDetailServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    public User findUserByUsername(String username) {
        Optional<User> userFromDb = Optional.ofNullable(userRepository.findByUsername(username));
        return userFromDb.orElse(new User());
    }
    public User findUserById(Integer userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public void updateAll(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void update(User updated, int id) {
        User userToBeUpdated = findUserById(id);
        userToBeUpdated.setName(updated.getName());
        userToBeUpdated.setLastname(updated.getLastname());
        userToBeUpdated.setAge(updated.getAge());
        userToBeUpdated.setRoles(updated.getRoles());
        userToBeUpdated.setUsername(updated.getUsername());
        userToBeUpdated.setPassword(updated.getPassword());
        userRepository.save(userToBeUpdated);
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
