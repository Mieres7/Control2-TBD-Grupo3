package grupo3.control2.Controllers;

import grupo3.control2.Entities.TaskEntity;
import grupo3.control2.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskEntity> save(@RequestBody TaskEntity task) {
        task.setId_task(taskRepository.save(task));
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/task/{id_task}")
    @ResponseBody
    public ResponseEntity<TaskEntity> update(@RequestBody TaskEntity task, @PathVariable Integer id_task) {
        taskRepository.update(task, id_task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/task/{id_task}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id_task) {
        taskRepository.delete(id_task);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully.");
    }

    @GetMapping("/tasks/{id_user}")
    public List<TaskEntity> getTasksByUser(@PathVariable Integer id_user) {
        return taskRepository.getTaskByUser(id_user);
    }

    @GetMapping("/tasks/{id_user}/{status}")
    public List<TaskEntity> getTasksByStatus(@PathVariable Integer id_user, @PathVariable String status) {
        return  taskRepository.getTaskByStatus(id_user, status);
    }

    @GetMapping("/tasks/{id_user}/{section}/{keywords}")
    public List<TaskEntity> getTasksByKeyWords(@PathVariable Integer id_user, @PathVariable String section, @PathVariable String keywords) {
        return taskRepository.getTaskByKeywords(id_user, section, keywords);
    }
}
