package kullanici;

import static diger.Sabitler.*;

public class Musteri extends Kullanici {

    public Musteri(int id, String ad, String soyad, String eposta, String sifre, String adres) {
        super(id, ad, soyad, eposta, sifre, MUSTERI, adres);
    }
}
