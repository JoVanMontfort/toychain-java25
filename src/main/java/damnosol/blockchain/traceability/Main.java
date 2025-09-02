package damnosol.blockchain.traceability;

import damnosol.blockchain.traceability.chain.Blockchain;
import damnosol.blockchain.traceability.repository.Transaction;

public class Main {
    public static void main(String[] args) {
        var supplyChain = new Blockchain();

        // Simulate supply chain steps
        supplyChain.addBlock(new Transaction("Farmer", "Distributor", "Coffee Beans", "Sent 100kg of beans"));
        supplyChain.addBlock(new Transaction("Distributor", "Retailer", "Coffee Beans", "Packed into 1kg bags"));
        supplyChain.addBlock(new Transaction("Retailer", "Customer", "Coffee Beans", "Sold to customer"));

        // Print blockchain traceability
        System.out.println("=== Supply Chain Traceability ===");
        for (var block : supplyChain.getChain()) {
            System.out.println("---------------------------");
            System.out.println("Index: " + block.index());
            System.out.println("Timestamp: " + block.timestamp());
            System.out.println("Transaction: " + block.transaction());
            System.out.println("Previous Hash: " + block.previousHash());
            System.out.println("Hash: " + block.hash());
        }

        // Validate chain
        System.out.println("\nBlockchain valid? " + supplyChain.isChainValid());
    }
}