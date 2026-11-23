import com.nosecrets.config.Database;
import com.nosecrets.dao.UserDAO;
import com.nosecrets.dao.impl.UserDAOImpl;
import com.nosecrets.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException {
        Database db = new Database();
        UserDAO u = new UserDAOImpl(db);

        List<User> uu = u.listarTodos();

        for(User x : uu){
            System.out.println(x.getUser()+" , "+x.getPassword());
        }

    }
}
