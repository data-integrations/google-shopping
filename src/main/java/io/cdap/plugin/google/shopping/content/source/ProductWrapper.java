package io.cdap.plugin.google.shopping.content.source;

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
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class to represent {@link Product}. This class contains method to transform from and to
 * {@link Product}.
 */
public class ProductWrapper {

  public List<String> additionalImageLinks;
  public List<String> additionalProductTypes;
  public Boolean adult;
  public String adwordsGrouping;
  public List<String> adwordsLabels;
  public String adwordsRedirect;
  public String ageGroup;
  public List<ProductAspectWrapper> aspects;
  public String availability;
  public String availabilityDate;
  public String brand;
  public String channel;
  public String color;
  public String condition;
  public String contentLanguage;
  public List<ProductCustomAttributeWrapper> customAttributes;
  public List<ProductCustomGroupWrapper> customGroups;
  public String customLabel0;
  public String customLabel1;
  public String customLabel2;
  public String customLabel3;
  public String customLabel4;
  public String description;
  public List<ProductDestinationWrapper> destinations;
  public String displayAdsId;
  public String displayAdsLink;
  public List<String> displayAdsSimilarIds;
  public String displayAdsTitle;
  public Double displayAdsValue;
  public String energyEfficiencyClass;
  public String expirationDate;
  public String gender;
  public String googleProductCategory;
  public String gtin;
  public String id;
  public Boolean identifierExists;
  public String imageLink;
  public InstallmentWrapper installment;
  public Boolean isBundle;
  public String itemGroupId;
  public String kind;
  public String link;
  public LoyaltyPointsWrapper loyaltyPoints;
  public String material;
  public Long maxHandlingTime;
  public Long minHandlingTime;
  public String mobileLink;
  public String mpn;
  public Long multipack;
  public String offerId;
  public Boolean onlineOnly;
  public String pattern;
  public PriceWrapper price;
  public String productType;
  public List<String> promotionIds;
  public PriceWrapper salePrice;
  public String salePriceEffectiveDate;
  public Long sellOnGoogleQuantity;
  public List<ProductShippingWrapper> shipping;
  public ProductShippingDimensionWrapper shippingHeight;
  public String shippingLabel;
  public ProductShippingDimensionWrapper shippingLength;
  public ProductShippingWeightWrapper shippingWeight;
  public ProductShippingDimensionWrapper shippingWidth;
  public String sizeSystem;
  public String sizeType;
  public List<String> sizes;
  public String targetCountry;
  public List<ProductTaxWrapper> taxes;
  public String title;
  public ProductUnitPricingBaseMeasureWrapper unitPricingBaseMeasure;
  public ProductUnitPricingMeasureWrapper unitPricingMeasure;
  public List<String> validatedDestinations;
  public List<ErrorWrapper> warnings;

