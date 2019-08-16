package io.cdap.plugin.google.shopping;

import com.google.api.services.content.model.Price;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductShippingWeight;
import com.google.gson.Gson;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.format.StructuredRecordStringConverter;
import io.cdap.plugin.google.shopping.content.source.ShoppingContentConstants;
import io.cdap.cdap.api.data.schema.Schema;


public class ShoppingContentTest {
  public static void main(String[] args) throws Exception {



    ProductShippingWeight ww = new ProductShippingWeight();
    ww.setUnit("lbs");
    ww.setValue(110.05);

    Product product = new Product();
    Price price = new Price();
    price.setCurrency("USD");
    price.setValue("12.3");
    product.setPrice(price);
    product.setShippingWeight(ww);
    String productString = new Gson().toJson(product);
    System.out.println("prdduct string " + productString);

    Schema schema = Schema.parseJson(ShoppingContentConstants.PRODUCT_SCHEMA);
    StructuredRecord record = StructuredRecordStringConverter
        .fromJsonString(productString, schema);
    System.out.println(StructuredRecordStringConverter.toJsonString(record));
  }

}
