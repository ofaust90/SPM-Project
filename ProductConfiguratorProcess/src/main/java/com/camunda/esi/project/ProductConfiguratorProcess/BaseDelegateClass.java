package com.camunda.esi.project.ProductConfiguratorProcess;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public  class BaseDelegateClass {
	
	
	private String baseResource;
	
	static ObjectMapper mapper = new ObjectMapper();

	
	public BaseDelegateClass() {
		this.baseResource = "http://localhost:8080/CRMWebservice/rest/";
	}
	
	
	protected String get(String resource) {
		try {
			

			System.out.println("GET: "+resource);
			Client client = Client.create();

			String uri = this.baseResource+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
			
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

	protected String post(String resource, String payload) {
		
		try {

			System.out.println("payload: "+payload);
			
			ClientConfig clientConfig = new DefaultClientConfig();              
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			Client client = Client.create(clientConfig);
			
			String uri = this.baseResource+resource;
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
	
			webResource.header("Content-Length", payload.length());
		
		
			System.out.println("length: "+payload.length());


			ClientResponse response = webResource.accept("application/json").type("application/json")
			   .post(ClientResponse.class, payload);
			
			
			//200, 201 etc is ok
			if (response.getStatus() > 205 ) {
				
				
				System.out.println("Erros: Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
			}

			System.out.println("Success: Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return "error";
	}
	
	

	
	
	

}
