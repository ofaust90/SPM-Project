package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.camunda.esi.project.ProductConfiguratorProcess.model.OfferExchange;

public class SendOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
	
	OfferExchange returnedOffer;
		
	int offerID = (int) execution.getVariable("offerID");
		
		
	//if has existing id, get existing customer 
	System.out.println("debug offer id: "+offerID);
			
	//call method to consume rest service
	returnedOffer = getExistingOffer(offerID);	

		
		
	}
	

public OfferExchange getExistingOffer(int id) {
	
		

		try {
			
			//define resource path and call get method
			String offerJson = get("offer/"+id+"/send");
			
			//convert json string into exchange object
			OfferExchange offer = mapper.readValue(offerJson, OfferExchange.class);
			return offer;
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Code should not reach this line (error condition)
		return null;

	}



}
