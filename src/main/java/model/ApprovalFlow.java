package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class ApprovalFlow {

    public enum BudgetDefinition {
        LOWER,
        HIGHER
    }
    private BudgetDefinition budgetDefinition;

    private double budgetLimit = 0;
    private ArrayList<User> approvingUsers;
    private static AtomicInteger afCount = new AtomicInteger(0);
    private final int id;
    public static volatile HashMap<Integer, ApprovalFlow> approvalFlows;

    public ApprovalFlow(String budgetDefinition, double budgetLimit, ArrayList<User> approvingUsers){
        validateStringToEnum(budgetDefinition);
        if (budgetLimit < 0) throw new IllegalArgumentException("Budget limit cannot be negative");
        this.budgetLimit = budgetLimit;
        this.approvingUsers = new ArrayList<>(approvingUsers);
        this.id = afCount.incrementAndGet();
        if (approvalFlows == null) approvalFlows = new HashMap<>();
    }

    public void validateStringToEnum(String budgetDefinition) {
        try {
            this.budgetDefinition = BudgetDefinition.valueOf(budgetDefinition);
            }
        catch (IllegalArgumentException err) {
            System.out.printf("Error: Received an invalid budget definition: %s", budgetDefinition);
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getApprovingUsers() {
        return approvingUsers;
    }

    public String printApprovingUsers() {
        StringJoiner joiner = new StringJoiner(" ");
        approvingUsers.forEach(user -> joiner.add(String.valueOf(user.getId())));
        return joiner.toString();
    }

    synchronized public void assignUserToAF(User user){
        if(user != null && !this.approvingUsers.contains(user)){
            this.approvingUsers.add(user);
        }
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public String getBudgetDefinition() {
        return budgetDefinition.toString();
    }

}
