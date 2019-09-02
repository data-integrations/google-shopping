package io.cdap.plugin.google.shopping;

import com.google.api.services.content.model.Installment;
import com.google.api.services.content.model.LoyaltyPoints;
import com.google.api.services.content.model.Price;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductAspect;
import io.cdap.plugin.google.shopping.content.source.ProductWrapper;
import io.cdap.plugin.google.shopping.content.source.ProductWrapper.ProductAspectWrapper;
import org.junit.Assert;
import org.junit.Test;

public class ProductWrapperTest {

  @Test
  public void productAspectConversion() {
    ProductAspect aspect = new ProductAspect();
    aspect.setAspectName("name");
    aspect.setIntention("intention");
    aspect.setDestinationName("dest");

    ProductAspectWrapper wrapper = ProductAspectWrapper.from(aspect);

    Assert.assertEquals(wrapper.aspectName, "name");
    Assert.assertEquals(wrapper.intention, "intention");
    Assert.assertEquals(wrapper.destinationName, "dest");
  }

  @Test
  public void productConversion() {
    Product product = new Product();
    LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
    loyaltyPoints.setRatio(2.1);
    loyaltyPoints.setPointsValue(6L);
    loyaltyPoints.setName("name");
    product.setLoyaltyPoints(loyaltyPoints);

    Installment installment = new Installment();
    installment.setMonths(3L);

    Price price = new Price();
    price.setValue("2.3");
    price.setCurrency("USD");
    installment.setAmount(price);
    product.setInstallment(installment);

    ProductWrapper wrapper = ProductWrapper.from(product);

    Assert.assertEquals((long) wrapper.installment.months, 3L);
    Assert.assertEquals(wrapper.installment.amount.currency, "USD");
    Assert.assertEquals(wrapper.installment.amount.value, "2.3");
    Assert.assertEquals(wrapper.loyaltyPoints.name, "name");
    Assert.assertEquals((double) wrapper.loyaltyPoints.ratio, (double) 2.1, 0);
    Assert.assertEquals((long) wrapper.loyaltyPoints.pointsValue, 6L);
  }

}
