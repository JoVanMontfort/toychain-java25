package damnosol.blockchain.core;

import damnosol.blockchain.core.chain.Blockchain;

public class Main {
    public static void main(String[] args) {
        var myChain = new Blockchain();

        System.out.println("Adding blocks...");
        myChain.addBlock("Alice pays Bob 10 BTC");
        myChain.addBlock("Bob pays Charlie 5 BTC");
        myChain.addBlock("Charlie pays Dave 2 BTC");

        // Print blockchain using text blocks (Java 25 feature)
        for (var block : myChain.getChain()) {
            System.out.println("""
                    ---------------------------
                    Index: %d
                    Timestamp: %d
                    Data: %s
                    Previous Hash: %s
                    Hash: %s
                    """.formatted(
                    block.index(),
                    block.timestamp(),
                    block.data(),
                    block.previousHash(),
                    block.hash()
            ));
        }

        System.out.println("\nBlockchain valid? " + myChain.isChainValid());
    }
}