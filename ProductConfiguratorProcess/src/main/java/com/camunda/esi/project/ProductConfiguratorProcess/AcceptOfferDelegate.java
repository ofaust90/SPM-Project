package com.camunda.esi.project.ProductConfiguratorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AcceptOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

			int offerID = (int) execution.getVariable("offerID");
			acceptOffer(offerID);
			
			byte[] instanceID = (byte[]) execution.getVariable("blockchain_instanceID");;
			String txHash = blockchain.registerActivity(instanceID, execution.getBusinessKey(), execution.getCurrentActivityName(),"-", "-");
			execution.setVariable("TXHASH_shipping", txHash);
	}

	public void acceptOffer(int id) {
		
		
		this.post("offer/"+id+"/accept","");
	}
}
