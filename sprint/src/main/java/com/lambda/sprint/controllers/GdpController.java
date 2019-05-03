package com.lambda.sprint.controllers;

import com.lambda.sprint.SprintApplication;
import com.lambda.sprint.exceptions.ResourceNotFoundException;
import com.lambda.sprint.models.GDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;

@RestController
public class GdpController
{
//    @GetMapping(value = "/")
//    public ResponseEntity<?> getStuff()
//    {
//        return null;
//    }

    // TODO whats up with the class the getLogger method takes?
    private static final Logger logger = LoggerFactory.getLogger(GdpController.class);

    // /names - return using the JSON format all of the countries alphabetized by name
    @GetMapping(value="/names")
    public ResponseEntity<?> getAllCountrysByName()
    {
        logger.info("processing request for endpoint \"/names\"");
        ArrayList<GDP> gdpList = SprintApplication.data.gdpList;
        gdpList.sort(Comparator.comparing(g1 -> g1.getName().toLowerCase()));
        return new ResponseEntity<>(gdpList, HttpStatus.OK);
    }

    // /economy - return using the JSON format all of the countries sorted from most to least GDP
    @GetMapping(value="/economy")
    public ResponseEntity<?> getAllCountriesByGDPDescending()
    {
        logger.info("processing request for endpoint \"/economy\"");
        ArrayList<GDP> gdpList = SprintApplication.data.gdpList;
        gdpList.sort(Comparator.comparingLong(GDP::getValue));

        return new ResponseEntity<>(gdpList, HttpStatus.OK);
    }

    // /gdp/{country name} - return using the JSON format the record for the named country. Must be spelled as in the data however the search should NOT be case sensitive.
    @GetMapping(value="/gdp/{name}")
    public ResponseEntity<?> getCountryByName(@PathVariable String name) throws ResourceNotFoundException
    {
        logger.info("processing request for endpoint \"/gdp/" + name + "\"");
        GDP rtnGdp = SprintApplication.data.find(g -> g.getName().toLowerCase().equals(name.toLowerCase()));

        if (rtnGdp == null) {
            throw new ResourceNotFoundException("Couldn't find resource with name: " + name);
        }

        return new ResponseEntity<>(rtnGdp, HttpStatus.OK);
    }

    // /country/{id} - return using the JSON format a single country and GDP based off of its id number
    @GetMapping(value="/country/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable long id)
    {
        logger.info("processing request for endpoint \"country/" + id + "\"");
        GDP rtnGdp = SprintApplication.data.find(g -> g.getId() == id);

        if (rtnGdp == null) {
            throw new ResourceNotFoundException("Couldn't find resource with id: " + id);
        }

        return new ResponseEntity<>(rtnGdp, HttpStatus.OK);
    }

    // /country/stats/median - return using the JSON the country and its GDP with the median GDP. For odd number list, return the the country in the middle. For even number list you may return either one of the countries found in the middle.
    @GetMapping(value="/country/stats/median")
    public ResponseEntity<?> getCountryByMedianGdp()
    {
        logger.info("processing request for endpoint \"country/stats/median\"");
        ArrayList<GDP> gdpList = SprintApplication.data.gdpList;
        gdpList.sort(Comparator.comparingLong(GDP::getValue));
        GDP rtnGdp = gdpList.get(gdpList.size() / 2);

        return new ResponseEntity<>(rtnGdp, HttpStatus.OK);
    }

}
