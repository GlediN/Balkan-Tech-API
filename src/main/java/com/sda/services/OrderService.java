package com.sda.services;

import com.sda.repositories.*;
import com.sda.dto.OrderItemWrite;
import com.sda.dto.OrderWrite;
import com.sda.entities.Order;
import com.sda.entities.OrderItem;
import com.sda.entities.Product;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final OrderItemsDAO orderItemsDAO;


    public ResponseEntity<String> getOrder(OrderWrite orderWrite) {
        return null;
    }


    public ResponseEntity<String> saveOrder(OrderWrite orderWrite) {
        try {
            Order order = createOrderFromDTO(orderWrite);
            orderDao.save(order);

            if (orderWrite.getOrderItems() != null) {
                for (OrderItemWrite itemWrite : orderWrite.getOrderItems()) {
                    OrderItem orderItem = new OrderItem();
                    Product product = productDao.findById((int) Long.parseLong(itemWrite.getProductId())).orElse(null);
                    if (product != null) {
                        orderItem.setProductId(product);
                        orderItem.setOrderId(order);
                        orderItem.setQuantity(itemWrite.getQuantity());
                       orderItem.setPrice(product.getPrice());
                        orderItemsDAO.save(orderItem);
                    } else {
                        return HelpfulUtils.getResponseEntity("Product ID not found", HttpStatus.BAD_REQUEST);
                    }
                }
            }
            updateProductQuantities(orderWrite);
            return ResponseEntity.ok("Order saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving order");
        }
    }
    private void updateProductQuantities(OrderWrite orderWrite) {
        if (orderWrite.getOrderItems() != null) {
            for (OrderItemWrite itemWrite : orderWrite.getOrderItems()) {
                Product product = productDao.findById((int) Long.parseLong(itemWrite.getProductId())).orElse(null);

                if (product != null) {
                    int orderQuantity = Integer.parseInt(itemWrite.getQuantity());
                    int productQuantity = Integer.parseInt(String.valueOf(product.getQuantity()));
                    if (orderQuantity > productQuantity) {
                        throw new IllegalArgumentException("Order quantity exceeds available product quantity");
                    }//Nje control check per orderQuantity

                    int remainingQuantity = productQuantity - orderQuantity;
                    product.setQuantity(Integer.valueOf(String.valueOf(remainingQuantity)));
                    product.setSoldQty(product.getSoldQty()+orderQuantity);//shtova edhe sasine e shitur te produktit
                    productDao.save(product);
                }
            }
        }
    }

    private Order createOrderFromDTO(OrderWrite orderWrite) {
        Order order = new Order();

        OrderItemWrite orderItemWrite= new OrderItemWrite();
        order.setId(String.valueOf(UUID.randomUUID()));
        order.setName(orderWrite.getName());
        order.setContactNumber(orderWrite.getContactNumber());
        order.setAddress(orderWrite.getAddress());
        order.setTotalPrice((orderWrite.getTotalPrice()));
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userDao.findByEmailId(orderWrite.getEmail()));


        return order;
    }
}
