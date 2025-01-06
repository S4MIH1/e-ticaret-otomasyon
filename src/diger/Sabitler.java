package diger;

public class Sabitler {
    // MySQL bağlantısı kurmak için gerekli olan sabit değişkenler
    public static final String URL = "jdbc:mysql://localhost:3306/eticaret";
    public static final String VERITABANI_KULLANICI = "root";
    public static final String VERITABANI_SIFRE = "root12345";

    // Kullanıcı tiplerini integer olarak tutarken anlamlı isim kullanmak için oluşturulmuş sabit değişkenler
    public static final int ADMIN = 1;
    public static final int MUSTERI = 2;
    public static final int MISAFIR = 3;
}