package com.sda.restImpl;

import com.sda.rest.OrderRest;
import com.sda.service.OrderService;
import com.sda.serviceImpl.OrderServiceImpl;
import com.sda.utils.HelpfulUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@Getter
@Setter
public class OrderRestImpl implements OrderRest {
    final OrderService orderService;
    @Override
    public ResponseEntity<String> checkout(Map<String, String> requestMap) {
        try {
            return orderService.getOrder(requestMap);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
