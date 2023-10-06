package grupo3.control2.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TaskEntity {

    @Id
    Integer id;

    private String title;

    private String description;

    private LocalDate expiration_date;

    private String state;

    @ManyToOne
    //@JoinColumn(name = "id") // Nombre de la columna de clave foránea en la tabla de Pedido
    private UserEntity user_entity;

}
