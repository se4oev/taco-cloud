package ru.se4oev.tacocloud.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.se4oev.tacocloud.entity.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        String query = "" +
                "SELECT id " +
                "      ,name " +
                "      ,type " +
                "  FROM Ingredient ";
        return jdbcTemplate.query(query, this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        String query = "" +
                "SELECT id " +
                "      ,name " +
                "      ,type " +
                "  FROM Ingredient " +
                " WHERE id = ? ";
        List<Ingredient> results = jdbcTemplate.query(query, this::mapRowToIngredient, id);
        return results.size() == 0
                ? Optional.empty()
                : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        String query = "" +
                "INSERT INTO Ingredient " +
                "  (id " +
                "  ,name " +
                "  ,type) " +
                "VALUES " +
                "  (? " +
                "  ,? " +
                "  ,?) ";
        jdbcTemplate.update(
                query,
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type"))
        );
    }

}
