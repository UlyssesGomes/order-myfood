package com.myfood.order.dto;

import com.myfood.order.model.OrderItem;
import com.myfood.order.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();
}
