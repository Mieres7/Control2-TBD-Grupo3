package grupo3.control2.Repositories;

import grupo3.control2.Entities.TaskEntity;

import java.util.List;


public interface TaskRepository {

    Integer save(TaskEntity task);

    void update(TaskEntity taskUpdated, Integer id_task);

    void delete(Integer id_task);

    List<TaskEntity> getTaskByUser(Integer id_user);

    List<TaskEntity> getTaskByKeywords(Integer id_user, String keywords, String status);
}