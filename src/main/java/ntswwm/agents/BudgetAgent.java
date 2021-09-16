package ntswwm.agents;

import jade.core.Agent;
import ntswwm.behaviours.RetrievalBehaviour;

public class BudgetAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -6407099766861309020L;

    public static String[] ANSWER_ATTRIBUTES = { "totalBudget", "foodBudget", "drinksBudget", "locationBudget" };

    protected void setup() {
        System.out.println("Budget Agent with ID: " + getAID().getName() + " is ready!");
        addBehaviour(new RetrievalBehaviour());
    }

    public String ping() {
        return "Hello, this is the budget agent with ID: " + getAID().getName();
    }

}
