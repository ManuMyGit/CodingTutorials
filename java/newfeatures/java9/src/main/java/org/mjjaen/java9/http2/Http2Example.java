package org.mjjaen.java9.http2;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java9.Example;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@Slf4j
public class Http2Example implements Example {
    @Override
    public void runExample() {
        var name = "Java 10";
        log.info("Running HTTP/2 example ...");
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("https://www.google.com"))
                    .headers("key1", "value1", "key2", "value2")
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            log.info("Response : "+resp.body());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
