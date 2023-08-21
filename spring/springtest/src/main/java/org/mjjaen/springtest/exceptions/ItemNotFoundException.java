package org.mjjaen.springtest.exceptions;

public class ItemNotFoundException extends RuntimeException {
    private Long id;
    public ItemNotFoundException(Integer id) {
        super("Could not find item " + id);
    }
}