  public static Product to(ProductWrapper wrapper) {
    Product product = new Product();
    product.setAdditionalImageLinks(wrapper.additionalImageLinks);
    product.setAdditionalProductTypes(wrapper.additionalProductTypes);
    product.setAdult(wrapper.adult);
    product.setAdwordsGrouping(wrapper.adwordsGrouping);
    product.setAdwordsLabels(wrapper.adwordsLabels);
    product.setAdwordsRedirect(wrapper.adwordsRedirect);
    product.setAgeGroup(wrapper.ageGroup);

    if (wrapper.aspects != null) {
      List<ProductAspect> aspects = new ArrayList<>();
      for (ProductAspectWrapper aspect : wrapper.aspects) {
        aspects.add(ProductAspectWrapper.to(aspect));
      }
      product.setAspects(aspects);
    }

    product.setAvailability(wrapper.availability);
    product.setAvailabilityDate(wrapper.availabilityDate);
    product.setBrand(wrapper.brand);
    product.setChannel(wrapper.channel);
    product.setColor(wrapper.color);
    product.setCondition(wrapper.condition);
    product.setContentLanguage(wrapper.contentLanguage);

    if (wrapper.customAttributes != null) {
      List<ProductCustomAttribute> attributes = new ArrayList<>();
      for (ProductCustomAttributeWrapper wrapper1 : wrapper.customAttributes) {
        attributes.add(ProductCustomAttributeWrapper.to(wrapper1));
      }
      product.setCustomAttributes(attributes);
    }

    if (wrapper.customGroups != null) {
      List<ProductCustomGroup> groups = new ArrayList<>();
      for (ProductCustomGroupWrapper groupWrapper : wrapper.customGroups) {
        groups.add(ProductCustomGroupWrapper.to(groupWrapper));
      }
      product.setCustomGroups(groups);
    }

    product.setCustomLabel0(wrapper.customLabel0);
    product.setCustomLabel1(wrapper.customLabel1);
    product.setCustomLabel2(wrapper.customLabel2);
    product.setCustomLabel3(wrapper.customLabel3);
    product.setCustomLabel4(wrapper.customLabel4);
    product.setDescription(wrapper.description);

    if (wrapper.destinations != null) {
      List<ProductDestination> destinations = new ArrayList<>();
      for (ProductDestinationWrapper destinationWrapper : wrapper.destinations) {
        destinations.add(ProductDestinationWrapper.to(destinationWrapper));
      }
      product.setDestinations(destinations);
    }

    product.setDisplayAdsId(wrapper.displayAdsId);
    product.setDisplayAdsLink(wrapper.displayAdsLink);
    product.setDisplayAdsSimilarIds(wrapper.displayAdsSimilarIds);
    product.setDisplayAdsTitle(wrapper.displayAdsTitle);
    product.setDisplayAdsValue(wrapper.displayAdsValue);
    product.setEnergyEfficiencyClass(wrapper.energyEfficiencyClass);
    product.setExpirationDate(wrapper.expirationDate);
    product.setGender(wrapper.gender);
    product.setGoogleProductCategory(wrapper.googleProductCategory);
    product.setGtin(wrapper.gtin);
    product.setId(wrapper.id);
    product.setIdentifierExists(wrapper.identifierExists);
    product.setImageLink(wrapper.imageLink);

    if (wrapper.installment != null) {
      product.setInstallment(InstallmentWrapper.to(wrapper.installment));
    }
    product.setIsBundle(wrapper.isBundle);
    product.setItemGroupId(wrapper.itemGroupId);
    product.setKind(wrapper.kind);
    product.setLink(wrapper.link);

    if (wrapper.loyaltyPoints != null) {
      product.setLoyaltyPoints(LoyaltyPointsWrapper.to(wrapper.loyaltyPoints));
    }
    product.setMaterial(wrapper.material);
    product.setMaxHandlingTime(wrapper.maxHandlingTime);
    product.setMinHandlingTime(wrapper.minHandlingTime);
    product.setMobileLink(wrapper.mobileLink);
    product.setMpn(wrapper.mpn);
    product.setMultipack(wrapper.multipack);
    product.setOfferId(wrapper.offerId);
    product.setPattern(wrapper.pattern);

    if (wrapper.price != null) {
      product.setPrice(PriceWrapper.to(wrapper.price));
    }
    product.setProductType(wrapper.productType);
    product.setPromotionIds(wrapper.promotionIds);

    if (wrapper.price != null) {
      product.setSalePrice(PriceWrapper.to(wrapper.price));
    }
    product.setSalePriceEffectiveDate(wrapper.salePriceEffectiveDate);
    product.setSellOnGoogleQuantity(wrapper.sellOnGoogleQuantity);

    if (wrapper.shippingHeight != null) {
      product.setShippingHeight(ProductShippingDimensionWrapper.to(wrapper.shippingHeight));
    }
    product.setShippingLabel(wrapper.shippingLabel);

    if (wrapper.shippingLength != null) {
      product.setShippingLength(ProductShippingDimensionWrapper.to(wrapper.shippingLength));
    }
    if (wrapper.shippingWeight != null) {
      product.setShippingWeight(ProductShippingWeightWrapper.to(wrapper.shippingWeight));
    }
    if (wrapper.shippingWidth != null) {
      product.setShippingWidth(ProductShippingDimensionWrapper.to(wrapper.shippingWidth));
    }

    if (wrapper.shipping != null) {
      List<ProductShipping> shippings = new ArrayList<>();
      for (ProductShippingWrapper shippingWrapper : wrapper.shipping) {
        shippings.add(ProductShippingWrapper.to(shippingWrapper));
      }
      product.setShipping(shippings);
    }

    product.setSizeSystem(wrapper.sizeSystem);
    product.setSizeType(wrapper.sizeType);
    product.setSizes(wrapper.sizes);
    product.setTargetCountry(wrapper.targetCountry);

    if (wrapper.taxes != null) {
      List<ProductTax> taxes = new ArrayList<>();
      for (ProductTaxWrapper taxWrapper : wrapper.taxes) {
        taxes.add(ProductTaxWrapper.to(taxWrapper));
      }
      product.setTaxes(taxes);
    }

    product.setTitle(wrapper.title);
    if (wrapper.unitPricingBaseMeasure != null) {
      product.setUnitPricingBaseMeasure(
          ProductUnitPricingBaseMeasureWrapper.to(wrapper.unitPricingBaseMeasure));
    }
    if (wrapper.unitPricingMeasure != null) {
      product
          .setUnitPricingMeasure(ProductUnitPricingMeasureWrapper.to(wrapper.unitPricingMeasure));
    }
    product.setValidatedDestinations(wrapper.validatedDestinations);

    if (wrapper.warnings != null) {
      List<Error> errors = new ArrayList<>();
      for (ErrorWrapper error : wrapper.warnings) {
        errors.add(ErrorWrapper.to(error));
      }
      product.setWarnings(errors);
    }

    return product;

  }

