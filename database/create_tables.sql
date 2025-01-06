CREATE TABLE `kullanici` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(45) DEFAULT NULL,
  `soyad` varchar(45) DEFAULT NULL,
  `e_posta` varchar(100) DEFAULT NULL,
  `sifre` varchar(45) DEFAULT NULL,
  `kullanici_tipi` int DEFAULT NULL,
  `adres` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `siparis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `musteriid` int DEFAULT NULL,
  `siparistarihi` date DEFAULT NULL,
  `siparistutari` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `urun` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(45) DEFAULT NULL,
  `detay` varchar(2000) DEFAULT NULL,
  `fiyat` double DEFAULT NULL,
  `stok` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
