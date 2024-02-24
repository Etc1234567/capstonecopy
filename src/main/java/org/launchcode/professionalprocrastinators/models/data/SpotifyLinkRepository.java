package org.launchcode.professionalprocrastinators.models.data;

import org.launchcode.professionalprocrastinators.models.Vacation;
import org.springframework.data.repository.CrudRepository;

public interface SpotifyLinkRepository extends CrudRepository<Vacation, Integer> {
//    Vacation findbyRefId(String refId);
}
