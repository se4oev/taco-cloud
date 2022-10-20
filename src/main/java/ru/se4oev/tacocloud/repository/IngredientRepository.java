package ru.se4oev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.se4oev.tacocloud.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
