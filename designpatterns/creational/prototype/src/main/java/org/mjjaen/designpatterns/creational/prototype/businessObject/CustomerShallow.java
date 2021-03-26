package org.mjjaen.designpatterns.creational.prototype.businessObject;

public class CustomerShallow extends Customer {
    @Override
    public Customer cloneDocument() {
        /*Clone with shallow copy*/
        CustomerShallow customerShallow = null;
        try {
            customerShallow = (CustomerShallow) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return customerShallow;
    }
    @Override
    public String toString() {
        return "[CustomerShallow: FirstName - " + getFirstName() + ", LastName - " + getLastName() + ", DriverLicense - " + getDriveLicense() + "]";
    }
}