package org.mjjaen.mongo.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mjjaen.mongo.businessObject.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BookReaderConverter implements Converter<Document, Book> {
@Override
public Book convert(Document document) {
        return Book.builder()
                .name((String)document.get("name"))
                .id((ObjectId) document.get("_id"))
                .build();
        }
}