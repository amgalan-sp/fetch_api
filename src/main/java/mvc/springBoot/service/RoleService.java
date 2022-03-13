package mvc.springBoot.service;

import mvc.springBoot.entity.Role;
import java.util.List;

public interface RoleService {

    List<Role> allRoles();
    void saveRole(Role role);
    void deleteUser(Long userId);
    Role getRole(Long id);
}
