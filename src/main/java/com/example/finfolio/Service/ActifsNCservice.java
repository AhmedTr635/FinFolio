package services;

import entite.ActifsNonCourants;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActifsNCservice implements iActifsNservices<ActifsNonCourants> {
    private Connection connection;
    private Statement ste;
    private PreparedStatement pst;

    public ActifsNCservice() {
        connection= DataSource.getInstance().getCnx();
    }





    @Override
    public void delete(ActifsNonCourants a) {
        String requete="DELETE  FROM actif_non_courant WHERE user_id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setInt(1, a.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void update(ActifsNonCourants a,String n, String t, float v, float p) {

        String requete="UPDATE actif_non_courant SET Name = ?, Type = ?, Valeur = ?,Prix_achat = ?WHERE user_id = ?";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, n);
            pst.setString(2, t);

            pst.setFloat(3, v);
            pst.setFloat(4,p);
            pst.setInt(5, a.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public List<ActifsNonCourants> readAll() {
        String requete="select * from actif_non_courant";
        List<ActifsNonCourants> list=new ArrayList<>();

        try {
            ste= connection.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new ActifsNonCourants(rs.getInt(1),
                        rs.getString(2),rs.getString(3),
                        rs.getFloat(4),rs.getFloat(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public ActifsNonCourants readById(int id) {
        return null;
    }


    public void add(ActifsNonCourants p){
        String requete="insert into actif_non_courant (name,type,valeur,prix_acaht,user_id) values(?,?,?,?,?)";

        try { pst= connection.prepareStatement(requete);
            pst.setString(1, p.getName());
            pst.setString(2, p.getType());
            pst.setFloat(3, p.getValeur());
            pst.setFloat(4, p.getPrix_achat());
            pst.setInt(5,p.getUser_id());
            pst.executeUpdate();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}


