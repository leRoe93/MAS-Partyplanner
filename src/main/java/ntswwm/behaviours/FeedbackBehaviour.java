package ntswwm.behaviours;

import de.dfki.mycbr.core.casebase.Instance;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ntswwm.agents.RetrievalAgent;
import ntswwm.bean.AgentToServletStack;

public class FeedbackBehaviour extends CyclicBehaviour {

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
        System.out.println("Waiting for retrieval request for feedback case.");

        if (msg != null) {
            System.out.println("I received a retrieval request!");
            for (Instance instance : RetrievalAgent.caseBase.getCases()) {
                var id = msg.getContent();

                if (id.equals(
                        instance.getAttForDesc(RetrievalAgent.concept.getAttributeDesc("id")).getValueAsString())) {
                    System.out.println("Found matching case for id: " + id);
                    AgentToServletStack.FEEDBACK_INSTANCE = instance;
                }
            }
        }

    }

}
