import OVChipkaart.OVChipkaart;
import OVChipkaart.OVChipkaartDAOsql;
import Product.Product;
import Product.ProductDAOsql;
import Reizigers.Reiziger;
import Reizigers.ReizigerDAO;
import Reizigers.ReizigerDAOsql;

import java.sql.*;
import java.util.List;

import Adres.Adres;
import Adres.AdresDAO;
import Adres.AdresDAOsql;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        OVChipkaartDAOsql ovdao = new OVChipkaartDAOsql(conn);
        ProductDAOsql pdao = new ProductDAOsql(conn);
        ReizigerDAOsql rdao = new ReizigerDAOsql(conn);
        testP5(ovdao, pdao, rdao);
    }


    private static void testP5(OVChipkaartDAOsql ovdao, ProductDAOsql pdao, ReizigerDAOsql rdao) throws SQLException {
        System.out.println("\n---------- Test P4 -------------");

        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] pdao.findAll() geeft de volgende producten:\n");
        for (Product p : producten) {
            System.out.println(p);
        }
        System.out.println();

        Reiziger reiziger = rdao.findById(2);
        OVChipkaart ovChipkaart = reiziger.getOVChipkaarten().get(0);
        // Haal producten op die bij de OVchipkaart horen
        List<Product> OVproducten = pdao.findByOVChipkaart(ovChipkaart);
        System.out.println("[Test] pdao.findByOVChipkaart() geeft de volgende producten:\n");
        for (Product p : OVproducten) {
            System.out.println(p);
        }
        System.out.println();

        // Maak nieuw product aan en sla deze op
        Product newProduct = new Product(23, "Test", "Test123", 16.50);
        ovChipkaart.addProduct(newProduct);
        System.out.println("[Test] pdao.save() geeft eerst " + producten.size() + " producten:\n");
        pdao.save(newProduct);
        producten = pdao.findAll();
        System.out.println("Erna, " +  producten.size() + " producten:\n");


        // Update product
        newProduct.setPrijs(12.50);
        pdao.update(newProduct);
        System.out.println("[Test] Update geeft de volgende producten:\n");
        OVproducten = pdao.findByOVChipkaart(ovChipkaart);
        for (Product p : OVproducten) {
            System.out.println(p);
        }
        System.out.println();

        // Delete product
        producten = pdao.findAll();
        System.out.println("[Test] Eerst " + producten.size() + " producten, na Delete :\n");
        pdao.delete(newProduct);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten \n");
    }
}