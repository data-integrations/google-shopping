package io.cdap.plugin.google.shopping.content.source;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.util.List;

/**
 * A {@link RecordReader} to read the {@link ShoppingContentSplit}.
 */
public class ShoppingContentRecordReader extends RecordReader<Text, ProductWritable> {

  // Current product ID.
  private Text currentProductId;
  // Current product.
  private ProductWritable currentProduct;
  private List<ProductWrapper> productList;
  private int currentIndex = 0;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) {
    ShoppingContentSplit shoppingContentSplit = (ShoppingContentSplit) inputSplit;
    productList = shoppingContentSplit.getProductList();
  }

  @Override
  public boolean nextKeyValue() {
    if (currentIndex >= productList.size()) {
      return false;
    }
    currentProductId = new Text(productList.get(currentIndex).id);
    currentProduct = new ProductWritable(productList.get(currentIndex));
    currentIndex++;
    return true;
  }

  @Override
  public Text getCurrentKey() {
    return currentProductId;
  }

  @Override
  public ProductWritable getCurrentValue() {
    return currentProduct;
  }

  @Override
  public float getProgress() {
    return 0.0f;
  }

  @Override
  public void close() {
    // no-op
  }
}
