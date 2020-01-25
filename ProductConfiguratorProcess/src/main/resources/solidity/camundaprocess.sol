pragma solidity >=0.4.22 <0.6.0;

contract CamundaProcess {

    struct Task {
        string businessKey;
        string name;
        string executor;
        string additionalInfo;


    }

    mapping(bytes32 => Task[]) private instances;

    function createCollaboration(string memory businessKey) public view returns (bytes32 instanceID){
        instanceID = keccak256(abi.encode(businessKey, block.timestamp));
        return instanceID;
    }

    function registerActivity(bytes32 instanceID, string memory businessKey, string memory taskName, string memory executor, string memory additionalInfo) public{
        instances[instanceID].push(Task(businessKey ,taskName, executor, additionalInfo));
    }

}
