import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.HashMap;
import java.util.List;

public class MyChainCode extends ChaincodeBase{
    @Override
    public Response init(ChaincodeStub stub) {
        byte[] isAdminExist = stub.getState("admin");

        if (isAdminExist.length == 0)
            stub.putStringState("admin", "0");

        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        HashMap<String, Integer> methodDictionary = new HashMap<>();

        methodDictionary.put("create_account", 0);
        methodDictionary.put("remit", 1);
        methodDictionary.put("balance_check", 2);
        methodDictionary.put("money_issuance", 3);
        methodDictionary.put("delete_account", 3);

        switch (methodDictionary.get(stub.getFunction())){
            // Account Opening
            case 0:
                return this.createAccount(stub);
            // Remit
            case 1:
                return this.remit(stub);
            // Balance Checking
            case 2:
                return this.balanceCheck(stub);
            // Money Issuance
            case 3:
                return this.moneyIssuance(stub);
            // Delete Account
            case 4:
                return this.deleteAccount(stub);
        }

        return newErrorResponse("Invoke Failed\n");
    }

    private Response createAccount(ChaincodeStub stub){
        // Usage : create_account <user> <deposit>
        List<String> args = stub.getParameters();
        final int requiedArgsNum = 2;

        if(requiedArgsNum != args.size())
            return newErrorResponse("[create account] Usage : create_account <user> <deposit>\n");

        return newSuccessResponse(String.format("[create account] %s created ~!\n", args.get(0)));
    }

    private Response remit(ChaincodeStub stub){
        // Usage : remit <user1> <user2> <amount>
        List<String> args = stub.getParameters();
        final int requiedArgsNum = 3;

        if(requiedArgsNum != args.size())
            return newErrorResponse("[remit] Usage : remit <user1> <user2> <amount>\n");

        return newSuccessResponse(String.format("[remit] %s -(%s)-> %s~!\n", args.get(0), args.get(2), args.get(1)));
    }

    private Response balanceCheck(ChaincodeStub stub){
        // Usage : balance_check <user>
        List<String> args = stub.getParameters();
        final int requiedArgsNum = 1;

        if(requiedArgsNum != args.size())
            return newErrorResponse("[balance check] Usage : balance_check <user>\n");

        return newSuccessResponse(String.format("[balance check] check %s ~!\n", args.get(0)));
    }

    private Response moneyIssuance(ChaincodeStub stub){
        // Usage : money_issuance <amount>
        List<String> args = stub.getParameters();
        final int requiedArgsNum = 1;

        if(requiedArgsNum != args.size())
            return newErrorResponse("[money issuance] Usage : money_issuance <amount>\n");

        try {
            int remain = Integer.parseInt(stub.getState("admin").toString());
            int issuanceAmount = Integer.parseInt(args.get(0));

            stub.putStringState("admin", new Integer(remain + issuanceAmount).toString());
        }
        catch (NumberFormatException e){
            return newErrorResponse("[money issuance] invalid argument\n");
        }
        catch (Exception e){
            return newErrorResponse("[money issuance] money issuance failed :(\n");
        }

        return newSuccessResponse("[money issuance] money issuance success~!\n");
    }

    private Response deleteAccount(ChaincodeStub stub) {
        // Usage : delete_account <user>
        List<String> args = stub.getParameters();
        final int requiedArgsNum = 1;

        if(requiedArgsNum != args.size())
            return newErrorResponse("[delete account] Usage : delete_account <user>\n");

        return newSuccessResponse(String.format("[delete account] %s successfully deleted~!\n", args.get(0)));
    }

    public static void main(String[] args) {
        System.out.println("Hello ChainCode\n");
        System.out.println("This is Main\n");
    }
}
