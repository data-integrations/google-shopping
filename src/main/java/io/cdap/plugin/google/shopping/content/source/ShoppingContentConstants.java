package io.cdap.plugin.google.shopping.content.source;

import io.cdap.cdap.api.data.schema.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Constants used by Shopping content related files.
 */
public class ShoppingContentConstants {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentConstants.class);

  public static final String SERVICE_ACCOUNT_PATH = "serviceaccount.path";
  public static final String MERCHANT_ID = "merchant id";
  // Schema of product to represent Product(https://developers.google.com/shopping-content/v2/reference/v2.1/products).
  public static final Schema PRODUCT_SCHEMA;

  static {
    PRODUCT_SCHEMA = loadSchema("productschema.json");
  }

  private static Schema loadSchema(String path) {
    ClassLoader classLoader = new ShoppingContentConstants().getClass().getClassLoader();

    try {
      File file = new File(classLoader.getResource(path).getFile());
      String stringSchema = new String(Files.readAllBytes(file.toPath()));
      return Schema.parseJson(stringSchema);
    } catch (IOException e) {
      LOGGER.debug("Load schema failed: " + e.getMessage());
      return null;
    }
  }
}
