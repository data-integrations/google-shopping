/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.google.shopping.content.source;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;

/**
 * A input split holding necessary information for {@link ShoppingContentRecordReader} to read from Google Shopping
 * Content API.
 */
public class ShoppingContentSplit extends InputSplit implements Writable {

  // A complete file path to locate service account json file. Used for authentication with Google
  // Shopping.
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
