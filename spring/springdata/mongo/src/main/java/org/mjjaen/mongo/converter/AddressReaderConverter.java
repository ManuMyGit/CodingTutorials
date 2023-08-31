package org.mjjaen.mongo.converter;

import org.bson.Document;
import org.mjjaen.mongo.businessObject.Address;
import org.springframework.core.convert.converter.Converter;

public class AddressReaderConverter implements Converter<Document, Address> {
    @Override
    public Address convert(Document document) {
        return Address.builder()
                .zipCode((String)document.get("zipCode"))
                .state((String)document.get("state"))
                .city((String)document.get("city"))
                .country((String)document.get("country"))
                .build();
    }
}
