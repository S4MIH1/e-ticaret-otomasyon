package kullanici;

public class Kullanici {
    private Integer id;
    private String ad;
    private String soyad;
    private String eposta;
    private String sifre;
    private int kullaniciTipi;  // 1: kullanici.Admin, 2: Müşteri, 3: kullanici.Misafir
    private String adres;

    public Kullanici(Integer id, String ad, String soyad, String eposta, String sifre, int kullaniciTipi, String adres) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.eposta = eposta;
        this.sifre = sifre;
        this.kullaniciTipi = kullaniciTipi;
        this.adres = adres;
    }

    // Getter ve Setter'lar
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public int getKullaniciTipi() {
        return kullaniciTipi;
    }

    public void setKullaniciTipi(int kullaniciTipi) {
        this.kullaniciTipi = kullaniciTipi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
