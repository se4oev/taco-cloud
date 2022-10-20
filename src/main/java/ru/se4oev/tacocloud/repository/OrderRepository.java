package ru.se4oev.tacocloud.repository;

import ru.se4oev.tacocloud.entity.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);

}
