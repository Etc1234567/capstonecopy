package org.launchcode.professionalprocrastinators.models.data;

import org.launchcode.professionalprocrastinators.models.Activity;
import org.launchcode.professionalprocrastinators.models.Likes;
import org.launchcode.professionalprocrastinators.models.User;
import org.launchcode.professionalprocrastinators.models.Vacation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Integer> {

    List<Likes> findByLikes(int likes);

    List<Likes> findAll();

    List<Likes> findByUser(User user);

    List<Likes> findByUserId(int userId);

    Likes findByUserAndVacation(User currentUser, Vacation vacation);

    boolean existsByUserAndVacation(User user, Vacation vacation);
}
