package org.mjjaen.designpatterns.creational.prototype.businessObject;

public abstract class Customer implements Cloneable {
    private String firstName;
    private String lastName;
    private String driveLicense;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDriveLicense(String driveLicense) {
        this.driveLicense = driveLicense;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDriveLicense() {
        return driveLicense;
    }

    public abstract Customer cloneDocument() throws CloneNotSupportedException;
}