package ru.se4oev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.se4oev.tacocloud.entity.Taco;

/**
 * Created by karpenko on 24.10.2022.
 * Description:
 */
public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