  public static ProductWrapper from(Product product) {
    ProductWrapper wrapper = new ProductWrapper();
    wrapper.additionalImageLinks = product.getAdditionalImageLinks() == null ? new ArrayList<>()
        : product.getAdditionalImageLinks();
    wrapper.additionalProductTypes = product.getAdditionalProductTypes() == null ? new ArrayList<>()
        : product.getAdditionalProductTypes();
    wrapper.adult = product.getAdult();
    wrapper.adwordsGrouping = product.getAdwordsGrouping();
    wrapper.adwordsLabels =
        product.getAdwordsLabels() == null ? new ArrayList<>() : product.getAdwordsLabels();
    wrapper.adwordsRedirect = product.getAdwordsRedirect();
    wrapper.ageGroup = product.getAgeGroup();

    List<ProductAspectWrapper> aspects = new ArrayList<>();
    if (product.getAspects() != null) {

      for (ProductAspect aspect : product.getAspects()) {
        aspects.add(ProductAspectWrapper.from(aspect));
      }
    }
    wrapper.aspects = aspects;

    wrapper.availability = product.getAvailability();
    wrapper.availabilityDate = product.getAvailabilityDate();
    wrapper.brand = product.getBrand();
    wrapper.channel = product.getChannel();
    wrapper.color = product.getColor();
    wrapper.condition = product.getCondition();
    wrapper.contentLanguage = product.getContentLanguage();

    List<ProductCustomAttributeWrapper> attributes = new ArrayList<>();
    if (product.getCustomAttributes() != null) {
      for (ProductCustomAttribute attribute : product.getCustomAttributes()) {
        attributes.add(ProductCustomAttributeWrapper.from(attribute));
      }
    }
    wrapper.customAttributes = attributes;

    List<ProductCustomGroupWrapper> groups = new ArrayList<>();
    if (product.getCustomGroups() != null) {

      for (ProductCustomGroup group : product.getCustomGroups()) {
        groups.add(ProductCustomGroupWrapper.from(group));
      }
    }
    wrapper.customGroups = groups;

    wrapper.customLabel0 = product.getCustomLabel0();
    wrapper.customLabel1 = product.getCustomLabel1();
    wrapper.customLabel2 = product.getCustomLabel2();
    wrapper.customLabel3 = product.getCustomLabel3();
    wrapper.customLabel4 = product.getCustomLabel4();
    wrapper.description = product.getDescription();

    List<ProductDestinationWrapper> destinations = new ArrayList<>();
    if (product.getDestinations() != null) {

      for (ProductDestination destination : product.getDestinations()) {
        destinations.add(ProductDestinationWrapper.from(destination));
      }
    }
    wrapper.destinations = destinations;

    wrapper.displayAdsId = product.getDisplayAdsId();
    wrapper.displayAdsLink = product.getDisplayAdsLink();
    wrapper.displayAdsSimilarIds = product.getDisplayAdsSimilarIds() == null ? new ArrayList<>()
        : product.getDisplayAdsSimilarIds();
    wrapper.displayAdsTitle = product.getDisplayAdsTitle();
    wrapper.displayAdsValue = product.getDisplayAdsValue();
    wrapper.energyEfficiencyClass = product.getEnergyEfficiencyClass();
    wrapper.expirationDate = product.getExpirationDate();
    wrapper.gender = product.getGender();
    wrapper.googleProductCategory = product.getGoogleProductCategory();
    wrapper.gtin = product.getGtin();
    wrapper.id = product.getId();
    wrapper.identifierExists = product.getIdentifierExists();
    wrapper.imageLink = product.getImageLink();

    if (product.getInstallment() != null) {
      wrapper.installment = InstallmentWrapper.from(product.getInstallment());
    }
    wrapper.isBundle = product.getIsBundle();
    wrapper.itemGroupId = product.getItemGroupId();
    wrapper.kind = product.getKind();
    wrapper.link = product.getLink();
    if (product.getLoyaltyPoints() != null) {
      wrapper.loyaltyPoints = LoyaltyPointsWrapper.from(product.getLoyaltyPoints());
    }
    wrapper.material = product.getMaterial();
    wrapper.maxHandlingTime = product.getMaxHandlingTime();
    wrapper.minHandlingTime = product.getMinHandlingTime();
    wrapper.mobileLink = product.getMobileLink();
    wrapper.mpn = product.getMpn();
    wrapper.multipack = product.getMultipack();
    wrapper.offerId = product.getOfferId();
    wrapper.pattern = product.getPattern();
    if (product.getPrice() != null) {

      wrapper.price = PriceWrapper.from(product.getPrice());
    }
    wrapper.productType = product.getProductType();
    wrapper.promotionIds =
        product.getPromotionIds() == null ? new ArrayList<>() : product.getPromotionIds();
    if (product.getSalePrice() != null) {

      wrapper.price = PriceWrapper.from(product.getSalePrice());
    }
    wrapper.salePriceEffectiveDate = product.getSalePriceEffectiveDate();
    wrapper.sellOnGoogleQuantity = product.getSellOnGoogleQuantity();
    if (product.getShippingHeight() != null) {
      wrapper.shippingHeight = ProductShippingDimensionWrapper.from(product.getShippingHeight());
    }
    wrapper.shippingLabel = product.getShippingLabel();
    if (product.getShippingLength() != null) {
      wrapper.shippingLength = ProductShippingDimensionWrapper.from(product.getShippingLength());
    }
    if (product.getShippingWeight() != null) {
      wrapper.shippingWeight = ProductShippingWeightWrapper.from(product.getShippingWeight());
    }
    if (product.getShippingWidth() != null) {
      wrapper.shippingWidth = ProductShippingDimensionWrapper.from(product.getShippingWidth());
    }

    List<ProductShippingWrapper> shippings = new ArrayList<>();
    if (product.getShipping() != null) {

      for (ProductShipping shipping : product.getShipping()) {
        shippings.add(ProductShippingWrapper.from(shipping));
      }
    }
    wrapper.shipping = shippings;

    wrapper.sizeSystem = product.getSizeSystem();
    wrapper.sizeType = product.getSizeType();
    wrapper.sizes = product.getSizes() == null ? new ArrayList<>() : product.getSizes();
    wrapper.targetCountry = product.getTargetCountry();

    List<ProductTaxWrapper> taxes = new ArrayList<>();
    if (product.getTaxes() != null) {
      for (ProductTax tax : product.getTaxes()) {
        taxes.add(ProductTaxWrapper.from(tax));
      }
    }
    wrapper.taxes = taxes;

    wrapper.title = product.getTitle();

    if (product.getUnitPricingBaseMeasure() != null) {
      wrapper.unitPricingBaseMeasure = ProductUnitPricingBaseMeasureWrapper
          .from(product.getUnitPricingBaseMeasure());
    }

    if (product.getUnitPricingMeasure() != null) {
      wrapper.unitPricingMeasure = ProductUnitPricingMeasureWrapper
          .from(product.getUnitPricingMeasure());
    }
    wrapper.validatedDestinations = product.getValidatedDestinations() == null ? new ArrayList<>()
        : product.getValidatedDestinations();

    List<ErrorWrapper> errors = new ArrayList<>();
    if (product.getWarnings() != null) {

      for (Error error : product.getWarnings()) {
        errors.add(ErrorWrapper.from(error));
      }
    }
    wrapper.warnings = errors;

    return wrapper;

  }

