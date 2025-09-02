# 🛠 Blockchain Traceability (Java 25)

This project is a **Java 25 tutorial** that demonstrates how to build a **simple blockchain** and apply it to a **traceability use case** — tracking goods across a supply chain.

It builds on the **[blockchain-basics-java](#)** project and extends it with **transactions** that model product movement from **Farmer → Distributor → Retailer → Customer**.

---

## ✨ Features

- ✅ Immutable blockchain data structure
- ✅ SHA-256 hashing for block integrity
- ✅ Transaction model with sender, receiver, product, and description
- ✅ Supply chain traceability demo (coffee beans example)
- ✅ Validation check to detect tampering

---

## 📦 Project Structure

```
src/main/java/com/blockchain/traceability/
│
├── Transaction.java # Represents a supply chain transaction
├── Block.java # Immutable block containing a transaction
├── Blockchain.java # Manages the chain and validation
└── Main.java # Demo application
```

---

## 🚀 Getting Started

### Prerequisites
- Java 25+
- Maven 3.9+

### Run the demo

```bash
mvn compile exec:java -Dexec.mainClass="com.blockchain.traceability.Main"
```

## 📖 Example Output
```
=== Supply Chain Traceability ===
---------------------------
Index: 0
Transaction: [Product: Coffee Beans, From: Origin, To: Farmer, Note: Harvested from farm]

---------------------------
Index: 1
Transaction: [Product: Coffee Beans, From: Farmer, To: Distributor, Note: Sent 100kg of beans]

---------------------------
Index: 2
Transaction: [Product: Coffee Beans, From: Distributor, To: Retailer, Note: Packed into 1kg bags]

---------------------------
Index: 3
Transaction: [Product: Coffee Beans, From: Retailer, To: Customer, Note: Sold to customer]

Blockchain valid? true
```

---

## 🔒 Why Traceability?

Blockchain ensures:
 + Immutability → data can’t be changed without detection
 + Transparency → every step is visible
 + Auditability → full history of a product is preserved

This makes blockchain ideal for:
 + Food safety & recalls
 + Pharmaceutical supply chains
 + Luxury goods authenticity
 + Financial transaction tracking

---

## 📚 Next Steps
 + Add tampering demo (see how the chain detects fraud)
 + Add Proof-of-Work (mining) for stronger security
 + Extend to multiple products and transactions