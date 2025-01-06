package girisnavigasyon;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GirisNavigasyonPaneli extends JPanel {

    private CardLayout cardLayout;
    private JPanel anaPanel;

    public GirisNavigasyonPaneli(CardLayout cardLayout, JPanel anaPanel) {
        this.cardLayout = cardLayout;
        this.anaPanel = anaPanel;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton girisButonu = menuButonu("Giriş Yap");
        JButton kayitOlButonu = menuButonu("Kayıt Ol");

        girisButonu.addActionListener(e -> cardLayout.show(anaPanel, "Giriş"));
        kayitOlButonu.addActionListener(e -> cardLayout.show(anaPanel, "Kayıt Ol"));

        add(girisButonu);
        add(kayitOlButonu);
    }

    public JButton menuButonu(String text) {
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
}
