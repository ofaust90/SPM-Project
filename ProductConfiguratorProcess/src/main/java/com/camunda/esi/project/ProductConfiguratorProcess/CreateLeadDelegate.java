package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.camunda.esi.project.ProductConfiguratorProcess.model.CustomerExchange;




public class CreateLeadDelegate extends BaseDelegateClass implements JavaDelegate  {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		

		
		//do whatever logic you need...
		
		//e.g. get process variables
		CustomerExchange returnedCustomer;
		
		if(execution.hasVariable("v_customerID")) {
			//if has existing id, get existing customer 
			int customerId = (int) execution.getVariable("customerId");
			System.out.println("debug customer id: "+customerId);
			
			//call method to consume rest service
			returnedCustomer = getExistingLead(customerId);
		}else {
			//else create new customer
			
			CustomerExchange newCustomer = new CustomerExchange();
			newCustomer.setName("camunda");
			newCustomer.setEmail("oliver.faust90@gmail.com");
			newCustomer.setCustomerType("new");
			//.... data should be retrieved from execution envoironemnt vars
			
			returnedCustomer = createNewLead(newCustomer);
			
			
		}
		
		
		
		//store whatever you want in process variable
		execution.setVariable("customerID", returnedCustomer.getId());
		execution.setVariable("customerName", returnedCustomer.getName());
		execution.setVariable("customerEmail", returnedCustomer.getEmail());
		execution.setVariable("customerType", "A");
		execution.setVariable("numberOfItems", 51);
		execution.setVariable("callbackRequested", true);

		
	
		

	}
	
	
	
	public CustomerExchange getExistingLead(int id) {
	
		

		try {
			
			//define resource path and call get method
			String customerJson = get("customer/"+id+"/get");
			
			//convert json string into exchange object
			CustomerExchange customer = mapper.readValue(customerJson, CustomerExchange.class);
			return customer;
			
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
	
	public CustomerExchange createNewLead(CustomerExchange newCustomer) {
		
		
		try {
			//convert customerExchange object into jsonstring
			String jsonInString = mapper.writeValueAsString(newCustomer);
			
			//call post method on resource and store result
			String response = this.post("customer/add",jsonInString);
			
			//Debug purpose
			String result = "Customer saved : " + newCustomer + " response: "+response;
			System.out.println(result);
			
			//map json result string back to customer exchange object
			CustomerExchange createdCustomer = mapper.readValue(response, CustomerExchange.class);
			
			return createdCustomer;
			
			
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
