package org.mjjaen.mongo.repository;

import org.bson.types.ObjectId;
import org.mjjaen.mongo.businessObject.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, ObjectId> {
}
