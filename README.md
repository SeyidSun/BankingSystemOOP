## Banka Otomasyon Sistemi (OOP Project)

Bu proje, **Nesne Yönelimli Programlama** (OOP) prensiplerini kullanarak basit bir banka otomasyon sistemi simüle eder.  
Amaç; **soyut sınıf**, **interface**, **miras (inheritance)** ve **polimorfizm** gibi kavramları gerçek bir senaryo üzerinden göstermek.

---

### Özellikler

- **Hesap Türleri**
  - `SavingsAccount` (Vadeli / birikim hesabı)
  - `CheckingAccount` (Vadesiz / ek hesap limitli hesap)
- **Temel İşlemler**
  - Para yatırma (`deposit`)
  - Para çekme (`withdraw`) – her hesap tipi kendi kuralı ile
  - Hesaplar arası para transferi (`transfer`)
- **Test Senaryoları**
  - `BankTest` ile para çekme, ek hesap limiti ve transfer işlemlerinin kontrolü

---

### Proje Yapısı

- `Account`  
  Soyut temel sınıf. Tüm hesap türleri bu sınıftan türetilir.

- `SavingsAccount`  
  Sadece bakiyesi kadar para çekebilen, ek limit olmayan hesap.

- `CheckingAccount`  
  Belirli bir **ek hesap limiti (overdraft)** ile bakiyenin altına inebilen hesap.

- `ITransferable`  
  Hesaplar arası para transferi için tanımlanmış interface.

- `Transaction`  
  Transfer işlemlerini temsil eden, `ITransferable` arayüzünü implemente eden sınıf.

- `Bank`  
  Birden fazla hesabı tutan, hesap ekleme ve listeleme fonksiyonlarını sağlayan sınıf.

- `Main`  
  Örnek senaryoları çalıştıran başlangıç sınıfı.

- `BankTest`  
  Basit test senaryoları ile sistemin davranışını terminal çıktısı üzerinden kontrol eder.

---

### Nasıl Çalıştırılır?

1. Proje klasörüne geç:

   ```powershell
   cd "C:\Users\Seyid Sun\Desktop\OOP_Project"
   ```

2. Kodları derle:

   ```powershell
   javac src\*.java
   ```

3. Ana senaryoyu çalıştır:

   ```powershell
   java -cp src Main
   ```

4. Test senaryolarını çalıştır (isteğe bağlı):

   ```powershell
   java -cp src BankTest
   ```

---

### Örnek OOP Kavramları

- **Abstraction (Soyutlama):**  
  `Account` sınıfı, tüm hesapların ortak özelliklerini ve davranışlarını soyutlar.

- **Inheritance (Kalıtım):**  
  `SavingsAccount` ve `CheckingAccount`, `Account` sınıfından miras alır.

- **Polymorphism (Çok Biçimlilik):**  
  `Account` tipinden referans ile farklı hesap türleri üzerinde ortak metodların (`withdraw`, `deposit`, `transfer`) çağrılması.

- **Interface Kullanımı:**  
  `ITransferable`, transfer işlemi için bir sözleşme tanımlar ve `Account` / `Transaction` tarafından implemente edilir.

---

### Notlar

- Bu proje eğitim amaçlıdır ve gerçek bir banka uygulamasındaki güvenlik, çoklu kullanıcı yönetimi gibi ileri seviye detayları kapsamaz.
- Kodlar, console çıktısı üzerinden kolay anlaşılabilir test senaryoları üretmek için sade tutulmuştur.

