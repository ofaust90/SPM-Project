package com.camunda.esi.project.ProductConfiguratorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	
		
	int offerID = (int) execution.getVariable("offerID");
	String email = (String) execution.getVariable("customerEmail");
		
	//if has existing id, get existing customer 
	System.out.println("debug offer id: "+offerID);
			
	//call method to consume rest service
	sendOffer(offerID,email);	
		
	}
	

public void sendOffer(int id,String email) {
	
	this.post("offer/"+id+"/send",email);
}

}
