package Models;

import Views.ViewFactory;
import com.example.finfolio.Entite.User;

public class Model {
    private static Model model;
    private final ViewFactory vf;
    private User user;

    private Model(){this.vf=new ViewFactory();
        user=new User();
    }
    public static  synchronized Model getInstance(){
        if (model==null)
        {model=new Model();}
        return model;
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

