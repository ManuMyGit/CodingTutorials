package org.mjjaen.springcache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springcache.businessObject.Payment;
import org.mjjaen.springcache.repository.PaymentRepository;
import org.mjjaen.springcache.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Cacheable(value = "paymentsCache", key = "#id", condition = "#fromCache")
    public Payment get(Long id, boolean fromCache) {
        log.info("Getting payment with ID {}.", id);
        return paymentRepository.findById(id).get();
    }

    @Override
    @CacheEvict(value = "paymentsCache", key = "#id")
    public void delete(Long id) {
        log.info("Deleting payment with ID {}.", id);
        paymentRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "paymentsCache", key = "#payment.id")
    public Payment update(Payment payment) {
        log.info("Updating payment with ID {}.", payment.getId());
        return paymentRepository.save(payment);
    }
}
