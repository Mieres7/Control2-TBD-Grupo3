package grupo3.control2.Repositories;

import grupo3.control2.Entities.TaskEntity;

import java.util.List;


public interface TaskRepository {

    long save(TaskEntity task);

    void update(TaskEntity taskUpdated, long id_task);

    void delete(long id_task);

    List<TaskEntity> getTaskByUser(long id_user);

    List<TaskEntity> getTaskByKeywords(long id_user, String keywords, String status);
}