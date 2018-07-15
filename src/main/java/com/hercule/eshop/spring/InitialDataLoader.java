package com.hercule.eshop.spring;

import com.hercule.eshop.models.Role;
import com.hercule.eshop.models.User;
import com.hercule.eshop.services.RoleService;
import com.hercule.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>
{

    boolean alreadySetup = false;

    static final String ADMIN_USERNAME = "administrator";
    static final String ADMIN_PASSWORD = "321321";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event)
    {

        if (alreadySetup)
        {
            return;
        }

        createRoleIfNotFound("ROLE_ADMIN");

        User user = userService.findByUsername(ADMIN_USERNAME);
        if (user == null)
        {
            user = new User();
            user.setUsername(ADMIN_USERNAME);
            user.setPassword(ADMIN_PASSWORD);
            HashSet<Role> roles = roleService.getAllRoles();
            user.setRoles(roles);
            userService.save(user);
        }


        alreadySetup = true;


    }

    @Transactional
    public void createRoleIfNotFound(String name)
    {
        Role role;

        HashSet<Role> roles = roleService.getAllRoles();
        if (!roles.contains(name))
        {
            role = new Role();
            role.setName(name);
            roleService.saveRole(role);
        }
    }
}
