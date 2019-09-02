package io.cdap.plugin.google.shopping.content.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;

/**
 * Constants used by Shopping content related files.
 */
public class ShoppingContentConstants {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentConstants.class);

  public static final String SERVICE_ACCOUNT_PATH = "serviceaccount.path";
  public static final String MERCHANT_ID = "merchant id";
  // Schema of product to represent Product(https://developers.google.com/shopping-content/v2/reference/v2.1/products).
  public static final String PRODUCT_SCHEMA;

  static {
    byte[] bytes = new byte[0];
    try {
      ClassLoader classLoader = new ShoppingContentConstants().getClass().getClassLoader();
      File file = new File(classLoader.getResource("productschema.json").getFile());
      bytes = Files.readAllBytes(file.toPath());
    } catch (Exception e) {
      LOGGER.debug("Reading productschema.json failed.");
    }
    PRODUCT_SCHEMA = new String(bytes);
  }
}
