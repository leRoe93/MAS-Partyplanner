package ntswwm.behaviours;

import de.dfki.mycbr.core.casebase.Instance;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ntswwm.bean.AgentToServletStack;
import ntswwm.bean.CBRManager;

public class FeedbackBehaviour extends CyclicBehaviour {

    /**
     * 
     */
    private static final long serialVersionUID = 7863160890814703367L;

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
            for (Instance instance : CBRManager.CASE_BASE.getCases()) {
                var id = msg.getContent();

                if (id.equals(instance.getAttForDesc(CBRManager.CONCEPT.getAttributeDesc("id")).getValueAsString())) {
                    System.out.println("Found matching case for id: " + id);
                    AgentToServletStack.FEEDBACK_INSTANCES.add(instance);
                }
            }
        }

    }

}
