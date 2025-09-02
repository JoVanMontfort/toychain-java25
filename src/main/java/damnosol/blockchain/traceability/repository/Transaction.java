package damnosol.blockchain.traceability.repository;

public record Transaction(String from, String to, String product, String description) {
    @Override
    public String toString() {
        return "[Product: %s, From: %s, To: %s, Note: %s]".formatted(product, from, to, description);
    }
}