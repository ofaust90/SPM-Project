/**
* @author: Oliver Faust
* This class handles the camunda api calling. Various api calls are implemented like starting the process and sending messages
*/

/*Singleton*/
var $camundaManager = null;
function getCamundaManagerInstance() {

	if ($camundaManager == null) {
		$camundaManager = new CamundaManager();
	}
	return $camundaManager;
}

//CamundaManager Class
function CamundaManager(){
  
  this.BASE_CAMUNDA_URL = "http://localhost:8080/engine-rest/";
//  this.PROCESS_DEFINITION_ID = "test_process:24:cc280bdb-5c31-11e9-8025-6e16e9d4eabd";
  this.PROCESS_DEFINITION_ID = "";
  
  //global variables
  this.ajaxHelper = new AjaxHelper(this.BASE_CAMUNDA_URL);
  console.log(this.ajaxHelper);
  this.processInstanceID = 0;
  
  this.messageName = "info_req";
  



}


CamundaManager.prototype.startProcess = function(processKey, businessKey, callback){
    var _this = this;


    if(businessKey == undefined)
    {
      businessKey = guid();

    }
    var requestBody =     {
                        
                            "businessKey" : businessKey
                          };


      //get latest process definition first!
      var requestURLProcess = "process-definition?key="+processKey+"&latestVersion=true";
      _this.ajaxHelper.getData(requestURLProcess, function(response){
                            //set process definition (which process should be triggered)
                            _this.PROCESS_DEFINITION_ID = response[0].id;

                            var requestURL = "process-definition/"+_this.PROCESS_DEFINITION_ID+"/start";
                              //starts the lates process definition
                            _this.ajaxHelper.postData(requestURL, requestBody, function(response){
                                      console.log(response);
                                      _this.processInstanceID =  response.id;
                                      callback();
                              });

          });




}

CamundaManager.prototype.sendMessage = function(msgName, processVariables, requestBody, callback){
  var _this  = this;
  //send msg to waiting catching msg event
  var requestURLResource = "message";
  
   requestBody =     {
            "messageName" : msgName,
            "businessKey" : $processFlowController.businessKey,
            "processInstanceId": _this.processInstanceID,
        	"all" : true,
            processVariables
          };
  
  
  _this.ajaxHelper.postData(requestURLResource, requestBody, function(response) {
        console.log("msg sent");


        if(callback != undefined){
          callback();
        }

        if(response != undefined)
        {
          if(response.type == "RestException"){
            alert("Rest Exception!");
          }
        }
  });


}

CamundaManager.prototype.sendCustomMessage = function(requestBody, callback){
	  var _this  = this;
	  //send msg to waiting catching msg event
	  var requestURLResource = "message";
	  
	  
	  _this.ajaxHelper.postData(requestURLResource, requestBody, function(response) {
	        console.log("msg sent");


	        if(callback != undefined){
	          callback();
	        }

	        if(response != undefined)
	        {
	          if(response.type == "RestException"){
	            alert("Rest Exception!");
	          }
	        }
	  });


}
