package org.launchcode.professionalprocrastinators.models.data;

import org.jetbrains.annotations.NotNull;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacationRepository extends CrudRepository<Vacation,Integer> {


    List<Vacation> findByUser(User user);

    List<Vacation> findByCity(String city);

    List<Vacation> findByCountry(String country);

    List<Vacation> findByState(String state);

    List<Vacation> findByVacationDate(LocalDateTime vacationDate);

    List<Vacation> findByCityAndCountry(String city, String country);
     @NotNull List<Vacation> findAll();
}
