package siparisler;

import diger.AktifKullanici;
import diger.YardimciAraclar;
import diger.Sabitler;
import diger.VeritabaniYoneticisi;
import siparis.Siparis;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class SiparislerPaneli extends JPanel {
    private ArrayList<Siparis> siparislerListesi;

    public SiparislerPaneli() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        kullaniciTipineGoreSiparisleriGoster();
    }

    public void kullaniciTipineGoreSiparisleriGoster() {
        removeAll();
        if (AktifKullanici.getAktifKullanici() != null &&
                AktifKullanici.getAktifKullanici().getKullaniciTipi() == Sabitler.ADMIN) {
            tumSiparisleriGoster();
        } else if (AktifKullanici.getAktifKullanici() != null &&
                AktifKullanici.getAktifKullanici().getKullaniciTipi() == Sabitler.MUSTERI) {
            musteriSiparisleriniGoster();
        }

        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    private void tumSiparisleriGoster() {
        siparislerListesi = VeritabaniYoneticisi.tumSiparisleriAl();

        for (Siparis siparis : siparislerListesi) {
            JPanel siparisPanel = new JPanel(new GridLayout(1, 4));
            Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
            siparisPanel.setBorder(border);
            YardimciAraclar.sabitBoyutOlustur(siparisPanel, new Dimension(600, 50));

            // Sipariş No
            JPanel siparisNoPanel = new JPanel(new GridBagLayout());
            siparisNoPanel.add(new JLabel("Sipariş No: "), createConstraints(0, 0));
            siparisNoPanel.add(new JLabel(String.valueOf(siparis.getId())), createConstraints(1, 0));
            siparisPanel.add(siparisNoPanel);

            // Müşteri Ad Soyad
            String musteriAdSoyad = "";
            var musteri = VeritabaniYoneticisi.istenenMusteriyiAl(siparis.getMusteriId());
            if (musteri != null) {
                musteriAdSoyad = musteri.getAd() + " " + musteri.getSoyad();
            }
            JPanel musteriPanel = new JPanel(new GridBagLayout());
            musteriPanel.add(new JLabel("Ad Soyad: "), createConstraints(0, 0));
            musteriPanel.add(new JLabel(musteriAdSoyad), createConstraints(1, 0));
            siparisPanel.add(musteriPanel);

            // Tarih
            JPanel tarihPanel = new JPanel(new GridBagLayout());
            tarihPanel.add(new JLabel("Tarih: "), createConstraints(0, 0));
            tarihPanel.add(new JLabel(siparis.getSiparisTarihi().toString()), createConstraints(1, 0));
            siparisPanel.add(tarihPanel);

            // Tutar
            JPanel tutarPanel = new JPanel(new GridBagLayout());
            tutarPanel.add(new JLabel("Tutar: "), createConstraints(0, 0));
            tutarPanel.add(new JLabel(siparis.getSiparisTutari() + " TL"), createConstraints(1, 0));
            siparisPanel.add(tutarPanel);

            add(siparisPanel);
        }
    }

    private void musteriSiparisleriniGoster() {
        int musteriId = AktifKullanici.getAktifKullanici() != null ? AktifKullanici.getAktifKullanici().getId() : 0;
        siparislerListesi = VeritabaniYoneticisi.musterininSiparisleriniAl(musteriId);

        for (Siparis siparis : siparislerListesi) {
            JPanel siparisPanel = new JPanel(new GridLayout(1, 3));
            Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
            siparisPanel.setBorder(border);
            YardimciAraclar.sabitBoyutOlustur(siparisPanel, new Dimension(600, 50));

            // Sipariş No
            JPanel siparisNoPanel = new JPanel(new GridBagLayout());
            siparisNoPanel.add(new JLabel("Sipariş No: "), createConstraints(0, 0));
            siparisNoPanel.add(new JLabel(String.valueOf(siparis.getId())), createConstraints(1, 0));
            siparisPanel.add(siparisNoPanel);

            // Tarih
            JPanel tarihPanel = new JPanel(new GridBagLayout());
            tarihPanel.add(new JLabel("Tarih: "), createConstraints(0, 0));
            tarihPanel.add(new JLabel(siparis.getSiparisTarihi().toString()), createConstraints(1, 0));
            siparisPanel.add(tarihPanel);

            // Tutar
            JPanel tutarPanel = new JPanel(new GridBagLayout());
            tutarPanel.add(new JLabel("Tutar: "), createConstraints(0, 0));
            tutarPanel.add(new JLabel(siparis.getSiparisTutari() + " TL"), createConstraints(1, 0));
            siparisPanel.add(tutarPanel);

            add(siparisPanel);
        }
    }

    private static GridBagConstraints createConstraints(int gridx, int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5); // Kenar boşlukları
        return constraints;
    }

    public void siparisleriGuncelle(){

    }
}
