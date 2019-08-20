package io.cdap.plugin.google.shopping.content.source;

import com.google.common.collect.ImmutableMap;
import io.cdap.cdap.api.data.batch.InputFormatProvider;
import java.util.Map;

/**
 * An {@link InputFormatProvider} for ShoppingContent API.
 * */
public class ShoppingContentInputFormatProvider implements InputFormatProvider {

  private final Map<String, String> conf;

  public ShoppingContentInputFormatProvider(ShoppingContentConfig config) {
    ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();
    builder.put(ShoppingContentConstants.MERCHANT_ID, config.getMerchantId());
    builder.put(ShoppingContentConstants.SERVICE_ACCOUNT_PATH, config.getServiceAccountPath());

    if (config.getMaxProducts() != null) {
      builder.put(ShoppingContentConstants.MAX_PRODUCTS, config.getMaxProducts());
    } else {
      builder.put(ShoppingContentConstants.MAX_PRODUCTS, "0");
    }
    this.conf = builder.build();
  }

  @Override
  public Map<String, String> getInputFormatConfiguration() {
    return conf;
  }

  @Override
  public String getInputFormatClassName() {
    return ShoppingContentInputFormat.class.getName();
  }
}
