package ru.se4oev.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ru.se4oev.tacocloud.entity.TacoOrder;
import ru.se4oev.tacocloud.repository.OrderRepository;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors())
            return "orderForm";

        log.info("Order submittes: {}", order);
        AtomicInteger i = new AtomicInteger(1);
        order.getTacos().forEach(t -> {
            t.setTacoOrder(order);
            t.setTacoOrderKey(i.getAndIncrement());
        });
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
