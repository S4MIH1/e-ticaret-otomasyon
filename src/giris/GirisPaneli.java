package giris;

import diger.AktifKullanici;
import diger.VeritabaniYoneticisi;
import kullanici.Admin;
import kullanici.Kullanici;
import kullanici.Misafir;
import kullanici.Musteri;
import sifresifirlama.SifreSifirlamaPaneli;
import java.awt.*;
import javax.swing.*;

public class GirisPaneli extends JPanel {
    private JTextField epostaField;
    private JPasswordField sifreField;
    private JButton adminGirisiButonu;
    private JButton musteriGirisiButonu;
    private JButton misafirGirisiButonu;
    private JButton sifreSifirlamaButonu;
    private CardLayout cardLayout;
    private JPanel anaPanel;
    private Runnable girisBasariliCallback;

    public GirisPaneli(CardLayout cardLayout, JPanel anaPanel, Runnable girisBasariliCallback) {
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;
        this.girisBasariliCallback = girisBasariliCallback;

        setLayout(new BorderLayout());

        // İçerik Paneli (BoxLayout kullanarak ortalama)
        JPanel icerikPaneli = new JPanel();
        icerikPaneli.setLayout(new BoxLayout(icerikPaneli, BoxLayout.Y_AXIS));
        icerikPaneli.setBackground(Color.WHITE);

        // Bileşenler
        JLabel epostaEtiketi = new JLabel("E-posta:");
        epostaField = new JTextField(15);

        JLabel sifreEtiketi = new JLabel("Şifre:");
        sifreField = new JPasswordField(15);

        adminGirisiButonu = new JButton("Admin Olarak Giriş Yap");
        musteriGirisiButonu = new JButton("Müşteri Olarak Giriş Yap");

        misafirGirisiButonu = new JButton("Misafir Olarak Giriş Yap");
        Font misafirGirisiButonuFont = new Font("Misafir Girisi Buton Fontu", Font.PLAIN, 14);
        misafirGirisiButonu.setFont(misafirGirisiButonuFont);

        sifreSifirlamaButonu = new JButton("Şifreyi Sıfırla");
        sifreSifirlamaButonu.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 10));
        sifreSifirlamaButonu.setBackground(icerikPaneli.getBackground());
        sifreSifirlamaButonu.setBorder(BorderFactory.createEmptyBorder());
        sifreSifirlamaButonu.setHorizontalAlignment(SwingConstants.LEFT);

        epostaField.setMaximumSize(new Dimension(200, 30));
        sifreField.setMaximumSize(new Dimension(200, 30));
        adminGirisiButonu.setMaximumSize(new Dimension(200, 30));
        musteriGirisiButonu.setMaximumSize(new Dimension(200, 30));
        misafirGirisiButonu.setMaximumSize(new Dimension(200, 30));
        sifreSifirlamaButonu.setMaximumSize(new Dimension(200, 30));

        // Padding ve Hizalama
        epostaEtiketi.setAlignmentX(Component.CENTER_ALIGNMENT);
        epostaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        sifreEtiketi.setAlignmentX(Component.CENTER_ALIGNMENT);
        sifreField.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminGirisiButonu.setAlignmentX(Component.CENTER_ALIGNMENT);
        musteriGirisiButonu.setAlignmentX(Component.CENTER_ALIGNMENT);
        misafirGirisiButonu.setAlignmentX(Component.CENTER_ALIGNMENT);
        sifreSifirlamaButonu.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminGirisiButonunaActionListenerEkle();
        musteriGirisiButonunaActionListenerEkle();
        misafirGirisiButonunaActionListenerEkle();
        sifreSifirlamaButonunaActionListenerEkle();

        // İçerik Paneline Bileşenleri Ekle
        icerikPaneli.add(Box.createVerticalStrut(20));
        icerikPaneli.add(epostaEtiketi);
        icerikPaneli.add(epostaField);
        icerikPaneli.add(Box.createVerticalStrut(10));
        icerikPaneli.add(sifreEtiketi);
        icerikPaneli.add(sifreField);
        icerikPaneli.add(Box.createVerticalStrut(5));
        icerikPaneli.add(sifreSifirlamaButonu);
        icerikPaneli.add(Box.createVerticalStrut(20));
        icerikPaneli.add(adminGirisiButonu);
        icerikPaneli.add(Box.createVerticalStrut(20));
        icerikPaneli.add(musteriGirisiButonu);
        icerikPaneli.add(Box.createVerticalStrut(20));
        icerikPaneli.add(misafirGirisiButonu);

        // İçerik Panelini Ortalamak için BorderLayout'un CENTER Bölgesine Ekle
        add(icerikPaneli, BorderLayout.CENTER);
    }

    private void adminGirisiButonunaActionListenerEkle() {
        adminGirisiButonu.addActionListener(e -> {
            if (adminGirisiniKontrolEt()) {
                // Girişin başarılı olduğunu AnaPencere classına bildir
                AktifKullanici.setAktifKullanici(VeritabaniYoneticisi.istenenKullaniciyiAl(epostaField.getText()));
                girisBasariliCallback.run();
            } else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this), "E-posta veya şifre yanlış",
                        "Hatalı Admin Girişi ", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    private boolean adminGirisiniKontrolEt() {
        var tumAdminlerVerisi = VeritabaniYoneticisi.tumAdminleriAl();
        return veritabanindaAdminVarMiKontrolEt(tumAdminlerVerisi);
    }

    private boolean veritabanindaAdminVarMiKontrolEt(java.util.ArrayList<Admin> tumAdminlerVerisi) {
        for (Admin admin : tumAdminlerVerisi) {
            if (admin.getEposta().equals(epostaField.getText()) &&
                    admin.getSifre().equals(new String(sifreField.getPassword()))) {
                return true;
            }
        }
        return false;
    }

    private void musteriGirisiButonunaActionListenerEkle() {
        musteriGirisiButonu.addActionListener(e -> {
            if (musteriGirisiniKontrolEt()) {
                AktifKullanici.setAktifKullanici(VeritabaniYoneticisi.istenenKullaniciyiAl(epostaField.getText()));
                girisBasariliCallback.run();
            } else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this), "E-posta veya şifre yanlış",
                        "Hatalı Müşteri Girişi ", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    private boolean musteriGirisiniKontrolEt() {
        var tumMusterilerListesi = VeritabaniYoneticisi.tumMusterileriAl();
        for (Musteri musteri : tumMusterilerListesi) {
            if (musteri.getEposta().equals(epostaField.getText()) &&
                    musteri.getSifre().equals(new String(sifreField.getPassword()))) {
                return true;
            }
        }
        return false;
    }

    private void misafirGirisiButonunaActionListenerEkle() {
        misafirGirisiButonu.addActionListener(e -> {
            Kullanici misafir = new Misafir("Misafir");
            AktifKullanici.setAktifKullanici(misafir);
            girisBasariliCallback.run();
        });
    }

    private void sifreSifirlamaButonunaActionListenerEkle() {
        SifreSifirlamaPaneli sifreSifirlamaPaneli = new SifreSifirlamaPaneli();
        anaPanel.add(sifreSifirlamaPaneli, "Şifre Sıfırlama");
        sifreSifirlamaButonu.addActionListener(e -> {
            // TODO: Şifre sıfırlama maili gönder
            cardLayout.show(anaPanel, "Şifre Sıfırlama");
        });
    }
}
