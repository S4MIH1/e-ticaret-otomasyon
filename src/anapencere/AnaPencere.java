package anapencere;

import admin.AdminPaneli;
import anasayfa.AnaSayfaPaneli;
import diger.AktifKullanici;
import giris.GirisPaneli;
import girisnavigasyon.GirisNavigasyonPaneli;
import kayit.KayitOlPaneli;
import menulernavigasyon.MenulerNavigasyonPaneli;
import profil.ProfilPaneli;
import sepet.SepetPaneli;

import javax.swing.*;
import java.awt.*;

public class AnaPencere extends JFrame {
    private CardLayout cardLayout;
    private JPanel anaPencerePaneli;

    private GirisNavigasyonPaneli girisNavigasyonPaneli;
    private MenulerNavigasyonPaneli menulerNavigasyonPaneli;

    public AnaPencere() {
        setTitle("E-Ticaret Uygulaması");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        cardLayout = new CardLayout();
        anaPencerePaneli = new JPanel(cardLayout);

        girisNavigasyonPaneli = new GirisNavigasyonPaneli(cardLayout, anaPencerePaneli);
        menulerNavigasyonPaneli = new MenulerNavigasyonPaneli(cardLayout, anaPencerePaneli);
        menulerNavigasyonPaneli.setVisible(false);

        GirisPaneli girisPaneli = new GirisPaneli(cardLayout, anaPencerePaneli, new Runnable() {
            @Override
            public void run() {
                kullaniciGirisiniYonlendir();
            }
        });

        anaPencerePaneli.add(girisPaneli, "Giriş");
        anaPencerePaneli.add(new KayitOlPaneli(), "Kayıt Ol");

        setLayout(new BorderLayout());
        add(girisNavigasyonPaneli, BorderLayout.NORTH);
        add(anaPencerePaneli, BorderLayout.CENTER);

        // Hangi panelin gösterileceğini belirler, burada Giriş Paneli gösterilir
        cardLayout.show(anaPencerePaneli, "Giriş");
    }

    private void kullaniciGirisiniYonlendir() {
        this.add(menulerNavigasyonPaneli);
        menulerNavigasyonPaneli.setVisible(true);
        this.remove(girisNavigasyonPaneli);
        menulerNavigasyonPaneli.kullaniciTipineGoreMenuleriDuzenle();
        kullaniciTipineGorePanelleriEkle();

        cardLayout.show(anaPencerePaneli, "Ana Sayfa");
    }

    private void kullaniciTipineGorePanelleriEkle() {
        Integer kullaniciTipi = AktifKullanici.getAktifKullanici() != null ? AktifKullanici.getAktifKullanici().getKullaniciTipi() : null;

        if (kullaniciTipi != null) {
            if (kullaniciTipi == 1) {
                anaPencerePaneli.add(new AnaSayfaPaneli(cardLayout, anaPencerePaneli), "Ana Sayfa");
                anaPencerePaneli.add(new AdminPaneli(cardLayout, anaPencerePaneli), "Admin");
            } else if (kullaniciTipi == 2) {
                SepetPaneli sepetPaneli = new SepetPaneli();
                AnaSayfaPaneli anaSayfaPaneli = new AnaSayfaPaneli(cardLayout, anaPencerePaneli);
                anaSayfaPaneli.setSepeteUrunEklendiCallback(() -> sepetPaneli.sepetiGuncelle());

                anaPencerePaneli.add(anaSayfaPaneli, "Ana Sayfa");
                anaPencerePaneli.add(sepetPaneli, "Sepet");
                anaPencerePaneli.add(new ProfilPaneli(cardLayout, anaPencerePaneli), "Profil");
            } else if (kullaniciTipi == 3) {
                anaPencerePaneli.add(new AnaSayfaPaneli(cardLayout, anaPencerePaneli), "Ana Sayfa");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnaPencere anaPencere = new AnaPencere();
            anaPencere.setVisible(true);
        });
    }
}
