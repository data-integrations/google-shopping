package io.cdap.plugin.google.shopping.content.source;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;

/**
 * A input split holding necessary information for {@link ShoppingContentRecordReader} to read from Google Shopping
 * Content API.
 */
public class ShoppingContentSplit extends InputSplit implements Writable {
  // A complete file path to locate service account json file. Used for authentication with Google Shopping.
  private String serviceAccountPath;
  // Merchant ID, an unique identifier to identify a Merchant.
  private BigInteger merchantId;

  public ShoppingContentSplit(String serviceAccountPath, BigInteger merchantId) {
    // Make sure serviceAccountPath is not null here.
    this.serviceAccountPath = serviceAccountPath == null ? "" : serviceAccountPath;
    this.merchantId = merchantId;
  }

  public ShoppingContentSplit() {
    // Default constructor here is needed for Hadoop deserialization.
  }

  public String getServiceAccountPath() {
    return serviceAccountPath;
  }

  public BigInteger getMerchantId() {
    return merchantId;
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    this.serviceAccountPath = in.readUTF();
    this.merchantId = new BigInteger(in.readUTF());
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(this.serviceAccountPath);
    out.writeUTF(this.merchantId.toString());
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
