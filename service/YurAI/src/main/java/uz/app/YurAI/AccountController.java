package uz.app.YurAI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uz.app.YurAI.security.JwtTokenUtil;
import uz.app.YurAI.security.model.SecurityUser;

/**
 * MainController
 */
@RestController
public class AccountController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    @GetMapping("/account")
    public String getAccountData() {
        SecurityUser user = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String token = jwtTokenUtil.generateToken(user);


        return "{'name': '"+user.getUsername()+"', 'token': "+SecurityContextHolder.getContext().getAuthentication()+"}";
    }
}