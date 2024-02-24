package org.launchcode.professionalprocrastinators.models.data;


import org.launchcode.professionalprocrastinators.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;

@Repository
public interface RewardsRepository extends CrudRepository<Rewards, Integer> {

}
