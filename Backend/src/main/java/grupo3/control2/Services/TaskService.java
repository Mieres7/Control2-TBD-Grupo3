package grupo3.control2.Services;

import grupo3.control2.Entities.TaskEntity;
import grupo3.control2.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService implements TaskRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public Integer save(TaskEntity task) {

        try(Connection conn = sql2o.open()) {
            String sql = "INSERT INTO public.task (title_task, description_task, expire_date, expire_time, status_task, id_user) " +
                    "VALUES (:title_task, :description_task, :expire_date, :expire_time, :status_task, :id_user)";
            return (Integer) conn.createQuery(sql, true)
                    .addParameter("title_task", task.getTitle_task())
                    .addParameter("description_task", task.getDescription_task())
                    .addParameter("expire_date", task.getExpire_date())
                    .addParameter("expire_time", task.getExpire_time())
                    .addParameter("status_task", task.getStatus_task())
                    .addParameter("id_user", task.getId_user())
                    .executeUpdate()
                    .getKey();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public void update(TaskEntity taskUpdated, Integer id_task) {
        try(Connection conn = sql2o.open()) {
            String sql = "UPDATE public.task SET title_task = :title_task, description_task = :description_task," +
                    "expire_date = :expire_date, expire_time = :expire_time, status_task = :status_task, id_user = :id_user WHERE id_task = :id_task";

            conn.createQuery(sql)
                    .addParameter("title_task", taskUpdated.getTitle_task())
                    .addParameter("description_task", taskUpdated.getDescription_task())
                    .addParameter("expire_date", taskUpdated.getExpire_date())
                    .addParameter("expire_time", taskUpdated.getExpire_time())
                    .addParameter("status_task", taskUpdated.getStatus_task())
                    .addParameter("id_user", taskUpdated.getId_user())
                    .addParameter("id_task", id_task)
                    .executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id_task) {
        try(Connection conn = sql2o.open()) {
            String sql = "DELETE FROM public.task WHERE id_task = :id_task";

            conn.createQuery(sql)
                    .addParameter("id_task", id_task)
                    .executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<TaskEntity> getTaskByUser(Integer id_user) {
        try(Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM public.task WHERE id_user = :id_user";

            return conn.createQuery(sql)
                    .addParameter("id_user", id_user)
                    .executeAndFetch(TaskEntity.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TaskEntity> getTaskByKeywords(Integer id_user, String keywords, String status) {
        String[] words = keywords.split(" ");

        if (Objects.equals(status, "TODOS")) {
            String sql = "SELECT * FROM public.task WHERE id_user = :id_user AND (LOWER(title_task) LIKE :keyword OR LOWER(description_task) LIKE :keyword)";

            try (Connection conn = sql2o.open()) {
                Query query = conn.createQuery(sql).addParameter("id_user", id_user);

                for (String word : words) {
                    query.addParameter("keyword", "%" + word.toLowerCase() + "%");
                }

                return query.executeAndFetch(TaskEntity.class);
            }
        }

        else {
            String sql = "SELECT * FROM public.task WHERE id_user = :id_user AND status_task = :status AND (LOWER(title_task) LIKE :keyword OR LOWER(description_task) LIKE :keyword)";

            try (Connection conn = sql2o.open()) {
                Query query = conn.createQuery(sql).addParameter("id_user", id_user)
                        .addParameter("status", status);

                for (String word : words) {
                    query.addParameter("keyword", "%" + word.toLowerCase() + "%");
                }

                return query.executeAndFetch(TaskEntity.class);
            }
        }

    }
}
