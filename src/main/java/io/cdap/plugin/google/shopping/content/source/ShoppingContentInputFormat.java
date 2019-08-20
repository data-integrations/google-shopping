package io.cdap.plugin.google.shopping.content.source;

import com.google.api.services.content.model.Product;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shopping content API input format.
 */
public class ShoppingContentInputFormat extends InputFormat {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentConstants.class);

  @Override
  public List<InputSplit> getSplits(JobContext context) {
    Configuration configuration = context.getConfiguration();

    try {
      String serviceAccountPath = configuration.get(ShoppingContentConstants.SERVICE_ACCOUNT_PATH);
      ShoppingContentClient client = new ShoppingContentClient(serviceAccountPath);
      List<List<Product>> products = client.listProductForMerchant(
          new BigInteger(configuration.get(ShoppingContentConstants.MERCHANT_ID)),
          new Long(configuration.get(ShoppingContentConstants.MAX_PRODUCTS)
          ));
      List<InputSplit> splits = new ArrayList<>();

      for (List<Product> page : products) {
        splits.add(new ShoppingContentSplit(page));
      }
      return splits;
    } catch (Exception e) {
      LOGGER.debug("Exception when reading from Shopping Content\n" + e.getMessage());
      throw new RuntimeException("There was issue communicating with Shopping content API", e);
    }
  }

  @Override
  public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context) {
    return new ShoppingContentRecordReader();
  }
}
