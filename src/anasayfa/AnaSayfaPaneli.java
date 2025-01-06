package anasayfa;

import diger.AktifKullanici;
import diger.YardimciAraclar;
import diger.Sabitler;
import diger.VeritabaniYoneticisi;
import urun.Urun;
import urundetay.UrunDetayPaneli;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static diger.YardimciAraclar.*;

public class AnaSayfaPaneli extends JPanel {

    private CardLayout cardLayout;
    private JPanel anaPanel;
    private JPanel urunListesiPanel;
    private ArrayList<Urun> urunlerListesi;
    private Runnable sepeteUrunEklendiCallback;

    public AnaSayfaPaneli(CardLayout cardLayout, JPanel anaPanel) {
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;
        this.urunlerListesi = VeritabaniYoneticisi.tumUrunleriAl();
        setLayout(new BorderLayout());

        kullaniciAdminseArayuzuDuzenle();

        urunListesiPanel = new JPanel();
        urunListesiPanel.setLayout(new BoxLayout(urunListesiPanel, BoxLayout.Y_AXIS));
        urunListesiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Urun urun : urunlerListesi) {
            urunuEkranaEkle(urun);
        }

        ekraniGuncelle();

        JScrollPane scrollPane = new JScrollPane(urunListesiPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void urunuEkranaEkle(Urun urun) {
        JPanel urunPanel = new JPanel(new GridBagLayout());
        sabitBoyutOlustur(urunPanel, new Dimension(750, 60));
        urunPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 10, 5, 10);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel urunAdiLabel = new JLabel(urun.getAd());
        urunAdiLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weightx = 1.0;
        urunPanel.add(urunAdiLabel, gridBagConstraints);

        JLabel urunFiyatLabel = new JLabel(urun.getFiyat() + " TL");
        urunFiyatLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        urunFiyatLabel.setForeground(new Color(50, 50, 50));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.0;
        urunPanel.add(urunFiyatLabel, gridBagConstraints);

        kullaniciMusteriyseArayuzuDuzenle(gridBagConstraints, urunPanel, urun);

        urunPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        urunPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                urunDetayiGoster(urun);
            }
        });

        urunListesiPanel.add(urunPanel);
        urunListesiPanel.add(Box.createVerticalStrut(10));
    }

    private void kullaniciAdminseArayuzuDuzenle() {
        if (AktifKullanici.getAktifKullanici().getKullaniciTipi() == Sabitler.ADMIN) {
            JPanel ustPanel = new JPanel(new BorderLayout());
            ustPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JButton urunEkleButonu = new JButton("Ürün Ekle");
            urunEkleButonu.setFont(new Font("Arial", Font.PLAIN, 14));
            urunEkleButonu.setBackground(new Color(8, 142, 51));
            urunEkleButonu.setForeground(Color.WHITE);
            urunEkleButonu.setFocusPainted(false);
            urunEkleButonu.addActionListener(this::urunEkleDialog);

            ustPanel.add(urunEkleButonu, BorderLayout.EAST);
            add(ustPanel, BorderLayout.NORTH);
        }
    }

    private void kullaniciMusteriyseArayuzuDuzenle(GridBagConstraints gridBagConstraints, JPanel urunPanel, Urun urun) {
        if (AktifKullanici.getAktifKullanici().getKullaniciTipi() == Sabitler.MUSTERI) {
            JTextField adetTextField = adetTextFieldOlustur();

            JButton eksiButon = eksiButonOlustur(adetTextField);
            gridBagConstraints.gridx = 2;
            urunPanel.add(eksiButon, gridBagConstraints);

            gridBagConstraints.gridx = 3;
            urunPanel.add(adetTextField, gridBagConstraints);

            JButton artiButon = artiButonOlustur(adetTextField);
            gridBagConstraints.gridx = 4;
            urunPanel.add(artiButon, gridBagConstraints);

            JButton sepeteEkleButonu = new JButton("Sepete Ekle");
            gridBagConstraints.gridx = 5;
            urunPanel.add(sepeteEkleButonu, gridBagConstraints);

            sepeteEkleButonu.addActionListener(e -> sepeteEkle(urun, Integer.parseInt(adetTextField.getText())));
        }
    }

    private JTextField adetTextFieldOlustur() {
        JTextField adetTextField = new JTextField("1");
        adetTextField.setPreferredSize(new Dimension(40, 20));
        adetTextField.setHorizontalAlignment(JTextField.CENTER);
        adetTextField.setEditable(false);
        return adetTextField;
    }

    private JButton artiButonOlustur(JTextField adetTextField) {
        JButton artiButon = new JButton("+");
        artiButon.setPreferredSize(new Dimension(30, 20));
        artiButon.setFont(new Font("Arial", Font.PLAIN, 12));
        artiButon.setBackground(Color.GREEN);
        artiButon.setMargin(new Insets(0, 0, 0, 0));
        artiButon.addActionListener(e -> {
            int mevcutAdet = Integer.parseInt(adetTextField.getText());
            adetTextField.setText(String.valueOf(mevcutAdet + 1));
        });
        return artiButon;
    }

    private JButton eksiButonOlustur(JTextField adetTextField) {
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
        return eksiButon;
    }

    private void sepeteEkle(Urun urun, int adet) {
        int toplamAdet = AktifKullanici.getSepet().getOrDefault(urun, 0) + adet;
        if (toplamAdet > urun.getStok()) {
            JOptionPane.showMessageDialog(this, "Stokta bu kadar ürün bulunmamaktadır. \nStok bilgisi için ürün detayı ekranına gidiniz.");
        } else {
            AktifKullanici.getSepet().put(urun, toplamAdet);
            if (sepeteUrunEklendiCallback != null) {
                sepeteUrunEklendiCallback.run();
            }
            JOptionPane.showMessageDialog(this, adet + " adet " + urun.getAd() + " sepete eklendi!");
        }
    }

    private void urunDetayiGoster(Urun urun) {
        UrunDetayPaneli urunDetayPaneli = new UrunDetayPaneli(urun, cardLayout, anaPanel);
        urunDetayPaneli.setDetayEkranindanUrunSepeteEklendiCallback(() -> {
            if (sepeteUrunEklendiCallback != null) {
                sepeteUrunEklendiCallback.run();
            }
        });
        urunDetayPaneli.setUrunSilmeBasariliCallback(() -> {
            int silinenIndex = urunlerListesi.indexOf(urun);
            urunListesiPanelindenSilinenUrunuKaldir(silinenIndex);
        });
        anaPanel.add(urunDetayPaneli, "Ürün Detayı");
        cardLayout.show(anaPanel, "Ürün Detayı");
    }

    private void urunListesiPanelindenSilinenUrunuKaldir(int silinenIndex) {
        urunlerListesi.remove(silinenIndex);
        urunListesiPanel.removeAll();
        for (Urun urun : urunlerListesi) {
            urunuEkranaEkle(urun);
        }
    }

    private void ekraniGuncelle() {
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }


    private void urunEkleDialog(ActionEvent actionEvent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ürün Ekle", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JTextField adField = new JTextField();
        sabitBoyutOlustur(adField, new Dimension(200, 40));

        JTextArea detayArea = new JTextArea();
        detayArea.setLineWrap(true);
        JScrollPane detayScrollPane = new JScrollPane(detayArea);
        sabitBoyutOlustur(detayScrollPane, new Dimension(200, 50));

        JTextField stokField = new JTextField();
        sabitBoyutOlustur(stokField, new Dimension(200, 40));
        sadeceSayiyaIzinVer(stokField);

        JTextField fiyatField = new JTextField();
        sabitBoyutOlustur(fiyatField, new Dimension(200, 40));
        sadeceOndalikSayiyaIzinVer(fiyatField);

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

        JButton iptalButonu = new JButton("İptal");
        sabitBoyutOlustur(iptalButonu, new Dimension(150, 30));
        iptalButonu.addActionListener(e -> dialog.dispose());

        JButton ekleButonu = new JButton("Ekle");
        sabitBoyutOlustur(ekleButonu, new Dimension(150, 30));
        ekleButonu.addActionListener(e -> {
            StringBuilder hataMesaji = new StringBuilder();
            adFieldDoldurulduMuKontrolEt(adField, hataMesaji);
            detayFieldDoldurulduMuKontrolEt(detayArea, hataMesaji);
            stokFieldDoldurulduMuKontrolEt(stokField, hataMesaji);
            fiyatFieldDoldurulduMuKontrolEt(fiyatField, hataMesaji);

            if (hataMesaji.toString().equals("")) {
                Urun yeniUrun = new Urun(
                        0, // İlk başta sıfır çünkü veritabanı kendisi id atayacak
                        adField.getText(),
                        detayArea.getText(),
                        Double.parseDouble(fiyatField.getText()),
                        Integer.parseInt(stokField.getText())
                );
                urunVeritabaninaEklendiyseEkrandaGoster(yeniUrun, dialog);
            } else {
                hataMesajiDialogGoster(hataMesaji);
            }
        });

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        dialog.add(ekleButonu, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        dialog.add(iptalButonu, gridBagConstraints);

        dialog.setVisible(true);
    }

    public void setSepeteUrunEklendiCallback(Runnable callback) {
        this.sepeteUrunEklendiCallback = callback;
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

    private void urunVeritabaninaEklendiyseEkrandaGoster(Urun yeniUrun, JDialog dialog) {
        int yeniUrunId = VeritabaniYoneticisi.urunEkleVeIdDondur(yeniUrun);
        if (yeniUrunId != -1) {
            Urun guncellenmisYeniUrun = yeniUrun.copy(yeniUrunId); // Veritabanının atadığı id ile ürün oluşturuldu
            urunlerListesi.add(guncellenmisYeniUrun); // Oluşturulan ürün listeye eklendi
            urunuEkranaEkle(guncellenmisYeniUrun); // Ürün ekrana eklendi
            urunListesiPaneliniGuncelle(); // Ürün Listesi Paneli güncellenerek tüm elemanların ekranda gösterilmesi sağlandı
            JOptionPane.showMessageDialog(this, "Yeni ürün başarıyla eklendi!");
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ürün veritabanına eklenirken bir sorun oluştu");
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

    private void urunListesiPaneliniGuncelle() {
        SwingUtilities.invokeLater(() -> {
            urunListesiPanel.revalidate();
            urunListesiPanel.repaint();
        });
    }

}
