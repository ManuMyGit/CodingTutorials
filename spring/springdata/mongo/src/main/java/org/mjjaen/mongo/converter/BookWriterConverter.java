package org.mjjaen.mongo.converter;

import org.bson.Document;
import org.mjjaen.mongo.businessObject.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class BookWriterConverter implements Converter<Book, Document> {
    @Override
    public Document convert(Book book) {
        Document obj = new Document();
        obj.put("name", book.getName());
        obj.put("_id", book.getId());
        return obj;
    }
}
