package com.web.login.User.Google;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoogleUserRepository extends MongoRepository<GoogleUser, String> {

}
