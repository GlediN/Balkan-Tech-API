package com.sda.wrapper;

import com.sda.entities.ProductPhoto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductWrapper {
   Integer id;
   String name;
   String description;
   Integer quantity;
   Integer category;
   double price;
   double discount;
   Integer soldQty;
   List<ProductPhoto> productPhotoList;

   public ProductWrapper(Integer id, String name, String description, Integer quantity, Integer category, double price, double discount, Integer soldQty, List<ProductPhoto> productPhotos) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.quantity = quantity;
      this.category = category;
      this.price = price;
      this.discount = discount;
      this.soldQty = soldQty;
      this.productPhotoList = productPhotos;
   }




}
