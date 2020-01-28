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
  - Documentation
    - Includes the documentation of the project




## Run project
To run the project on your machine, you need to do the following steps:
  - Download Camunda
  - Start Camunda with start-camunda.sh/.bat
  - Copy the files from the deployment folder into camundas webapps folder
    - ProductConfiguratorProcess.war
    - CRMWebservice.war
    - ProductConfiguratorWebsite (Folder)
  - Open your webbrowser and go to the Product Configurator: http://localhost:8080/ProductConfiguratorWebsite/WebContent
  - Open Camunda Tasklist at: http://localhost:8080/camunda/app/tasklist/default/#/login
  
  For further information and login credentials, please see the appendix of the provided documentation

