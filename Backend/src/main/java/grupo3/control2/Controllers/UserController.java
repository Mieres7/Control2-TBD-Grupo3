package grupo3.control2.Controllers;

import grupo3.control2.Entities.UserEntity;
import grupo3.control2.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        user.setId_user(userRepository.save(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
