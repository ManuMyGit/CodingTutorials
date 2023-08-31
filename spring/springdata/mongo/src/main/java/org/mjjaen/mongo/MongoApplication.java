package org.mjjaen.mongo;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.mongo.businessObject.Address;
import org.mjjaen.mongo.businessObject.Book;
import org.mjjaen.mongo.businessObject.Profile;
import org.mjjaen.mongo.businessObject.User;
import org.mjjaen.mongo.repository.BookRepository;
import org.mjjaen.mongo.repository.ProfileRepository;
import org.mjjaen.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.OffsetDateTime;
import java.util.*;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@Slf4j
public class MongoApplication implements CommandLineRunner
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BookRepository bookRepository;

    private static ApplicationContext applicationContext;

    public static void main( String[] args )
    {
        applicationContext = SpringApplication.run(MongoApplication.class, args);
        String [] beans = applicationContext.getBeanDefinitionNames();
        System.out.println(beans);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running example ...");
        log.info("Deleting all users ...");
        userRepository.deleteAll();
        log.info("Deleting all profiles ...");
        profileRepository.deleteAll();
        log.info("Deleting all books ...");
        bookRepository.deleteAll();
        Map<String, String> settings = new HashMap<>();
        settings.put("settingsKey1", "settingsValue1");
        settings.put("settingsKey2", "settingsValue2");
        //Embedded relationship
        Address address = Address.builder().city("Dallas").country("US").state("TX").zipCode("75219").build();
        //Manuel reference relationship
        Profile profile = Profile.builder().email("any@gmail.com").phoneNumber("1231231231").build();
        log.info("Saving profile to create the manual reference relationship ...");
        profileRepository.save(profile);
        //DBRef reference
        Book book1 = Book.builder().name("The Lord of the Rings").build();
        Book book2 = Book.builder().name("The Hobbit").build();
        log.info("Saving books to create the DBRef relationship ...");
        bookRepository.save(book1);
        bookRepository.save(book2);
        User user = User.builder().userSettings(settings).name("Turgon").address(address).profile(profile.getId()).books(Arrays.asList(book1, book2)).build();
        log.info("Saving the user...");
        userRepository.insert(user);
        List<User> users = userRepository.findAll();
        log.info("Getting all users and checking the list size is 1 ...");
        assertTrue( users.size() >= 1);

        log.info("Testing transactional operations ...");
        //Testing transactionality
        Book bookL = Book.builder().name("Book 1").build();
        Book bookM = Book.builder().name("Book 1").build();
        Book bookN = Book.builder().name("Book 1").build();
        bookRepository.add(bookL, bookM, bookN);
        log.info("Deleting all users ...");
        userRepository.deleteAll();
        log.info("Deleting all profiles ...");
        profileRepository.deleteAll();
        log.info("Deleting all books ...");
    }

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
