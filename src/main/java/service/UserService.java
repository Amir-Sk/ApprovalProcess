package service;

import model.User;

public class UserService {

    private static volatile UserService userServiceInstance;

    public static UserService getInstance(){
        if (userServiceInstance == null) {
            synchronized (UserService.class) {
                if (userServiceInstance == null ){
                    userServiceInstance = new UserService();
                }
            }
        }
        return userServiceInstance;
    }

    private UserService(){
        if (userServiceInstance != null){
            throw new RuntimeException("Please use getInstance() in order to get the single instance.");
        }
    }

    public User createUser(String name, String email){
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
