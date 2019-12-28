/**
* @author: Oliver Faust
* This Class provides functionallity to steer the process flow.
*/

/*on document ready*/
$( function() {
	var $processFlowController = getPFCInstance();
});

/*Singleton*/
var $processFlowController = null;
function getPFCInstance() {

	if ($processFlowController == null) {
		$processFlowController = new ProcessFlowController();
	}
	return $processFlowController;
}

//ProcessFlowController Class
function ProcessFlowController(){

  this.$camundaManager = new getCamundaManagerInstance();

  this.businessKey = guid();
  this.registerEventHandlers();

 	

}

ProcessFlowController.prototype.registerEventHandlers = function(){
  console.log("start registering eventhandlers");
  var _this = this;


  $("#startFormBtn").on("click", function(){

    //start new process instance in camunda
	  _this.$camundaManager.startProcess("ProductConfiguratorProcessCustomer", _this.businessKey,function(){
		  
		  
		  var customerName = $("#customerName").val() +" "+$("#customerFirstName").val()
		  $("#userNameSpan").html("<i><b>"+customerName+"</i></b>");
				  
		  var id = $("#customerID").val();
		  if(id > 0){
			  $("#typeSpan").html("Existing Customer: <i><b>"+id+"</b></i>");
		  }else{
			  $("#typeSpan").html("<i><b>New Customer</b></i>")
		  }
		
		  
		  $("#productForm").removeClass("hidden");
		  $("#startForm").addClass("hidden"); 
		  
		  
		  
	  } );
	  
	 


  });

  	$("#requestButton").on("click", function(){

  		
  		_this.sendRequestMsg();
  		
  		  });


}
 

  ProcessFlowController.prototype.sendRequestMsg = function(){

	  var _this = this;
	  var msgName = _this.$camundaManager.messageName;
	  var callback = function(){
		  
		  
		  $("#completeForm").removeClass("hidden");
		  $("#productForm").addClass("hidden"); 
		 
		  
	  };
	  
	  //product
		
	  var p_cpu = $("input[name='cpu']:checked").val();
	  var p_ram = $("input[name='ram']:checked").val();
	  var p_hdd = $("input[name='hdd']:checked").val();
	  var p_display = $("input[name='displaysize']:checked").val();
	
	  
	  var basePrice = 1200;
	  var price = basePrice;
	  if(p_cpu == "i7"){
		  price =  price + 120;
	  }
	  if(p_ram == "16GB"){
		  price = price + 200;
	  }
	  if(p_hdd == "1TB"){
		  price = price + 150;
	  }
	  if(p_display == "17inch"){
		  price = price + 80;
	  }
	  
	  var amount =  $("#amountslider").val();
	  var productDesc = "Premium Laptop: "+p_cpu+" "+p_ram+" RAM "+p_hdd+" SSD "+p_display+" Monitor";


	  var cid= $("#customerID").val();
	  
	  if(!isNumeric(cid)){
		  cid = 0;
	  }
	  
	  function isNumeric(value) {
		    return /^\d+$/.test(value);
		}
	  
      var processVariables = {

              "v_customerName" : {"value" :  $("#customerName").val(), "type": "String" } ,
              "v_customerFirstName" : {"value" : $("#customerFirstName").val(), "type": "String"},
              "v_customerEmail" : {"value" : $("#customerEmail").val(), "type": "String"},
              "v_customerPhone" : {"value" : $("#customerPhone").val(), "type": "String"},
              "v_customerAddress" : {"value" : $("#customerAddress").val(), "type": "String"},
              "v_customerZip" : {"value" : $("#customerZip").val(), "type": "Integer"},
              "v_customerID" : {"value" : $("#customerID").val(), "type": "Integer"},
				
              "v_productDesc" : {"value" : productDesc, "type": "String"},
              "v_productAmount" : {"value" : amount, "type": "Integer"}, 
              "v_productPrice" : {"value" : price, "type": "Integer"},
            
              "callbackRequested" : {"value" : $("#callbackRequested").prop("checked"), "type": "Boolean"}
              
              

          };

      
      console.log(processVariables);
      
      	_this.$camundaManager.sendMessage(msgName,processVariables, callback);  

      	
   
   
  }

