package io.cdap.plugin.google.shopping.content.source;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants used by Shopping content related files.
 */
public class ShoppingContentConstants {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentConstants.class);

  public static final String SERVICE_ACCOUNT_PATH = "serviceaccount.path";
  public static final String MERCHANT_ID = "merchant id";
  public static final String MAX_PRODUCTS = "max products";
  // Schema of product to represent Product(https://developers.google.com/shopping-content/v2/reference/v2.1/products).
  public static String PRODUCT_SCHEMA;

  static {
    try{
      ClassLoader classLoader = new ShoppingContentConstants().getClass().getClassLoader();
      File file = new File(classLoader.getResource("productschema.json").getFile());
      PRODUCT_SCHEMA = new String(Files.readAllBytes(file.toPath()));
    } catch (Exception e) {
      LOGGER.debug("Reading productschema.json failed.");
    }
  }

}