package ntswwm.agents;

import jade.core.Agent;

public class CoordinatorAgent extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = 9102258426056237056L;

    protected void setup() {
        System.out.println("Coordinator Agent with ID: " + getAID().getName() + " is ready!");
    }

}
