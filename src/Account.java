import java.util.ArrayList;
import java.util.List;

public abstract class Account implements ITransferable {
    protected String accountNumber;
    protected double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("DEPOSIT", accountNumber, accountNumber, amount));
            System.out.println(amount + " TL yatırıldı. Yeni Bakiye: " + balance);
        } else {
            System.out.println("Geçersiz miktar!");
        }
    }

    public abstract void withdraw(double amount);

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // ITransferable interface metodunu implement et
    @Override
    public void transfer(Account toAccount, double amount) {
        if (this.balance >= amount && amount > 0) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            transactionHistory.add(new Transaction("TRANSFER", this.accountNumber, toAccount.getAccountNumber(), amount));
            toAccount.addTransaction(new Transaction("TRANSFER", this.accountNumber, toAccount.getAccountNumber(), amount));
            System.out.println("Transfer başarılı: " + amount + " TL -> " + toAccount.getAccountNumber());
        } else {
            System.out.println("Transfer başarısız: Yetersiz bakiye!");
        }
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public void displayTransactionHistory() {
        System.out.println("\n=== İşlem Geçmişi: " + accountNumber + " ===");
        if (transactionHistory.isEmpty()) {
            System.out.println("Henüz işlem yapılmamış.");
        } else {
            for (Transaction t : transactionHistory) {
                System.out.println(t.toString());
            }
        }
        System.out.println("===============================\n");
    }

    /**
     * Belirli bir ay için aylık rapor oluşturur
     * @param month Ay (1-12)
     * @param year Yıl
     */
    public void generateMonthlyReport(int month, int year) {
        System.out.println("\n========================================");
        System.out.println("   AYLIK RAPOR - " + accountNumber);
        System.out.println("   Ay: " + month + " / " + year);
        System.out.println("========================================");
        
        List<Transaction> monthlyTransactions = new ArrayList<>();
        double totalDeposit = 0.0;
        double totalWithdraw = 0.0;
        double totalTransferIn = 0.0;
        double totalTransferOut = 0.0;
        
        for (Transaction t : transactionHistory) {
            if (t.getMonth() == month && t.getYear() == year) {
                monthlyTransactions.add(t);
                switch (t.getType()) {
                    case "DEPOSIT":
                        totalDeposit += t.getAmount();
                        break;
                    case "WITHDRAW":
                        totalWithdraw += t.getAmount();
                        break;
                    case "TRANSFER":
                        if (t.getFromAccount().equals(accountNumber)) {
                            totalTransferOut += t.getAmount();
                        } else {
                            totalTransferIn += t.getAmount();
                        }
                        break;
                }
            }
        }
        
        if (monthlyTransactions.isEmpty()) {
            System.out.println("Bu ay için işlem bulunamadı.");
        } else {
            System.out.println("\n--- İşlem Özeti ---");
            System.out.println("Toplam Para Yatırma: " + totalDeposit + " TL");
            System.out.println("Toplam Para Çekme: " + totalWithdraw + " TL");
            System.out.println("Toplam Gelen Transfer: " + totalTransferIn + " TL");
            System.out.println("Toplam Giden Transfer: " + totalTransferOut + " TL");
            System.out.println("\n--- Detaylı İşlemler ---");
            for (Transaction t : monthlyTransactions) {
                System.out.println(t.toString());
            }
        }
        
        System.out.println("\nGüncel Bakiye: " + balance + " TL");
        System.out.println("========================================\n");
    }
}