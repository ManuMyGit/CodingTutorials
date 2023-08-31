package org.mjjaen.mongo.repository;

import org.bson.types.ObjectId;
import org.mjjaen.mongo.businessObject.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>, UserRepositoryCustom {
}
