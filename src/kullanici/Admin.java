package kullanici;

import static diger.Sabitler.*;

public class Admin extends Kullanici {
    public Admin(Integer id, String eposta, String sifre) {
        super(id, "admin", "admin", eposta, sifre, ADMIN, "");
    }
}
