package grupo3.control2.Controllers;

import grupo3.control2.Entities.UserEntity;
import grupo3.control2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        user.setId_user(userRepository.save(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/user/{username}")
    @ResponseBody
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable String username) {
         return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsername(username).stream().findFirst().orElseThrow());
    }
}
