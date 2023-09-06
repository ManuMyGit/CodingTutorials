package org.mjjaen.mongo.converter;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mjjaen.mongo.businessObject.Address;
import org.mjjaen.mongo.businessObject.Book;
import org.mjjaen.mongo.businessObject.User;
import org.springframework.core.convert.converter.Converter;
import org.bson.Document;
import org.springframework.data.convert.ReadingConverter;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ReadingConverter
@Slf4j
public class UserReaderConverter implements Converter<Document, User> {
    @Override
    public User convert(Document document) {
        log.info("Using the converter to read ...");
        Date dateCreated = (Date)document.get("dateCreated");
        Date lastUpdated = (Date)document.get("lastUpdated");
        Address address = new AddressReaderConverter().convert((Document)document.get("address"));
        User user = User.builder()
                .id((ObjectId)document.get("_id"))
                .userSettings((Map<String, String>)document.get("userSettings"))
                .dateCreated(dateCreated != null ? dateCreated.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime() : null)
                .lastUpdated(lastUpdated != null ? lastUpdated.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime() : null)
                .address(address)
                .profile((ObjectId)document.get("profile"))
                .books((List<Book>)document.get("books"))
                .version((Integer) document.get("version"))
                .build();
        log.info(user.toString());
        return user;
    }
}
