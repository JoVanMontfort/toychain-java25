package damnosol.blockchain.traceability.chain;

import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();

    public Blockchain() {
        // Genesis block (start of chain)
        chain.add(Block.create(0,
                new Transaction("Origin", "Farmer", "Coffee Beans", "Harvested from farm"),
                "0"));
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Transaction tx) {
        var latest = getLatestBlock();
        var newBlock = Block.create(chain.size(), tx, latest.hash());
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            var current = chain.get(i);
            var previous = chain.get(i - 1);

            if (!current.hash().equals(current.recalculateHash())) {
                return false;
            }
            if (!current.previousHash().equals(previous.hash())) {
                return false;
            }
        }
        return true;
    }

    public List<Block> getChain() {
        return chain;
    }
}