  /**
   * A wrapper class to represent {@link ProductAspect}. This class contains method to transform
   * from and to {@link ProductAspect}.
   */
  public static final class ProductAspectWrapper {

    public String aspectName;
    public String destinationName;
    public String intention;

    public static ProductAspect to(ProductAspectWrapper wrapper) {
      ProductAspect aspect = new ProductAspect();
      aspect.setAspectName(wrapper.aspectName);
      aspect.setDestinationName(wrapper.destinationName);
      aspect.setIntention(wrapper.intention);
      return aspect;
    }

    public static ProductAspectWrapper from(ProductAspect aspect) {
      ProductAspectWrapper wrapper = new ProductAspectWrapper();
      wrapper.aspectName = aspect.getAspectName();
      wrapper.destinationName = aspect.getDestinationName();
      wrapper.intention = aspect.getIntention();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductCustomAttribute}. This class contains method to
   * transform from and to {@link ProductCustomAttribute}.
   */
  public static final class ProductCustomAttributeWrapper {

    public String name;
    public String type;
    public String unit;
    public String value;

    public static ProductCustomAttribute to(ProductCustomAttributeWrapper wrapper) {
      ProductCustomAttribute attribute = new ProductCustomAttribute();
      attribute.setName(wrapper.name);
      attribute.setType(wrapper.type);
      attribute.setUnit(wrapper.unit);
      attribute.setValue(wrapper.value);
      return attribute;
    }

