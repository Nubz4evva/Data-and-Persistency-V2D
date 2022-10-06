package Product;

import Adres.Adres;
import Adres.AdresDAOsql;
import OVChipkaart.OVChipkaart;
import OVChipkaart.OVChipkaartDAOsql;
import Reizigers.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOsql implements ProductDAO{
    private Connection conn;

    public ProductDAOsql(Connection conn) { this.conn = conn; }

    public boolean save(Product product) {
        try {
            Statement st = conn.createStatement();
            String saveString = "INSERT INTO product VALUES(?, ?, ?, ?)";
            PreparedStatement saveProduct = conn.prepareStatement(saveString);
            saveProduct.setInt(1, product.getProductNummer());
            saveProduct.setString(2, product.getNaam());
            saveProduct.setString(3, product.getBeschrijving());
            saveProduct.setDouble(4, product.getPrijs());
            saveProduct.executeUpdate();
            // Save Relatie OVChipkaarten
            for (OVChipkaart ovChipkaart : product.getOVChipkaarten()) {
                saveString = "INSERT INTO ov_chipkaart_product VALUES(?, ?)";
                PreparedStatement saveRelatie = conn.prepareStatement(saveString);
                saveRelatie.setInt(1, ovChipkaart.getKaartNummer());
                saveRelatie.setInt(2, product.getProductNummer());
                saveRelatie.executeUpdate();
            }
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean update(Product product) {
        try {
            Statement st = conn.createStatement();
            String saveString = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
            PreparedStatement saveProduct = conn.prepareStatement(saveString);
            saveProduct.setString(1, product.getNaam());
            saveProduct.setString(2, product.getBeschrijving());
            saveProduct.setDouble(3, product.getPrijs());
            saveProduct.setInt(4, product.getProductNummer());
            saveProduct.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(Product product) {
        try {
            Statement st = conn.createStatement();
            // Firstly delete Relatie OVChipkaarten
            String saveString = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
            PreparedStatement saveRelatie = conn.prepareStatement(saveString);
            saveRelatie.setInt(1, product.getProductNummer());
            saveRelatie.executeUpdate();
            // Delete product
            saveString = "DELETE FROM product WHERE product_nummer = ?";
            PreparedStatement saveProduct = conn.prepareStatement(saveString);
            saveProduct.setInt(1, product.getProductNummer());
            saveProduct.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT p.* FROM ov_chipkaart_product ovp" +
                    " RIGHT JOIN product p ON ovp.product_nummer = p.product_nummer" +
                    " WHERE ovp.kaart_nummer = ?";
            PreparedStatement saveOVChipkaarten = conn.prepareStatement(saveString);
            saveOVChipkaarten.setInt(1, ovChipkaart.getKaartNummer());
            ResultSet rs = saveOVChipkaarten.executeQuery();
            st.close();

            ArrayList<Product> producten = new ArrayList<Product>();
            while (rs.next()) {
                int productNummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                double prijs = rs.getDouble("prijs");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                product.addOVChipkaart(ovChipkaart);
                producten.add(product);
            }
            return producten;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Product> findAll() {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM product";
            PreparedStatement saveProduct = conn.prepareStatement(saveString);
            ResultSet rs = saveProduct.executeQuery();
            st.close();

            ArrayList<Product> producten = new ArrayList<Product>();
            while (rs.next()) {
                int productNummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                Double prijs = rs.getDouble("prijs");
                Product product = new Product(productNummer, naam, beschrijving, prijs);
                // Add to list
                producten.add(product);
            }
            return producten;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
