package Adres;

import Reizigers.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection conn;

    public AdresDAOsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres) {
        try {
            Statement st = conn.createStatement();
            String saveString = "INSERT INTO adres VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            saveAdres.setInt(1, adres.getId());
            saveAdres.setString(2, adres.getPostcode());
            saveAdres.setString(3, adres.getHuisnummer());
            saveAdres.setString(4, adres.getStraat());
            saveAdres.setString(5, adres.getWoonplaats());
            saveAdres.setInt(6, adres.getReizigerId());
            saveAdres.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean update(Adres adres) {
        try {
            Statement st = conn.createStatement();
            String saveString = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ?  WHERE adres_id = ?";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            saveAdres.setString(1, adres.getPostcode());
            saveAdres.setString(2, adres.getHuisnummer());
            saveAdres.setString(3, adres.getStraat());
            saveAdres.setString(4, adres.getWoonplaats());
            saveAdres.setInt(5, adres.getReizigerId());
            saveAdres.setInt(6, adres.getId());
            saveAdres.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(Adres adres) {
        try {
            Statement st = conn.createStatement();
            String saveString = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            saveAdres.setInt(1, adres.getId());
            saveAdres.executeUpdate();
            st.close();
            return true;
        } catch(SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Adres findById(int id) {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM adres WHERE adres_id = ?";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            saveAdres.setInt(1, id);
            ResultSet rs = saveAdres.executeQuery();
            st.close();

            rs.next();
            String pc = rs.getString("postcode");
            String hnr = rs.getString("huisnummer");
            String strt = rs.getString("straat");
            String wnplts = rs.getString("woonplaats");
            int rId = rs.getInt("reiziger_id");
            Adres adres = new Adres(id, pc, hnr, strt, wnplts, rId);
            return adres;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Adres> findAll() {
        try {
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM adres";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            ResultSet rs = saveAdres.executeQuery();
            st.close();

            List<Adres> adresList = new ArrayList<Adres>();
            while (rs.next()) {
                int id = rs.getInt("adres_id");
                String pc = rs.getString("postcode");
                String hnr = rs.getString("huisnummer");
                String strt = rs.getString("straat");
                String wnplts = rs.getString("woonplaats");
                int rId = rs.getInt("reiziger_id");
                Adres adres = new Adres(id, pc, hnr, strt, wnplts, rId);
                adresList.add(adres);
            }
            return adresList;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public Adres findByReiziger(Reiziger reiziger) {
        try {
            int rId = reiziger.getId();
            Statement st = conn.createStatement();
            String saveString = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement saveAdres = conn.prepareStatement(saveString);
            saveAdres.setInt(1, rId);
            ResultSet rs = saveAdres.executeQuery();
            st.close();

            rs.next();
            String pc = rs.getString("postcode");
            String hnr = rs.getString("huisnummer");
            String strt = rs.getString("straat");
            String wnplts = rs.getString("woonplaats");
            int id = rs.getInt("adres_id");
            Adres adres = new Adres(id, pc, hnr, strt, wnplts, rId);
            return adres;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
