import com.google.protobuf.ByteString;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.HashMap;
import java.util.List;

// @SuppressWarnings("deprecation")
public class MyChainCode extends ChaincodeBase {
    @Override
    public Response init(ChaincodeStub stub) {
        try {
            if (stub.getStringState("admin") == null)
                stub.putStringState("admin", "0");
        }
        catch (Exception e){
            return newErrorResponse("[init] Init Failed\n");
        }

        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        HashMap<String, Integer> methodDictionary = new HashMap<>();

        methodDictionary.put("create_account", 0);
        methodDictionary.put("remit", 1);
        methodDictionary.put("balance_check", 2);
        methodDictionary.put("money_issuance", 3);
        methodDictionary.put("delete_account", 4);

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
        final int requiredArgsNum = 2;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[create account] Usage : create_account <user> <deposit>\n");

        try {
            String val1 = stub.getStringState(args.get(0));
            int amount = Integer.parseInt(args.get(1));

            if (amount < 0){
                throw new NumberFormatException();
            }

            if(val1 != null){
                throw new RuntimeException("[create account] amount must greater than 0\n");
            }

            stub.putStringState(val1, Integer.toString(amount));
        }
        catch (NumberFormatException e){

        }
        catch (Exception e) {

        }

        return newSuccessResponse(String.format("[create account] %s created ~!\n", args.get(0)));
    }

    private Response remit(ChaincodeStub stub){
        // Usage : remit <user1> <user2> <amount>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 3;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[remit] Usage : remit <user1> <user2> <amount>\n");

        try {
            String val1 = stub.getStringState(args.get(0));
            String val2 = stub.getStringState(args.get(1));

            if (val1 == null)
                return newErrorResponse(String.format("[remit] Error: state for %s is null", args.get(0)));

            if (val2 == null)
                return newErrorResponse(String.format("[remit] Error: state for %s is null", args.get(1)));

            return newSuccessResponse(String.format("[remit] %s -> %s successfully remitted~!\n", val1, val2));
        }
        catch (Exception e){
            return newSuccessResponse(String.format("[remit] %s -(%s)-> %s~!\n", args.get(0), args.get(2), args.get(1)));
        }
    }

    private Response balanceCheck(ChaincodeStub stub){
        // Usage : balance_check <user>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 1;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[balance check] Usage : balance_check <user>\n");

        try {
            String val = stub.getStringState(args.get(0));

            if (val == null)
                return newErrorResponse(String.format("[balance check] Error: state for %s is null", args.get(0)));

            return newSuccessResponse(val, ByteString.copyFrom(val, UTF_8).toByteArray());
        }
        catch (Exception e){
            return newErrorResponse(String.format("[balance check] %s balance check failed :\n", args.get(0)));
        }
    }

    private Response moneyIssuance(ChaincodeStub stub){
        // Usage : money_issuance <amount>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 1;

        if(requiredArgsNum != args.size())
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
        final int requiredArgsNum = 1;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[delete account] Usage : delete_account <user>\n");

        try {
            stub.delState(args.get(0));
        }
        catch (Exception e){
            return newErrorResponse(String.format("[delete account] delete %d failed :\n", args.get(0)));
        }

        return newSuccessResponse(String.format("[delete account] %s successfully deleted~!\n", args.get(0)));
    }

    public static void main(String[] args) {
        System.out.println("Hello ChainCode\n");
        System.out.println("This is Main\n");
    }
}
