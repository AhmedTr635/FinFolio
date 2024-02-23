package Models;

import Views.ViewFactory;
import com.example.finfolio.Entite.User;
import com.example.finfolio.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class Model {
    private static Model model;
    private final ViewFactory vf;
    private User user;
    ObservableList<User> users ;

    private Model(){
        this.vf=new ViewFactory();
        user=new User();
        users=FXCollections.observableArrayList();
    }
    public static  synchronized Model getInstance(){
        if (model==null)
        {model=new Model();}
        return model;
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers() throws SQLException {
        UserService us=new UserService();
        List<User >userList=us.readAll();
        users.addAll(userList);
    }
    public void setUser(User u)
    {
        user=u;
    }
    public User getUser()
    {
        return user;
    }

    public ViewFactory getViewFactory() {
        return vf;
    }
}

