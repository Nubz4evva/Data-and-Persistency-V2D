package OVChipkaart;

import Product.Product;
import Product.ProductDAOsql;
import Reizigers.Reiziger;

import java.sql.*;
import java.util.ArrayList;

public class OVChipkaartDAOsql implements OVChipkaartDAO{
    private Connection conn;

    public OVChipkaartDAOsql(Connection conn) { this.conn = conn; }

    public boolean save(OVChipkaart ovChipkaart) {
        try {
            Statement st = conn.createStatement();
            String saveString = "INSERT INTO ov_chipkaart VALUES(?, ?, ?, ?, ?)";
            PreparedStatement saveOVChipkaart = conn.prepareStatement(saveString);
            saveOVChipkaart.setInt(1, ovChipkaart.getKaartNummer());
            saveOVChipkaart.setDate(2, ovChipkaart.getGeldigTot());
            saveOVChipkaart.setInt(3, ovChipkaart.getKlasse());
            saveOVChipkaart.setDouble(4, ovChipkaart.getSaldo());
            saveOVChipkaart.setInt(5, ovChipkaart.getReiziger().getId());
            saveOVChipkaart.executeUpdate();
            // Save Relatie Producten
            for (Product product : ovChipkaart.getProducten()) {
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

    public boolean update(OVChipkaart ovChipkaart) {
        try {
            Statement st = conn.createStatement();
            String saveString = "UPDATE ov_chipkaart SET reiziger_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ? WHERE kaart_nummer = ?";
            PreparedStatement saveOVChipkaart = conn.prepareStatement(saveString);
            saveOVChipkaart.setInt(1, ovChipkaart.getReiziger().getId());
            saveOVChipkaart.setDate(2, ovChipkaart.getGeldigTot());
            saveOVChipkaart.setInt(3, ovChipkaart.getKlasse());
            saveOVChipkaart.setDouble(4, ovChipkaart.getSaldo());
            saveOVChipkaart.setInt(5, ovChipkaart.getKaartNummer());
            saveOVChipkaart.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            Statement st = conn.createStatement();
            String saveString = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement saveOVChipkaart = conn.prepareStatement(saveString);
            saveOVChipkaart.setInt(1, ovChipkaart.getKaartNummer());
            saveOVChipkaart.executeUpdate();
            // Delete Relatie Producten
            saveString = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";
            PreparedStatement saveRelatie = conn.prepareStatement(saveString);
            saveRelatie.setInt(1, ovChipkaart.getKaartNummer());
            saveRelatie.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement saveOVChipkaarten = conn.prepareStatement(saveString);
            saveOVChipkaarten.setInt(1, id);
            ResultSet rs = saveOVChipkaarten.executeQuery();
            st.close();

            ArrayList<OVChipkaart> OVChipkaarten = new ArrayList<OVChipkaart>();
            ProductDAOsql pdao = new ProductDAOsql(conn);
            while (rs.next()) {
                int kaartNummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                OVChipkaart ovChipkaart = new OVChipkaart(kaartNummer, geldigTot, klasse, saldo, reiziger);
                // Load producten
                ArrayList<Product> producten = pdao.findByOVChipkaart(ovChipkaart);
                ovChipkaart.setProducten(producten);
                ovChipkaart.setReiziger(reiziger);
                // Save to List
                OVChipkaarten.add(ovChipkaart);
            }
            return OVChipkaarten;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
