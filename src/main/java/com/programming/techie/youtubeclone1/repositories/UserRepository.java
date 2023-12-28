package com.programming.techie.youtubeclone1.repositories;

import com.programming.techie.youtubeclone1.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserBySub(String sub);
}
