package damnosol.blockchain.traceability.game;

import damnosol.blockchain.traceability.chain.Blockchain;
import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameEngine {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private int score = 0;

    public void start() {
        System.out.println("=== Welcome to Blockchain Trace Quest ===");
        System.out.println("Your mission: Audit supply chains and detect tampering!");
        System.out.println("Correct answer = +10 points, Wrong = -5 points");
        System.out.println("------------------------------------------");

        for (int round = 1; round <= 5; round++) {
            System.out.println("\n=== Round " + round + " ===");

            // Generate blockchain
            Blockchain chain = new Blockchain();
            chain.addBlock(new Transaction("Farmer", "Distributor", "Coffee Beans", "Sent 100kg of beans"));
            chain.addBlock(new Transaction("Distributor", "Retailer", "Coffee Beans", "Packed into 1kg bags"));
            chain.addBlock(new Transaction("Retailer", "Customer", "Coffee Beans", "Sold"));

            // Maybe tamper with it
            boolean tampered = random.nextBoolean();
            if (tampered) {
                List<Block> blocks = chain.getChain();
                // Change data in block #1
                Transaction fakeTx = new Transaction("Farmer", "Distributor", "Coffee Beans", "Sent 500kg of beans (tampered!)");
                Block tamperedBlock = new Block(
                        blocks.get(1).index(),
                        blocks.get(1).timestamp(),
                        fakeTx,
                        blocks.get(1).previousHash(),
                        blocks.get(1).hash()
                );
                blocks.set(1, tamperedBlock);
            }

            // Show blockchain trace
            for (Block block : chain.getChain()) {
                System.out.println("Block #" + block.index() + ": " + block.transaction());
            }

            // Ask player
            System.out.print("\nIs this blockchain valid? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            boolean playerSaysValid = answer.equals("y");

            boolean chainValid = chain.isChainValid();

            if (playerSaysValid == chainValid) {
                System.out.println("✅ Correct!");
                score += 10;
            } else {
                System.out.println("❌ Wrong!");
                score -= 5;
            }

            System.out.println("Current Score: " + score);
        }

        System.out.println("\n=== Game Over ===");
        System.out.println("Final Score: " + score);
        if (score >= 30) {
            System.out.println("🏆 Great job! You're a blockchain auditor pro.");
        } else {
            System.out.println("📚 Keep practicing! The blockchain still holds secrets for you.");
        }
    }
}