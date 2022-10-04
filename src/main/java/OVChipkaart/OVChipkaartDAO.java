package OVChipkaart;

import Reizigers.Reiziger;

import java.util.ArrayList;

public interface OVChipkaartDAO {
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger);
}
