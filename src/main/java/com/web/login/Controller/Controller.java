package com.web.login.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.login.Role.Role;
import com.web.login.Role.RoleRepository;
import com.web.login.Searcher.Searcher;
import com.web.login.User.User;
import com.web.login.User.UserRepository;
import com.web.login.User.Microsoft.MicrosoftUser;
import com.web.login.User.Microsoft.MicrosoftUserRepository;
import com.web.login.User.Google.GoogleUser;
import com.web.login.User.Google.GoogleUserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class Controller {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Searcher searcher;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MicrosoftUserRepository microsoftUserRepository;

    @Autowired
    GoogleUserRepository googleUserRepository;

    /* USER ROUTES */
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/userCount")
    public Integer userCount() {
        return (int) userRepository.count();
    }

    @GetMapping("/name/{name}")
    public User findUserByName(@PathVariable String name) {
        try {
            return searcher.findUserByName(name).get(0);
        } catch (Exception e) {
            User errorUser = new User("", "The user does not exist", "", "", "", "", "", "ERROR");

            return errorUser;
        }
    }

    @GetMapping("/user/{user}")
    public User findUserByUser(@PathVariable String user) {
        try {
            return searcher.findUserByUser(user).get(0);
        } catch (Exception e) {
            User errorUser = new User("", "The user does not exist", "", "", "", "", "", "ERROR");

            return errorUser;
        }
    }
    
    @GetMapping("/admins")
    public List<User> findUserByRole() {
        try {
            return searcher.findUsersByRole("Admin");
        } catch (Exception e) {
            List<User> errorUser = new ArrayList<User>();
            
            errorUser.add(new User("", "The user does not exist", "", "", "", "", "", "ERROR"));

            return errorUser;
        }
    }

    @PostMapping("/createUser")
    public User addUser(@RequestBody User user) {
        try {
            List<Role> rolelist = searcher.findRoleByName(user.getRole());

            Role role = rolelist.get(0);

            role.setParticipants(role.getParticipants() + 1);
            roleRepository.save(role);

            return userRepository.save(user);
        } catch (Exception e) {
            User errorUser = new User("", e.getMessage(), "", "", "", "", "", "ERROR");

            return errorUser;
        }
    }

    @PostMapping("/createUser/microsoft")
    public MicrosoftUser addUser(@RequestBody MicrosoftUser user) {
        try {
            List<Role> rolelist = searcher.findRoleByName(user.getRole());

            Role role = rolelist.get(0);

            role.setParticipants(role.getParticipants() + 1);
            roleRepository.save(role);

            return microsoftUserRepository.save(user);
        } catch (Exception e) {
            MicrosoftUser errorUser = new MicrosoftUser("", e.getMessage(), "", "", "ERROR");

            return errorUser;
        }
    }
    
    @PostMapping("/createUser/google")
    public GoogleUser addUser(@RequestBody GoogleUser user) {
        try {
            List<Role> rolelist = searcher.findRoleByName(user.getRole());

            Role role = rolelist.get(0);

            role.setParticipants(role.getParticipants() + 1);
            roleRepository.save(role);

            return googleUserRepository.save(user);
        } catch (Exception e) {
            GoogleUser errorUser = new GoogleUser("", e.getMessage(), "", "", "", "ERROR");

            return errorUser;
        }
    }
    
    @PostMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        try {
            Role oldRole = searcher.findRoleByUserName(user.getName()).get(0);
            
            if (oldRole.getName() != user.getRole()) {
                oldRole.setParticipants(oldRole.getParticipants() - 1);

                Role actualRole = searcher.findRoleByName(user.getRole()).get(0);

                actualRole.setParticipants(actualRole.getParticipants() + 1);

                roleRepository.save(oldRole);
                roleRepository.save(actualRole);
            }

            return userRepository.save(user);
        } catch (Exception e) {
            User errorUser = new User("", e.getMessage(), "", "", "", "", "", "ERROR");

            return errorUser;
        }
    }

    @PostMapping("/deleteUser")
    public User deleteUser(@RequestBody String name) {

        User user = searcher.findUserByName(name).get(0);

        try {
            Role role = searcher.findRoleByName(user.getRole()).get(0);

            role.setParticipants(role.getParticipants() - 1);
            roleRepository.save(role);

            userRepository.delete(user);
            
            return user;
        } catch (Exception e) {
            User errorUser = new User("", e.getMessage(), "", "", "", "", "", "ERROR");

            return errorUser;
        }
    }

    @PostMapping("/deleteUser/microsoft")
    public MicrosoftUser deleteMicrosoftUser(@RequestBody String name) {

        MicrosoftUser user = searcher.findMicrosoftUserByName(name).get(0);

        try {
            Role role = searcher.findRoleByName(user.getRole()).get(0);

            role.setParticipants(role.getParticipants() - 1);
            roleRepository.save(role);

            microsoftUserRepository.delete(user);
            return user;
        } catch (Exception e) {
            MicrosoftUser errorUser = new MicrosoftUser("", e.getMessage(), "", "", "ERROR");

            return errorUser;
        }
    }
    
    @PostMapping("/deleteUser/google")
    public GoogleUser deleteGoogleUser(@RequestBody String name) {

        GoogleUser user = searcher.findGoogleUserByName(name).get(0);

        try {
            Role role = searcher.findRoleByName(user.getRole()).get(0);

            role.setParticipants(role.getParticipants() - 1);
            roleRepository.save(role);

            googleUserRepository.delete(user);
            
            return user;
        } catch (Exception e) {
            GoogleUser errorUser = new GoogleUser("", e.getMessage(), "", "", "", "ERROR");

            return errorUser;
        }
    }

    /* ROLE ROUTES */
    @GetMapping("/allRoles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/roleCount")
    public Integer roleCount() {
        return (int) roleRepository.count();
    }

    @GetMapping("/role/{name}")
    public Role findRoleByName(@PathVariable String name) {
        try {
            Role role = searcher.findRoleByName(name).get(0);
            return role;
        } catch (Exception e) {
            Role errorRole = new Role("", e.getMessage(), "", 0);

            return errorRole;
        }
    }

    @PostMapping("/createRole")
    public Role addRole(@RequestBody Role role) {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            Role errorRole = new Role("", e.getMessage(), "", 0);

            return errorRole;
        }
    }
    
    @PostMapping("/updateRole")
    public Role updateRole(@RequestBody Role role) {
        try {
            return roleRepository.save(role);
        } catch (Exception e) {
            Role errorRole = new Role("" ,e.getMessage(), "", 0);

            return errorRole;
        }
    }

    @PostMapping("/deleteRole")
    public Role deleteRole(@RequestBody String name) {
        Role role = searcher.findRoleByName(name).get(0);

        try {
            roleRepository.delete(role);
            return role;
        } catch (Exception e) {
            Role errorRole = new Role("", e.getMessage(), "", 0);

            return errorRole;
        }
    }
}
