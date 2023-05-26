package com.example.userservicegradle.controller;


import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.service.UserService;
import com.example.userservicegradle.vo.Greeting;
import com.example.userservicegradle.vo.RequestUser;
import com.example.userservicegradle.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private UserService userService;
    private Greeting greeting;

    @Autowired
    public UserController(Environment env,Greeting greeting,UserService userService){
        this.userService=userService;
        this.greeting=greeting;
        this.env=env;
    }

    @GetMapping("/heath_check")
    public String status(){
        return "It's Working in User Service";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        UserDto userDto=mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);


        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
