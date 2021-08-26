package ntswwm.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class DrinksAgent extends Agent {
    /**
     * 
     */
    private static final long serialVersionUID = 8227268897794532618L;

    protected void setup() {
        System.out.println("Drinks Agent with ID: " + getAID().getName() + " is ready!");
        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
