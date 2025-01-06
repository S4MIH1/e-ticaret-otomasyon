package sifresifirlama;

import diger.VeritabaniYoneticisi;
import javax.swing.*;
import java.awt.*;

public class SifreSifirlamaPaneli extends JPanel {
    private JTextField epostaField;
    private JButton sifreSifirlamaButonu;

    public SifreSifirlamaPaneli() {
        setLayout(new BorderLayout());

        // İçerik Paneli (BoxLayout kullanarak ortalama)
        JPanel icerikPaneli = new JPanel();
        icerikPaneli.setLayout(new BoxLayout(icerikPaneli, BoxLayout.Y_AXIS));
        icerikPaneli.setBackground(Color.WHITE);

        // Bileşenler
        JLabel epostaEtiketi = new JLabel("E-posta:");
        epostaEtiketi.setAlignmentX(Component.CENTER_ALIGNMENT);

        epostaField = new JTextField(15);
        epostaField.setMaximumSize(new Dimension(200, 30));
        epostaField.setAlignmentX(Component.CENTER_ALIGNMENT);

        sifreSifirlamaButonu = new JButton("Şifreyi Sıfırla");
        sifreSifirlamaButonu.setFont(new Font("Arial", Font.BOLD, 10));
        sifreSifirlamaButonu.setMaximumSize(new Dimension(200, 30));
        sifreSifirlamaButonu.setAlignmentX(Component.CENTER_ALIGNMENT);

        sifreSifirlamaButonunaActionListenerEkle();

        icerikPaneli.add(Box.createVerticalStrut(20));
        icerikPaneli.add(epostaEtiketi);
        icerikPaneli.add(epostaField);
        icerikPaneli.add(Box.createVerticalStrut(10));
        icerikPaneli.add(Box.createVerticalStrut(5));
        icerikPaneli.add(sifreSifirlamaButonu);

        add(icerikPaneli, BorderLayout.CENTER);
    }

    private void sifreSifirlamaButonunaActionListenerEkle() {
        sifreSifirlamaButonu.addActionListener(e -> {
            // E-posta doğrulaması
            java.util.List<String> epostalarListesi = VeritabaniYoneticisi.tumKullaniciEpostalariniAl();
            if (epostalarListesi.contains(epostaField.getText())) {
                // TODO: Şifre sıfırlama maili gönder
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Şifre sıfırlama maili gönderildi",
                        "Mail Gönderim Bildirimi",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "Böyle bir e-posta bulunamadı",
                        "E-posta Bulunamadı Hatası",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
