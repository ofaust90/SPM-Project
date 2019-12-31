package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.camunda.esi.project.ProductConfiguratorProcess.model.OfferExchange;

public class CreateOfferDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		OfferExchange returnedOffer;
		
		
		
		OfferExchange newOffer = new OfferExchange();
		
		
		
		newOffer.setContact_id((int) execution.getVariable("customerID"));
		newOffer.setDiscount((double) execution.getVariable("discount"));
		newOffer.setPrice((int) execution.getVariable("v_productPrice"));
		newOffer.setAmount((int) execution.getVariable("v_productAmount"));
		newOffer.setPositions((String) execution.getVariable("v_productDesc"));
		
		
		
		returnedOffer = createNewOffer(newOffer);
		
		execution.setVariable("totalPrice", (int) returnedOffer.getTotal_price());
		execution.setVariable("offerID", returnedOffer.getId());
		
	}
	
public OfferExchange createNewOffer(OfferExchange newOffer) {
		
		
		try {
			//convert customerExchange object into jsonstring
			String jsonInString = mapper.writeValueAsString(newOffer);
			
			//call post method on resource and store result
			String response = this.post("offer/create",jsonInString);
			
			//Debug purpose
			String result = "Offer created : " + newOffer + " response: "+response;
			System.out.println(result);
			
			//map json result string back to customer exchange object
			OfferExchange createdOffer = mapper.readValue(response, OfferExchange.class);
			
			return createdOffer;
			
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
