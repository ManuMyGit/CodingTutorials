package org.mjjaen.designpatterns.creational.prototype.businessObject;

public class CustomerDeep extends Customer {
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Customer cloneDocument() throws CloneNotSupportedException {
        /*Clone with deep copy*/
        CustomerDeep customer;
        customer = (CustomerDeep) super.clone();
        Address clonedAddress = (Address) customer.getAddress().clone();
        customer.setAddress(clonedAddress);
        return customer;
    }
    @Override
    public String toString() {
        return "[CustomerShallow: FirstName - " + getFirstName() + ", LastName - " + getLastName() + ", DriverLicense - " + getDriveLicense() + ", Address - " + getAddress() + "]";
    }
}