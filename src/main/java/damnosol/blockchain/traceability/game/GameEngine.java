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

    // Example product journeys
    private final String[][] journeys = {
            {"Coffee Beans", "Farmer", "Distributor", "Retailer", "Customer"},
            {"Wheat", "Farmer", "Mill", "Bakery", "Customer"},
            {"Medicine", "Lab", "Distributor", "Pharmacy", "Patient"},
            {"Diamonds", "Mine", "Exporter", "Jeweler", "Customer"},
            {"Electronics", "Factory", "Warehouse", "Store", "Customer"}
    };

    // Dynamic note generators
    private String generateNote(String product, int step) {
        return switch (product) {
            case "Coffee Beans" -> switch (step) {
                case 1 -> "Sent " + (random.nextInt(5) + 95) + "kg of beans";
                case 2 -> "Packed into " + (random.nextInt(3) + 1) + "kg bags";
                case 3 -> "Sold to customers in local market";
                default -> "Transaction recorded";
            };
            case "Wheat" -> switch (step) {
                case 1 -> "Harvested " + (random.nextInt(20) + 80) + "kg of wheat";
                case 2 -> "Milled into flour";
                case 3 -> "Baked into bread and delivered";
                default -> "Transaction recorded";
            };
            case "Medicine" -> switch (step) {
                case 1 -> "Produced batch #" + (1000 + random.nextInt(9000));
                case 2 -> "Shipped under refrigeration";
                case 3 -> "Sold with prescription";
                default -> "Transaction recorded";
            };
            case "Diamonds" -> switch (step) {
                case 1 -> "Mined raw diamonds";
                case 2 -> "Exported with certificate";
                case 3 -> "Cut and sold as jewelry";
                default -> "Transaction recorded";
            };
            case "Electronics" -> switch (step) {
                case 1 -> "Assembled smartphones batch #" + (random.nextInt(500) + 100);
                case 2 -> "Stored in central warehouse";
                case 3 -> "Delivered to retail store";
                default -> "Transaction recorded";
            };
            default -> "Transaction recorded";
        };
    }

    // Tampering logic
    private String tamperNote(String original) {
        if (original.contains("100") || original.contains("kg")) {
            return original.replaceAll("\\d+", String.valueOf(random.nextInt(400) + 200)); // inflate numbers
        } else if (original.contains("batch")) {
            return "Fake batch #" + (random.nextInt(9999) + 10000);
        } else if (original.contains("certificate")) {
            return "Exported WITHOUT certificate";
        } else if (original.contains("refrigeration")) {
            return "Shipped without refrigeration";
        } else if (original.contains("Sold")) {
            return "Sold without receipt";
        }
        return "Data altered illegally";
    }

    public void start() {
        System.out.println("=== Welcome to Blockchain Trace Quest ===");
        System.out.println("Your mission: Audit supply chains and detect tampering!");
        System.out.println("Correct answer = +10 points, Wrong = -5 points");
        System.out.println("------------------------------------------");

        for (int round = 1; round <= 5; round++) {
            System.out.println("\n=== Round " + round + " ===");

            // Pick a random product journey
            String[] journey = journeys[random.nextInt(journeys.length)];
            String product = journey[0];

            // Generate blockchain with dynamic genesis
            Blockchain chain = new Blockchain();
            chain.addBlock(new Transaction("Origin", journey[1], product, "Harvested / created at source"));
            chain.addBlock(new Transaction(journey[1], journey[2], product, generateNote(product, 1)));
            chain.addBlock(new Transaction(journey[2], journey[3], product, generateNote(product, 2)));
            chain.addBlock(new Transaction(journey[3], journey[4], product, generateNote(product, 3)));

            // Maybe tamper with it
            boolean tampered = random.nextBoolean();
            if (tampered) {
                List<Block> blocks = chain.getChain();
                int tamperedIndex = random.nextInt(1, blocks.size()); // avoid genesis
                Transaction originalTx = blocks.get(tamperedIndex).transaction();
                String tamperedNote = tamperNote(originalTx.description());

                Transaction fakeTx = new Transaction(
                        originalTx.from(),
                        originalTx.to(),
                        originalTx.product(),
                        tamperedNote
                );

                Block tamperedBlock = new Block(
                        blocks.get(tamperedIndex).index(),
                        blocks.get(tamperedIndex).timestamp(),
                        fakeTx,
                        blocks.get(tamperedIndex).previousHash(),
                        blocks.get(tamperedIndex).hash()
                );
                blocks.set(tamperedIndex, tamperedBlock);
            }

            // Show blockchain trace
            System.out.println("Product: " + product);
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