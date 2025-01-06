package urun;

public class Urun {
    private int id;
    private String ad;
    private String detay;
    private double fiyat;
    private int stok;

    public Urun(int id, String ad, String detay, double fiyat, int stok) {
        this.id = id;
        this.ad = ad;
        this.detay = detay;
        this.fiyat = fiyat;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public Urun copy(int yeniUrunId) {
        return new Urun(yeniUrunId, this.ad, this.detay, this.fiyat, this.stok);
    }

    @Override
    public String toString() {
        return "Urun{" +
                "id=" + id +
                ", ad='" + ad + '\'' +
                ", detay='" + detay + '\'' +
                ", fiyat=" + fiyat +
                ", stok=" + stok +
                '}';
    }
}
