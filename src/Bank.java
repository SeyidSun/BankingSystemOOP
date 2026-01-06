import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;
    private static final String DATA_FILE = "bank_data.dat";

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        saveData(); // Her hesap eklendiğinde otomatik kaydet
    }

    /**
     * Hesabı siler
     * @param accountNumber Silinecek hesap numarası
     * @return Başarılı mı?
     */
    public boolean deleteAccount(String accountNumber) {
        Account acc = getAccount(accountNumber);
        if (acc != null) {
            accounts.remove(acc);
            saveData(); // Silme işleminden sonra kaydet
            return true;
        }
        return false;
    }

    /**
     * Tüm hesapları listeler
     */
    public void listAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("\nHenüz kayıtlı hesap bulunmamaktadır.");
        } else {
            System.out.println("\n=== KAYITLI HESAPLAR ===");
            System.out.println("Toplam Hesap Sayısı: " + accounts.size() + "\n");
            for (Account acc : accounts) {
                String accountType = acc instanceof SavingsAccount ? "Vadeli Hesap" : "Vadesiz Hesap";
                System.out.println("Hesap No: " + acc.getAccountNumber() + 
                                 " | Tür: " + accountType + 
                                 " | Bakiye: " + acc.getBalance() + " TL");
            }
            System.out.println("=======================\n");
        }
    }

    // Kullanıcının girdiği numaraya göre hesabı bulan metot
    public Account getAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null; // Hesap yoksa null döner
    }

    /**
     * Tüm hesapları dosyaya kaydeder
     */
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("Veriler kaydedildi: " + accounts.size() + " hesap");
        } catch (IOException e) {
            System.out.println("Veri kaydetme hatası: " + e.getMessage());
        }
    }

    /**
     * Dosyadan hesapları yükler
     */
    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            accounts = (List<Account>) ois.readObject();
            System.out.println("Veriler yüklendi: " + accounts.size() + " hesap bulundu");
        } catch (FileNotFoundException e) {
            System.out.println("Kayıtlı veri bulunamadı. Yeni başlangıç yapılıyor.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Veri yükleme hatası: " + e.getMessage());
        }
    }

    /**
     * Toplam hesap sayısını döndürür
     */
    public int getAccountCount() {
        return accounts.size();
    }
}
