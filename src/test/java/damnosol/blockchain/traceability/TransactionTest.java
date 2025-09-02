package damnosol.blockchain.traceability;

import damnosol.blockchain.traceability.repository.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {

    @Test
    void testTransactionToString() {
        Transaction tx = new Transaction("Farmer", "Distributor", "Coffee Beans", "Sent 100kg of beans");

        String expected = "[Product: Coffee Beans, From: Farmer, To: Distributor, Note: Sent 100kg of beans]";
        assertEquals(expected, tx.toString());
    }
}