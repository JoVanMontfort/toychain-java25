package damnosol.blockchain.traceability;

import damnosol.blockchain.traceability.chain.Blockchain;
import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {

    @Test
    void testGenesisBlockExists() {
        Blockchain chain = new Blockchain();
        assertEquals(1, chain.getChain().size(), "Blockchain should start with 1 genesis block");
        assertEquals("0", chain.getChain().getFirst().previousHash(), "Genesis block should have previousHash = '0'");
    }

    @Test
    void testAddBlocksAndValidateChain() {
        Blockchain chain = new Blockchain();
        chain.addBlock(new Transaction("Farmer", "Distributor", "Coffee Beans", "100kg shipment"));
        chain.addBlock(new Transaction("Distributor", "Retailer", "Coffee Beans", "Packed into 1kg bags"));

        assertEquals(3, chain.getChain().size(), "Blockchain should contain 3 blocks total");
        assertTrue(chain.isChainValid(), "Blockchain should be valid after adding blocks");
    }

    @Test
    void testTamperedChainIsInvalid() {
        Blockchain chain = new Blockchain();
        chain.addBlock(new Transaction("Farmer", "Distributor", "Coffee Beans", "100kg shipment"));
        chain.addBlock(new Transaction("Distributor", "Retailer", "Coffee Beans", "Packed into 1kg bags"));

        // Tamper with block #1 (change transaction)
        Block tamperedBlock = new Block(
                chain.getChain().get(1).index(),
                chain.getChain().get(1).timestamp(),
                new Transaction("Farmer", "Distributor", "Coffee Beans", "200kg shipment"), // changed
                chain.getChain().get(1).previousHash(),
                chain.getChain().get(1).hash()
        );
        chain.getChain().set(1, tamperedBlock);

        assertFalse(chain.isChainValid(), "Blockchain should be invalid after tampering");
    }
}