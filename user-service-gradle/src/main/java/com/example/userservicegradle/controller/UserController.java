package com.example.userservicegradle.controller;


import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.jpa.UserEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-service")
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

    @GetMapping("/health_check")
    public String status(){
        return "It's Working in User Service on Port"+env.getProperty("local.server.port");
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
    @GetMapping("/users")
    public ResponseEntity<List<?>> getUsers(){
        List<UserEntity> userList = userService.getUserByAll();
//        List<ResponseUser> result = userList.stream().map(u -> new ResponseUser(u)).collect(Collectors.toList());
        List<ResponseUser> result=new ArrayList<>();
        userList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });


        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }



    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId){

        System.out.println(userId);

        UserDto userDto = userService.getUserById(userId);
        System.out.println(userDto.getName());
        ResponseUser result = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
