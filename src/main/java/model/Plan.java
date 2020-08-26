package model;

import java.util.HashMap;
import java.util.List;

public class Plan {

    private Status status;
    private double budget;
    private int id;
    private HashMap<Integer, List<Decision>> approvalFlowsAndDecisions;

    public enum Status {
        INITIATED,
        TERMINATED,
        SUCCESSFUL;

        public static boolean isValidEnum(String enumName){
            if (enumName == null) {
                return false;
            }
            try {
                Status.valueOf(enumName);
                return true;
            } catch (final IllegalArgumentException ex) {
                return false;
            }
        }

    }
    public Plan(int id, double budget) {
        this.status = Status.INITIATED;
        this.id = id;
        this.budget = budget;
        this.approvalFlowsAndDecisions = new HashMap<Integer, List<Decision>>();
    }

    public void setStatus(String status) {
        try {
            if (Status.isValidEnum(status)){
                this.status = Status.valueOf(status);
            }
        }
        catch (IllegalArgumentException err) {
            System.out.printf("Error: Received an invalid status: %s", status);
        }
    }

    public double getBudget() {
        return budget;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() { return status; }

    public void addDecisionForAF(int afID, Decision decision) {
        approvalFlowsAndDecisions.get(afID).add(decision);
    }

    public HashMap<Integer, List<Decision>> getApprovalFlowsAndDecisions() {
        return approvalFlowsAndDecisions;
    }
}
