package damnosol.blockchain.traceability.chain;

import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();

    // Constructor now requires a genesis transaction
    public Blockchain(Transaction genesisTransaction) {
        chain.add(Block.create(0, genesisTransaction, "0"));
    }

    public Block getLatestBlock() {
        return chain.getLast(); // safer than getLast() for Java < 21
    }

    public void addBlock(Transaction tx) {
        Block latest = getLatestBlock();
        Block newBlock = Block.create(chain.size(), tx, latest.hash());
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

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