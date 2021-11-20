import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTest {
    MyChainCode target;
    ChaincodeStub stub;

    @BeforeEach
    void setUp() {
        target = new MyChainCode();
    }

    @Test
    public void test() {
        System.out.println("test");
        // Assertions.assertEquals("", target.init());
    }
}
