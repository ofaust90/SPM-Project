package com.camunda.esi.project.ProductConfiguratorProcess.blockchain;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import com.camunda.esi.project.ProductConfiguratorProcess.blockchain.smartcontract.Camundaprocess;


public class BlockchainHelper {
	

	private static String PRIVATE_KEY = "4A8BBDBCA7A4BBD4CFE110A0D828E03C1A1024D216F45F4713B8B03077826D9C";
	private static String HTTPS_SERVER = "https://rinkeby.infura.io/v3/975ee58ff5a144c48a603e16a15cc88f";
	private static String CONTRACT_ADDRESS = "0x07560a88e5f17252a236f47ebc00d6eb7f6dffb8";
	
	private Web3j web3j;
	private Credentials credentials;
	private Camundaprocess contract;

	

	public BlockchainHelper() {
		web3j = Web3j.build(new HttpService(HTTPS_SERVER));
		
		credentials = getCredentialFromPrivateKey();
		
		contract = loadContract(CONTRACT_ADDRESS,web3j,credentials);
		
		
	}
	
	
	public byte[] createCollaboration(String businessKey) {
		byte[] instanceID = null;
	
		try {
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
			txHash = 	contract.registerActivity(instanceID, businessKey, taskName, executor, additionalInfo).send().getTransactionHash();
			System.out.println("hash of tx: "+txHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return txHash;
	}
	

	private void printWeb3Version(Web3j web3j) {
		Web3ClientVersion web3ClientVersion = null;
		try {
			 web3ClientVersion = web3j.web3ClientVersion().send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String web3CStringString = web3ClientVersion.getWeb3ClientVersion();
		System.out.println("Web3 client version: " +web3CStringString);
		
	}

	private Credentials getCredentialFromPrivateKey() {
		return Credentials.create(PRIVATE_KEY);
	}

	private Camundaprocess loadContract(String contractAddress,Web3j web3j, Credentials credentials) {
		
		return Camundaprocess.load(contractAddress, web3j, credentials, DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT);
	}
}
