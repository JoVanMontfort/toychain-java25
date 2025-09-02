package damnosol.blockchain.core.chain;

import damnosol.blockchain.core.model.Block;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();

    public Blockchain() {
        // Genesis block
        chain.add(Block.create(0, "Genesis Block", "0"));
    }

    public Block getLatestBlock() {
        return chain.getLast();
    }

    public void addBlock(String data) {
        var latest = getLatestBlock();
        var newBlock = Block.create(chain.size(), data, latest.hash());
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