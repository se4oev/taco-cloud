package ru.se4oev.tacocloud.repository;

import ru.se4oev.tacocloud.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
