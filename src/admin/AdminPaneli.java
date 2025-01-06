package admin;

import siparisler.SiparislerPaneli;

import javax.swing.*;
import java.awt.*;

import static diger.YardimciAraclar.sabitBoyutOlustur;

public class AdminPaneli extends JPanel {

    public AdminPaneli(CardLayout cardLayout, JPanel anaPanel) {
        setLayout(new BorderLayout());

        JPanel siparisPaneli = new JPanel();
        siparisPaneli.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton siparislerButonu = new JButton("Müşteri Siparişleri");
        sabitBoyutOlustur(siparislerButonu, new Dimension(300, 40));

        siparisPaneli.add(siparislerButonu);

        anaPanel.add(new SiparislerPaneli(), "Siparişler");

        siparislerButonu.addActionListener(e -> cardLayout.show(anaPanel, "Siparişler"));

        add(siparisPaneli, BorderLayout.CENTER);
    }
}
