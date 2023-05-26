package com.example.userservicegradle.service;

import com.example.userservicegradle.dto.UserDto;
import com.example.userservicegradle.jpa.UserEntity;
import com.example.userservicegradle.jpa.UserRepository;
import com.example.userservicegradle.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnuserDto = mapper.map(userEntity, UserDto.class);
        return returnuserDto;
    }

    @Override
    public UserDto getUserById(String userId) {

        UserEntity userEntity=userRepository.findByUserId(userId);

        if(userEntity==null){
            throw new UsernameNotFoundException("User not found");
        }



        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orders=new ArrayList<>();

        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public List<UserEntity> getUserByAll() {
        return (List<UserEntity>) userRepository.findAll();
    }
}
