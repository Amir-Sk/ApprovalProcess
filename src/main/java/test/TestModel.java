package test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import decisions.PrehandedPlanDecisions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.ApprovalFlow;
import model.ApprovalFlow.BudgetDefinition;
import model.Decision;
import model.Plan;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import service.ApprovalFlowService;
import service.DateAndTime;
import service.PlanService;
import service.UserService;

@TestInstance(Lifecycle.PER_CLASS)
public class TestModel {
    private DateAndTime dateAndTime;
    private ArrayList<User> afApproversList1;
    private ArrayList<User> afApproversList2;
    private ApprovalFlow af1;
    private ApprovalFlow af2;
    private HashMap decisions;
    private ApprovalFlowService afService;
    private PlanService planService;
    private UserService userService;

    @BeforeAll
    void setUp(){
        decisions = PrehandedPlanDecisions.getDecisions();
        dateAndTime = DateAndTime.getInstance();
        planService = PlanService.getInstance();
        afService = ApprovalFlowService.getInstance();
        userService = UserService.getInstance();
        generateUsersList();
        generateAFs();
    }

    private void generateAFs() {
        try {
            af1 = afService.createAF("LOWER", 700, afApproversList1);
            System.out.printf("add-workflow %s\n",
                afService.concatAFFieldsForAction(af1));
            af2 = afService.createAF("HIGHER", 500, afApproversList2);
            System.out.printf("add-workflow %s",
                afService.concatAFFieldsForAction(af2));
        }
        catch (Exception err) {
            System.out.printf("Exception encountered: %s", err.getMessage());
        }
    }

    private User generateTestingUser(String index) {
        User user =  userService.createUser("approver".concat(index), index.concat("@domain.com"));
        System.out.printf("%s add-user %d %s %s \n",
            dateAndTime.getCurrentTimeAndDate(), user.getId(), user.getEmail(), user.getName());
        return user;
    }

    private void generateUsersList() {
        afApproversList1 = new ArrayList<>();
        afApproversList2 = new ArrayList<>();
        for(int i = 1; i < 6;i++){
            if (i % 2 == 0 || i == 5)
                afApproversList1.add(generateTestingUser(String.valueOf(i)));
            else if (i % 2 != 0 || i == 5){
                afApproversList2.add(generateTestingUser(String.valueOf(i)));
            }
        }
    }

    @Test
    void givenPlanAndAppFlowApprovePlanByThreeUsers(){
        Plan plan = afService.processAF(af1, 444,750.4, (List) decisions.get("approved"));
        List<Decision> resultDecisions = plan.getApprovalFlowsAndDecisions()
            .get(af1.getId());
        for(Decision dec : resultDecisions){
            assertThat(dec.isApproved(), equalTo(true));
        }
        planService.printPlanStatus(plan);
    }

    @Test
    void testCreateAFWithUsers() {
        generateAFs();
        assertThat(af1.getApprovingUsers(), hasSize(equalTo(afApproversList1.size())));
        assertThat(af1.getBudgetLimit(), equalTo(700.0));
        assertThat(BudgetDefinition.valueOf(af1.getBudgetDefinition()),
            equalTo(BudgetDefinition.LOWER));
    }
}
