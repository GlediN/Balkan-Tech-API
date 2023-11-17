package com.sda.serviceImpl;

import com.sda.dao.OrderDao;
import com.sda.dao.OrderItemsDAO;
import com.sda.dao.ProductDao;
import com.sda.dao.UserDao;
import com.sda.dto.OrderWrite;
import com.sda.entities.OrderItem;
import com.sda.entities.Order;
import com.sda.entities.Product;
import com.sda.service.OrderService;
import com.sda.utils.HelpfulUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public ResponseEntity<String> saveOrder(OrderWrite dto) {
        // Ketu duhet bere logjika e ruatjes
        // Perdorni layerin controller / service / repository
        // Keni vendosur ne service logjike qe duhet ta beje kontrolleri
        // Kur doni te postoni nga client ne server punoni me Objekte jo me Map

        return ResponseEntity.ok("In development");
//        try {
//            Product product = new Product();
//            Order orderCheck = orderDao.save(getOrdersFromMap(requestMap));
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrderId(orderCheck);
//            orderItem.setQuantity(requestMap.get("product.quantity"));
//            orderItem.setPrice(requestMap.get("totalPrice"));
//            orderItem.setProductId(getProductFromMap(requestMap));
//            orderItemsDAO.save(orderItem);
//            return HelpfulUtils.getResponseEntity("Order Saved", HttpStatus.OK);
//
//            List<Map<String, String>> products = (List<Map<String, String>>) requestMap.get("product");
//            for (Map<String, String> product1 : products) {
//                // Create and save OrderItem object
//                orderItem.setOrderId(orderCheck);
//                orderItem.setQuantity((String) product1.get("quantity"));
//                // You might want to set other properties such as price based on the product map
//                // ...
//
//                // Create and save Product object
//                Product productEntity = new Product();
//                productEntity.setId(Integer.valueOf((String) product1.get("productId")));
//                // Set other properties based on the product map or fetch from the database
//                // ...
//
//            } }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return HelpfulUtils.getResponseEntity(HelpfulUtils.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Order getOrdersFromMap(Map<String, String> requestMap) {
        Order orders = new Order();

        orders.setId(String.valueOf(UUID.randomUUID()));
        orders.setName(requestMap.get("name"));
        orders.setEmail(requestMap.get("surname"));
        orders.setContactNumber(requestMap.get("contactNumber"));
        orders.setAddress(requestMap.get("address"));
        orders.setTotalPrice(Double.parseDouble(requestMap.get("totalPrice")));
        orders.setOrderDate(LocalDateTime.now());
        orders.setUser(userDao.findByEmailId(requestMap.get("email")));
//        orders.set(requestMap.get(""));

        return orders;
    }

    private Product getProductFromMap(Map<String, String> requestMap) {
        Product product = new Product();
        product.setId(Integer.valueOf(requestMap.get("product.productId")));
        return product;
    }
//    private Product soldProductsTotal(Product product,Map<String, String> requestMap){
//        int quantitySold = Integer.parseInt(requestMap.get("quantity"));
//
//         product = productDao.incrementQuantitySold();
//
//
//    }
}
//    private Orders getOrderId(Orders orders){
//        Orders orderId = orders.getId();
//        return orderId;
//    }
