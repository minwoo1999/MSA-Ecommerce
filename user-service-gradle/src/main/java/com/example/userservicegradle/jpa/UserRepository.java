package com.example.userservicegradle.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserEntity,Long> {


}
