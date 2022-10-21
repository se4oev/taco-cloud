package ru.se4oev.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.se4oev.tacocloud.entity.TacoOrder;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> readOrdersByDeliveryZipAndCreateDateBetween(String deliveryZip, Date startDate, Date endDate);

}
