package org.mjjaen.mongo.repository;

import org.bson.types.ObjectId;
import org.mjjaen.mongo.businessObject.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId>, BookRepositoryCustom {
}
