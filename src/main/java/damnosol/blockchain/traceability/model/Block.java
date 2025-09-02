package damnosol.blockchain.traceability.model;

import damnosol.blockchain.traceability.repository.Transaction;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;

public record Block(
        int index,
        long timestamp,
        Transaction transaction,
        String previousHash,
        String hash
) {
    public static Block create(int index, Transaction tx, String previousHash) {
        long timestamp = Instant.now().toEpochMilli();
        String hash = calculateHash(index, timestamp, tx.toString(), previousHash);
        return new Block(index, timestamp, tx, previousHash, hash);
    }

    public String recalculateHash() {
        return calculateHash(index, timestamp, transaction.toString(), previousHash);
    }

    private static String calculateHash(int index, long timestamp, String data, String previousHash) {
        try {
            String input = index + previousHash + timestamp + data;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}