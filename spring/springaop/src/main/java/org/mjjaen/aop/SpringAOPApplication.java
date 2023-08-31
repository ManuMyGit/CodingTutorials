package org.mjjaen.aop;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.aop.businessObject.Book;
import org.mjjaen.aop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy
@Slf4j
public class SpringAOPApplication implements CommandLineRunner {
    private static ApplicationContext applicationContext;

    @Autowired
    private BookService bookService;

    public static void main( String[] args ) {
        applicationContext = SpringApplication.run(SpringAOPApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running example ...");
        log.info("Creating three books ...");
        Book book1 = Book.builder().id("1").name("The Fellowship of the Ring").isbn("123").build();
        Book book2 = Book.builder().id("1").name("The Two Towers").isbn("456").build();
        Book book3 = Book.builder().id("1").name("The Return of the King").isbn("789").build();

        log.info("Saving the three books. ExecutionTimeAspect will step in ...");
        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);

        log.info("Retrieving all the books. Before, AfterReturning and After advices will be executed ...");
        List<Book> books = bookService.findAll();
        try {
            log.info("Deleting a book. Before, AfterThrowing and After advices will be executed ...");
            bookService.deleteById(book1.getId());
        } catch (Exception e) {
            // Do nothing
        }
    }
}
