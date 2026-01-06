import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private int transactionId;
    private String type; // "DEPOSIT", "WITHDRAW", "TRANSFER"
    private String fromAccount;
    private String toAccount;
    private double amount;
    private String timestamp;

    public Transaction(String type, String fromAccount, String toAccount, double amount) {
        this.transactionId = (int) (Math.random() * 10000);
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public java.time.LocalDate getDate() {
        return java.time.LocalDateTime.parse(timestamp).toLocalDate();
    }

    public int getMonth() {
        return getDate().getMonthValue();
    }

    public int getYear() {
        return getDate().getYear();
    }

    @Override
    public String toString() {
        if (type.equals("TRANSFER")) {
            return String.format("[ID: %d] %s -> %s: %.2f TL (%s)", 
                transactionId, fromAccount, toAccount, amount, timestamp);
        } else {
            return String.format("[ID: %d] %s: %.2f TL - Hesap: %s (%s)", 
                transactionId, type, amount, fromAccount, timestamp);
        }
    }
}
