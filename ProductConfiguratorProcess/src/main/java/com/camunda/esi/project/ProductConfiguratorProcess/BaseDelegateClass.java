package com.camunda.esi.project.ProductConfiguratorProcess;

import org.codehaus.jackson.map.ObjectMapper;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import com.camunda.esi.project.ProductConfiguratorProcess.blockchain.BlockchainHelper;
import com.camunda.esi.project.ProductConfiguratorProcess.blockchain.smartcontract.Camundaprocess;
import com.camunda.esi.project.ProductConfiguratorProcess.model.Message;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public  class BaseDelegateClass {
	
	
	private String baseResource;
	private String baseCamundaUrl;
	
	static ObjectMapper mapper = new ObjectMapper();
	
	protected BlockchainHelper blockchain;

	/*
	protected static String PRIVATE_KEY = "4A8BBDBCA7A4BBD4CFE110A0D828E03C1A1024D216F45F4713B8B03077826D9C";
	protected static String HTTPS_SERVER = "https://rinkeby.infura.io/v3/975ee58ff5a144c48a603e16a15cc88f";
	protected static String CONTRACT_ADDRESS = "0x07560a88e5f17252a236f47ebc00d6eb7f6dffb8";
	*/
	public BaseDelegateClass() {
		this.baseResource = "http://localhost:8080/CRMWebservice/rest/";
		this.baseCamundaUrl = "http://localhost:8080/engine-rest/";
		
		blockchain = new BlockchainHelper();
		
	}
	
	/*
	public byte[] createCollaboration(String businessKey) {
		byte[] instanceID = null;
		try {
		Web3j web3j = Web3j.build(new HttpService(HTTPS_SERVER));
		
		
		Credentials credentials = getCredentialFromPrivateKey();
		
		Camundaprocess contract = loadContract(CONTRACT_ADDRESS,web3j,credentials);
		
	
			instanceID = contract.createCollaboration(businessKey).send();
			System.out.println("instance ID: "+instanceID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return instanceID;
	
		
	}
	
	public String registerActivity(byte[] instanceID, String businessKey, String taskName, String  executor, String additionalInfo) {
		String txHash = "";
		try {
		Web3j web3j = Web3j.build(new HttpService(HTTPS_SERVER));

	
		Credentials credentials = getCredentialFromPrivateKey();
		
		Camundaprocess contract = loadContract(CONTRACT_ADDRESS,web3j,credentials);
		
		
	 	
		
			txHash = 	contract.registerActivity(instanceID, businessKey, taskName, executor, additionalInfo).send().getTransactionHash();
			System.out.println("hash of tx: "+txHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return txHash;
	}

	protected Credentials getCredentialFromPrivateKey() {
		return Credentials.create(PRIVATE_KEY);
	}

	protected Camundaprocess loadContract(String contractAddress,Web3j web3j, Credentials credentials) {
		
		return Camundaprocess.load(contractAddress, web3j, credentials, DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
	}
*/
	
	
	protected void sendMessage(Message msg) {
		try {


			String jsonMsg = mapper.writeValueAsString(msg);
			System.out.println(jsonMsg);
			
			ClientConfig clientConfig = new DefaultClientConfig();              
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			
			Client client = Client.create(clientConfig);
			
			String uri = this.baseCamundaUrl+"message";
			System.out.println("uri: "+ uri);
			WebResource webResource = client.resource(uri);
				ClientResponse response = webResource.accept("application/json").type("application/json")
			   .post(ClientResponse.class, jsonMsg);
			
			
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
			

		  } catch (Exception e) {

			e.printStackTrace();

		  }
	
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
