package io.cdap.plugin.google.shopping;

import com.google.api.services.content.model.Error;
import com.google.api.services.content.model.Installment;
import com.google.api.services.content.model.LoyaltyPoints;
import com.google.api.services.content.model.Price;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductAspect;
import com.google.api.services.content.model.ProductCustomAttribute;
import com.google.api.services.content.model.ProductCustomGroup;
import com.google.api.services.content.model.ProductDestination;
import com.google.api.services.content.model.ProductShipping;
import com.google.api.services.content.model.ProductShippingDimension;
import com.google.api.services.content.model.ProductShippingWeight;
import com.google.api.services.content.model.ProductTax;
import com.google.api.services.content.model.ProductUnitPricingBaseMeasure;
import com.google.api.services.content.model.ProductUnitPricingMeasure;

import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.plugin.google.shopping.content.source.ProductToStructureRecordTransform;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductToStructureRecordTransformTest {

  @Test
  public void productConversion() {
    Product product = new Product();

    ProductAspect aspect = new ProductAspect();
    aspect.setAspectName("aspect");
    aspect.setDestinationName("dest");
    aspect.setIntention("intention");
    List<ProductAspect> aspects = new ArrayList<>();
    aspects.add(aspect);
    product.setAspects(aspects);

    ProductCustomAttribute productCustomAttribute = new ProductCustomAttribute();
    productCustomAttribute.setValue("vv");
    productCustomAttribute.setName("name");
    productCustomAttribute.setUnit("kg");
    productCustomAttribute.setType("type");
    List<ProductCustomAttribute> attributes = new ArrayList<>();
    attributes.add(productCustomAttribute);
    product.setCustomAttributes(attributes);

    ProductCustomGroup productCustomGroup = new ProductCustomGroup();
    productCustomGroup.setName("ccc");
    productCustomGroup.setAttributes(attributes);
    List<ProductCustomGroup> groups = new ArrayList<>();
    groups.add(productCustomGroup);
    product.setCustomGroups(groups);

    ProductDestination destination = new ProductDestination();
    destination.setDestinationName("ddd");
    destination.setIntention("iii");
    List<ProductDestination> destinations = new ArrayList<>();
    destinations.add(destination);
    product.setDestinations(destinations);

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

    product.setPrice(price);
    product.setSalePrice(price);

    ProductShipping shipping = new ProductShipping();
    shipping.setPrice(price);
    shipping.setCountry("US");
    shipping.setLocationGroupName("name");
    shipping.setRegion("region");
    List<ProductShipping> shippings = new ArrayList<>();
    shippings.add(shipping);
    product.setShipping(shippings);

    ProductShippingDimension dimension = new ProductShippingDimension();
    dimension.setUnit("unit");
    dimension.setValue(12.3);
    product.setShippingHeight(dimension);
    product.setShippingLength(dimension);
    product.setShippingWidth(dimension);

    ProductShippingWeight weight = new ProductShippingWeight();
    weight.setUnit("kg");
    weight.setValue(56.1);
    product.setShippingWeight(weight);

    ProductTax tax = new ProductTax();
    tax.setCountry("US");
    List<ProductTax> taxes = new ArrayList<>();
    taxes.add(tax);
    product.setTaxes(taxes);

    ProductUnitPricingBaseMeasure measure = new ProductUnitPricingBaseMeasure();
    measure.setUnit("unit");
    measure.setValue(123L);
    product.setUnitPricingBaseMeasure(measure);

    ProductUnitPricingMeasure measure1 = new ProductUnitPricingMeasure();
    measure1.setUnit("unit");
    measure1.setValue(123.1);
    product.setUnitPricingMeasure(measure1);

    Error error = new Error();
    error.setMessage("error");
    List<Error> errors = new ArrayList<>();
    errors.add(error);
    product.setWarnings(errors);

    // Verify transformed StructureRecord.
    StructuredRecord record = ProductToStructureRecordTransform.productToStructureRecord(product);
    List<StructuredRecord> customAttributes = (List<StructuredRecord>) record.get("customAttributes");
    Assert.assertEquals(customAttributes.size(), 1);
    Assert.assertEquals(customAttributes.get(0).get("name"), "name");
    Assert.assertEquals(customAttributes.get(0).get("type"), "type");
    Assert.assertEquals(customAttributes.get(0).get("unit"), "kg");
    Assert.assertEquals(customAttributes.get(0).get("value"), "vv");

    List<StructuredRecord> customGroup = (List<StructuredRecord>) record.get("customGroups");
    Assert.assertEquals(customGroup.size(), 1);
    Assert.assertEquals(customGroup.get(0).get("name"), "ccc");
    List<StructuredRecord> attributes1 = customGroup.get(0).get("attributes");
    Assert.assertEquals(attributes1.get(0), customAttributes.get(0));

    List<StructuredRecord> destinationsRecord = (List<StructuredRecord>) record.get("destinations");
    Assert.assertEquals(destinationsRecord.size(), 1);
    Assert.assertEquals(destinationsRecord.get(0).get("destinationName"), "ddd");
    Assert.assertEquals(destinationsRecord.get(0).get("intention"), "iii");

    List<StructuredRecord> aspectsRecord = (List<StructuredRecord>) record.get("aspects");
    Assert.assertEquals(aspectsRecord.size(), 1);
    Assert.assertEquals(aspectsRecord.get(0).get("aspectName"), "aspect");
    Assert.assertEquals(aspectsRecord.get(0).get("destinationName"), "dest");
    Assert.assertEquals(aspectsRecord.get(0).get("intention"), "intention");

    StructuredRecord loyaltyPointsRecord = (StructuredRecord) record.get("loyaltyPoints");
    Assert.assertEquals(loyaltyPointsRecord.get("name"), "name");
    Assert.assertEquals((double) loyaltyPointsRecord.get("ratio"), (double) 2.1, 0);
    Assert.assertEquals((long) loyaltyPointsRecord.get("pointsValue"), 6L);

    StructuredRecord installmentRecord = (StructuredRecord) record.get("installment");
    Assert.assertEquals((long) installmentRecord.get("months"), 3L);

    StructuredRecord installmentAmountRecord = (StructuredRecord) installmentRecord.get("amount");
    Assert.assertEquals(installmentAmountRecord.get("currency"), "USD");
    Assert.assertEquals(installmentAmountRecord.get("value"), "2.3");

    StructuredRecord priceRecord = (StructuredRecord) record.get("price");
    Assert.assertEquals(priceRecord, installmentAmountRecord);

    StructuredRecord salePriceRecord = (StructuredRecord) record.get("salePrice");
    Assert.assertEquals(salePriceRecord, installmentAmountRecord);

    List<StructuredRecord> shippingRecord = (List<StructuredRecord>) record.get("shipping");
    Assert.assertEquals(shippingRecord.size(), 1);
    Assert.assertEquals(shippingRecord.get(0).get("country"), "US");
    Assert.assertEquals(shippingRecord.get(0).get("price"), priceRecord);
    Assert.assertEquals(shippingRecord.get(0).get("region"), "region");
    Assert.assertEquals(shippingRecord.get(0).get("locationGroupName"), "name");

    StructuredRecord weightRecord = record.get("shippingWeight");
    Assert.assertEquals(weightRecord.get("unit"), "kg");
    Assert.assertEquals((double) weightRecord.get("value"), 56.1, 0);

    StructuredRecord dimensionRecord = record.get("shippingLength");
    Assert.assertEquals(dimensionRecord.get("unit"), "unit");
    Assert.assertEquals((double) dimensionRecord.get("value"), 12.3, 0);

    dimensionRecord = record.get("shippingHeight");
    Assert.assertEquals(dimensionRecord.get("unit"), "unit");
    Assert.assertEquals((double) dimensionRecord.get("value"), 12.3, 0);

    dimensionRecord = record.get("shippingHeight");
    Assert.assertEquals(dimensionRecord.get("unit"), "unit");
    Assert.assertEquals((double) dimensionRecord.get("value"), 12.3, 0);

    List<StructuredRecord> taxRecord = record.get("taxes");
    Assert.assertEquals(taxRecord.size(), 1);
    Assert.assertEquals(taxRecord.get(0).get("country"), "US");

    StructuredRecord unitPricingBaseMeasure = record.get("unitPricingBaseMeasure");
    Assert.assertEquals(unitPricingBaseMeasure.get("unit"), "unit");
    Assert.assertEquals((long) unitPricingBaseMeasure.get("value"), 123L);

    StructuredRecord unitPricingMeasure = record.get("unitPricingMeasure");
    Assert.assertEquals(unitPricingMeasure.get("unit"), "unit");
    Assert.assertEquals((double) unitPricingMeasure.get("value"), 123.1, 0);

    List<StructuredRecord> warningRecord = record.get("warnings");
    Assert.assertEquals(warningRecord.size(), 1);
    Assert.assertEquals(warningRecord.get(0).get("message"), "error");
  }
}
