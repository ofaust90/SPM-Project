package com.unicam.crm.bexio;



import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.unicam.crm.CRMConnector;
import com.unicam.crm.bexio.model.BexioContact;
import com.unicam.crm.bexio.model.BexioContactGet;
import com.unicam.rest.model.Customer;
import com.unicam.rest.model.CustomerExchange;



public class BexioConnector implements CRMConnector{
	
	
	private final  String BASE_URL = "https://office.bexio.com/api2.php/";
	private final String COMPANY_ID = "eocsqx9ue67h";
	private final String USER_ID = "1";
	private final String PUBLIC_KEY = "f58965246a814b51e23e4602fa762fb4";
	private final String SIGNATURE_KEY = "4c9ac6c3fec271fb91cb4d3efa548b7c";
	private String companyBexioURL;
	
	static ObjectMapper mapper = new ObjectMapper();
	
	
	
	public BexioConnector() {
		
		this.companyBexioURL = BASE_URL+COMPANY_ID+"/"+USER_ID+"/"+PUBLIC_KEY+"/";
		System.out.println("URL:"+companyBexioURL);
	}
	
	private String calculateSignature(String method, String resource, String payload) {
		
		String uri = this.companyBexioURL+resource;
		String requestHashString = method + uri + payload + this.SIGNATURE_KEY;
		System.out.println("tobe hashed: "+requestHashString);
		String md5Hex = DigestUtils.md5Hex(requestHashString);
		
		return md5Hex;
	}
	
	
	private String post(String resource, String payload) {
		
		try {

			System.out.println("payload: "+payload);
			
			ClientConfig clientConfig = new DefaultClientConfig();              
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			Client client = Client.create(clientConfig);
			
			String uri = this.companyBexioURL+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
	
			webResource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");		
			webResource.header("Content-Length", payload.length());
		
		
			System.out.println("length: "+payload.length());


			ClientResponse response = webResource.accept("application/json").type("application/json")
			   .post(ClientResponse.class, payload);
			
			

			if (response.getStatus() != 201) {
				

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return "error";
	}
	
	
	private String get(String resource) {
		try {
			

			System.out.println("GET: "+resource);
			Client client = Client.create();

			String uri = this.companyBexioURL+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
			
			String signature = calculateSignature("get" ,resource, "");
			System.out.println("Signature: "+signature);
			
			webResource.header("Signature",signature);
			webResource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			webResource.header("Accept", "application/json");
			
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
				
				System.out.println("Err. Response "+response.toString());
				String output = response.getEntity(String.class);

				System.out.println("Output from Server .... \n");
				System.out.println(output);
				
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);
			
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		
		return "error";
	}

	

	
	
//	private BexioContact customerMapper(CustomerExchange c) {
	@Override
	public Customer customerMapper(CustomerExchange c) {
		
		BexioContact bc = new BexioContact();
		bc.setName_1(c.getName());
	
		
		return bc;
		
	}
	
	//private CustomerExchange bexioMapper(BexioContactGet bc) {
	
	@Override
	public <C extends Customer> CustomerExchange crmCustomerMapper(C crmCustomer) {
		
		
		CustomerExchange ce = new CustomerExchange();
		ce.setName(((BexioContactGet) crmCustomer).getName_1());
		ce.setId(((BexioContactGet) crmCustomer).getId());
		
		return ce;
	}

	/*
	public CustomerExchange crmCustomerMapper(BexioContactGet crmCustomer) {
		
		CustomerExchange ce = new CustomerExchange();
		ce.setName(crmCustomer.getName_1());
		ce.setId(crmCustomer.getId());
		
		return ce;
	}*/
	
	@Override
	public Customer addCustomer(CustomerExchange c) {
		
		
		BexioContact bc = (BexioContact) customerMapper(c);

		try {
		
			String jsonInString = this.mapper.writeValueAsString(bc);
			
			String response = this.post("contact",jsonInString);
			
			String result = "Customer saved : " + c + " response: "+response;
			
			BexioContactGet bexRet = mapper.readValue(response, BexioContactGet.class);
			CustomerExchange exchangeCustomer = crmCustomerMapper(bexRet);
			
			//Customer contact = mapper.readValue(response, Customer.class);
			
			return exchangeCustomer;//Response.status(201).entity(contact).build();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public CustomerExchange getCustomer(int id) {
		

		
		try {
			
			String contactJson = this.get("contact/"+id);
			//CustomerExchange contact;
			//contact = mapper.readValue(contactJson, CustomerExchange.class);
			BexioContactGet bexioContactget = mapper.readValue(contactJson, BexioContactGet.class);
			CustomerExchange customer = crmCustomerMapper(bexioContactget);
			
			return customer;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	
}
