package ntswwm.agents;

import jade.core.Agent;

public class FoodAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -9131211145708002083L;

    public static String[] ANSWER_ATTRIBUTES = { "meatAmount", "garnishAmount", "snacksAmount", "foodBudget" };

    protected void setup() {
        System.out.println("Food Agent with ID: " + getAID().getName() + " is ready!");
    }

    public String ping() {
        return "Hello, this is the food agent with ID: " + getAID().getName();
    }

}
