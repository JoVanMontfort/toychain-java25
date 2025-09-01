# Blockchain Java 25 Tutorial

A **minimal blockchain implementation in Java 25** — built step by step as an educational project.  
This project demonstrates the fundamentals of blockchain:  
- Blocks with SHA-256 hashing  
- Linking blocks into a chain  
- Chain validation  
- Genesis block creation  

👉 Perfect for anyone learning **Java** and **blockchain basics**.  

---

## 🛠 Prerequisites
- **JDK 25** (latest) installed → [Download](https://jdk.java.net/25/)  
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)  

---

## 📂 Project Structure
```
src/
└── com/blockchain/tutorial/
├── Block.java # Block structure with SHA-256 hashing
├── Blockchain.java # Blockchain logic (add, validate, get latest)
└── Main.java # Demo entry point
```

---

## ▶️ Run the Project

### 1. Compile
```bash
javac -d out src/com/blockchain/tutorial/*.java
```

### 2. Run
```bash
java -cp out com.blockchain.tutorial.Main
```

### 📖 Example Output
```yaml
Adding blocks...
---------------------------
Index: 0
Timestamp: 1693589282954
Data: Genesis Block
Previous Hash: 0
Hash: e3d1f...

---------------------------
Index: 1
Timestamp: 1693589282962
Data: Alice pays Bob 10 BTC
Previous Hash: e3d1f...
Hash: a42be...

...

Blockchain valid? true
```

---

## 🚀 Next Steps

This is a learning-focused blockchain, not a production-ready one.
Here are ideas to extend it:

  + Add Proof-of-Work (mining with difficulty)
  + Store multiple transactions per block
  + Add digital signatures for authenticity
  + Build peer-to-peer networking
  + Explore Java blockchain libraries like:
    + Web3j(Ethereum)
    + BitcoinJ(Bitcoin)
    + Hyperledger Fabric SDK

---

## 📚 Learning Resources

  + Mastering Blockchain (Book)
  + Java 25 Release Notes
  + Blockchain Demo (visualization)

---

## 📜 License
MIT License — feel free to use, fork, and extend!

✨ With this project, you’ll understand how blockchains work under the hood before diving into real-world platforms like Ethereum, Hyperledger, or Corda.
