package org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.impl;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import lombok.extern.log4j.Log4j2;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.configuration.TwitterConfiguration;
import org.mjjaen.microservices.eventdriven.kafka.twitterproducer.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Log4j2
public class TwitterServiceImpl implements TwitterService {
    @Autowired
    private TwitterConfiguration twitterConfiguration;

    @Override
    public BlockingQueue<String> createMessageQueue(Integer capacity) {
        return new LinkedBlockingQueue<String>(capacity);
    }

    @Override
    public Client createTwitterClient(BlockingQueue<String> messageQueue, String ... hotTopics) {
        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: we've got to follow people (followings) or terms
        //List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList(hotTopics);
        //hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(twitterConfiguration.getApiKey(), twitterConfiguration.getApiSecretKey(), twitterConfiguration.getAccessToken(), twitterConfiguration.getSecretAccessToken());

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01") // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(messageQueue));
        //.eventMessageQueue(eventQueue); // optional: use this if you want to process client events

        return builder.build();
    }

    @Override
    public void connect(Client client) {
        client.connect();
    }

    @Override
    public void stop(Client client) {
        client.stop();
    }
}
