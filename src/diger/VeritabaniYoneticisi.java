package diger;

import kullanici.Admin;
import kullanici.Kullanici;
import kullanici.Musteri;
import siparis.Siparis;
import urun.Urun;

import java.sql.*;
import java.util.ArrayList;

import static diger.Sabitler.*;

public class VeritabaniYoneticisi {

    // Veritabanına müşteri ekler
    public static int musteriEkle(Musteri musteri) {
        String sorgu = "INSERT INTO kullanici (ad, soyad, e_posta, sifre, kullanici_tipi, adres) VALUES (?, ?, ?, ?, ?, ?)";
        int eklenenSatirSayisi = 0;

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {

            preparedStatement.setString(1, musteri.getAd());
            preparedStatement.setString(2, musteri.getSoyad());
            preparedStatement.setString(3, musteri.getEposta());
            preparedStatement.setString(4, musteri.getSifre());
            preparedStatement.setInt(5, musteri.getKullaniciTipi());
            preparedStatement.setString(6, musteri.getAdres());

            eklenenSatirSayisi = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eklenenSatirSayisi;
    }

    // Veritabanında bulunan bir müşterinin adresini günceller
    public static int musteriAdresiGuncelle(String guncellenecekMusteriEposta, String yeniAdres) {
        String sorgu = "UPDATE kullanici SET adres = ? WHERE e_posta = ?";
        int guncellenenSatirSayisi = 0;

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {

            preparedStatement.setString(1, yeniAdres);
            preparedStatement.setString(2, guncellenecekMusteriEposta);

            guncellenenSatirSayisi = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guncellenenSatirSayisi;
    }

    // Veritabanından tüm Admin kullanıcılarının verisini alır ve liste olarak verir
    public static ArrayList<Admin> tumAdminleriAl() {
        ArrayList<Admin> adminlerListesi = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM kullanici WHERE kullanici_tipi = " + ADMIN);

            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("e_posta"),
                        resultSet.getString("sifre")
                );
                adminlerListesi.add(admin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminlerListesi;
    }

    // Veritabanından tüm müşteri verilerini alır ve liste olarak verir
    public static ArrayList<Musteri> tumMusterileriAl() {
        ArrayList<Musteri> musteriListesi = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM kullanici WHERE kullanici_tipi = " + MUSTERI);

            while (resultSet.next()) {
                Musteri musteri = new Musteri(
                        resultSet.getInt("id"),
                        resultSet.getString("ad"),
                        resultSet.getString("soyad"),
                        resultSet.getString("e_posta"),
                        resultSet.getString("sifre"),
                        resultSet.getString("adres")
                );
                musteriListesi.add(musteri);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musteriListesi;
    }

    // Tüm kullanıcı epostalarını veritabanından alır ve liste olarak verir
    public static ArrayList<String> tumKullaniciEpostalariniAl() {
        ArrayList<String> kullaniciEpostalari = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT e_posta FROM kullanici");
            while (resultSet.next()) {
                kullaniciEpostalari.add(resultSet.getString("e_posta"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kullaniciEpostalari;
    }

    // Verilen id ile istenen müşteri verisini alır
    public static Musteri istenenMusteriyiAl(int id) {
        Musteri musteri = null;

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM kullanici WHERE id = " + id);

            if (resultSet.next()) {
                musteri = new Musteri(
                        resultSet.getInt("id"),
                        resultSet.getString("ad"),
                        resultSet.getString("soyad"),
                        resultSet.getString("e_posta"),
                        resultSet.getString("sifre"),
                        resultSet.getString("adres")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musteri;
    }

    // Verilen eposta ile istenen kullanıcı verisini alır. Eposta her kullanıcı için özeldir.
    public static Kullanici istenenKullaniciyiAl(String eposta) {
        Kullanici kullanici = null;

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM kullanici WHERE e_posta = '" + eposta + "'");

            if (resultSet.next()) {
                kullanici = new Kullanici(
                        resultSet.getInt("id"),
                        resultSet.getString("ad"),
                        resultSet.getString("soyad"),
                        resultSet.getString("e_posta"),
                        resultSet.getString("sifre"),
                        resultSet.getInt("kullanici_tipi"),
                        resultSet.getString("adres")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kullanici;
    }

    // Tüm ürünlerin datasını veritabanından alır ve liste olarak verir
    public static ArrayList<Urun> tumUrunleriAl() {
        ArrayList<Urun> urunlerListesi = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM urun");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ad = resultSet.getString("ad");
                String detay = resultSet.getString("detay");
                double fiyat = resultSet.getDouble("fiyat");
                int stok = resultSet.getInt("stok");
                urunlerListesi.add(new Urun(id, ad, detay, fiyat, stok));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urunlerListesi;
    }

    // Verilen id ile istenen ürünü veritabanından alır
    public static Urun istenenUrunuAl(int urunId) {
        Urun urun = new Urun(0, "", "", 0.0, 0);

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM urun WHERE id = " + urunId);

            if (resultSet.next()) {
                urun = new Urun(
                        resultSet.getInt("id"),
                        resultSet.getString("ad"),
                        resultSet.getString("detay"),
                        resultSet.getDouble("fiyat"),
                        resultSet.getInt("stok")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urun;
    }

    // Veritabanına ürün ekler ve id döner
    public static int urunEkleVeIdDondur(Urun urun) {
        String sorgu = "INSERT INTO urun (ad, detay, fiyat, stok) VALUES (?, ?, ?, ?)";
        int olusturulanId = -1;

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             PreparedStatement preparedStatement = connection.prepareStatement(sorgu, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, urun.getAd());
            preparedStatement.setString(2, urun.getDetay());
            preparedStatement.setDouble(3, urun.getFiyat());
            preparedStatement.setInt(4, urun.getStok());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                olusturulanId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return olusturulanId;
    }

    // Veritabanından ürün siler
    public static void urunSil(int silinecekId) {
        String sorgu = "DELETE FROM urun WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE)) {
            connection.setAutoCommit(true);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
                preparedStatement.setInt(1, silinecekId);
                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Ürün başarıyla silindi.");
                } else {
                    System.out.println("Silinecek ürün bulunamadı.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Veritabanında bulunan bir ürün üzerinde güncelleme yapar
    public static void urunGuncelle(Urun yeniUrun) {
        String sorgu = "UPDATE urun SET AD = ?, DETAY = ?, FIYAT = ?, STOK = ? WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE)) {

            // PreparedStatement ile sorguyu hazırla
            try (PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
                // ? işaretlerine değerleri ata
                preparedStatement.setString(1, yeniUrun.getAd());
                preparedStatement.setString(2, yeniUrun.getDetay());
                preparedStatement.setDouble(3, yeniUrun.getFiyat());
                preparedStatement.setInt(4, yeniUrun.getStok());
                preparedStatement.setInt(5, yeniUrun.getId());

                // Sorguyu çalıştır
                int rowsUpdated = preparedStatement.executeUpdate();

                // Sonuçları kontrol et
                if (rowsUpdated > 0) {
                    System.out.println("Ürün güncellendi.");
                } else {
                    System.out.println("Belirtilen ID ile eşleşen bir kayıt bulunamadı.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Veritabanına sipariş ekler
    public static void siparisEkle(Siparis siparis) {
        String sorgu = "INSERT INTO siparis (musteriid, siparistarihi, siparistutari) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE)) {

            // PreparedStatement ile sorguyu hazırla
            try (PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
                // Tarihi uygun formata dönüştür
                java.sql.Date mySqlTarihi = new java.sql.Date(siparis.getSiparisTarihi().getTime());
                preparedStatement.setInt(1, siparis.getMusteriId());
                preparedStatement.setDate(2, mySqlTarihi);
                preparedStatement.setDouble(3, siparis.getSiparisTutari());

                // Sorguyu çalıştır
                int rowsInserted = preparedStatement.executeUpdate();

                // Sonuçları kontrol et
                if (rowsInserted > 0) {
                    System.out.println("Sipariş verisi başarıyla eklendi!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tüm siparişleri alır
    public static ArrayList<Siparis> tumSiparisleriAl() {
        ArrayList<Siparis> siparisler = new ArrayList<>();

        String sorgu = "SELECT * FROM siparis";

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE)) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sorgu)) {
                while (resultSet.next()) {
                    siparisler.add(
                            new Siparis(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("musteriid"),
                                    resultSet.getDate("siparistarihi"),
                                    resultSet.getDouble("siparistutari")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siparisler;
    }

    // İstenen müşterinin siparişlerini alır
    public static ArrayList<Siparis> musterininSiparisleriniAl(int musteriId) {
        ArrayList<Siparis> siparisler = new ArrayList<>();

        String sorgu = "SELECT * FROM siparis WHERE musteriid = ?";

        try (Connection connection = DriverManager.getConnection(URL, VERITABANI_KULLANICI, VERITABANI_SIFRE);
             PreparedStatement preparedStatement = connection.prepareStatement(sorgu)) {
            preparedStatement.setInt(1, musteriId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    siparisler.add(
                            new Siparis(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("musteriid"),
                                    resultSet.getDate("siparistarihi"),
                                    resultSet.getDouble("siparistutari")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siparisler;
    }

}
