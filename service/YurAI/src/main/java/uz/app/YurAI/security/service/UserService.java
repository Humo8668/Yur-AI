package uz.app.YurAI.security.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import uz.app.Anno.orm.OrmContext;
import uz.app.Anno.orm.Repository;
import uz.app.Anno.orm.RepositoryFactory;
import uz.app.Anno.orm.exceptions.AnnoException;
import uz.app.Anno.orm.exceptions.ValidationException;
import uz.app.YurAI.security.model.SecurityUser;
import uz.app.YurAI.security.model.User;

@Component
public class UserService {
    
    private static Repository<User> userRepo;

    public UserService() {
        try {
            RepositoryFactory.setContext(new OrmContext("db.properties"));
            userRepo = RepositoryFactory.getRepository(User.class);
        } catch (SQLException | AnnoException | IOException e) {
            e.printStackTrace();
        }
    }

    public User registerUser(User user) {
        User newUser = new User(0, user.getLogin(), user.getFullName(), user.getEmail(), user.getProfilePictureUrl(), user.getPassword());
        try {
            userRepo.save(newUser);
        } catch (SQLException | ValidationException | AnnoException ex) {
            throw new RuntimeException(ex);
        }
        return newUser;
    }

    public User getUserByLogin(String login) {
        try {
            User[] matchedUsers = userRepo.where("login").equal(login).get();
            if(matchedUsers.length <= 0) {
                return null;
            } else if(matchedUsers.length > 1) {
                throw new RuntimeException("User uniqueness is impacted. User login: " + login);
            }
            return matchedUsers[0];
        } catch (SQLException | RuntimeException | AnnoException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasUserByLogin(String login) {
        int usersCount;
        try {
            usersCount = userRepo.where("login").equal(login).getCount();
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
        return usersCount > 0;
    }
}
