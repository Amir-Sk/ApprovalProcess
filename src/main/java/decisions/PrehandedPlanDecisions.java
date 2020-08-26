package decisions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PrehandedPlanDecisions {

    public final static HashMap decisions = new HashMap<String, List<Boolean>>();

    public PrehandedPlanDecisions(){
        this.decisions.put("approved", Arrays.asList(true, true, true));
        this.decisions.put("rejectedBySecond", Arrays.asList(true, false));
        this.decisions.put("rejectedByThird", Arrays.asList(true, true, false));
    }

    public static HashMap getDecisions() {
        if (decisions.isEmpty()) {
             new PrehandedPlanDecisions();
        }
        return decisions;
    }
}