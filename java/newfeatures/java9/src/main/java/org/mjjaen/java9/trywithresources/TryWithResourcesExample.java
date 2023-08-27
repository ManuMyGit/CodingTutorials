package org.mjjaen.java9.trywithresources;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java9.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
@Slf4j
public class TryWithResourcesExample implements Example {
    @Override
    public void runExample() {
        Scanner scanner = null;
        try {
            log.info("Opening file");
            scanner = new Scanner(ResourceUtils.getFile("classpath:text.txt"));
            log.info("Printing the content of the file with a regular try-catch-finally block");
            while (scanner.hasNext()) {
                log.info(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            log.info("Calling close in finally block");
            if (scanner != null) {
                scanner.close();
            }
        }

        log.info("Printing the content of the file with a try-with-resource block");
        try (Scanner scanner2 = new Scanner(ResourceUtils.getFile("classpath:text.txt"))) {
            while (scanner2.hasNext()) {
                log.info(scanner2.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        log.info("No need to close the resource manually");
    }
}
