package ntswwm.agents;

import jade.core.Agent;
import ntswwm.behaviours.RetrievalBehaviour;

public class RetrievalAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -1378286095267597345L;

    protected void setup() {
        System.out.println("Retrieval Agent with ID: " + getAID().getName() + " is ready!");
        addBehaviour(new RetrievalBehaviour());
    }

    public String ping() {
        return "Hello, this is the retrieval agent with ID: " + getAID().getName();
    }
}
