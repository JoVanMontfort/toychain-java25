# Blockchain Trace Quest – Game Manual

Welcome to **Blockchain Trace Quest**, an educational game designed to teach blockchain traceability concepts in a fun
and interactive way.

---

## Table of Contents

1. [Overview](#overview)
2. [Game Objective](#game-objective)
3. [Getting Started](#getting-started)
4. [Gameplay](#gameplay)
5. [Blocks and Transactions](#blocks-and-transactions)
6. [Scoring](#scoring)
7. [Tampering and Auditing](#tampering-and-auditing)
8. [Controls](#controls)
9. [Tips & Tricks](#tips--tricks)

---

## Overview

Blockchain Trace Quest simulates a product supply chain using blockchain technology. Each round, you will audit a
product’s journey and verify the integrity of its blockchain.  
The game highlights the **importance of immutable records**, helping you understand how tampering can be detected.

---

## Game Objective

- Audit the blockchain of various products.
- Detect if any block in the chain has been tampered with.
- Maximize your score by correctly identifying valid or tampered chains.

---

## Getting Started

1. Ensure you have **Java 21** installed.
2. Clone or download the project from GitHub:
   ```bash
   git clone https://github.com/JoVanMontfort/toychain-java25
   ```
3. Navigate to the traceability game module.
4. Run the JavaFX game using Maven:
   ```bash
   mvn clean javafx:run
   ```
5. The game window will open, showing the blockchain visualization.

---

## Gameplay

1. Each round, a product journey is randomly selected:
    * Example products: Coffee Beans, Wheat, Medicine, Diamonds, Electronics.
2. The blockchain for the product is displayed visually:
    * Blocks represent transactions.
    * Arrows indicate the flow from one block to the next.
    * Salmon-colored blocks indicate tampered blocks (if applicable).
3. Evaluate the blockchain and decide whether it is valid or tampered.

---

## Blocks and Transactions

* Each block contains:
    * Block number (index)
    * Product name
    * From → To (transaction parties)
    * Note (details about the transaction)
    * Hash (shortened for display)
* Blocks are linked together via cryptographic hashes to ensure integrity.

---

## Scoring

* Correct Answer: +10 points
* Wrong Answer: -5 points
* Final score determines your audit proficiency:
    * 30+ points: Blockchain auditor pro 🏆
    * Below 30: Keep practicing 📚

---

## Tampering and Auditing

* Some blocks may be tampered randomly to simulate fraud or errors.
* Tampering examples:
    * Changing quantity of products
    * Modifying batch numbers
    * Removing or altering certificates
    * Falsifying delivery notes
* Your task is to detect these tampered blocks by comparing the blockchain’s integrity.

---

## Controls

* Valid ✅ – Declare the blockchain as valid.
* Tampered ❌ – Declare the blockchain as tampered.
* Next Round 🔄 – Skip to the next product journey.

---

## Tips & Tricks

* Always check product flow consistency: “From” and “To” fields should match the expected journey.
* Tampered blocks are often highlighted visually (salmon color).
* Observe hashes for irregularities (shortened hash prefixes are displayed).
* Familiarize yourself with common product journeys for faster detection.

---

🚀🎮 Embark on your quest, conquer the blocks, and become the ultimate **Master of Blockchain Traceability**! 🔗🏆