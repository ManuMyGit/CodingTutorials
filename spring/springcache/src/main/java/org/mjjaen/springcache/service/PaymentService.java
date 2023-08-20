package org.mjjaen.springcache.service;

import org.mjjaen.springcache.businessObject.Payment;

public interface PaymentService {
    Payment get(Long id, boolean fromCache);
    void delete(Long id);
    Payment update(Payment payment);
}