    public static ProductCustomAttributeWrapper from(ProductCustomAttribute attribute) {
      ProductCustomAttributeWrapper wrapper = new ProductCustomAttributeWrapper();
      wrapper.name = attribute.getName();
      wrapper.type = attribute.getType();
      wrapper.unit = attribute.getUnit();
      wrapper.value = attribute.getValue();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductCustomGroup}. This class contains method to
   * transform from and to {@link ProductCustomGroup}.
   */
  public static final class ProductCustomGroupWrapper {

    public List<ProductCustomAttributeWrapper> attributes;
    public String name;

    public static ProductCustomGroup to(ProductCustomGroupWrapper wrapper) {
      ProductCustomGroup group = new ProductCustomGroup();
      List<ProductCustomAttribute> attributes = new ArrayList<>();
      for (ProductCustomAttributeWrapper attribute : wrapper.attributes) {
        attributes.add(ProductCustomAttributeWrapper.to(attribute));
      }
      group.setAttributes(attributes);
      group.setName(wrapper.name);
      return group;
    }

    public static ProductCustomGroupWrapper from(ProductCustomGroup group) {
      ProductCustomGroupWrapper wrapper = new ProductCustomGroupWrapper();
      List<ProductCustomAttributeWrapper> attributes = new ArrayList<>();
      for (ProductCustomAttribute attribute : group.getAttributes()) {
        attributes.add(ProductCustomAttributeWrapper.from(attribute));
      }
      wrapper.attributes = attributes;
      wrapper.name = group.getName();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductDestination}. This class contains method to
   * transform from and to {@link ProductDestination}.
   */
  public static final class ProductDestinationWrapper {

    public String destinationName;
    public String intention;

    public static ProductDestination to(ProductDestinationWrapper wrapper) {
      ProductDestination destination = new ProductDestination();
      destination.setDestinationName(wrapper.destinationName);
      destination.setIntention(wrapper.intention);
      return destination;
    }

    public static ProductDestinationWrapper from(ProductDestination destination) {
      ProductDestinationWrapper wrapper = new ProductDestinationWrapper();
      wrapper.destinationName = destination.getDestinationName();
      wrapper.intention = destination.getIntention();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link Price}. This class contains method to transform from and to
   * {@link Price}.
   */
  public static final class PriceWrapper {

