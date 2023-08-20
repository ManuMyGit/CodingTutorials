package org.mjjaen.springcache;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springcache.businessObject.Payment;
import org.mjjaen.springcache.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableCaching
@Slf4j
public class SpringCacheApplication implements CommandLineRunner
{
    @Autowired
    private PaymentService paymentService;

    private static ApplicationContext applicationContext;

    public static void main( String[] args )
    {
        applicationContext = SpringApplication.run(SpringCacheApplication.class, args);
        String [] beans = applicationContext.getBeanDefinitionNames();
        System.out.println(beans);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Retrieving payment 1 for the first time ... The information will be retrieved from the database.");
        log.info(paymentService.get(new Long(1), true).toString());
        log.info("Retrieving payment 1 for the second time ... The information will be retrieved from the cache.");
        log.info(paymentService.get(new Long(1), true).toString());
        log.info("Retrieving payment 1 for the third time with no retrieving from cache option ... The information will be retrieved from the database.");
        log.info(paymentService.get(new Long(1), false).toString());
        /*log.info("Retrieving payment 2 for the first time ... The information will be retrieved from the database.");
        log.info(paymentService.get(new Long(2), true).toString());
        log.info("Retrieving payment 2 for the second time ... The information will be retrieved from the cache.");
        log.info(paymentService.get(new Long(2), true).toString());
        log.info("Deleting payment 1 ... The information will be deleted both from the database and the cache.");
        paymentService.delete(new Long(1));
        Payment payment = paymentService.get(new Long(2), true);
        payment.setAmount(Double.valueOf(100000000));
        log.info("Updating payment 2 ... The information will be updated both in the database and the cache.");
        paymentService.update(payment);
        System.exit(0);*/
    }
}
