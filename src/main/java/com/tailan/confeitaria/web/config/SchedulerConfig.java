package com.tailan.confeitaria.web.config;

import com.tailan.confeitaria.web.domain.Order;
import com.tailan.confeitaria.web.domain.enums.OrderStatus;
import com.tailan.confeitaria.web.repository.OrderRepository;
import com.tailan.confeitaria.web.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    public static final Long FIXED_DELAY = 180000L;
    private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public SchedulerConfig(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }


    //A cada 3 minutos vai no banco buscar pedidos criado a 3 minutos atras com status de aguardando pagamento
    //Cada pedido encontrato, ele chama o metodo interno para cancelar o pedido.
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void cancelOrderWithouPayment() {
        // Cancelar pedidos sem pagamentos em ate 3  minutos.
        Instant instantLimit = Instant.now().minus(3, ChronoUnit.MINUTES);

        //Pega todos pedidos mais velho que 3 minutos.
        List<Order> orderList = orderRepository.findAllByStatusAndMomentOrderBefore(OrderStatus.AWAITING_PAYMENT.getCode(), instantLimit);
        for (Order order : orderList) {
            orderService.executeCancellationInternal(order);
            log.info("Cancellando order {}", order.getId());
        }
    }
}
