package com.sda.serviceImpl;

import com.sda.dao.OrderDao;
import com.sda.dao.OrderItemsDAO;
import com.sda.dao.ProductDao;
import com.sda.dao.UserDao;
import com.sda.entities.OrderItem;
import com.sda.entities.Orders;
import com.sda.entities.Product;
import com.sda.entities.User;
import com.sda.service.OrderService;
import com.sda.utils.HelpfulUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final UserDao userDao;
    final OrderDao orderDao;
    final ProductDao productDao;
    final OrderItemsDAO orderItemsDAO;

    @Override
    public ResponseEntity<String> getOrder(Map<String, String> requestMap) {
        try {
            getOrdersFromMap(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean validateOrder(Map<String, String> requestMap) {
        if (requestMap.containsKey("name")
                && requestMap.containsKey("surname") && requestMap.containsKey("contactNumber") && requestMap.containsKey("address") && requestMap.containsKey("country")) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity saveOrder(Map<String, String> requestMap){
        try {
            Orders savedOrder = orderDao.save(getOrdersFromMap(requestMap));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder);
            orderItem.setQuantity(null);
            orderItemsDAO.save(orderItem);

            return HelpfulUtils.getResponseEntity("Order Saved",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("productId")) {
            try {
                int productId = Integer.parseInt(requestMap.get("productId"));
                return productDao.findById(productId).orElse(null);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private Orders getOrdersFromMap(Map<String, String> requestMap) {
        Orders orders = new Orders();
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        orders.setId(String.valueOf(UUID.randomUUID()));
        orders.setName(requestMap.get("name"));
        orders.setEmail(requestMap.get("surname"));
        orders.setContactNumber(requestMap.get("contactNumber"));
        orders.setAddress(requestMap.get("address"));
        orders.setTotalPrice(Double.parseDouble(requestMap.get("totalPrice")));
        orders.setOrderDate(LocalDateTime.now());
        orders.setUser(userDao.findByEmailId(requestMap.get("email")));
        return orders;

    }


}