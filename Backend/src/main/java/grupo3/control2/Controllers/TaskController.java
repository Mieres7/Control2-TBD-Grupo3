package grupo3.control2.Controllers;

import grupo3.control2.Entities.TaskEntity;
import grupo3.control2.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<TaskEntity> update(@RequestBody TaskEntity task, @PathVariable long id_task) {
        taskRepository.update(task, id_task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/task/{id_task}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable long id_task) {
        taskRepository.delete(id_task);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully.");
    }

    @GetMapping("/tasks/{id_user}")
    public List<TaskEntity> getTasksByUser(@PathVariable long id_user) {
        return taskRepository.getTaskByUser(id_user);
    }

    @GetMapping("/tasks/{id_user}/key/{keywords}/status/{status}")
    public List<TaskEntity> getTasksByKeyWords(@PathVariable long id_user, @PathVariable String keywords, @PathVariable String status) {
        return taskRepository.getTaskByKeywords(id_user, keywords, status);
    }
}
