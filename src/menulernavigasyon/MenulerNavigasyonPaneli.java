package menulernavigasyon;

import diger.AktifKullanici;
import diger.Sabitler;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenulerNavigasyonPaneli extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel anaPanel;
    private final JButton anaSayfaButonu;
    private final JButton sepetButonu;
    private final JButton profilButonu;
    private final JButton adminButonu;

    public MenulerNavigasyonPaneli(CardLayout cardLayout, JPanel anaPanel) {
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        // Menü butonları
        anaSayfaButonu = menuButonu("Ana Sayfa");
        sepetButonu = menuButonu("Sepet");
        profilButonu = menuButonu("Profil");
        adminButonu = menuButonu("Admin");

        // Butonlara action listener ekleniyor
        anaSayfaButonu.addActionListener(e -> cardLayout.show(anaPanel, "Ana Sayfa"));
        sepetButonu.addActionListener(e -> cardLayout.show(anaPanel, "Sepet"));
        profilButonu.addActionListener(e -> cardLayout.show(anaPanel, "Profil"));
        adminButonu.addActionListener(e -> cardLayout.show(anaPanel, "Admin"));

        // Butonları panele ekleme
        add(anaSayfaButonu);
        add(sepetButonu);
        add(profilButonu);
        add(adminButonu);
    }

    // Tasarım için tekrar kullanılabilir buton fonksiyonu
    private JButton menuButonu(String text) {
        JButton buton = new JButton(text);
        buton.setPreferredSize(new Dimension(120, 40));
        buton.setFont(new Font("Arial", Font.BOLD, 14));
        buton.setBackground(new Color(59, 89, 152));
        buton.setForeground(Color.WHITE);
        buton.setBorder(new LineBorder(new Color(34, 58, 119), 2, true));
        buton.setFocusPainted(false);
        buton.setContentAreaFilled(true);

        // Hover efekti
        buton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buton.setBackground(new Color(91, 129, 204)); // Üzerine gelince daha açık mavi
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buton.setBackground(new Color(59, 89, 152)); // Eski haline dönsün
            }
        });

        return buton;
    }

    public void kullaniciTipineGoreMenuleriDuzenle() {
        int kullaniciTipi = AktifKullanici.getAktifKullanici() != null ? AktifKullanici.getAktifKullanici().getKullaniciTipi() : 0;
        if (kullaniciTipi == Sabitler.ADMIN) {
            anaSayfaButonu.setVisible(true);
            sepetButonu.setVisible(false);
            profilButonu.setVisible(false);
            adminButonu.setVisible(true);
        } else if (kullaniciTipi == Sabitler.MUSTERI) {
            anaSayfaButonu.setVisible(true);
            sepetButonu.setVisible(true);
            profilButonu.setVisible(true);
            adminButonu.setVisible(false);
        } else if (kullaniciTipi == Sabitler.MISAFIR) {
            anaSayfaButonu.setVisible(true);
            sepetButonu.setVisible(false);
            profilButonu.setVisible(false);
            adminButonu.setVisible(false);
        }
    }
}
