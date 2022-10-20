package ru.se4oev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.se4oev.tacocloud.entity.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}
