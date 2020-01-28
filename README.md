# Entreprise Software Infrastructure - Camunda Project



## Folder overview:

  - BPM
    - Includes all bpmn related models as a reference. 
    - The deployed models however can be found under ProductConfiguratorProcess/src/main/resources
  - CRMWebservice
    - Includes the REST-Webservice
  - ProductConfiguratorProcess
    - Includes the camunda related source files
  - ProductConfiguratorWebsite
    - Includes the customer facing website
  - solidity
    - Includes the smart contract related files
  - Deployment
    - Includes the war files and the website which should be put into the tomacts webapps folder to run the project locally.




## Run project
To run the project on your machine, you need to do the following steps:
  - Download Camunda
  - Start Camunda with start-camunda.sh/.bat
  - Copy the files from the deployment folder into camundas webapps folder
    - ProductConfiguratorProcess.war
    - CRMWebservice.war
    - ProductConfiguratorWebsite (Folder)
  - Open your webbrowser and go to http://localhost:8080/ProductConfiguratorWebsite/WebContent

