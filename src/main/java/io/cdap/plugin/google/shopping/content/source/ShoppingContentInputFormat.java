package io.cdap.plugin.google.shopping.content.source;

import com.google.api.services.content.model.Product;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping content API input format, which only has one split holding all the products returned by
 * the API.
 */
public class ShoppingContentInputFormat extends InputFormat {

  @Override
  public List<InputSplit> getSplits(JobContext context) {
    Configuration configuration = context.getConfiguration();

    try {
      String serviceAccountPath = configuration.get(ShoppingContentConstants.SERVICE_ACCOUNT_PATH);
      ShoppingContentClient client = new ShoppingContentClient(serviceAccountPath);
      List<Product> products = client.listProductForMerchant(getMerchantId(configuration));
      List<InputSplit> splits = new ArrayList<>();
      splits.add(new ShoppingContentSplit(products));
      return splits;
    } catch (Exception e) {
      throw new RuntimeException("There was issue communicating with Shopping content API", e);
    }
  }

  @Override
  public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context) {
    return new ShoppingContentRecordReader();
  }

  private BigInteger getMerchantId(Configuration configuration) {
    return new BigInteger(configuration.get(ShoppingContentConstants.MERCHANT_ID));
  }
}
