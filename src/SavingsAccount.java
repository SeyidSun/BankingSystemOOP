public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            addTransaction(new Transaction("WITHDRAW", accountNumber, accountNumber, amount));
            System.out.println(amount + " TL çekildi (Vadeli Hesap). Kalan Bakiye: " + balance);
        } else {
            System.out.println("Yetersiz bakiye! Vadeli hesaptan sadece mevcut bakiye kadar çekim yapılabilir.");
        }
    }

    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Aylık faiz hesaplar ve bakiyeye ekler
     * @return Hesaplanan faiz tutarı
     */
    public double calculateInterest() {
        if (balance > 0) {
            double monthlyInterest = balance * interestRate;
            balance += monthlyInterest;
            addTransaction(new Transaction("INTEREST", accountNumber, accountNumber, monthlyInterest));
            System.out.println("Faiz hesaplandı: " + monthlyInterest + " TL. Yeni Bakiye: " + balance);
            return monthlyInterest;
        }
        return 0.0;
    }

    /**
     * Belirli bir süre için faiz hesaplar (yatırım simülasyonu)
     * @param months Ay sayısı
     * @return Toplam faiz tutarı
     */
    public double calculateInterestForMonths(int months) {
        double totalInterest = 0.0;
        double currentBalance = balance;
        for (int i = 0; i < months; i++) {
            double monthlyInterest = currentBalance * interestRate;
            totalInterest += monthlyInterest;
            currentBalance += monthlyInterest;
        }
        return totalInterest;
    }
}