package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.camunda.esi.project.ProductConfiguratorProcess.model.OfferSendExchange;

public class SendInvoiceDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		int invoiceID = (int) execution.getVariable("invoiceID");
		String email = (String) execution.getVariable("customerEmail");
		String businessKey = (String) execution.getBusinessKey();
		System.out.println("debug offer id: "+invoiceID);
				
		//call method to consume rest service
		sendInvoice(invoiceID,email,businessKey);	
			
		}
		

	public void sendInvoice(int id,String email, String businessKey) {
		
		OfferSendExchange exchange = new OfferSendExchange();
		exchange.setBusinessKey(businessKey);
		exchange.setEmail(email);
		
		
		String jsonInString;
		try {
			jsonInString = mapper.writeValueAsString(exchange);
			this.post("offer/"+id+"/invoice/send",jsonInString);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	;
	}

}
