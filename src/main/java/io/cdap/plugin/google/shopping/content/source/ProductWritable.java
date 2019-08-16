package io.cdap.plugin.google.shopping.content.source;

import com.google.api.services.content.model.Product;
import com.google.gson.Gson;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A wrapper for {@link Product} which implements {@link Writable} interface.
 */
public class ProductWritable implements Writable {

  private ProductWrapper product;

  public ProductWritable(ProductWrapper product) {
    this.product = product;
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    // Deserialize an instance of Product.
    int length = in.readInt();
    byte[] buf = new byte[length];
    in.readFully(buf);
    product = new Gson().fromJson(new String(buf, "UTF-8"), ProductWrapper.class);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    // Serialize a product into Json string.
    byte[] buf = new Gson().toJson(product).getBytes();
    out.writeInt(buf.length);
    out.write(buf);
  }

  public ProductWrapper getProduct() {
    return product;
  }
}
