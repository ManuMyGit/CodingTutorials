package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeospatialExamples implements Examples {
    @Autowired
    private final RedisTemplate stringRedisTemplate;
    private final GeoOperations<String, String> geoOperations;

    public GeospatialExamples(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        geoOperations = stringRedisTemplate.opsForGeo();
    }

    @Override
    public void runExamples() {
        log.info("RUNNING GEOSPATIAL EXAMPLES ...");
        log.info("Storing in Redis London, New York and Ubrique ...");
        geoOperations.add("Cities", new Point(51.509865, -0.118092), "London");
        geoOperations.add("Cities", new Point(40.730610, -73.935242), "New York");
        geoOperations.add("Cities", new Point(36.67777, -5.446), "Ubrique");
        log.info("Getting the distance from London to New York ...");
        Distance distance = geoOperations.distance("Cities", "London", "New York");
        log.info(distance.toString());
        log.info("Getting the distance from London to Ubrique ...");
        distance = geoOperations.distance("Cities", "London", "Ubrique");
        log.info(distance.toString());
        log.info("Cities located within 2000 KMs from London ...");
        GeoResults geoResults = geoOperations.radius("Cities", "London", new Distance(2000, RedisGeoCommands.DistanceUnit.KILOMETERS));
        geoResults.forEach(e -> log.info(e.toString()));
        log.info("Deleting Cities ...");
        stringRedisTemplate.delete("Cities");
    }
}
