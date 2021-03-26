package org.mjjaen.designpatterns.creational.prototype.businessObject;

import java.util.Map;
import java.util.HashMap;

public class CustomerPrototypeManager {
    private static Map<String, Customer> prototypes = new HashMap<String, Customer>();

    static {
        CustomerShallow customerShallow = new CustomerShallow();
        customerShallow.setFirstName("Mark");
        customerShallow.setLastName("Johnson");
        /*Retrieve data from database/network call/disk I/O here*/
        customerShallow.setDriveLicense("12312323 (stored in database/network)");
        prototypes.put("customerShallow", customerShallow);
        Address address = new Address();
        address.setStreet("5th Ave");
        address.setCity("New York City");
        address.setState("New York");
        address.setZipCode("10110");
        CustomerDeep customerDeep = new CustomerDeep();
        customerDeep.setFirstName("John");
        customerDeep.setLastName("Markson");
        /*Retrieve data from database/network call/disk I/O here*/
        customerDeep.setDriveLicense("76876876 (stored in database/network)");
        customerDeep.setAddress(address);
        prototypes.put("customerDeep", customerDeep);
    }

    public static Customer getClonedDocument(final String type) {
        Customer clonedDoc = null;
        try {
            Customer doc = prototypes.get(type);
            clonedDoc = doc.cloneDocument();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clonedDoc;
    }
}