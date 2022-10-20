package ru.se4oev.tacocloud.repository;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.se4oev.tacocloud.entity.Ingredient;
import ru.se4oev.tacocloud.entity.Taco;
import ru.se4oev.tacocloud.entity.TacoOrder;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        String query = "" +
                "INSERT INTO taco_order " +
                "  (delivery_name " +
                "  ,delivery_street " +
                "  ,delivery_city " +
                "  ,delivery_state " +
                "  ,delivery_zip " +
                "  ,cc_number " +
                "  ,cc_expiration " +
                "  ,cc_cvv " +
                "  ,create_date)" +
                "VALUES" +
                "  (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                query,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.TIMESTAMP
        );
        factory.setReturnGeneratedKeys(true);

        order.setCreateDate(new Date());
        PreparedStatementCreator creator = factory.newPreparedStatementCreator(
                List.of(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getCreateDate()
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long orderId = ((Number) keyHolder.getKeys().get("id")).longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        for (int i = 0; i < tacos.size(); i++) {
            saveTaco(orderId, i, tacos.get(i));
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreateDate(new Date());
        String query = "" +
                "INSERT INTO taco " +
                "  (name " +
                "  ,create_date " +
                "  ,taco_order " +
                "  ,taco_order_key) " +
                "VALUES (?, ?, ?, ?) ";
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
                query,
                Types.VARCHAR,
                Types.TIMESTAMP,
                Type.LONG,
                Type.LONG
        );
        factory.setReturnGeneratedKeys(true);

        PreparedStatementCreator creator = factory.newPreparedStatementCreator(
                List.of(
                        taco.getName(),
                        taco.getCreateDate(),
                        orderId,
                        orderKey
                )
        );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(creator, keyHolder);
        long tacoId = ((Number) keyHolder.getKeys().get("id")).longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(Long tacoId, List<Ingredient> ingredients) {
        String query = "" +
                "INSERT INTO ingredient_ref " +
                "  (ingredient " +
                "  ,taco " +
                "  ,taco_key) " +
                "VALUES (?, ?, ?) ";
        for (int i = 0; i < ingredients.size(); i++) {
            jdbcOperations.update(
                    query, ingredients.get(i).getId(), tacoId, i
            );
        }
    }

}
