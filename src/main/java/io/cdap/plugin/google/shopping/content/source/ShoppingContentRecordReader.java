package io.cdap.plugin.google.shopping.content.source;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.util.List;

/**
 * A {@link RecordReader} to read the {@link ShoppingContentSplit}.
 */
public class ShoppingContentRecordReader extends RecordReader<Text, ProductWritable> {

  private List<ProductWritable> productList;
  private int currentIndex = 0;
  private ShoppingContentSplit shoppingContentSplit;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) {
    shoppingContentSplit = (ShoppingContentSplit) inputSplit;
  }

  @Override
  public boolean nextKeyValue() throws IOException {

    if (currentIndex < productList.size()) {
      return true;
    }

    if (!shoppingContentSplit.hasNextPage()) {
      return false;
    }

    productList = shoppingContentSplit.getNextPage();
    currentIndex = 0;
    return true;
  }

  @Override
  public Text getCurrentKey() {
    return new Text(productList.get(currentIndex).getProduct().id);
  }

  @Override
  public ProductWritable getCurrentValue() {
    return productList.get(currentIndex);
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
