USE eticaret;

INSERT INTO `urun` (`id`, `ad`, `detay`, `fiyat`, `stok`) VALUES
(1, 'Monster Laptop', '13.7 inç ekran, Intel i7 işlemci, 16GB RAM, 4 çekirdek', 39500.5, 12),
(2, 'Akıllı Telefon', '128GB hafıza, 6.1 inç AMOLED ekran', 24999.99, 20),
(3, 'Spor Ayakkabı', 'Koşu için uygun, nefes alabilir kumaş', 599.99, 25),
(4, 'Kamp Çadırı', '4 kişilik, su geçirmez', 999.9, 10),
(5, 'Masa Lambası', 'Ayarlanabilir ışık seviyesi, modern tasarım', 199.99, 40),
(6, 'Mutfak Robotu', '12 farklı fonksiyon, paslanmaz çelik', 12500, 15),
(7, 'Televizyon', '55 inç, IPS Ekran', 24999.99, 5);

INSERT INTO `eticaret`.`kullanici`
(`id`,
`ad`,
`soyad`,
`e_posta`,
`sifre`,
`kullanici_tipi`,
`adres`)
VALUES
(1, 'Admin', 'Admin', 'admin@admin.com', 'admin', 1)
