package uz.app.YurAI.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uz.app.YurAI.common.model.StdResponse;
import uz.app.YurAI.security.model.User;
import uz.app.YurAI.security.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
	PasswordEncoder passEncoder;

    @PostMapping("/register")
    public StdResponse registerUser(@RequestBody User user) {
        if(userService.hasUserByLogin(user.getLogin()))
            return new StdResponse("001", "User with login '"+user.getLogin()+"' already exists!");
        user.setPassword(passEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return new StdResponse("0", "Successfully registered");
    }
    
    @GetMapping("/get/{login}")
    public User getByLogin(@PathVariable("login") String login) {
        return userService.getUserByLogin(login);
    }
}
