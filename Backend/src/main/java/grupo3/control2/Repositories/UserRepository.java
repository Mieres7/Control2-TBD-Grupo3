package grupo3.control2.Repositories;

import grupo3.control2.Entities.UserEntity;

import java.util.List;

public interface UserRepository {
    List<UserEntity> findByUsername(String username);

    Integer save(UserEntity user);
}