    public String currency;
    public String value;

    public static Price to(PriceWrapper wrapper) {
      Price price = new Price();
      price.setCurrency(wrapper.currency);
      price.setValue(wrapper.value);
      return price;
    }

    public static PriceWrapper from(Price price) {
      PriceWrapper wrapper = new PriceWrapper();
      wrapper.currency = price.getCurrency();
      wrapper.value = price.getValue();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link Installment}. This class contains method to transform from
   * and to {@link Installment}.
   */
  public static final class InstallmentWrapper {

    public PriceWrapper amount;
    public Long months;

    public static Installment to(InstallmentWrapper wrapper) {
      Installment installment = new Installment();
      installment.setAmount(PriceWrapper.to(wrapper.amount));
      installment.setMonths(wrapper.months);
      return installment;
    }

    public static InstallmentWrapper from(Installment installment) {
      InstallmentWrapper wrapper = new InstallmentWrapper();
      wrapper.amount = PriceWrapper.from(installment.getAmount());
      wrapper.months = installment.getMonths();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link LoyaltyPoints}. This class contains method to transform
   * from and to {@link LoyaltyPoints}.
   */
  public static final class LoyaltyPointsWrapper {

    public String name;
    public Long pointsValue;
    public Double ratio;

    public static LoyaltyPoints to(LoyaltyPointsWrapper wrapper) {
      LoyaltyPoints points = new LoyaltyPoints();
      points.setName(wrapper.name);
      points.setPointsValue(wrapper.pointsValue);
      points.setRatio(wrapper.ratio);
      return points;
    }

    public static LoyaltyPointsWrapper from(LoyaltyPoints points) {
      LoyaltyPointsWrapper wrapper = new LoyaltyPointsWrapper();
      wrapper.name = points.getName();
      wrapper.pointsValue = points.getPointsValue();
      wrapper.ratio = points.getRatio();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductShipping}. This class contains method to transform
   * from and to {@link ProductShipping}.
   */
  public static final class ProductShippingWrapper {

    public String country;
    public String locationGroupName;
    public Long locationId;
    public String postalCode;
    public PriceWrapper price;
    public String region;
    public String service;

    public static ProductShipping to(ProductShippingWrapper wrapper) {
      ProductShipping shipping = new ProductShipping();
      shipping.setCountry(wrapper.country);
      shipping.setLocationGroupName(wrapper.locationGroupName);
      shipping.setLocationId(wrapper.locationId);
      shipping.setPostalCode(wrapper.postalCode);
      shipping.setPrice(PriceWrapper.to(wrapper.price));
      shipping.setRegion(wrapper.region);
      shipping.setService(wrapper.service);
      return shipping;
    }

    public static ProductShippingWrapper from(ProductShipping shipping) {
      ProductShippingWrapper wrapper = new ProductShippingWrapper();
      wrapper.country = shipping.getCountry();
      wrapper.locationGroupName = shipping.getLocationGroupName();
      wrapper.locationId = shipping.getLocationId();
      wrapper.postalCode = shipping.getPostalCode();
      wrapper.price = PriceWrapper.from(shipping.getPrice());
      wrapper.region = shipping.getRegion();
      wrapper.service = shipping.getService();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductShippingDimension}. This class contains method to
   * transform from and to {@link ProductShippingDimension}.
   */
  public static final class ProductShippingDimensionWrapper {

    public String unit;
    public Double value;

    public static ProductShippingDimension to(ProductShippingDimensionWrapper wrapper) {
      ProductShippingDimension dimension = new ProductShippingDimension();
      dimension.setUnit(wrapper.unit);
      dimension.setValue(wrapper.value);
      return dimension;
    }

    public static ProductShippingDimensionWrapper from(ProductShippingDimension dimension) {
      ProductShippingDimensionWrapper wrapper = new ProductShippingDimensionWrapper();
      wrapper.unit = dimension.getUnit();
      wrapper.value = dimension.getValue();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductTax}. This class contains method to transform from
   * and to {@link ProductTax}.
   */
  public static final class ProductTaxWrapper {

