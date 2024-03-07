package com.example.finfolio.Service;

import com.example.finfolio.Entite.Offre;
import com.example.finfolio.Entite.User;

import java.util.List;

public interface IOffre<T> {
  boolean addOffreToCredit(int creditId, double montant, double interet, int userId);
  boolean addOffreToCredit(int creditId, double montant, double interet, User user);




    List<Offre> getAllOffres();

    boolean deleteOffre(int OffreId);

    boolean updateOffre(Offre offre);

    void updateOffre(int id, Offre offre);

    void updateOffre(int id, double montant, double interet);
}
