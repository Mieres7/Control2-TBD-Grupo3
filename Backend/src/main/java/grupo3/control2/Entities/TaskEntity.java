package grupo3.control2.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskEntity {
    @Id
    Integer id_task;
    String title_task;
    String description_task;
    Date expire_date;
    LocalTime expire_time;
    String status_task;
    Integer id_user;
}
