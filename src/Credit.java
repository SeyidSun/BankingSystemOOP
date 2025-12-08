public class Credit {
    private String creditId;
    private String accountNumber;
    private double amount;
    private double remainingAmount;
    private int termMonths;
    private double interestRate;
    private String status; // "PENDING", "APPROVED", "REJECTED", "PAID"
    private int paidMonths;

    public Credit(String creditId, String accountNumber, double amount, int termMonths, double interestRate) {
        this.creditId = creditId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.remainingAmount = amount;
        this.termMonths = termMonths;
        this.interestRate = interestRate;
        this.status = "PENDING";
        this.paidMonths = 0;
    }

    public String getCreditId() {
        return creditId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void approve() {
        this.status = "APPROVED";
    }

    public void reject() {
        this.status = "REJECTED";
    }

    /**
     * Aylık ödeme tutarını hesaplar
     * @return Aylık ödeme tutarı
     */
    public double calculateMonthlyPayment() {
        double totalAmount = amount * (1 + interestRate * termMonths / 12.0);
        return totalAmount / termMonths;
    }

    /**
     * Kredi ödemesi yapar
     * @param paymentAmount Ödenen tutar
     * @return Başarılı mı?
     */
    public boolean makePayment(double paymentAmount) {
        if (status.equals("APPROVED") && remainingAmount > 0) {
            double monthlyPayment = calculateMonthlyPayment();
            if (paymentAmount >= monthlyPayment) {
                remainingAmount -= monthlyPayment;
                paidMonths++;
                if (remainingAmount <= 0) {
                    remainingAmount = 0;
                    status = "PAID";
                }
                return true;
            } else {
                System.out.println("Yetersiz ödeme! Aylık ödeme: " + monthlyPayment + " TL");
                return false;
            }
        }
        return false;
    }

    /**
     * Kalan ödeme sayısını döndürür
     * @return Kalan ay sayısı
     */
    public int getRemainingMonths() {
        return termMonths - paidMonths;
    }

    @Override
    public String toString() {
        return String.format("Kredi ID: %s | Tutar: %.2f TL | Kalan: %.2f TL | Vade: %d ay | Durum: %s",
                creditId, amount, remainingAmount, termMonths, status);
    }
}

