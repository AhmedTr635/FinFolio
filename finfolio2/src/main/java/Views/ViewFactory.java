package Views;

import com.example.finfolio.Admin.AdminController;
import com.example.finfolio.UsrController.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    //-----------User views-----------
    private AnchorPane dashboardView;
    private AnchorPane creditsView;
    private AnchorPane depensesView;
    private AnchorPane investissementsView;
    private AnchorPane evenementsView;
    private AnchorPane portfolioView;
    private AnchorPane profilView;

    private final StringProperty userSelectedMenuItem;

    private AnchorPane signUpView;

    //-------------Admin views---------------

    private final StringProperty adminSelectMenuItem;
    private AnchorPane createUserView;

    private AnchorPane creditsAdminView;
    private AnchorPane taxesAdminView;
    private AnchorPane investissementsAdminView;
    private AnchorPane evenementsAdminView;
    private AnchorPane usersAdminView;
    private AccountType loginAccountType;



    public ViewFactory(){
        adminSelectMenuItem=   new SimpleStringProperty("");

        userSelectedMenuItem = new SimpleStringProperty("");
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /* ---------------Afficher les interfaces-----------------*/
    private void createStage(FXMLLoader loader) {
        Scene scene =null;

        try {
            scene=new Scene(loader.load());
        }catch (Exception e){e.printStackTrace();}
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/finfolio/Pics/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("FinFolio");
        stage.show();
    }

    public void showSignUpWindow() {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/example/finfolio/User/signup.fxml"));
        createStage(loader);
    }
    public void showMotDepasseOublieWindow() {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/example/finfolio/User/MotDePasseOublie.fxml"));
        createStage(loader);
    }

    /*
    *Admin Views functions
     */

    public AnchorPane getCreateUserView() {
        if (createUserView==null)
            try {
                createUserView= new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/createUser.fxml")).load();
            }catch (Exception e){e.printStackTrace();
            }
        return createUserView;
    }

    public void showAdminWindow(){
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    public StringProperty getAdminSelectMenuItem() {
        return adminSelectMenuItem;
    }
    public AnchorPane getCreditsAdminView(){
        if (creditsAdminView==null)
            try {
                creditsAdminView = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/creditsAdmin.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return creditsAdminView;

    }

    public AnchorPane getTaxesAdminView() {
        if (taxesAdminView==null)
            try {
                taxesAdminView = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/taxes.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return taxesAdminView;
    }

    public AnchorPane getEvenementsAdminView() {
        if (evenementsAdminView==null)
            try {
                evenementsAdminView = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/evenementsAdmin.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return evenementsAdminView;
    }

    public AnchorPane getInvestissementsAdminView() {
        if (investissementsAdminView==null)
            try {
                investissementsAdminView = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/investissementAdmin.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return investissementsAdminView;
    }

    public AnchorPane getUsersAdminView() {
        if (usersAdminView==null)
            try {
                usersAdminView = new FXMLLoader(getClass().getResource("/com/example/finfolio/Admin/users.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return usersAdminView;
    }

    /*
     *User Views---------
     */

    public  AnchorPane getDashboardView(){
        if (dashboardView== null){//ki bech yemchi men fenetre lwahda okhra yokkedch yaawed yhel
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/dashboard.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        }
        return dashboardView;
    }
    public  AnchorPane getProfilView(){
        if (profilView== null){//ki bech yemchi men fenetre lwahda okhra yokkedch yaawed yhel
            try {
                profilView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/profil.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        }
        return profilView;
    }


    public StringProperty getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    public AnchorPane getCreditsView(){
        if (creditsView==null)
            try {
                creditsView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/credit.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return creditsView;

    }

    public AnchorPane getDepensesView() {
        if (depensesView==null)
            try {
                depensesView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/depenses.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return depensesView;
    }

    public AnchorPane getEvenementsView() {
        if (evenementsView==null)
            try {
                evenementsView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/evenements.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return evenementsView;
    }

    public AnchorPane getInvestissementsView() {
        if (investissementsView==null)
            try {
                investissementsView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/investissements.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return investissementsView;
    }

    public AnchorPane getPortfolioView() {
        if (portfolioView==null)
            try {
                portfolioView = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/portfolio.fxml")).load();
            }catch (Exception e)
            {e.printStackTrace();}
        return portfolioView;
    }


    public void showLoginWindow() throws IOException {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/example/finfolio/User/login2.fxml"));
            createStage(loader);
    }
    public void showUserWindow(){
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/example/finfolio/User/user.fxml"));
        UserController userController= new UserController();//preconstructed controller
        loader.setController(userController);
        createStage(loader);
    }
public void closeStage(Stage stage)
{
    stage.close();
}

}
