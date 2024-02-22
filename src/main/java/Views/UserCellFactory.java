package Views;

import com.example.finfolio.Entite.User;
import com.example.finfolio.UsrController.CellsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

public class UserCellFactory extends ListCell<User> {
    private ListView<User> usersListView;

    public UserCellFactory(ListView<User> usersListview) {
        this.usersListView = usersListView;
    }

    @Override
    protected void updateItem(User user, boolean b) {
        super.updateItem(user, b);
        if (b)
        {
            setText(null);
            setGraphic(null);
        }else {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/com/example/finfolio/User/cells.fxml"));
            CellsController controller =new CellsController(user);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
