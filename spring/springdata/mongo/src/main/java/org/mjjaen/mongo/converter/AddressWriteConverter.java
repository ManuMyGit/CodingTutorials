package org.mjjaen.mongo.converter;

import org.bson.Document;
import org.mjjaen.mongo.businessObject.Address;
import org.springframework.core.convert.converter.Converter;

public class AddressWriteConverter implements Converter<Address, Document> {
    @Override
    public Document convert(Address address) {
        Document obj = new Document();
        obj.put("city", address.getCity());
        obj.put("state", address.getState());
        obj.put("zipCode", address.getZipCode());
        obj.put("country", address.getCountry());
        obj.remove("_class");
        return obj;
    }
}