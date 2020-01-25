package com.camunda.esi.project.ProductConfiguratorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.camunda.esi.project.ProductConfiguratorProcess.model.Message;

public class ShippingNotifcationDelegate extends BaseDelegateClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Message camundaMsg = new Message();
		camundaMsg.setAll(true);
		camundaMsg.setBusinessKey((String)execution.getBusinessKey());
		camundaMsg.setMessageName("shipping_notification");
		
		super.sendMessage(camundaMsg);
		
		byte[] instanceID = (byte[]) execution.getVariable("blockchain_instanceID");;
		String txHash = blockchain.registerActivity(instanceID, execution.getBusinessKey(), execution.getCurrentActivityName(),"Peter", "Goods shipped");
		execution.setVariable("TXHASH_shipping", txHash);

	}

}
