package Service;

import Entity.Offre;

import java.util.List;

public interface IOffre<T> {
  boolean addOffreToCredit(int creditId, double montant, double interet, int userId);




    List<Offre> getAllOffres();

    boolean deleteOffre(int OffreId);

    boolean updateOffre(Offre offre);

    void updateOffre(int id, Offre offre);

    void updateOffre(int id, double montant, double interet);
}
