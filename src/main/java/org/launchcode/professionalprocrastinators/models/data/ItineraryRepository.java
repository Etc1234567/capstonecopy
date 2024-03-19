package org.launchcode.professionalprocrastinators.models.data;

import org.launchcode.professionalprocrastinators.models.Activity;
import org.launchcode.professionalprocrastinators.models.Itinerary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends CrudRepository<Itinerary,Integer> {

    List<Itinerary> findByLocation(String location);
    List<Itinerary> findAll();

}
