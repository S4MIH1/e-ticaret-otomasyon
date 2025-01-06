package kayit;

import diger.VeritabaniYoneticisi;
import kullanici.Musteri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.StringBuilder;

public class KayitOlPaneli extends JPanel {

    private JLabel baslik;
    private JPanel formPanel;

    private JPanel adPanel;
    private JLabel adLabel;
    private JTextField adField;

    private JPanel soyadPanel;
    private JLabel soyadLabel;
    private JTextField soyadField;

    private JPanel epostaPanel;
    private JLabel epostaLabel;
    private JTextField epostaField;

    private JPanel sifrePanel;
    private JLabel sifreLabel;
    private JPasswordField sifreField;

    private JPanel adresPanel;
    private JLabel adresLabel;
    private JTextArea adresField;

    private JButton kayitOlButonu;

    private StringBuilder hataMesaji;

    public KayitOlPaneli() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Başlık
        baslik = new JLabel("Kayıt Ol");
        baslik.setFont(baslik.getFont().deriveFont(18f));
        baslik.setAlignmentX(CENTER_ALIGNMENT);

        // Form Paneli
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Ad alanı
        adPanel = new JPanel();
        adPanel.setLayout(new BoxLayout(adPanel, BoxLayout.X_AXIS));
        adLabel = new JLabel(ayniKarakterUzunlugundaLabelOlustur(10, "Ad:"));
        adField = new JTextField(20);
        adPanel.add(adLabel);
        adPanel.add(adField);

        // Soyad alanı
        soyadPanel = new JPanel();
        soyadPanel.setLayout(new BoxLayout(soyadPanel, BoxLayout.X_AXIS));
        soyadLabel = new JLabel(ayniKarakterUzunlugundaLabelOlustur(10, "Soyad:"));
        soyadField = new JTextField(20);
        soyadPanel.add(soyadLabel);
        soyadPanel.add(soyadField);

        // E-posta alanı
        epostaPanel = new JPanel();
        epostaPanel.setLayout(new BoxLayout(epostaPanel, BoxLayout.X_AXIS));
        epostaLabel = new JLabel(ayniKarakterUzunlugundaLabelOlustur(10, "E-posta:"));
        epostaField = new JTextField(20);
        epostaPanel.add(epostaLabel);
        epostaPanel.add(epostaField);

        // Şifre alanı
        sifrePanel = new JPanel();
        sifrePanel.setLayout(new BoxLayout(sifrePanel, BoxLayout.X_AXIS));
        sifreLabel = new JLabel(ayniKarakterUzunlugundaLabelOlustur(10, "Şifre:"));
        sifreField = new JPasswordField(20);
        sifrePanel.add(sifreLabel);
        sifrePanel.add(sifreField);

        // Adres alanı
        adresPanel = new JPanel();
        adresPanel.setLayout(new BoxLayout(adresPanel, BoxLayout.X_AXIS));
        adresLabel = new JLabel(ayniKarakterUzunlugundaLabelOlustur(10, "Adres:"));
        adresField = new JTextArea(3, 20);
        adresField.setLineWrap(true);
        adresField.setWrapStyleWord(true);
        adresField.setPreferredSize(new Dimension(300, 80));

        JScrollPane adresScrollPane = new JScrollPane(adresField);
        adresPanel.add(adresLabel);
        adresPanel.add(adresScrollPane);

        // Kayıt Ol Butonu
        kayitOlButonu = new JButton("Kayıt Ol");
        kayitOlButonu.setPreferredSize(new Dimension(150, 40));
        kayitOlButonu.setAlignmentX(CENTER_ALIGNMENT);

        kayitOlButonunaActionListenerEkle();

        // Alanların maksimum boyutlarını ayarla
        int maxWidth = 300;
        adField.setMaximumSize(new Dimension(maxWidth, 40));
        soyadField.setMaximumSize(new Dimension(maxWidth, 40));
        epostaField.setMaximumSize(new Dimension(maxWidth, 40));
        sifreField.setMaximumSize(new Dimension(maxWidth, 40));

        adresScrollPane.setPreferredSize(new Dimension(300, 60));
        adresScrollPane.setMaximumSize(new Dimension(300, 60));

