package damnosol.blockchain.core;

import damnosol.blockchain.core.chain.Blockchain;
import damnosol.blockchain.core.model.Block;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockchainTest {

    @Test
    void testBlockchainValidity() {
        Blockchain chain = new Blockchain();
        chain.addBlock("Alice pays Bob 10 BTC");
        chain.addBlock("Bob pays Charlie 5 BTC");
        assertTrue(chain.isChainValid(), "Blockchain should be valid after adding blocks");
    }

    @Test
    void testTamperedChain() {
        Blockchain chain = new Blockchain();
        chain.addBlock("Alice pays Bob 10 BTC");
        Block original = chain.getChain().get(1);

        Block tampered = new Block(
                original.index(),
                original.timestamp(),
                "Tampered Transaction",
                original.previousHash(),
                original.recalculateHash()
        );

        chain.getChain().set(1, tampered);
        assertFalse(chain.isChainValid(), "Blockchain should be invalid if tampered");
    }
}