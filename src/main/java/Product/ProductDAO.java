package Product;

import OVChipkaart.OVChipkaart;

import java.util.ArrayList;

public interface ProductDAO {
    public ArrayList<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
}
