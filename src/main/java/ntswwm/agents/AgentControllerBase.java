package ntswwm.agents;

import jade.core.Agent;

public abstract class AgentControllerBase extends Agent {

    /**
     * 
     */
    private static final long serialVersionUID = 1063351853102418806L;

    public String ping() {
        return "Hello this is the agent controller with ID: " + getAID() + " for class: " + this.getClass().getName();
    }
}
