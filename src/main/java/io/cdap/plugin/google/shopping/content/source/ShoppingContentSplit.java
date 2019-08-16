package io.cdap.plugin.google.shopping.content.source;

import com.google.api.services.content.model.Product;
import com.google.gson.Gson;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.InputSplit;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A input split containing products reading from Google Shopping Content API.
 */
public class ShoppingContentSplit extends InputSplit implements Writable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentBatchSource.class);

    private List<ProductWrapper> productList;

    public ShoppingContentSplit(List<Product> products) {
        productList = new ArrayList<>();

        for(Product p : products) {
            productList.add(ProductWrapper.from(p));
        }
    }

    public ShoppingContentSplit() {
        // Default constructor here is needed for Hadoop deserialization.
    }

    public List<ProductWrapper> getProductList() {
        return productList;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int size = in.readInt();

        if (size > 0) {
            productList = new ArrayList<>();
        }

        for (int i = 0; i < size; i ++) {
            // Deserialize an instance of Product.
            int length = in.readInt();
            byte[] buf = new byte[length];
            in.readFully(buf);
            String productstring = new String(buf, "UTF-8");
LOGGER.debug("in split product string is " + productstring);
            ProductWrapper product = new Gson().fromJson(productstring, ProductWrapper.class);
            this.productList.add(product);
        }

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.productList.size());

        for (int i = 0; i < this.productList.size(); i ++) {
            // Serialize each product into Json string.
            byte[] buf = new Gson().toJson(this.productList.get(i)).getBytes();
            out.writeInt(buf.length);
            out.write(buf);
        }
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
