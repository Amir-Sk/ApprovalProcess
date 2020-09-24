package service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import model.ApprovalFlow;
import model.Plan;
import model.Plan.Status;
import model.User;

public class ApprovalFlowService {

    private static volatile ApprovalFlowService approvalFlowServiceInstance;
    private DateAndTime dateAndTime;
    private PlanService planService;

    private ApprovalFlowService(){
        if (approvalFlowServiceInstance != null){
            throw new RuntimeException("Please use getInstance() in order to get the single instance.");
        }
        dateAndTime = DateAndTime.getInstance();
        planService = PlanService.getInstance();
    }

    public static ApprovalFlowService getInstance(){
        if (approvalFlowServiceInstance == null) {
            synchronized (ApprovalFlowService.class) {
                if (approvalFlowServiceInstance == null ){
                    approvalFlowServiceInstance = new ApprovalFlowService();
                }
            }
        }
        return approvalFlowServiceInstance;
    }

    public Plan processAF(ApprovalFlow af, int planId, double planBudget, List prehandedDecisions){
        Plan plan = new Plan(planId, planBudget);
        planService.addAfToPlan(plan, af);
        iterateApproversDecisions(af, prehandedDecisions, plan);
        return plan;
    }

    public void iterateApproversDecisions(ApprovalFlow af, List prehandedDecisions, Plan plan) {
        AtomicInteger cnt = new AtomicInteger(0);
        AtomicBoolean decision = new AtomicBoolean(true);
        af.getApprovingUsers()
            .stream()
            .takeWhile(user -> decision.get() && plan.getStatus() == Status.INITIATED)
            .forEach(user -> {
                checkAndSetNotificationForUserIfNeeded(user);
                decision.set( (boolean) prehandedDecisions.get(cnt.getAndIncrement()));
                planService.addDecisionToPlansAf(plan, af.getId(), decision.get() , user,"");
        });
    }

    public void checkAndSetNotificationForUserIfNeeded(User user) {
        if(!user.isNotified()) {
            System.out.printf("%s User %s was notified by email\n",dateAndTime.getCurrentTimeAndDate() , user.getName());
            user.setNotified(true);
        }
    }

    public ApprovalFlow createAF(String budgetDefinition, double budgetLimit, ArrayList<User> afApproversList) {
        ApprovalFlow af =  new ApprovalFlow(budgetDefinition, budgetLimit,  afApproversList);
        addAfToExistingAFsList(af);
        return af;
    }

    public String concatAFFieldsForAction(ApprovalFlow af){
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(dateAndTime.getCurrentTimeAndDate());
        joiner.add(af.getBudgetDefinition());
        joiner.add(String.valueOf(af.getBudgetLimit()));
        joiner.add(af.printApprovingUsers());
        return joiner.toString();

    }

    synchronized public void addAfToExistingAFsList(ApprovalFlow af){
        if(!ApprovalFlow.approvalFlows.containsKey(af.getId()))
            ApprovalFlow.approvalFlows.put(af.getId(), af);
    }

    public ApprovalFlow getApprovalFlowById(int afId) {
        return ApprovalFlow.approvalFlows.get(afId);
    }
}
