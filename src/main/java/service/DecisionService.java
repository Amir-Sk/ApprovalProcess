package service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import model.Decision;
import model.User;

public class DecisionService {
    private static volatile DecisionService decisionServiceInstance;

    private DecisionService(){
        if (decisionServiceInstance != null){
            throw new RuntimeException("Please use getInstance() in order to get the single instance.");
        }
    }

    public static DecisionService getInstance(){
        if (decisionServiceInstance == null){
            decisionServiceInstance = new DecisionService();
        }
        return decisionServiceInstance;
    }

    public boolean isUserDecisionInList(List<Decision> decisions, int userId){
        AtomicBoolean isAlreadyExist = new AtomicBoolean(false);
        decisions
            .stream()
            .forEach(dec -> {
                User user = dec.getUser();
                if (user.getId() == userId)
                    isAlreadyExist.set(true);
                });
        return isAlreadyExist.get();
    }

}
