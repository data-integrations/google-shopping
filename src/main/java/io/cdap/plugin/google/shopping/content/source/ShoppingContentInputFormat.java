package io.cdap.plugin.google.shopping.content.source;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping content API input format.
 */
public class ShoppingContentInputFormat extends InputFormat {

  @Override
  public List<InputSplit> getSplits(JobContext context) {
    Configuration configuration = context.getConfiguration();

    String serviceAccountPath = configuration.get(ShoppingContentConstants.SERVICE_ACCOUNT_PATH);
    String merchantId = configuration.get(ShoppingContentConstants.MERCHANT_ID);
    List<InputSplit> splits = new ArrayList<>();
    splits.add(new ShoppingContentSplit(serviceAccountPath, new BigInteger(merchantId)));
    return splits;
  }

  @Override
  public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context) {
    return new ShoppingContentRecordReader();
  }
}
