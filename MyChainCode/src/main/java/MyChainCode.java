import com.google.protobuf.ByteString;
import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.HashMap;
import java.util.List;

// @SuppressWarnings("deprecation")
public class MyChainCode extends ChaincodeBase {

    private static Log _logger = LogFactory.getLog(MyChainCode.class);

    @Override
    public Response init(ChaincodeStub stub) {
        _logger.info("Init java simple chaincode");

        try {
            if (StringUtil.isNullOrEmpty(stub.getStringState("admin")))
                stub.putStringState("admin", "0");
        }
        catch (Exception e){
            return newErrorResponse("[init] Init Failed\n");
        }

        return newSuccessResponse("[init] Init Success\n");
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        HashMap<String, Integer> methodDictionary = new HashMap<>();

        methodDictionary.put("create_account", 0);
        methodDictionary.put("remit", 1);
        methodDictionary.put("balance_check", 2);
        methodDictionary.put("money_issuance", 3);
        methodDictionary.put("delete_account", 4);

        try {
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
        }
        catch (NullPointerException e){
            return newErrorResponse(String.format("[Invoke] %s are not supported\n", stub.getFunction()));
        }
        catch (Exception e){
            return newErrorResponse(String.format("[Invoke] %s failed\n", stub.getFunction()));
        }

        return newErrorResponse(String.format("[Invoke] %s are not supported\n", stub.getFunction()));
    }

    private Response createAccount(ChaincodeStub stub){
        // Usage : create_account <user> <deposit>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 2;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[create account] Usage : create_account <user> <deposit>\n");

        try {
            String user = args.get(0);
            int amount = Integer.parseInt(args.get(1));

            if (amount < 0){
                throw new NumberFormatException("amount must greater than 0");
            }

            if(!StringUtil.isNullOrEmpty(stub.getStringState(user))){
                throw new RuntimeException(String.format("%s already exist", user));
            }

            stub.putStringState(user, Integer.toString(amount));
        }
        catch (NumberFormatException e){
            return newErrorResponse(String.format("[create account] %s\n", e));
        }
        catch (Exception e) {
            return newErrorResponse(String.format("[create account] %s\n", e));
        }

        return newSuccessResponse(String.format("[create account] %s created ~!\n", args.get(0)));
    }

    private Response remit(ChaincodeStub stub){
        // Usage : remit <user1> <user2> <amount>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 3;
        String sender, receiver;
        int remittanceAmount = 0, senderBalance = 0, receiverBalance = 0;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[remit] Usage : remit <user1> <user2> <amount>\n");

        sender = args.get(0);
        receiver = args.get(1);

        try {
            remittanceAmount = Integer.parseInt(args.get(2));
            senderBalance = Integer.parseInt(stub.getStringState(sender));
            receiverBalance = Integer.parseInt(stub.getStringState(receiver));

            if (StringUtil.isNullOrEmpty(stub.getStringState(sender)))
                return newErrorResponse(String.format("[remit] Error: state for %s is null", sender));

            if (StringUtil.isNullOrEmpty(stub.getStringState(receiver)))
                return newErrorResponse(String.format("[remit] Error: state for %s is null", receiver));

            if(remittanceAmount < 0)
                throw new NumberFormatException("amount must greater than 0");

            if(senderBalance < remittanceAmount)
                throw new RuntimeException("remittance amount must greater than balance");

            stub.putStringState(sender, Integer.toString(senderBalance - remittanceAmount));
            stub.putStringState(receiver, Integer.toString(receiverBalance + remittanceAmount));
        }
        catch (NumberFormatException e){
            return newErrorResponse(String.format("[remit] %s\n", e));
        }
        catch (Exception e){
            return newErrorResponse(String.format("[remit] %s -(%s)-> %s fauled\n", sender, remittanceAmount, receiver));
        }

        return newSuccessResponse(String.format("[remit] %s -> %s successfully remitted~!\n", sender, receiver));
    }

    private Response balanceCheck(ChaincodeStub stub){
        // Usage : balance_check <user>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 1;
        String balance;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[balance check] Usage : balance_check <user>\n");

        try {
            balance = stub.getStringState(args.get(0));

            if (StringUtil.isNullOrEmpty(balance))
                return newErrorResponse(String.format("[balance check] Error: state for %s is null", args.get(0)));
        }
        catch (Exception e){
            return newErrorResponse(String.format("[balance check] %s balance check failed :\n", args.get(0)));
        }

        return newSuccessResponse(balance, ByteString.copyFrom(balance, UTF_8).toByteArray());
    }

    private Response moneyIssuance(ChaincodeStub stub){
        // Usage : money_issuance <amount>
        List<String> args = stub.getParameters();
        final int requiredArgsNum = 1;

        if(requiredArgsNum != args.size())
            return newErrorResponse("[money issuance] Usage : money_issuance <amount>\n");

        try {
            int issuanceAmount = Integer.parseInt(args.get(0)) + Integer.parseInt(stub.getStringState("admin"));

            stub.putStringState("admin", Integer.toString(issuanceAmount));
        }
        catch (NumberFormatException e){
            return newErrorResponse("[money issuance] invalid argument\n");
        }
        catch (Exception e){
            return newErrorResponse("[money issuance] money issuance failed\n");
        }

        return newSuccessResponse();
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

        new MyChainCode().start(args);
    }
}
