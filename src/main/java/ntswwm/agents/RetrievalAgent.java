package ntswwm.agents;

import jade.core.Agent;

public class RetrievalAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = -1378286095267597345L;

    protected void setup() {
        System.out.println("Retrieval Agent with ID: " + getAID().getName() + " is ready!");
    }

    public String ping() {
        return "Hello, this is the retrieval agent with ID: " + getAID().getName();
    }
}
