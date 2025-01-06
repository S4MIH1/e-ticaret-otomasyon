package sepet;


import diger.AktifKullanici;
import diger.VeritabaniYoneticisi;
import diger.YardimciAraclar;
import siparis.Siparis;
import urun.Urun;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SepetPaneli extends JPanel {

    private Map<Urun, Integer> sepetUrunleri = AktifKullanici.getSepet();
    private JLabel toplamFiyatField = new JLabel("0.0 TL");
    private JPanel urunlerPanel;

    public SepetPaneli() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        toplamFiyatField.setFont(new Font("Arial", Font.BOLD, 14));
        toplamFiyatField.setForeground(Color.RED);

        urunlerPanel = new JPanel();
        urunlerPanel.setLayout(new BoxLayout(urunlerPanel, BoxLayout.Y_AXIS));
        urunlerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel toplamPanel = new JPanel(new BorderLayout());
        toplamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        toplamPanel.add(new JLabel("Toplam Sepet Tutarı: "), BorderLayout.WEST);
        toplamPanel.add(toplamFiyatField, BorderLayout.EAST);

        JButton siparisVerButon = new JButton("Sipariş Ver");
        siparisVerButon.addActionListener(e -> {
            if (sepetUrunleri.isEmpty()) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Sepetiniz Boş! \nSipariş vermek için lütfen ürün ekleyiniz",
                        "Boş Sepet Hatası",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                int musteriId = AktifKullanici.getAktifKullanici() != null ? AktifKullanici.getAktifKullanici().getId() : 0;
                Date tarih = new Date();
                Siparis siparis = new Siparis(0, musteriId, tarih, toplamFiyatHesapla());
                VeritabaniYoneticisi.siparisEkle(siparis);
                JOptionPane.showMessageDialog(this, "Siparişiniz başarıyla alındı!");
            }
        });

        JScrollPane urunlerScrollPane = new JScrollPane(urunlerPanel);
        YardimciAraclar.sabitBoyutOlustur(urunlerScrollPane, new Dimension(800, 400));

        add(urunlerScrollPane, BorderLayout.NORTH);
        add(toplamPanel, BorderLayout.CENTER);
        add(siparisVerButon, BorderLayout.PAGE_END);

        sepetiGuncelle();
    }

    public void sepetiGuncelle() {
        urunlerPanel.removeAll();
        sepetUrunleri = AktifKullanici.getSepet();

        for (Map.Entry<Urun, Integer> entry : sepetUrunleri.entrySet()) {
            Urun urun = entry.getKey();
            int adet = entry.getValue();

            JPanel urunPanel = new JPanel(new GridBagLayout());
            YardimciAraclar.sabitBoyutOlustur(urunPanel, new Dimension(700, 60));
            urunPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new Insets(5, 10, 5, 10);
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

            // Ürün Adı
            JLabel urunAdiLabel = new JLabel(urun.getAd()); // .ad yerine uygun getter kullanılmalı
            urunAdiLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 1.0;
            urunPanel.add(urunAdiLabel, gridBagConstraints);

            // Adet Paneli (Artır / Azalt Butonları)
            JPanel adetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JButton azaltButton = new JButton("-");
            azaltButton.setPreferredSize(new Dimension(30, 20));
            azaltButton.setFont(new Font("Arial", Font.PLAIN, 12));
            azaltButton.setMargin(new Insets(0, 0, 0, 0));
            azaltButton.setBackground(Color.RED);
            azaltButton.addActionListener(e -> {
                if (adet > 1) {
                    sepetUrunleri.put(urun, adet - 1);
                    sepetiGuncelle();
                }
            });

            JTextField adetDegeriField = new JTextField(String.valueOf(adet));
            adetDegeriField.setPreferredSize(new Dimension(40, 20));
            adetDegeriField.setHorizontalAlignment(JTextField.CENTER);
            adetDegeriField.setEditable(false);
            adetDegeriField.setBackground(Color.WHITE);

            JButton artirButton = new JButton("+");
            artirButton.setPreferredSize(new Dimension(30, 20));
            artirButton.setFont(new Font("Arial", Font.PLAIN, 12));
            artirButton.setBackground(Color.GREEN);
            artirButton.setMargin(new Insets(0, 0, 0, 0));
            artirButton.addActionListener(e -> {
                sepetUrunleri.put(urun, adet + 1);
                sepetiGuncelle();
            });

            adetPanel.add(azaltButton);
            adetPanel.add(adetDegeriField);
            adetPanel.add(artirButton);

            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.0;
            urunPanel.add(adetPanel, gridBagConstraints);

            // Tutar
            JLabel tutarLabel = new JLabel("Tutar: " + sayiyiIkiBasamagaYuvarla(adet * urun.getFiyat()) + " TL"); // .fiyat uygun şekilde bağlanmalı
            tutarLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            gridBagConstraints.gridx = 2;
            gridBagConstraints.weightx = 0.0;
            urunPanel.add(tutarLabel, gridBagConstraints);

            // Ürünü Kaldır Butonu
            JButton kaldirButton = new JButton("Kaldır");
            kaldirButton.addActionListener(e -> {
                sepetUrunleri.remove(urun);
                sepetiGuncelle();
            });

            gridBagConstraints.gridx = 3;
            gridBagConstraints.weightx = 0.0;
            urunPanel.add(kaldirButton, gridBagConstraints);

            urunlerPanel.add(urunPanel);
            urunlerPanel.add(Box.createVerticalStrut(10));
        }

        toplamFiyatGuncelle();

        SwingUtilities.invokeLater(() -> {
            urunlerPanel.revalidate();
            urunlerPanel.repaint();
        });
    }

    private void toplamFiyatGuncelle() {
        double toplamFiyat = toplamFiyatHesapla();
        String yuvarlanmisToplamFiyat = sayiyiIkiBasamagaYuvarla(toplamFiyat);
        toplamFiyatField.setText(yuvarlanmisToplamFiyat + " TL");
    }

    private double toplamFiyatHesapla() {
        double toplamFiyat = 0.0;

        // Sepetteki ürünler üzerinde gezin
        for (var entry : AktifKullanici.getSepet().entrySet()) {
            Urun urun = entry.getKey(); // Ürün nesnesi
            int miktar = entry.getValue(); // Ürünün sepetteki miktarı

            // Ürün fiyatı * miktar ekle
            toplamFiyat += urun.getFiyat() * miktar;
        }

        return toplamFiyat;
    }

    private String sayiyiIkiBasamagaYuvarla(double sayi) {
        DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(sayi);
    }
}