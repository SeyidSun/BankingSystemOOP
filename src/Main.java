import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   BANKA OTOMASYON SİSTEMİ (v1.0)");
        System.out.println("=========================================");
        
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ANA MENÜ ---");
            System.out.println("1. Yeni Hesap Aç");
            System.out.println("2. Para Yatır");
            System.out.println("3. Para Çek");
            System.out.println("4. Para Transferi (Havale/EFT)");
            System.out.println("5. Bakiye Sorgula");
            System.out.println("6. İşlem Geçmişi Görüntüle");
            System.out.println("7. Faiz Hesapla (Vadeli Hesap)");
            System.out.println("8. Aylık Rapor Görüntüle");
            System.out.println("9. Kredi İşlemleri");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": createAccount(); break;
                case "2": depositMoney(); break;
                case "3": withdrawMoney(); break;
                case "4": transferMoney(); break;
                case "5": showBalance(); break;
                case "6": showTransactionHistory(); break;
                case "7": calculateInterest(); break;
                case "8": showMonthlyReport(); break;
                case "9": creditOperations(); break;
                case "0": 
                    exit = true; 
                    System.out.println("Çıkış yapılıyor. İyi günler!"); 
                    break;
                default: System.out.println("Hata: Geçersiz seçim!");
            }
        }
    }

    private static void createAccount() {
        System.out.println("\n--- YENİ HESAP AÇ ---");
        System.out.print("Hesap Numarası Giriniz (Örn: 1234): ");
        String accNum = scanner.nextLine();

        System.out.println("Hesap Türü Seçin:");
        System.out.println("1 - Vadeli Hesap (Faizli)");
        System.out.println("2 - Vadesiz Hesap (Kredili)");
        System.out.print("Seçim: ");
        String type = scanner.nextLine();

        if (type.equals("1")) {
            System.out.print("Faiz Oranı Girin (Örn: 0.05): ");
            double rate = Double.parseDouble(scanner.nextLine());
            // ARTIK İSİM İSTEMİYORUZ, SADECE NO VE ORAN
            bank.addAccount(new SavingsAccount(accNum, rate));
            System.out.println(">> Vadeli hesap başarıyla açıldı.");
        } else if (type.equals("2")) {
            System.out.print("Ek Hesap Limiti Girin (Örn: 1000): ");
            double limit = Double.parseDouble(scanner.nextLine());
            // ARTIK İSİM İSTEMİYORUZ, SADECE NO VE LİMİT
            bank.addAccount(new CheckingAccount(accNum, limit));
            System.out.println(">> Vadesiz hesap basariyla acildi.");
        } else {
            System.out.println(">> Hatali secim yaptiniz!");
        }
    }

    private static void depositMoney() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null) {
            System.out.print("Yatırılacak Tutar: ");
            double amount = Double.parseDouble(scanner.nextLine());
            acc.deposit(amount);
        } else {
            System.out.println(">> HATA: Böyle bir hesap bulunamadı!");
        }
    }

    private static void withdrawMoney() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null) {
            System.out.print("Çekilecek Tutar: ");
            double amount = Double.parseDouble(scanner.nextLine());
            acc.withdraw(amount);
        } else {
            System.out.println(">> HATA: Böyle bir hesap bulunamadı!");
        }
    }

    private static void transferMoney() {
        System.out.print("\nGönderen Hesap No: ");
        String fromId = scanner.nextLine();
        Account fromAcc = bank.getAccount(fromId);

        if (fromAcc != null) {
            System.out.print("Alıcı Hesap No: ");
            String toId = scanner.nextLine();
            Account toAcc = bank.getAccount(toId);
            
            if (toAcc != null) {
                System.out.print("Gönderilecek Tutar: ");
                double amount = Double.parseDouble(scanner.nextLine());
                fromAcc.transfer(toAcc, amount);
            } else {
                System.out.println(">> Alıcı hesap bulunamadı!");
            }
        } else {
            System.out.println(">> Gönderen hesap bulunamadı!");
        }
    }

    private static void showBalance() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null) {
            System.out.println(">> Güncel Bakiye: " + acc.getBalance() + " TL");
        } else {
            System.out.println(">> HATA: Hesap bulunamadı!");
        }
    }

    private static void showTransactionHistory() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null) {
            acc.displayTransactionHistory();
        } else {
            System.out.println(">> HATA: Hesap bulunamadı!");
        }
    }

    private static void calculateInterest() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null && acc instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) acc;
            System.out.println("Faiz Oranı: " + (savings.getInterestRate() * 100) + "%");
            savings.calculateInterest();
        } else {
            System.out.println(">> HATA: Hesap bulunamadı veya vadeli hesap değil!");
        }
    }

    private static void showMonthlyReport() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null) {
            System.out.print("Ay (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.print("Yıl: ");
            int year = Integer.parseInt(scanner.nextLine());
            acc.generateMonthlyReport(month, year);
        } else {
            System.out.println(">> HATA: Hesap bulunamadı!");
        }
    }

    private static void creditOperations() {
        System.out.print("\nHesap No Giriniz: ");
        String accNum = scanner.nextLine();
        Account acc = bank.getAccount(accNum);
        
        if (acc != null && acc instanceof CheckingAccount) {
            CheckingAccount checking = (CheckingAccount) acc;
            
            System.out.println("\n--- KREDİ İŞLEMLERİ ---");
            System.out.println("1. Kredi Başvurusu");
            System.out.println("2. Kredi Onayla");
            System.out.println("3. Kredi Ödemesi");
            System.out.println("4. Kredileri Listele");
            System.out.print("Seçim: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("Kredi Tutarı: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Vade (ay): ");
                    int term = Integer.parseInt(scanner.nextLine());
                    System.out.print("Faiz Oranı (örn: 0.15): ");
                    double rate = Double.parseDouble(scanner.nextLine());
                    checking.applyForCredit(amount, term, rate);
                    break;
                case "2":
                    System.out.print("Kredi ID: ");
                    String creditId = scanner.nextLine();
                    checking.approveCredit(creditId);
                    break;
                case "3":
                    System.out.print("Kredi ID: ");
                    String payCreditId = scanner.nextLine();
                    System.out.print("Ödeme Tutarı: ");
                    double payment = Double.parseDouble(scanner.nextLine());
                    checking.payCredit(payCreditId, payment);
                    break;
                case "4":
                    System.out.println("\n--- TÜM KREDİLER ---");
                    for (Credit credit : checking.getCredits()) {
                        System.out.println(credit.toString());
                    }
                    System.out.println("\n--- AKTİF KREDİLER ---");
                    for (Credit credit : checking.getActiveCredits()) {
                        System.out.println(credit.toString());
                    }
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } else {
            System.out.println(">> HATA: Hesap bulunamadı veya vadesiz hesap değil!");
        }
    }
}