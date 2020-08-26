package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import lombok.NonNull;
import model.ApprovalFlow;
import model.Decision;
import model.Plan;
import model.User;

public class PlanService {

    private static volatile PlanService planServiceInstance;
    DateAndTime dateAndTime;

    private PlanService(){
        if (planServiceInstance != null){
            throw new RuntimeException("Please use getInstance() in order to get the single instance.");
        }
        dateAndTime = DateAndTime.getInstance();
    }

    public static PlanService getInstance(){
        if (planServiceInstance == null) {
            synchronized (PlanService.class) {
                if (planServiceInstance == null)
                    planServiceInstance = new PlanService();
            }
        }
        return planServiceInstance;
    }

    public void addAfToPlan(@NonNull Plan plan, @NonNull ApprovalFlow af){
        plan.getApprovalFlowsAndDecisions().put(af.getId(), new ArrayList());
    }

    public void addDecisionToPlansAf(@NonNull Plan plan,
        int afId, boolean decision, User user, String comment){
        if (!decision) {
            plan.setStatus("TERMINATED");
        }
        Decision dec = new Decision(decision, user, comment);
        plan.addDecisionForAF(afId, dec);
    }

    public String printPlanStatus(Plan plan) {
        StringJoiner joiner= new StringJoiner(" ");
        buildDetailedPlanStatus(plan, joiner);
        HashMap<Integer, List<Decision>> plansAfsAndDecisions = plan.getApprovalFlowsAndDecisions();
        plansAfsAndDecisions.keySet().stream().forEach(afId -> {
            plansAfsAndDecisions.get(afId).stream().forEach(dec -> {
                buildUsersAndDecisions(joiner, dec);
            });
        });
        return joiner.toString();
    }

    public void buildDetailedPlanStatus(Plan plan, StringJoiner joiner) {
        joiner.add(dateAndTime.getCurrentTimeAndDate());
        joiner.add("Id: ".concat(String.valueOf(plan.getId())));
        joiner.add("Budget: ".concat(String.valueOf(plan.getBudget())));
        joiner.add("Approval Status: ".concat(String.valueOf(plan.getStatus())));
        joiner.add("\n");
    }

    public void buildUsersAndDecisions(StringJoiner joiner, Decision dec) {
        joiner.add("User id: ".concat(String.valueOf(dec.getUser().getId())));
        joiner.add("email: ".concat(dec.getUser().getEmail()));
        joiner.add("name: ".concat(dec.getUser().getName()));
        joiner.add("Is user notified: ".concat(dec.getUser().isNotified() ? "Yes" : "No"));
        joiner.add("Decision timestamp: ".concat(dec.getTimestamp()));
        joiner.add("Decision: ".concat(dec.isApproved() ? "Approved" : "Declined"));
        joiner.add("Comment: ".concat(dec.getComment()));
    }

}
