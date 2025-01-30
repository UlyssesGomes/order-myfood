package com.myfood.order.service;

import com.myfood.order.dto.OrderDTO;
import com.myfood.order.dto.StatusDTO;
import com.myfood.order.model.Order;
import com.myfood.order.model.Status;
import com.myfood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<OrderDTO> getAll() {
        return repository.findAll().stream()
                .map(o -> modelMapper.map(o, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.ORDER_PLACED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order savedOrder = repository.save(order);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    public OrderDTO updateStatus(Long id, StatusDTO dto) {

        Order order = repository.getByIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void aproveOrderPayment(Long id) {

        Order order = repository.getByIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
