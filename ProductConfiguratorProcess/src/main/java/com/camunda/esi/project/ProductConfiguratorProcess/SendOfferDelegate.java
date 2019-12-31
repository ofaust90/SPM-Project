package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.camunda.esi.project.ProductConfiguratorProcess.model.OfferSendExchange;

public class SendOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	
		
	int offerID = (int) execution.getVariable("offerID");
	String email = (String) execution.getVariable("customerEmail");
	String businessKey = (String) execution.getBusinessKey();
	System.out.println("debug offer id: "+offerID);
			
	//call method to consume rest service
	sendOffer(offerID,email,businessKey);	
		
	}
	

public void sendOffer(int id,String email, String businessKey) {
	
	OfferSendExchange exchange = new OfferSendExchange();
	exchange.setBusinessKey(businessKey);
	exchange.setEmail(email);
	
	
	String jsonInString;
	try {
		jsonInString = mapper.writeValueAsString(exchange);
		this.post("offer/"+id+"/send",jsonInString);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
;
}

}
