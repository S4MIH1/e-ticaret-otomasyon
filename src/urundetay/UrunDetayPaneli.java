package urundetay;


import diger.AktifKullanici;
import diger.YardimciAraclar;
import diger.Sabitler;
import diger.VeritabaniYoneticisi;
import urun.Urun;

import java.awt.*;
import javax.swing.*;

public class UrunDetayPaneli extends JPanel {

    private Urun urun;
    private CardLayout cardLayout;
    private JPanel anaPanel;
    private JPanel ustPanel;
    private JPanel altPanel;
    private JLabel baslikLabel;
    private JPanel urunBilgileriPaneli;

    private Runnable urunSilmeBasariliCallback;
    private Runnable detayEkranindanUrunSepeteEklendiCallback;

    public UrunDetayPaneli(Urun urun, CardLayout cardLayout, JPanel anaPanel) {
        this.urun = urun;
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Üst Panel: Geri Butonu ve Başlık
        ustPanel = new JPanel(new BorderLayout());
        JButton geriButton = new JButton("← Ana Sayfaya Dön");
        geriButton.setFont(new Font("Arial", Font.PLAIN, 12));
        geriButton.setMargin(new Insets(5, 10, 5, 10));
        geriButton.addActionListener(e -> anaSayfayaDon());
        ustPanel.add(geriButton, BorderLayout.WEST);

        baslikLabel = new JLabel(urun.getAd());
        baslikLabel.setFont(new Font("Arial", Font.BOLD, 24));
        baslikLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ustPanel.add(baslikLabel, BorderLayout.CENTER);
        add(ustPanel, BorderLayout.NORTH);

        // Ürün Detayları Paneli
        urunBilgileriPaneli = new JPanel(new GridLayout(3, 1, 10, 10));
        urunBilgileriPaneli.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bilgiPanelleriniUrunBilgileriPanelineEkle();
        add(urunBilgileriPaneli, BorderLayout.CENTER);

        // Alt Panel (Müşteri, Admin ve Misafir durumları)
        altPanel = new JPanel(new GridBagLayout());
        if (AktifKullanici.getAktifKullanici().getKullaniciTipi()== (Sabitler.MUSTERI)) {
            musteriAltPaneli(altPanel);
        } else if (AktifKullanici.getAktifKullanici().getKullaniciTipi() == (Sabitler.ADMIN)) {
            adminAltPaneli(altPanel);
        }
        add(altPanel, BorderLayout.SOUTH);
    }

    private void anaSayfayaDon() {
        cardLayout.show(anaPanel, "Ana Sayfa");
    }

    private JPanel urunBilgiPaneliOlustur(String baslik, String deger) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel baslikLabel = new JLabel(baslik);
        baslikLabel.setFont(new Font("Arial", Font.BOLD, 14));
        baslikLabel.setForeground(new Color(60, 60, 60));
        panel.add(baslikLabel, BorderLayout.WEST);

        JLabel degerLabel = new JLabel(deger);
        degerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        degerLabel.setForeground(new Color(80, 80, 80));
        panel.add(degerLabel, BorderLayout.CENTER);

