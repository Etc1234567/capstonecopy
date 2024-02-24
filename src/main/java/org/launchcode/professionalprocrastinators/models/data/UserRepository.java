package org.launchcode.professionalprocrastinators.models.data;


import jakarta.servlet.http.*;
import org.launchcode.professionalprocrastinators.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

}
