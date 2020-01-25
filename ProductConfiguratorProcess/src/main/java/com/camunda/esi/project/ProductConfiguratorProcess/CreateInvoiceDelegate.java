package com.camunda.esi.project.ProductConfiguratorProcess;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CreateInvoiceDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		int offerID = (int) execution.getVariable("offerID");
		System.out.println("debug offer id: "+offerID);
		
		execution.setVariable("invoiceID", createInvoice(offerID));
		
		
		byte[] instanceID = (byte[]) execution.getVariable("blockchain_instanceID");;
		String txHash = blockchain.registerActivity(instanceID, execution.getBusinessKey(), execution.getCurrentActivityName(),"Billing", "Invoice created with id: "+execution.getVariable("invoiceID"));
		execution.setVariable("TXHASH_createInvoice", txHash);	

	}

	
	public int createInvoice(int id) {
			
		return Integer.parseInt(this.post("offer/"+id+"/invoice","")) ;
			
	}
	
}
