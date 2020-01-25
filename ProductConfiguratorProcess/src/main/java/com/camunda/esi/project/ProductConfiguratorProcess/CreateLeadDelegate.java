package com.camunda.esi.project.ProductConfiguratorProcess;

import java.io.IOException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.camunda.esi.project.ProductConfiguratorProcess.blockchain.smartcontract.Camundaprocess;
import com.camunda.esi.project.ProductConfiguratorProcess.model.CustomerExchange;
import com.camunda.esi.project.ProductConfiguratorProcess.model.Message;




public class CreateLeadDelegate extends BaseDelegateClass implements JavaDelegate  {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		

		
		//do whatever logic you need...
		
		//e.g. get process variables
		CustomerExchange returnedCustomer;
		
		int customerID = (int) execution.getVariable("v_customerID");
		
		
		if(customerID > 0) {
			//if has existing id, get existing customer 
			System.out.println("debug customer id: "+customerID);
			
			//call method to consume rest service
			returnedCustomer = getExistingLead(customerID);
			
			
		}else {
			//else create new customer
			
			CustomerExchange newCustomer = new CustomerExchange();
			newCustomer.setFamilyname((String) execution.getVariable("v_customerName"));
			newCustomer.setName((String) execution.getVariable("v_customerFirstName"));
			newCustomer.setAddress((String) execution.getVariable("v_customerAddress"));
			newCustomer.setEmail((String) execution.getVariable("v_customerEmail"));
			newCustomer.setPostcode((int) execution.getVariable("v_customerZip"));
			newCustomer.setCity((String) execution.getVariable("v_customerCity"));
			newCustomer.setPhone((String) execution.getVariable("v_customerPhone"));
			newCustomer.setCustomerType("new");
			//.... data should be retrieved from execution envoironemnt vars
			
			returnedCustomer = createNewLead(newCustomer);
			
		
			
			
		}
		
		
		
		//store whatever you want in process variable
		execution.setVariable("customerID", returnedCustomer.getId());
		execution.setVariable("customerName", returnedCustomer.getFamilyname());
		execution.setVariable("customerFirstName", returnedCustomer.getName());
		execution.setVariable("customerEmail", returnedCustomer.getEmail());
		execution.setVariable("customerPhone", returnedCustomer.getPhone());
		execution.setVariable("customerZip", returnedCustomer.getPostcode());
		execution.setVariable("customerAddress", returnedCustomer.getAddress());
		execution.setVariable("customerCity", returnedCustomer.getCity());
	//	execution.setVariable("productAmount", execution.getVariable("v_productAmount"));
		execution.setVariable("callbackRequested", execution.getVariable("callbackRequested"));
		execution.setVariable("customerType", returnedCustomer.getCustomerType());
		
		execution.setVariable("specialDiscount", false);
	
		execution.setVariable("customerObject", returnedCustomer);
		

	

		
		byte[] instanceID = blockchain.createCollaboration(execution.getBusinessKey());
		execution.setVariable("blockchain_instanceID", instanceID);;
		String txHash = blockchain.registerActivity(instanceID, execution.getBusinessKey(), execution.getCurrentActivityName(),"John", "Customer created with id: "+execution.getVariable("customerID"));
		execution.setVariable("TXHASH_createLead", txHash);		
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
