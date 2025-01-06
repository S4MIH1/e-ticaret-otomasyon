package siparis;

import java.util.Date;

public class Siparis {
    private int id;
    private int musteriId;
    private Date siparisTarihi;
    private double siparisTutari;

    public Siparis(int id, int musteriId, Date siparisTarihi, double siparisTutari) {
        this.id = id;
        this.musteriId = musteriId;
        this.siparisTarihi = siparisTarihi;
        this.siparisTutari = siparisTutari;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(int musteriId) {
        this.musteriId = musteriId;
    }

    public Date getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(Date siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }

    public double getSiparisTutari() {
        return siparisTutari;
    }

    public void setSiparisTutari(double siparisTutari) {
        this.siparisTutari = siparisTutari;
    }

    @Override
    public String toString() {
        return "Siparis{" +
                "id=" + id +
                ", musteriId=" + musteriId +
                ", siparisTarihi=" + siparisTarihi +
                ", siparisTutari=" + siparisTutari +
                '}';
    }
}
