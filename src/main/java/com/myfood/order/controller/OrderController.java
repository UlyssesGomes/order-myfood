package com.myfood.order.controller;

import com.myfood.order.dto.OrderDTO;
import com.myfood.order.dto.StatusDTO;
import com.myfood.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDTO> getAll() {
            return service.getAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> getById(@PathVariable @NotNull Long id) {
            OrderDTO dto = service.getById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
            OrderDTO orderDto = service.create(dto);

            URI address = uriBuilder.path("/orders/{id}").buildAndExpand(orderDto.getId()).toUri();

            return ResponseEntity.created(address).body(orderDto);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody StatusDTO status){
           OrderDTO dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/paid")
        public ResponseEntity<Void> aprovePayment(@PathVariable @NotNull Long id) {
            service.aproveOrderPayment(id);

            return ResponseEntity.ok().build();

        }
}
