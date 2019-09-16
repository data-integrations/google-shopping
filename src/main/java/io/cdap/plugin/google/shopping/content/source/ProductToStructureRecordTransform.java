/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.google.shopping.content.source;

import com.google.api.services.content.model.Error;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductAspect;
import com.google.api.services.content.model.ProductCustomAttribute;
import com.google.api.services.content.model.ProductCustomGroup;
import com.google.api.services.content.model.ProductDestination;
import com.google.api.services.content.model.ProductShipping;
import com.google.api.services.content.model.ProductTax;
import io.cdap.cdap.api.data.format.StructuredRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * Transform a {@link com.google.api.services.content.model.Product} into {@link io.cdap.cdap.api.data.format.StructuredRecord}.
 */
public class ProductToStructureRecordTransform {

  public static StructuredRecord productToStructureRecord(Product product) {
    StructuredRecord.Builder builder = StructuredRecord
        .builder(ShoppingContentConstants.PRODUCT_SCHEMA);

    builder.set("additionalImageLinks",
                product.getAdditionalImageLinks() == null ? new ArrayList<StructuredRecord>() :
                    product.getAdditionalImageLinks());
    builder.set("additionalProductTypes", product.getAdditionalProductTypes() == null ?
        new ArrayList<StructuredRecord>() : product.getAdditionalProductTypes());
    builder.set("adult", product.getAdult());
    builder.set("adwordsGrouping", product.getAdwordsGrouping());
    builder.set("adwordsLabels", product.getAdwordsLabels() == null ?
        new ArrayList<StructuredRecord>() : product.getAdwordsLabels());
    builder.set("adwordsRedirect", product.getAdwordsRedirect());
    builder.set("ageGroup", product.getAgeGroup());

    List<StructuredRecord> aspects = new ArrayList<>();
    if (product.getAspects() != null) {

      for (ProductAspect aspect : product.getAspects()) {
        StructuredRecord.Builder builder1 = StructuredRecord.builder(
            ShoppingContentConstants.PRODUCT_SCHEMA.getField("aspects").getSchema()
                                                   .getComponentSchema());
        builder1.set("aspectName", aspect.getAspectName());
        builder1.set("destinationName", aspect.getDestinationName());
        builder1.set("intention", aspect.getIntention());
        aspects.add(builder1.build());
      }
    }

    builder.set("aspects", aspects);

    builder.set("availability", product.getAvailability());
    builder.set("availabilityDate", product.getAvailabilityDate());
    builder.set("brand", product.getBrand());
    builder.set("channel", product.getChannel());
    builder.set("color", product.getColor());
    builder.set("condition", product.getCondition());
    builder.set("contentLanguage", product.getContentLanguage());

    List<StructuredRecord> customAttributes = new ArrayList<>();

    if (product.getCustomAttributes() != null) {
      for (ProductCustomAttribute productCustomAttribute : product.getCustomAttributes()) {
        StructuredRecord.Builder builder1 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("customAttributes").getSchema().getComponentSchema());
        builder1.set("name", productCustomAttribute.getName());
        builder1.set("type", productCustomAttribute.getType());
        builder1.set("unit", productCustomAttribute.getUnit());
        builder1.set("value", productCustomAttribute.getValue());
        customAttributes.add(builder1.build());
      }
    }
    builder.set("customAttributes", customAttributes);

    List<StructuredRecord> customGroups = new ArrayList<>();

