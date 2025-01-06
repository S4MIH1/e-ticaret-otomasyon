package profil;

import diger.AktifKullanici;
import diger.VeritabaniYoneticisi;
import diger.YardimciAraclar;
import siparisler.SiparislerPaneli;

import javax.swing.*;
import java.awt.*;

public class ProfilPaneli extends JPanel {

    private final CardLayout cardLayout;
    private final JPanel anaPanel;

    public ProfilPaneli(CardLayout cardLayout, JPanel anaPanel) {
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;

        if (AktifKullanici.getAktifKullanici() == null) {
            throw new IllegalStateException("ProfilPaneli başlatılmadan önce AktifKullanici set edilmelidir.");
        }

        setLayout(new BorderLayout());

        // Bilgi Paneli (Solda Labellar, Sağda Bilgiler)
        JPanel bilgiPaneli = new JPanel();
        bilgiPaneli.setLayout(new GridBagLayout());
        bilgiPaneli.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.WEST;

        JLabel adLabel = new JLabel("Ad:");
        JLabel adDegeri = new JLabel(AktifKullanici.getAktifKullanici().getAd() != null ? AktifKullanici.getAktifKullanici().getAd() : "");
        JLabel soyadLabel = new JLabel("Soyad:");
        JLabel soyadDegeri = new JLabel(AktifKullanici.getAktifKullanici().getSoyad() != null ? AktifKullanici.getAktifKullanici().getSoyad() : "");
        JLabel epostaLabel = new JLabel("E-posta:");
        JLabel epostaDegeri = new JLabel(AktifKullanici.getAktifKullanici().getEposta() != null ? AktifKullanici.getAktifKullanici().getEposta() : "");
        JLabel adresLabel = new JLabel("Adres:");
        JTextArea adresDegeri = new JTextArea(AktifKullanici.getAktifKullanici().getAdres() != null ? AktifKullanici.getAktifKullanici().getAdres() : "");
        JButton adresDuzenleButonu = new JButton("Düzenle");

        // Kullanıcı bilgilerini gösteren label'lara tasarım ekle
        kullaniciDegerleriLabelTasarimiOlustur(adDegeri);
        kullaniciDegerleriLabelTasarimiOlustur(soyadDegeri);
        kullaniciDegerleriLabelTasarimiOlustur(epostaDegeri);

        // Adres TextArea'ya tasarım ekle
        adresDegeriTextAreaTasarimiOlustur(adresDegeri);

        JScrollPane adresScrollPane = new JScrollPane(adresDegeri);
        adresScrollPane.setPreferredSize(new Dimension(300, 60));  // Sabit boyut
        adresScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        adresScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Bilgileri bilgi paneline ekle
        c.gridx = 0;
        c.gridy = 0;
        bilgiPaneli.add(adLabel, c);
        c.gridx = 1;
        bilgiPaneli.add(adDegeri, c);

        c.gridx = 0;
        c.gridy = 1;
        bilgiPaneli.add(soyadLabel, c);
        c.gridx = 1;
        bilgiPaneli.add(soyadDegeri, c);

        c.gridx = 0;
        c.gridy = 2;
        bilgiPaneli.add(epostaLabel, c);
        c.gridx = 1;
        bilgiPaneli.add(epostaDegeri, c);

        c.gridx = 0;
        c.gridy = 3;
        bilgiPaneli.add(adresLabel, c);
        c.gridx = 1;
        bilgiPaneli.add(adresScrollPane, c);
        c.gridx = 2;
        bilgiPaneli.add(adresDuzenleButonu, c);

        adresDuzenleButonu.addActionListener(e -> adresDuzenleDialogOlustur(adresDegeri));

        JButton siparislerimButonu = new JButton("Siparişlerim");
        YardimciAraclar.sabitBoyutOlustur(siparislerimButonu, new Dimension(200, 40));

        SiparislerPaneli siparislerPaneli = new SiparislerPaneli();
        anaPanel.add(siparislerPaneli, "Siparişler");
        siparislerimButonu.addActionListener(e -> {
            siparislerPaneli.kullaniciTipineGoreSiparisleriGoster();
            cardLayout.show(anaPanel, "Siparişler");

        });

        // siparislerimButonunun boyutunu ayarlayabilmek için bir panel oluştur ve butonu o panele ekle
        JPanel siparislerimPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        siparislerimPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Üst-alt boşluk
        siparislerimPanel.add(siparislerimButonu);

        add(bilgiPaneli, BorderLayout.NORTH);
        add(siparislerimPanel, BorderLayout.CENTER);
    }

    private void adresDuzenleDialogOlustur(JTextArea adresDegeri) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Adres Düzenle", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);

        // Çok satırlı ve scrollable JTextArea
        JTextArea yeniAdresField = new JTextArea(AktifKullanici.getAktifKullanici().getAdres(), 5, 20);
        yeniAdresField.setLineWrap(true);  // Satır sonuna gelince alt satıra geç
        yeniAdresField.setWrapStyleWord(true);  // Kelime bütünlüğüne dikkat et
        JScrollPane scrollPane = new JScrollPane(yeniAdresField);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Yeni Adres:"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(scrollPane);  // JScrollPane'i panele ekle

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton iptalButonu = new JButton("İptal");
        JButton guncelleButonu = new JButton("Güncelle");

        iptalButonu.addActionListener(e -> dialog.dispose());

        guncelleButonu.addActionListener(e -> {
            int guncellenenSatirSayisi = VeritabaniYoneticisi.musteriAdresiGuncelle(
                    AktifKullanici.getAktifKullanici().getEposta(),
                    yeniAdresField.getText()
            );

            // guncellenenSatirSayisi değeri 0 dan büyükse adres güncelleme işlemi başarılı olmuştur. Kullanıcıya bunu bir dialog
            // yardımıyla göster. Güncelleme başarısızsa kullanıcı bulunamamıştır. Hata mesajını göster.
            if (guncellenenSatirSayisi > 0) {
                dialog.dispose();
                AktifKullanici.getAktifKullanici().setAdres(yeniAdresField.getText());
                adresDegeri.setText(AktifKullanici.getAktifKullanici().getAdres());
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Müşteri adresi başarıyla güncellendi",
                        "Adres Güncelleme Başarılı ", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Adres güncelleme başarısız oldu, lütfen tekrar deneyiniz",
                        "Adres Güncelleme Başarısız ", JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        buttonPanel.add(iptalButonu);
        buttonPanel.add(guncelleButonu);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void kullaniciDegerleriLabelTasarimiOlustur(JLabel label) {
        label.setPreferredSize(new Dimension(300, 30));
        label.setBackground(new Color(230, 230, 250)); // Açık mor arka plan rengi
        label.setForeground(new Color(50, 50, 50));    // Yazı rengi
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Yazı tipi ve boyutu
        label.setOpaque(true);                    // Arka plan renginin görünmesi için gerekli
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1), // Dış kenar
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));       // İç padding
    }

    private void adresDegeriTextAreaTasarimiOlustur(JTextArea textArea) {
        textArea.setLineWrap(true);  // Metin satır sonuna geldiğinde alt satıra geçsin
        textArea.setWrapStyleWord(true);  // Kelime bütünlüğünü korusun
        textArea.setEditable(false);  // Sadece görüntüleme için
        textArea.setBackground(new Color(230, 230, 250));
        textArea.setForeground(new Color(50, 50, 50));
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}
