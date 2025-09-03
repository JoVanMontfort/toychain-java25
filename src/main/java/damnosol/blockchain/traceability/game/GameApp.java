package damnosol.blockchain.traceability.game;

import damnosol.blockchain.traceability.chain.Blockchain;
import damnosol.blockchain.traceability.model.Block;
import damnosol.blockchain.traceability.repository.Transaction;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class GameApp extends Application {

    private int score = 0;
    private final Random random = new Random();
    private Blockchain blockchain;
    private boolean tampered;

    // Example product journeys
    private final String[][] journeys = {
            {"Coffee Beans", "Farmer", "Distributor", "Retailer", "Customer"},
            {"Wheat", "Farmer", "Mill", "Bakery", "Customer"},
            {"Medicine", "Lab", "Distributor", "Pharmacy", "Patient"},
            {"Diamonds", "Mine", "Exporter", "Jeweler", "Customer"},
            {"Electronics", "Factory", "Warehouse", "Store", "Customer"}
    };

    @Override
    public void start(Stage stage) {
        stage.setTitle("Blockchain Trace Quest (JavaFX)");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Label scoreLabel = new Label("Score: 0");
        root.setTop(scoreLabel);

        Pane chainPane = new Pane();
        root.setCenter(chainPane);

        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        Button validBtn = new Button("Valid ✅");
        Button tamperedBtn = new Button("Tampered ❌");
        Button nextRoundBtn = new Button("Next Round 🔄");
        controls.getChildren().addAll(validBtn, tamperedBtn, nextRoundBtn);
        root.setBottom(controls);

        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.show();

        // Setup first round
        playRound(chainPane);

        validBtn.setOnAction(e -> checkAnswer(true, scoreLabel));
        tamperedBtn.setOnAction(e -> checkAnswer(false, scoreLabel));
        nextRoundBtn.setOnAction(e -> playRound(chainPane));
    }

    private void playRound(Pane chainPane) {
        chainPane.getChildren().clear();

        // Pick a random journey
        String[] journey = journeys[random.nextInt(journeys.length)];
        String product = journey[0];

        // Genesis transaction
        Transaction genesisTx = new Transaction("Origin", journey[1], product, "Harvested / created at source");
        blockchain = new Blockchain(genesisTx);

        // Add journey steps
        blockchain.addBlock(new Transaction(journey[1], journey[2], product, generateNote(product, 1)));
        blockchain.addBlock(new Transaction(journey[2], journey[3], product, generateNote(product, 2)));
        blockchain.addBlock(new Transaction(journey[3], journey[4], product, generateNote(product, 3)));

        // Maybe tamper one block
        tampered = random.nextBoolean();
        if (tampered) {
            List<Block> blocks = blockchain.getChain();
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

        // Draw blockchain visually
        double x = 50;
        double y = 150;
        for (int i = 0; i < blockchain.getChain().size(); i++) {
            Block b = blockchain.getChain().get(i);

            VBox blockBox = new VBox(5);
            blockBox.setPadding(new Insets(10));
            blockBox.setPrefWidth(200);
            blockBox.setPrefHeight(120);
            blockBox.setBackground(new Background(new BackgroundFill(
                    (tampered && i > 0 && !blockchain.isChainValid()) ? Color.SALMON : Color.LIGHTBLUE,
                    new CornerRadii(10), Insets.EMPTY
            )));
            blockBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, new CornerRadii(10), BorderWidths.DEFAULT)));

            blockBox.getChildren().addAll(
                    new Label("Block #" + b.index()),
                    new Label("Product: " + b.transaction().product()),
                    new Label("From: " + b.transaction().from()),
                    new Label("To: " + b.transaction().to()),
                    new Label("Note: " + b.transaction().description()),
                    new Label("Hash: " + b.hash().substring(0, 8) + "...")
            );

            blockBox.setLayoutX(x);
            blockBox.setLayoutY(y);
            chainPane.getChildren().add(blockBox);

            // Draw arrow to next block
            if (i < blockchain.getChain().size() - 1) {
                double startX = x + blockBox.getPrefWidth();           // right edge of current block
                double startY = y + blockBox.getPrefHeight() / 2;     // vertical center
                double endX = startX + 50;                             // small gap
                double endY = startY;

                // Main line
                Line line = new Line(startX, startY, endX, endY);
                line.setStrokeWidth(2);
                line.setStroke(Color.BLACK);
                chainPane.getChildren().add(line);

                // Arrowhead
                double arrowSize = 6;
                Line arrow1 = new Line(endX, endY, endX - arrowSize, endY - arrowSize);
                Line arrow2 = new Line(endX, endY, endX - arrowSize, endY + arrowSize);
                arrow1.setStrokeWidth(2);
                arrow2.setStrokeWidth(2);
                chainPane.getChildren().addAll(arrow1, arrow2);
            }

            x += 250; // space to next block
        }
    }

    private void checkAnswer(boolean playerSaysValid, Label scoreLabel) {
        boolean chainValid = blockchain.isChainValid();
        if (playerSaysValid == chainValid) {
            score += 10;
        } else {
            score -= 5;
        }
        scoreLabel.setText("Score: " + score);
    }

    // 🔧 Same note generation logic as console version
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

    // 🔧 Same tampering logic as console version
    private String tamperNote(String original) {
        if (original.contains("100") || original.contains("kg")) {
            return original.replaceAll("\\d+", String.valueOf(random.nextInt(400) + 200));
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

    public static void main(String[] args) {
        launch(args);
    }
}