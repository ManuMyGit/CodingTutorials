package org.mjjaen.mongo.converter;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.mongo.businessObject.User;
import org.springframework.core.convert.converter.Converter;
import org.bson.Document;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
@Slf4j
public class UserWriterConverter implements Converter<User, Document> {
    @Override
    public Document convert(User user) {
        log.info("Using the converter to write ...");
        log.info(user.toString());
        Document document = new Document();
        document.put("_id", user.getId());
        document.put("name", user.getName());
        document.put("userSettings", user.getUserSettings());
        document.put("dateCreated", user.getDateCreated());
        document.put("lastUpdated", user.getLastUpdated());
        document.put("version", user.getVersion());
        document.put("address", new AddressWriteConverter().convert(user.getAddress()));
        document.put("profile", user.getProfile());
        document.put("books", user.getBooks());
        return document;
    }
}
