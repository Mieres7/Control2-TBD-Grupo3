package grupo3.control2.Services;

import grupo3.control2.Entities.UserEntity;
import grupo3.control2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Service
public class UserService implements UserRepository {
    @Autowired
    private Sql2o sql2o;
    @Override
    public List<UserEntity> findByUsername(String username) {
        try(Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM public.user WHERE username= :username";
            return conn.createQuery(sql, true)
                    .addParameter("username", username)
                    .executeAndFetch(UserEntity.class);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public long save(UserEntity user) {
        try(Connection conn = sql2o.open()) {
            String sql = "INSERT INTO public.user (username, password, role) VALUES (:username, :password, :role)";
            return (long) conn.createQuery(sql, true)
                    .addParameter("username", user.getUsername())
                    .addParameter("password", user.getPassword())
                    .addParameter("role", user.getRole())
                    .executeUpdate()
                    .getKey();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
