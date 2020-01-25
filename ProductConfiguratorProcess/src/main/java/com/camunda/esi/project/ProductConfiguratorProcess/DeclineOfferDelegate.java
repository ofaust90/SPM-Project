package com.camunda.esi.project.ProductConfiguratorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeclineOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

			int offerID = (int) execution.getVariable("offerID");
			declineOffer(offerID);
			
			byte[] instanceID = (byte[]) execution.getVariable("blockchain_instanceID");;
			String txHash = blockchain.registerActivity(instanceID, execution.getBusinessKey(), execution.getCurrentActivityName(),"-", "-");
			execution.setVariable("TXHASH_declined", txHash);
	}

	public void declineOffer(int id) {
		
		
		this.post("offer/"+id+"/decline","");
	}
}
