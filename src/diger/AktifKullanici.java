package diger;

import kullanici.Kullanici;
import urun.Urun;
import java.util.HashMap;

public class AktifKullanici {
    private static Kullanici aktifKullanici = null;
    private static HashMap<Urun, Integer> sepet = new HashMap<>();

    public static Kullanici getAktifKullanici() {
        return aktifKullanici;
    }

    public static void setAktifKullanici(Kullanici aktifKullanici) {
        AktifKullanici.aktifKullanici = aktifKullanici;
    }

    public static HashMap<Urun, Integer> getSepet() {
        return sepet;
    }

    public static void setSepet(HashMap<Urun, Integer> sepet) {
        AktifKullanici.sepet = sepet;
    }
}

