package com.bpm.BpmBackstageDemo.rest;

import com.bpm.BpmBackstageDemo.domain.Orders;
import com.bpm.BpmBackstageDemo.model.OrderDto;
import com.bpm.BpmBackstageDemo.model.ResultDto;
import com.bpm.BpmBackstageDemo.repository.OrdersRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping
    public ResultDto<Object> findAll() {
        List<Orders> orders = ordersRepository.findAll();
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-ticketRules");

        List<OrderDto> orderDtos = orders.stream().map(item -> OrderDto.builder().
                id(item.getId()).money(item.getMoney()).name(item.getName()).customer(item.getCunstomer()).
                build()).collect(Collectors.toList());
        orderDtos.stream().forEach(item -> {
            kieSession.insert(item);
            kieSession.fireAllRules();
        });
        kieSession.dispose();
        return ResultDto.builder().code(0).isSuccess(true).message("success").data(orderDtos).build();
    }
}
