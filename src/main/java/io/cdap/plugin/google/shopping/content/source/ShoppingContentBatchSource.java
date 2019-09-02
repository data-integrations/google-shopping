package io.cdap.plugin.google.shopping.content.source;

import com.google.gson.Gson;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.cdap.etl.api.validation.InvalidConfigPropertyException;
import io.cdap.cdap.format.StructuredRecordStringConverter;
import io.cdap.plugin.common.LineageRecorder;

import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * A BatchSource plugin to read products from Google Shopping content API.
 * https://developers.google.com/shopping-content/v2/reference/v2.1/products
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("ShoppingContent")
@Description("A Google shopping content API Batch Source")
public class ShoppingContentBatchSource extends
    BatchSource<Text, ProductWritable, StructuredRecord> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentBatchSource.class);
  private final ShoppingContentConfig config;

  public ShoppingContentBatchSource(ShoppingContentConfig config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer configurer) {
    config.validate();
    try {
      LOGGER.info("Set output schema \n");
      configurer.getStageConfigurer()
          .setOutputSchema(Schema.parseJson(ShoppingContentConstants.PRODUCT_SCHEMA));
    } catch (IOException e) {
      LOGGER.debug("IOException \n" + e.getMessage());
      throw new InvalidConfigPropertyException(e.getMessage(), e, "schema");
    }
  }


  @Override
  public void prepareRun(BatchSourceContext context) throws Exception {
    context
        .setInput(Input.of(config.referenceName, new ShoppingContentInputFormatProvider(config)));

    // Record lineage.
    LineageRecorder lineageRecorder = new LineageRecorder(context, config.referenceName);
    lineageRecorder.recordRead("Read", "Read from Google Shopping Content API.",
        Schema.parseJson(ShoppingContentConstants.PRODUCT_SCHEMA).getFields().stream()
            .map(Schema.Field::getName).collect(
            Collectors.toList()));
  }

  @Override
  public void transform(KeyValue<Text, ProductWritable> input,
      Emitter<StructuredRecord> emitter) throws Exception {
    Schema productSchema = Schema.parseJson(ShoppingContentConstants.PRODUCT_SCHEMA);
    String productString = new Gson().toJson(input.getValue().getProduct());
    StructuredRecord productRecord = StructuredRecordStringConverter
        .fromJsonString(productString, productSchema);
    emitter.emit(productRecord);
  }
}
