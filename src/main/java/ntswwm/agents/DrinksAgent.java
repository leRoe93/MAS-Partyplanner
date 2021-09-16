package ntswwm.agents;

import jade.core.Agent;
import ntswwm.behaviours.RetrievalBehaviour;

public class DrinksAgent extends Agent {
    /**
     * 
     */
    private static final long serialVersionUID = 8227268897794532618L;

    public static String[] ANSWER_ATTRIBUTES = { "beerAmount", "wineAmount", "spiritsAmount", "softsAmount" };

    protected void setup() {
        System.out.println("Drinks Agent with ID: " + getAID().getName() + " is ready!");
        addBehaviour(new RetrievalBehaviour());
    }

    public String ping() {
        return "Hello, this is the drinks agent with ID: " + getAID().getName();
    }

}
