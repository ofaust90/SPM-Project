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
		

	}

	
	public int createInvoice(int id) {
			
		return Integer.parseInt(this.post("offer/"+id+"/createinvoice","")) ;
			
	}
	
}
