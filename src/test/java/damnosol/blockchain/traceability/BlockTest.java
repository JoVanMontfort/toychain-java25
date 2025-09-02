package damnosol.blockchain.traceability;

import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BlockTest {

    @Test
    void testBlockHashIsDeterministic() {
        Transaction tx = new Transaction("Farmer", "Distributor", "Coffee Beans", "100kg shipment");
        Block block1 = Block.create(1, tx, "0");
        Block block2 = new Block(block1.index(), block1.timestamp(), block1.transaction(), block1.previousHash(), block1.hash());

        assertEquals(block1.hash(), block2.hash(), "Hashes should match for identical block data");
    }

    @Test
    void testRecalculateHashDetectsTampering() {
        Transaction tx = new Transaction("Farmer", "Distributor", "Coffee Beans", "100kg shipment");
        Block block = Block.create(1, tx, "0");

        String originalHash = block.hash();

        // Simulate tampering: modify description
        Transaction tamperedTx = new Transaction("Farmer", "Distributor", "Coffee Beans", "200kg shipment");
        Block tamperedBlock = new Block(block.index(), block.timestamp(), tamperedTx, block.previousHash(), block.hash());

        assertNotEquals(originalHash, tamperedBlock.recalculateHash(), "Tampered block should produce a different hash");
    }
}