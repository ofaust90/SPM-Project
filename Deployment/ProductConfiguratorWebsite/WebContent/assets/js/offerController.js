/*on document ready*/
$( function() {
	var $offerController = getOfferInstance();
});

/*Singleton*/
var $offerController = null;
function getOfferInstance() {

	if ($offerController == null) {
		$offerController = new OfferController();
	}
	return $offerController;
}

//ProcessFlowController Class
function OfferController(){

  this.$camundaManager = new getCamundaManagerInstance();

  
  this.offerUrl = getUrlParameter('link');
  this.offerID = getUrlParameter('id');
  this.businessKey = getUrlParameter('businessKey');
  $('#offerFrame').attr('src', this.offerUrl);
  $('#bexioLink').attr('href', this.offerUrl);
  
  
  this.registerEventHandlers();

  this.informCamundaAboutOffer();

}

OfferController.prototype.informCamundaAboutOffer = function(){
	var _this = this;
	var requestBody =     {
            "messageName" : "offer_sent",
            "businessKey" : _this.businessKey,
        	"all" : true
   
          };
  
	  _this.$camundaManager.sendCustomMessage(requestBody ,function(){
		 
	  });
}  

OfferController.prototype.registerEventHandlers = function(){
  console.log("start registering eventhandlers");
  var _this = this;
  $("#acceptBtn").on("click", function(){
	  var processVariables = {

              "v_offerID" : {"value" :  _this.offerID, "type": "Integer" } ,
	  };
	  
	  var requestBody =     {
	            "messageName" : "offer_accepted",
	            "businessKey" : _this.businessKey,
	        	"all" : true,
	            processVariables
	          };
	  
		  _this.$camundaManager.sendCustomMessage(requestBody ,function(){
			  $("#acceptForm").removeClass("hidden");
			  $("#startForm").addClass("hidden"); 
		  });		  
  	});
		
  
  $("#declineBtn").on("click", function(){
	  var processVariables = {

              "v_offerID" : {"value" :  _this.offerID, "type": "Integer" } ,
	  };
	  
	  var requestBody =     {
	            "messageName" : "offer_declined",
	            "businessKey" : _this.businessKey,
	        	"all" : true,
	            processVariables
	          };
	  
		  _this.$camundaManager.sendCustomMessage( requestBody ,function(){
			  
			  $("#declineForm").removeClass("hidden");
			  $("#startForm").addClass("hidden"); 
			 
		  });		  
  	});
		  
  
  
}

