package service;

import model.User;

public class UserService {

    public static User createUser(String name, String email){
        if (!name.isBlank() && !email.isBlank()) {
            return new User(name, email);
        }
        else {
            throw new IllegalArgumentException("Name/Email are invalid");
        }
    }

    public static void notifyUser(User user){
        if(user.isNotified()){
            System.out.println("User already notified");
            return;
        }
        user.setNotified(true);
        System.out.printf("User %s was notified", user.getName());

    }

}
