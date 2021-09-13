package ntswwm.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class RetrievalBehaviour extends CyclicBehaviour {

    /**
     * 
     */
    private static final long serialVersionUID = -4550436136565114827L;

    @Override
    public void action() {
        // TODO Auto-generated method stub
        ACLMessage msg = myAgent.receive();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Waiting for message to be received");

        if (msg != null) {
            System.out.println("I received a message!!!");
        }

    }

}