    public String country;
    public Long locationId;
    public String postalCode;
    public Double rate;
    public String region;
    public Boolean taxShip;

    public static ProductTax to(ProductTaxWrapper wrapper) {
      ProductTax tax = new ProductTax();
      tax.setCountry(wrapper.country);
      tax.setLocationId(wrapper.locationId);
      tax.setPostalCode(wrapper.postalCode);
      tax.setRate(wrapper.rate);
      tax.setRegion(wrapper.region);
      tax.setTaxShip(wrapper.taxShip);
      return tax;
    }

    public static ProductTaxWrapper from(ProductTax tax) {
      ProductTaxWrapper wrapper = new ProductTaxWrapper();
      wrapper.country = tax.getCountry();
      wrapper.locationId = tax.getLocationId();
      wrapper.postalCode = tax.getPostalCode();
      wrapper.rate = tax.getRate();
      wrapper.region = tax.getRegion();
      wrapper.taxShip = tax.getTaxShip();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductUnitPricingBaseMeasure}. This class contains method
   * to transform from and to {@link ProductUnitPricingBaseMeasure}.
   */
  public static final class ProductUnitPricingBaseMeasureWrapper {

    public String unit;
    public Long value;

    public static ProductUnitPricingBaseMeasure to(ProductUnitPricingBaseMeasureWrapper wrapper) {
      ProductUnitPricingBaseMeasure measure = new ProductUnitPricingBaseMeasure();
      measure.setUnit(wrapper.unit);
      measure.setValue(wrapper.value);
      return measure;
    }

    public static ProductUnitPricingBaseMeasureWrapper from(ProductUnitPricingBaseMeasure measure) {
      ProductUnitPricingBaseMeasureWrapper wrapper = new ProductUnitPricingBaseMeasureWrapper();
      wrapper.unit = measure.getUnit();
      wrapper.value = measure.getValue();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductUnitPricingMeasure}. This class contains method to
   * transform from and to {@link ProductUnitPricingMeasure}.
   */
  public static final class ProductUnitPricingMeasureWrapper {

    public String unit;
    public Double value;

    public static ProductUnitPricingMeasure to(ProductUnitPricingMeasureWrapper wrapper) {
      ProductUnitPricingMeasure measure = new ProductUnitPricingMeasure();
      measure.setUnit(wrapper.unit);
      measure.setValue(wrapper.value);
      return measure;
    }

    public static ProductUnitPricingMeasureWrapper from(ProductUnitPricingMeasure measure) {
      ProductUnitPricingMeasureWrapper wrapper = new ProductUnitPricingMeasureWrapper();
      wrapper.unit = measure.getUnit();
      wrapper.value = measure.getValue();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link Error}. This class contains method to transform from and to
   * {@link Error}.
   */
  public static final class ErrorWrapper {

    public String domain;
    public String message;
    public String reason;

    public static Error to(ErrorWrapper wrapper) {
      Error error = new Error();
      error.setDomain(wrapper.domain);
      error.setMessage(wrapper.message);
      error.setReason(wrapper.reason);
      return error;
    }

    public static ErrorWrapper from(Error error) {
      ErrorWrapper wrapper = new ErrorWrapper();
      wrapper.domain = error.getDomain();
      wrapper.message = error.getMessage();
      wrapper.reason = error.getReason();
      return wrapper;
    }
  }

  /**
   * A wrapper class to represent {@link ProductShippingWeight}. This class contains method to
   * transform from and to {@link ProductShippingWeight}.
   */
  public static final class ProductShippingWeightWrapper {

    public String unit;
    public Double value;

    public static ProductShippingWeight to(ProductShippingWeightWrapper weightWrapper) {
      ProductShippingWeight weight = new ProductShippingWeight();
      weight.setUnit(weightWrapper.unit);
      weight.setValue(weightWrapper.value);
      return weight;
    }

    public static ProductShippingWeightWrapper from(ProductShippingWeight weight) {
      ProductShippingWeightWrapper weightWrapper = new ProductShippingWeightWrapper();
      weightWrapper.unit = weight.getUnit();
      weightWrapper.value = weight.getValue();
      return weightWrapper;
    }
  }
}



