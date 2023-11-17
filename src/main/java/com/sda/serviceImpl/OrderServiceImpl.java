package com.sda.serviceImpl;

import com.sda.dao.OrderDao;
import com.sda.dao.OrderItemsDAO;
import com.sda.dao.ProductDao;
import com.sda.dao.UserDao;
import com.sda.dto.OrderItemWrite;
import com.sda.dto.OrderWrite;
import com.sda.entities.Order;
import com.sda.entities.OrderItem;
import com.sda.entities.Product;
import com.sda.service.OrderService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final OrderItemsDAO orderItemsDAO;

    @Override
    public ResponseEntity<String> getOrder(OrderWrite orderWrite) {
        return null;
    }

    @Override
    public ResponseEntity<String> saveOrder(OrderWrite orderWrite) {
        try {
//            orderWrite.setTotalPrice("totalPrice"); // This line seems unnecessary, please review

            // Add your business logic here for saving the order
            Order order = createOrderFromDTO(orderWrite);
            orderDao.save(order);
            OrderItemWrite orderItemWrite=new OrderItemWrite();
            Product product= productDao.getProductById(Integer.valueOf(orderItemWrite.getProductId()));
            if (orderWrite.getOrderItems() != null) {
                for (OrderItemWrite itemWrite : orderWrite.getOrderItems()) {
                    OrderItem orderItem = new OrderItem();
//                    Product product1= productDao.getProductById(orderItem.getProductId());

                    orderItem.setProductId(product);
                    orderItem.setOrderId(order);
                    orderItem.setQuantity(itemWrite.getQuantity());

                    // Fetch the product from the database using the productId
//                    Product product = productDao.findById((int) Long.parseLong(itemWrite.getProductId())).orElse(null);
                    if (product != null) {
                        orderItem.setProduct(product);
                        orderItemsDAO.save(orderItem);
                    } else {
                        HelpfulUtils.getResponseEntity("Producy ID not found", HttpStatus.BAD_REQUEST);
                        // Handle the case where the product with the given ID is not found
                        // You might want to throw an exception or handle it appropriately
                    }
                }
            }
            return ResponseEntity.ok("Order saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving order");
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

        // Set other properties from the DTO as needed
        // ...

        // Save order items
//        if (orderWrite.getOrderItems() != null) {
//            for (OrderItemWrite itemWrite : orderWrite.getOrderItems()) {
//                OrderItem orderItem = new OrderItem();
//                orderItem.setOrderId(order);
//                orderItem.setQuantity(itemWrite.getQuantity());
//
//                // Fetch the product from the database using the productId
//                Product product = productDao.findById((int) Long.parseLong(itemWrite.getProductId())).orElse(null);
//                if (product != null) {
//                    orderItem.setProduct(product);
//                    orderItemsDAO.save(orderItem);
//                } else {
//                    // Handle the case where the product with the given ID is not found
//                    // You might want to throw an exception or handle it appropriately
//                }
//            }
//        }

        return order;
    }

    // Other methods as needed
}
