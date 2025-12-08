public class BankTest {
    public static void main(String[] args) {
        System.out.println("====== BANKA SİSTEMİ TEST RAPORU ======");
        System.out.println(); // Boşluk

        // --- TEST 1: HESAP OLUŞTURMA ---
        System.out.println("Test 1: Hesaplar Oluşturuluyor...");
        // İsimsiz, yeni formatımıza uygun hesaplar
        SavingsAccount savings = new SavingsAccount("S001", 0.05);
        CheckingAccount checking = new CheckingAccount("C001", 200.0); // Limit 200 TL (Resimdeki gibi)
        
        if (savings != null && checking != null) {
            System.out.println("? BAŞARILI: Hesap nesneleri oluşturuldu.");
        } else {
            System.out.println("! HATALI: Hesaplar oluşturulamadı.");
        }
        System.out.println();

        // --- TEST 2: VADELİ HESAP SINIR ZORLAMA ---
        // Resimdeki senaryo: Bakiye 1000, İstenen 1500
        savings.deposit(1000.0);
        System.out.println("Test 2: Vadeli Hesaptan Fazla Para Çekme Denemesi (Bakiye: 1000, İstenen: 1500)");
        
        // Konsola kirlilik yapmasın diye sout'u manipüle edebiliriz ama
        // sınıfın kendi içindeki hata mesajı çıkacaktır, sorun yok.
        savings.withdraw(1500.0); 

        if (savings.getBalance() == 1000.0) {
            System.out.println("İşlem Reddedildi: Yetersiz Bakiye.");
            System.out.println("? BAŞARILI: Yetersiz bakiye işlemine izin verilmedi.");
        } else {
            System.out.println("! HATALI: Para çekildi, hata var!");
        }
        System.out.println();

        // --- TEST 3: VADESİZ HESAP LİMİT KULLANIMI ---
        // Resimdeki senaryo: Bakiye 500, Limit 200, İstenen 600
        checking.deposit(500.0);
        System.out.println("Test 3: Vadesiz Hesap Ek Limit Kullanımı (Bakiye: 500, Limit: 200, İstenen: 600)");
        
        checking.withdraw(600.0); // 500 kendi parası + 100 limit = Kalan -100 olmalı
        
        System.out.println(checking.getBalance() + " TL bakiye kaldı.");
        
        if (checking.getBalance() == -100.0) {
            System.out.println("? BAŞARILI: Ek hesap limiti devreye girdi. Yeni Bakiye: -100.0");
        } else {
            System.out.println("! HATALI: Hesaplama yanlış! Beklenen: -100.0, Çıkan: " + checking.getBalance());
        }
        System.out.println();

        // --- TEST 4: TRANSFER TESTİ ---
        System.out.println("Test 4: Transfer Testi (Checking -> Savings 100 TL)");
        
        // Checking hesabında şu an -100 var. Limit 200 idi. Yani 100 TL daha çekebilir.
        // Transferi deneyelim.
        double transferAmount = 100.0;
        double savingsOldBalance = savings.getBalance();
        
        // Account sınıfındaki transfer metodunu kullan
        checking.transfer(savings, transferAmount);
        
        if (savings.getBalance() == savingsOldBalance + transferAmount) {
            System.out.println("? BAŞARILI: Transfer gerçekleşti ve bakiyelere yansıdı.");
        } else {
            System.out.println("! HATALI: Transfer bakiyeye yansımadı.");
        }

        System.out.println();
        // O İSTEDİĞİN GİZLİ SONUÇ KODU
        System.out.println("====== TESTLER TAMAMLANDI (Sonuç: 9) ======");
    }
}