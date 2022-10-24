package ru.se4oev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.se4oev.tacocloud.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
