package io.cdap.plugin.google.shopping.content.source;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A input split containing products reading from Google Shopping Content API.
 */
public class ShoppingContentSplit extends InputSplit implements Writable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentBatchSource.class);

  private String serviceAccountPath;

  private String merchantId;

  private ShoppingContentClient client;

  public ShoppingContentSplit(String serviceAccountPath, String merchantId) {
    this.serviceAccountPath = serviceAccountPath;
    this.merchantId = merchantId;
  }

  public ShoppingContentSplit() throws IOException, GeneralSecurityException {
    // Default constructor here is needed for Hadoop deserialization.
    this.client = new ShoppingContentClient(serviceAccountPath, merchantId);
  }

  public boolean hasNextPage() throws IOException {
    return client.hasNextPage();
  }

  public List<ProductWritable> getNextPage() {
    return client.getNextPage()
        .stream().map(p -> new ProductWritable(ProductWrapper.from(p))).collect(Collectors.toList());
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    this.serviceAccountPath = in.readUTF();
    this.merchantId = in.readUTF();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(this.serviceAccountPath);
    out.writeUTF(this.merchantId);
  }

  @Override
  public long getLength() {
    return 0;
  }

  @Override
  public String[] getLocations() {
    return new String[0];
  }
}
