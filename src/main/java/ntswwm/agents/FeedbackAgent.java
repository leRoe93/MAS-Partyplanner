package ntswwm.agents;

import jade.core.Agent;
import ntswwm.behaviours.FeedbackBehaviour;

public class FeedbackAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -9131211145708002083L;

    protected void setup() {
        System.out.println("Feedback Agent with ID: " + getAID().getName() + " is ready!");
        addBehaviour(new FeedbackBehaviour());
    }

    public String ping() {
        return "Hello, this is the feedback agent with ID: " + getAID().getName();
    }

}