    if (product.getCustomGroups() != null) {
      for (ProductCustomGroup productCustomGroup : product.getCustomGroups()) {
        StructuredRecord.Builder builder1 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("customGroups").getSchema().getComponentSchema());

        List<StructuredRecord> attributes = new ArrayList<>();

        for (ProductCustomAttribute productCustomAttribute : productCustomGroup.getAttributes()) {
          StructuredRecord.Builder builder2 = StructuredRecord
              .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                           .getField("customAttributes").getSchema().getComponentSchema());
          builder2.set("name", productCustomAttribute.getName());
          builder2.set("type", productCustomAttribute.getType());
          builder2.set("unit", productCustomAttribute.getUnit());
          builder2.set("value", productCustomAttribute.getValue());
          attributes.add(builder2.build());
        }
        builder1.set("attributes", attributes);
        builder1.set("name", productCustomGroup.getName());
        customGroups.add(builder1.build());
      }
    }

    builder.set("customGroups", customGroups);

    builder.set("customLabel0", product.getCustomLabel0());
    builder.set("customLabel1", product.getCustomLabel1());
    builder.set("customLabel2", product.getCustomLabel2());
    builder.set("customLabel3", product.getCustomLabel3());
    builder.set("customLabel4", product.getCustomLabel4());

    builder.set("description", product.getDescription());

    // destinations
    List<StructuredRecord> destinations = new ArrayList<>();

    if (product.getDestinations() != null) {
      for (ProductDestination destination : product.getDestinations()) {
        StructuredRecord.Builder builder1 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("destinations").getSchema().getComponentSchema());
        builder1.set("destinationName", destination.getDestinationName());
        builder1.set("intention", destination.getIntention());
        destinations.add(builder1.build());
      }
    }
    builder.set("destinations", destinations);

    builder.set("displayAdsId", product.getDisplayAdsId());
    builder.set("displayAdsLink", product.getDisplayAdsLink());
    builder.set("displayAdsSimilarIds", product.getDisplayAdsSimilarIds() == null ?
        new ArrayList<StructuredRecord>() : product.getDisplayAdsSimilarIds());
    builder.set("displayAdsTitle", product.getDisplayAdsTitle());
    builder.set("displayAdsValue", product.getDisplayAdsValue());

    builder.set("energyEfficiencyClass", product.getEnergyEfficiencyClass());
    builder.set("expirationDate", product.getExpirationDate());
    builder.set("gender", product.getGender());
    builder.set("googleProductCategory", product.getGoogleProductCategory());
    builder.set("gtin", product.getGtin());
    builder.set("id", product.getId());
    builder.set("identifierExists", product.getIdentifierExists());
    builder.set("imageLink", product.getImageLink());

    if (product.getInstallment() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("installment").getSchema().getNonNullable());

      if (product.getInstallment().getAmount() != null) {
        StructuredRecord.Builder builder2 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("installment").getSchema().getNonNullable().getField("amount").getSchema()
                         .getNonNullable());
        builder2.set("currency", product.getInstallment().getAmount().getCurrency());
        builder2.set("value", product.getInstallment().getAmount().getValue());
        builder1.set("amount", builder2.build());
      }
      builder1.set("months", product.getInstallment().getMonths());
      builder.set("installment", builder1.build());
    }

    builder.set("isBundle", product.getIsBundle());
    builder.set("itemGroupId", product.getItemGroupId());
    builder.set("kind", product.getKind());
    builder.set("link", product.getLink());

    // loyaltyPoints
    if (product.getLoyaltyPoints() != null) {

      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("loyaltyPoints").getSchema().getNonNullable());
      builder1.set("name", product.getLoyaltyPoints().getName());
      builder1.set("pointsValue", product.getLoyaltyPoints().getPointsValue());
      builder1.set("ratio", product.getLoyaltyPoints().getRatio());
      builder.set("loyaltyPoints", builder1.build());
    }

    builder.set("material", product.getMaterial());
    builder.set("maxHandlingTime", product.getMaxHandlingTime());
    builder.set("minHandlingTime", product.getMinHandlingTime());
    builder.set("mobileLink", product.getMobileLink());
    builder.set("mpn", product.getMpn());
    builder.set("multipack", product.getMultipack());
    builder.set("offerId", product.getOfferId());
    builder.set("onlineOnly", product.getOnlineOnly());
    builder.set("pattern", product.getPattern());

    if (product.getPrice() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("installment").getSchema().getNonNullable().getField("amount").getSchema()
                       .getNonNullable());
      builder1.set("currency", product.getPrice().getCurrency());
      builder1.set("value", product.getPrice().getValue());
      builder.set("price", builder1.build());
    }

    builder.set("productType", product.getProductType());
    builder.set("promotionIds", product.getPromotionIds() == null ?
        new ArrayList<StructuredRecord>() : product.getPromotionIds());

    if (product.getSalePrice() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("installment").getSchema().getNonNullable().getField("amount").getSchema()
                       .getNonNullable());
      builder1.set("currency", product.getSalePrice().getCurrency());
      builder1.set("value", product.getSalePrice().getValue());
      builder.set("salePrice", builder1.build());
    }

    builder.set("salePriceEffectiveDate", product.getSalePriceEffectiveDate());
    builder.set("sellOnGoogleQuantity", product.getSellOnGoogleQuantity());

    // shipping
    List<StructuredRecord> shippings = new ArrayList<>();

    if (product.getShipping() != null) {
      for (ProductShipping shipping : product.getShipping()) {
        StructuredRecord.Builder builder1 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("shipping").getSchema().getComponentSchema());
        builder1.set("country", shipping.getCountry());
        builder1.set("locationGroupName", shipping.getLocationGroupName());
        builder1.set("locationId", shipping.getLocationId());
        builder1.set("postalCode", shipping.getPostalCode());

        if (shipping.getPrice() != null) {
          StructuredRecord.Builder builder2 = StructuredRecord
              .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                           .getField("installment").getSchema().getNonNullable().getField("amount")
                           .getSchema()
                           .getNonNullable());
          builder2.set("currency", shipping.getPrice().getCurrency());
          builder2.set("value", shipping.getPrice().getValue());
          builder1.set("price", builder2.build());
        }
        builder1.set("region", shipping.getRegion());
        builder1.set("service", shipping.getService());
        shippings.add(builder1.build());
      }
    }

    builder.set("shipping", shippings);

    // shippingHeight
    if (product.getShippingHeight() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("shippingHeight").getSchema().getNonNullable());
      builder1.set("unit", product.getShippingHeight().getUnit());
      builder1.set("value", product.getShippingHeight().getValue());
      builder.set("shippingHeight", builder1.build());
    }

    // shippingLabel
    builder.set("shippingLabel", product.getShippingLabel());

    // shippingLength
    if (product.getShippingLength() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("shippingHeight").getSchema().getNonNullable());
      builder1.set("unit", product.getShippingLength().getUnit());
      builder1.set("value", product.getShippingLength().getValue());
      builder.set("shippingLength", builder1.build());
    }

    // shippingWeight
    if (product.getShippingWeight() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("shippingWeight").getSchema().getNonNullable());
      builder1.set("unit", product.getShippingWeight().getUnit());
      builder1.set("value", product.getShippingWeight().getValue());
      builder.set("shippingWeight", builder1.build());
    }

    // shippingWidth
    if (product.getShippingWidth() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("shippingHeight").getSchema().getNonNullable());
      builder1.set("unit", product.getShippingWidth().getUnit());
      builder1.set("value", product.getShippingWidth().getValue());
      builder.set("shippingWidth", builder1.build());
    }

    builder.set("sizeSystem", product.getSizeSystem());
    builder.set("sizeType", product.getSizeType());
    builder.set("sizes",
                product.getSizes() == null ? new ArrayList<StructuredRecord>() : product.getSizes());
    builder.set("targetCountry", product.getTargetCountry());

    // taxes
    List<StructuredRecord> taxes = new ArrayList<>();

    if (product.getTaxes() != null) {
      for (ProductTax tax : product.getTaxes()) {
        StructuredRecord.Builder builder2 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("taxes").getSchema().getComponentSchema());
        builder2.set("country", tax.getCountry());
        builder2.set("locationId", tax.getLocationId());
        builder2.set("postalCode", tax.getPostalCode());
        builder2.set("rate", tax.getRate());
        builder2.set("region", tax.getRegion());
        builder2.set("taxShip", tax.getTaxShip());
        taxes.add(builder2.build());
      }
    }

    builder.set("taxes", taxes);

    builder.set("title", product.getTitle());

    // unitPricingBaseMeasure
    if (product.getUnitPricingBaseMeasure() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("unitPricingBaseMeasure").getSchema().getNonNullable());
      builder1.set("unit", product.getUnitPricingBaseMeasure().getUnit());
      builder1.set("value", product.getUnitPricingBaseMeasure().getValue());
      builder.set("unitPricingBaseMeasure", builder1.build());
    }

    // unitPricingMeasure
    if (product.getUnitPricingMeasure() != null) {
      StructuredRecord.Builder builder1 = StructuredRecord
          .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                       .getField("unitPricingMeasure").getSchema().getNonNullable());
      builder1.set("unit", product.getUnitPricingMeasure().getUnit());
      builder1.set("value", product.getUnitPricingMeasure().getValue());
      builder.set("unitPricingMeasure", builder1.build());
    }

    builder.set("validatedDestinations", product.getValidatedDestinations() == null ?
        new ArrayList<StructuredRecord>() : product.getValidatedDestinations());

    // warnings
    List<StructuredRecord> warnings = new ArrayList<>();

    if (product.getWarnings() != null) {
      for (Error error : product.getWarnings()) {
        StructuredRecord.Builder builder1 = StructuredRecord
            .builder(ShoppingContentConstants.PRODUCT_SCHEMA
                         .getField("warnings").getSchema().getComponentSchema());
        builder1.set("domain", error.getDomain());
        builder1.set("message", error.getMessage());
        builder1.set("reason", error.getReason());
        warnings.add(builder1.build());
      }
    }

    builder.set("warnings", warnings);
    return builder.build();
  }
}
