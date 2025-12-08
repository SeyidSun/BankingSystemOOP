import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends Account {
    private double overdraftLimit;
    private List<Credit> credits;

    public CheckingAccount(String accountNumber, double overdraftLimit) {
        super(accountNumber);
        this.overdraftLimit = overdraftLimit;
        this.credits = new ArrayList<>();
    }

    @Override
    public void withdraw(double amount) {
        // Bakiye + Limit yetiyorsa çekmeye izin ver
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            addTransaction(new Transaction("WITHDRAW", accountNumber, accountNumber, amount));
            System.out.println(amount + " TL çekildi (Vadesiz Hesap). Kalan Bakiye: " + balance);
        } else {
            System.out.println("Limit yetersiz! İşlem reddedildi.");
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    /**
     * Kredi başvurusu yapar
     * @param amount Kredi tutarı
     * @param termMonths Vade (ay)
     * @param interestRate Faiz oranı
     * @return Oluşturulan kredi nesnesi
     */
    public Credit applyForCredit(double amount, int termMonths, double interestRate) {
        String creditId = "CR" + accountNumber + System.currentTimeMillis();
        Credit credit = new Credit(creditId, accountNumber, amount, termMonths, interestRate);
        credits.add(credit);
        System.out.println("Kredi başvurusu oluşturuldu: " + creditId);
        return credit;
    }

    /**
     * Kredi başvurusunu onaylar ve parayı hesaba yatırır
     * @param creditId Kredi ID
     */
    public void approveCredit(String creditId) {
        for (Credit credit : credits) {
            if (credit.getCreditId().equals(creditId) && credit.getStatus().equals("PENDING")) {
                credit.approve();
                deposit(credit.getAmount());
                addTransaction(new Transaction("CREDIT", accountNumber, accountNumber, credit.getAmount()));
                System.out.println("Kredi onaylandı ve hesaba yatırıldı: " + credit.getAmount() + " TL");
                return;
            }
        }
        System.out.println("Kredi bulunamadı veya zaten işlenmiş!");
    }

    /**
     * Kredi ödemesi yapar
     * @param creditId Kredi ID
     * @param paymentAmount Ödeme tutarı
     */
    public void payCredit(String creditId, double paymentAmount) {
        for (Credit credit : credits) {
            if (credit.getCreditId().equals(creditId)) {
                if (credit.makePayment(paymentAmount)) {
                    withdraw(paymentAmount);
                    addTransaction(new Transaction("CREDIT_PAYMENT", accountNumber, accountNumber, paymentAmount));
                    System.out.println("Kredi ödemesi yapıldı: " + paymentAmount + " TL");
                    if (credit.getStatus().equals("PAID")) {
                        System.out.println("Kredi tamamen ödendi!");
                    }
                }
                return;
            }
        }
        System.out.println("Kredi bulunamadı!");
    }

    /**
     * Tüm kredileri listeler
     * @return Kredi listesi
     */
    public List<Credit> getCredits() {
        return new ArrayList<>(credits);
    }

    /**
     * Aktif kredileri listeler
     * @return Aktif kredi listesi
     */
    public List<Credit> getActiveCredits() {
        List<Credit> active = new ArrayList<>();
        for (Credit credit : credits) {
            if (credit.getStatus().equals("APPROVED")) {
                active.add(credit);
            }
        }
        return active;
    }
}