        // Ekrana elemanları ekle
        add(Box.createVerticalStrut(20));
        add(baslik);
        add(Box.createVerticalStrut(20));
        formPanel.add(adPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(soyadPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(epostaPanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(sifrePanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(adresPanel);
        formPanel.add(Box.createVerticalStrut(20));
        add(formPanel);
        add(kayitOlButonu);
    }

    private String ayniKarakterUzunlugundaLabelOlustur(int uzunluk, String label) {
        StringBuilder str = new StringBuilder(label);
        if (label.length() < uzunluk) {
            for (int i = 0; i < uzunluk - label.length(); i++) {
                str.append(" ");
            }
        }
        return str.toString();
    }

    private void kayitOlButonunaActionListenerEkle() {
        kayitOlButonu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tumFieldlarDoldurulduMuKontrolEt();
                epostaKullaniliyorMuKontrolEt();
                epostaFormatiniKontrolEt();
                hataMesajiYoksaKayitIsleminiGerceklestir();
            }
        });
    }

    private void tumFieldlarDoldurulduMuKontrolEt() {
        hataMesaji = new StringBuilder();
        adFieldDoldurulduMuKontrolEt();
        soyadFieldDoldurulduMuKontrolEt();
        epostaFieldDoldurulduMuKontrolEt();
        sifreFieldDoldurulduMuKontrolEt();
        adresFieldDoldurulduMuKontrolEt();
    }

    private void hataMesajiYoksaKayitIsleminiGerceklestir() {
        if (hataMesaji.toString().isEmpty()) {
            Musteri musteri = new Musteri(
                    0,
                    adField.getText(),
                    soyadField.getText(),
                    epostaField.getText(),
                    new String(sifreField.getPassword()),
                    adresField.getText()
            );
            int sonuc = VeritabaniYoneticisi.musteriEkle(musteri);
            if (sonuc > 0) {
                basariDialogGoster();
                tumFieldlariBosYap();
            }
        } else {
            hataMesajiDialogGoster();
        }
    }

    private void tumFieldlariBosYap() {
        adField.setText("");
        soyadField.setText("");
        epostaField.setText("");
        sifreField.setDocument(null);
        adresField.setText("");
    }

    private void basariDialogGoster() {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this), "Kayıt işlemi başarılı",
                "Kayıt İşlemi Başarılı", JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void hataMesajiDialogGoster() {
        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this), hataMesaji.toString(),
                "Eksik veya Hatalı Veri Girişi", JOptionPane.ERROR_MESSAGE
        );
    }

    private void adFieldDoldurulduMuKontrolEt() {
        if (adField.getText().isEmpty()) {
            hataMesaji.append("Ad alanı boş bırakılamaz \n");
        }
    }

    private void soyadFieldDoldurulduMuKontrolEt() {
        if (soyadField.getText().isEmpty()) {
            hataMesaji.append("Soyad alanı boş bırakılamaz \n");
        }
    }

    private void epostaFieldDoldurulduMuKontrolEt() {
        if (epostaField.getText().isEmpty()) {
            hataMesaji.append("E-posta alanı boş bırakılamaz \n");
        }
    }

    private void epostaKullaniliyorMuKontrolEt() {
        java.util.List<String> epostalarListesi = VeritabaniYoneticisi.tumKullaniciEpostalariniAl();
        if (epostalarListesi.contains(epostaField.getText())) {
            hataMesaji.append("Bu e-posta başka birisi tarafından kullanılmaktadır. Lütfen başka e-posta adresi giriniz.");
        }
    }

    private void epostaFormatiniKontrolEt() {
        if (!epostaFormatiGecerliMi(epostaField.getText())) {
            hataMesaji.append("E-posta formatı hatalı! Lütfen doğru formatta e-posta adresi giriniz.");
        }
    }

    private boolean epostaFormatiGecerliMi(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private void sifreFieldDoldurulduMuKontrolEt() {
        if (new String(sifreField.getPassword()).isEmpty()) {
            hataMesaji.append("Şifre alanı boş bırakılamaz \n");
        } else if (new String(sifreField.getPassword()).length() < 6) {
            hataMesaji.append("Şifre uzunluğu en az 6 karakter olmalıdır \n");
        }
    }

    private void adresFieldDoldurulduMuKontrolEt() {
        if (adresField.getText().isEmpty()) {
            hataMesaji.append("Adres alanı boş bırakılamaz \n");
        } else if (adresField.getText().length() < 10) {
            hataMesaji.append("Adres 10 karakterden kısa olamaz \n");
        }
    }
}