        return panel;
    }

    private void musteriAltPaneli(JPanel altPanel) {
        JPanel adetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField adetTextField = new JTextField("1");
        adetTextField.setPreferredSize(new Dimension(40, 20));
        adetTextField.setHorizontalAlignment(JTextField.CENTER);
        adetTextField.setEditable(false);

        JButton eksiButon = new JButton("-");
        eksiButon.setPreferredSize(new Dimension(30, 20));
        eksiButon.setFont(new Font("Arial", Font.PLAIN, 12));
        eksiButon.setMargin(new Insets(0, 0, 0, 0));
        eksiButon.setBackground(Color.RED);
        eksiButon.addActionListener(e -> {
            int mevcutAdet = Integer.parseInt(adetTextField.getText());
            if (mevcutAdet > 1) {
                adetTextField.setText(String.valueOf(mevcutAdet - 1));
            }
        });

        JButton artiButon = new JButton("+");
        artiButon.setPreferredSize(new Dimension(30, 20));
        artiButon.setFont(new Font("Arial", Font.PLAIN, 12));
        artiButon.setBackground(Color.GREEN);
        artiButon.setMargin(new Insets(0, 0, 0, 0));
        artiButon.addActionListener(e -> {
            int mevcutAdet = Integer.parseInt(adetTextField.getText());
            adetTextField.setText(String.valueOf(mevcutAdet + 1));
        });

        adetPanel.add(eksiButon);
        adetPanel.add(adetTextField);
        adetPanel.add(artiButon);

        JButton sepeteEkleButonu = new JButton("Sepete Ekle");
        sepeteEkleButonu.addActionListener(e -> {
            sepeteEkle(urun, Integer.parseInt(adetTextField.getText()));
            if (detayEkranindanUrunSepeteEklendiCallback != null) {
                detayEkranindanUrunSepeteEklendiCallback.run();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        altPanel.add(adetPanel, gbc);

        gbc.gridy = 1;
        altPanel.add(sepeteEkleButonu, gbc);
    }

    private void sepeteEkle(Urun urun, int adet) {
        int toplamAdet = (AktifKullanici.getSepet().getOrDefault(urun, 0)) + adet;
        if (toplamAdet > urun.getStok()) {
            JOptionPane.showMessageDialog(this,
                    "Stokta bu kadar ürün bulunmamaktadır. \nStok bilgisi için ürün detayı ekranına gidiniz.");
        } else {
            AktifKullanici.getSepet().put(urun, toplamAdet);
            if (detayEkranindanUrunSepeteEklendiCallback != null) {
                detayEkranindanUrunSepeteEklendiCallback.run();
            }
            JOptionPane.showMessageDialog(this, adet + " adet " + urun.getAd() + " sepete eklendi!");
        }
    }

    private void adminAltPaneli(JPanel altPanel) {
        JButton duzenleButonu = new JButton("Düzenle");
        duzenleButonu.addActionListener(e -> urunDuzenleDialog());

        JButton silButonu = new JButton("Ürünü Sil");
        silButonu.setBackground(Color.RED);
        silButonu.addActionListener(e -> urunSilDialog());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 5, 10, 5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        altPanel.add(duzenleButonu, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        altPanel.add(silButonu, gridBagConstraints);
    }

    private void urunDuzenleDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ürün Düzenle", true);
        dialog.setSize(500, 350);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JTextField adField = new JTextField(urun.getAd());
        YardimciAraclar.sabitBoyutOlustur(adField, new Dimension(200, 40));

        JTextArea detayArea = new JTextArea(urun.getDetay());
        detayArea.setLineWrap(true);
        JScrollPane detayScrollPane = new JScrollPane(detayArea);
        YardimciAraclar.sabitBoyutOlustur(detayScrollPane, new Dimension(200, 50));

        JTextField stokField = new JTextField(String.valueOf(urun.getStok()));
        YardimciAraclar.sabitBoyutOlustur(stokField, new Dimension(200, 30));
        YardimciAraclar.sadeceSayiyaIzinVer(stokField);

        JTextField fiyatField = new JTextField(String.valueOf(urun.getFiyat()));
        YardimciAraclar.sabitBoyutOlustur(fiyatField, new Dimension(200, 30));
        YardimciAraclar.sadeceOndalikSayiyaIzinVer(fiyatField);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        dialog.add(new JLabel("Ad:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        dialog.add(adField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        dialog.add(new JLabel("Detay:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        dialog.add(detayScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        dialog.add(new JLabel("Stok:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        dialog.add(stokField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        dialog.add(new JLabel("Fiyat:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        dialog.add(fiyatField, gridBagConstraints);

        JButton kaydetButonu = new JButton("Kaydet");
        YardimciAraclar.sabitBoyutOlustur(kaydetButonu, new Dimension(150, 30));

        kaydetButonu.addActionListener(e -> {
            // Boş veya hatalı doldurulmuş bir bilgi var mı kontrol et
            StringBuilder hataMesaji = new StringBuilder();
            adFieldDoldurulduMuKontrolEt(adField, hataMesaji);
            detayFieldDoldurulduMuKontrolEt(detayArea, hataMesaji);
            stokFieldDoldurulduMuKontrolEt(stokField, hataMesaji);
            fiyatFieldDoldurulduMuKontrolEt(fiyatField, hataMesaji);

            // Hata mesajı yoksa güncelleme işlemini gerçekleştir
            if (hataMesaji.length() == 0) {
                VeritabaniYoneticisi.urunGuncelle(
                        new Urun(urun.getId(), adField.getText(), detayArea.getText(),
                                Double.parseDouble(fiyatField.getText()), Integer.parseInt(stokField.getText()))
                );
                urun = VeritabaniYoneticisi.istenenUrunuAl(urun.getId());
                urunBilgileriGuncelle();
                JOptionPane.showMessageDialog(UrunDetayPaneli.this, "Ürün güncellendi!");
                dialog.dispose();
            } else { // Hata mesajı varsa dialog göster
                hataMesajiDialogGoster(hataMesaji);
            }
        });

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        dialog.add(kaydetButonu, gridBagConstraints);

        dialog.setVisible(true);
    }

    private void urunBilgileriGuncelle() {
        baslikLabel.setText(urun.getAd());
        urunBilgileriPaneli.removeAll(); // Bilgiler güncellendikten sonra bilgi panellerini kaldır
        bilgiPanelleriniUrunBilgileriPanelineEkle(); // Yeni bilgilerle tekrar bilgi panellerini ekle

        // urunBilgilerPanelini ekrana tekrar çizdir. Böylece güncelleme sonrası yeni bilgiler görünsün
        SwingUtilities.invokeLater(() -> {
            urunBilgileriPaneli.revalidate();
            urunBilgileriPaneli.repaint();
        });
    }

    private void bilgiPanelleriniUrunBilgileriPanelineEkle() {
        urunBilgileriPaneli.add(urunBilgiPaneliOlustur("Detay: ", urun.getDetay()));
        urunBilgileriPaneli.add(urunBilgiPaneliOlustur("Stok: ", String.valueOf(urun.getStok())));
        urunBilgileriPaneli.add(urunBilgiPaneliOlustur("Fiyat: ", urun.getFiyat() + " TL"));
    }

    private void adFieldDoldurulduMuKontrolEt(JTextField adField, StringBuilder hataMesaji) {
        if (adField.getText().isEmpty()) {
            hataMesaji.append("Ad alanı boş bırakılamaz.\n");
        }
    }

    private void detayFieldDoldurulduMuKontrolEt(JTextArea detayArea, StringBuilder hataMesaji) {
        if (detayArea.getText().isEmpty()) {
            hataMesaji.append("Detay alanı boş bırakılamaz.\n");
        } else if (detayArea.getText().length() < 10) {
            hataMesaji.append("Detay 10 karakterden kısa olamaz.\n");
        }
    }

    private void stokFieldDoldurulduMuKontrolEt(JTextField stokField, StringBuilder hataMesaji) {
        if (stokField.getText().isEmpty()) {
            hataMesaji.append("Stok alanı boş bırakılamaz.\n");
        } else {
            try {
                if (Integer.parseInt(stokField.getText()) < 0) {
                    hataMesaji.append("Stok negatif bir değer olamaz.\n");
                }
            } catch (NumberFormatException e) {
                hataMesaji.append("Stok sadece sayı içermelidir.\n");
            }
        }
    }

    private void fiyatFieldDoldurulduMuKontrolEt(JTextField fiyatField, StringBuilder hataMesaji) {
        if (fiyatField.getText().isEmpty()) {
            hataMesaji.append("Fiyat alanı boş bırakılamaz.\n");
        } else {
            try {
                if (Double.parseDouble(fiyatField.getText()) <= 0) {
                    hataMesaji.append("Fiyat sıfırdan büyük olmalıdır.\n");
                }
            } catch (NumberFormatException e) {
                hataMesaji.append("Fiyat yalnızca ondalık sayı olmalıdır.\n");
            }
        }
    }

    private void hataMesajiDialogGoster(StringBuilder hataMesaji) {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                hataMesaji.toString(),
                "Eksik veya Hatalı Veri Girişi",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void urunSilDialog() {
        int tercih = JOptionPane.showConfirmDialog(this,
                "Bu ürünü silmek istediğinizden emin misiniz?", "Ürün Silme", JOptionPane.YES_NO_OPTION);

        if (tercih == JOptionPane.YES_OPTION) {
            VeritabaniYoneticisi.urunSil(urun.getId());
            if (urunSilmeBasariliCallback != null) {
                urunSilmeBasariliCallback.run();
            }
            cardLayout.show(anaPanel, "Ana Sayfa");
        }
    }



    public void setUrunSilmeBasariliCallback(Runnable callback) {
        this.urunSilmeBasariliCallback = callback;
    }

    public void setDetayEkranindanUrunSepeteEklendiCallback(Runnable callback) {
        this.detayEkranindanUrunSepeteEklendiCallback = callback;
    }
}

