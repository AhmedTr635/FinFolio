package Entity;

public class Offre {
    private int id;
    private double montant;
    private double interet;
    private int user_id;
    private int credit_id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    // Constructors
    public Offre() {

    }

    public Offre(int id, double montant, double interet, int user_id, int credit_id, String userName) {
        this.id = id;
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
        this.userName = userName; // Set user name
    }

    public Offre(int id, double montant, double interet, int user_id, int credit_id) {
        this.id = id;
        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
    }

    public Offre( int credit_id, double  montant,double  interet ,int user_id ) {

        this.montant = montant;
        this.interet = interet;
        this.user_id = user_id;
        this.credit_id = credit_id;
    }



    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getInteret() {
        return interet;
    }

    public void setInteret(double interet) {
        this.interet = interet;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(int credit_id) {
        this.credit_id = credit_id;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", montant=" + montant +
                ", interet=" + interet +
                ", user_id=" + user_id +
                ", credit_id=" + credit_id +
                '}';
    }
}
