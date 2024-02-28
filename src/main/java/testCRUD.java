import com.example.finfolio.Entite.DigitalCoins;
import com.example.finfolio.Entite.Investissement;
import com.example.finfolio.Entite.RealEstate;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.DigitalCoinsService;
import com.example.finfolio.Service.InvestissementService;
import com.example.finfolio.Service.RealEstateService;
import com.example.finfolio.Service.UserService;
import com.example.finfolio.util.DataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class testCRUD {
    public static void main(String[] args) throws SQLException {
        DataSource.getInstance();

        UserService usS=new UserService();
        User mhmd =usS.readById(3);


        /*Test CRUD DigitalCoins*/

        String dateString="2023-03-01";
        //String dateString1="15/02/2024";
        String dateString2 = "2024-02-26";

        // Define a formatter to parse the string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string to a LocalDate object
        LocalDate date = LocalDate.parse(dateString, formatter);
        //LocalDate date = LocalDate.parse(dateString);
        LocalDate date1 = LocalDate.parse(dateString2, formatter);
        DigitalCoins B =new DigitalCoins(1,"BTC",15200,date,date1,20000,0,0,mhmd,10000,14200,0);
        //System.out.println(B.toString());

        //reS.add();
        DigitalCoinsService dcS=new DigitalCoinsService();
        //dcS.add(B);
        //dcS.delete(B);
        DigitalCoins dc=new DigitalCoins(2,"BTC",15200,date,date1,20000,0,0,mhmd,10000,14200,120);
        //dcS.update(dc,1);

        //DigitalCoins dc1=dcS.readById(1);
        //System.out.println(dc1);
        //dcS.add(dc);

       /* Set<DigitalCoins> digitalCoinsSet = new HashSet<>();
        digitalCoinsSet =dcS.readAll();
        System.out.println(digitalCoinsSet);*/

        /*Test CRUD RealEstate*/
        Map<User,Double>userpart=new HashMap<>();
        RealEstate mansion=new RealEstate(16,"miami", 9.92F,1950000,10,2000);
        RealEstateService reS=new RealEstateService();
        //reS.add(mansion);
        //reS.delete(reS.readById(4));
        //RealEstate rbi=reS.readById(3);
        //System.out.println(rbi);
        //reS.update(mansion,16);
        /*Set<RealEstate> realEstateSet = new HashSet<>();
        realEstateSet=reS.readAll();
        System.out.println(realEstateSet);*/

        /*Test CRUD investissement*/

        Investissement inv=new Investissement(1,10000,150000,date,2000,mansion,mhmd,100);
        Investissement inv1=new Investissement(2,15000,75000,date,3600,mansion,mhmd,100);
        Investissement inv2=new Investissement(3,25000,2000,date,750,mansion,mhmd,100);
        Investissement inv3=new Investissement(4,1000000,30000,date,1500,mansion,mhmd,100);
        InvestissementService invS=new InvestissementService();
        /*invS.add(inv);
        invS.add(inv1);
        invS.add(inv2);
        invS.add(inv3);*/
        //invS.delete(inv1);
        /*invS.update(inv3,4);
        System.out.println(invS.readById(4));*/

        System.out.println(reS.fetchUserParticipation(16));
    }
}
