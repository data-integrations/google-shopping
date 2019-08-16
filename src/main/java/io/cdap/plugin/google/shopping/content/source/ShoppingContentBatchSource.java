package io.cdap.plugin.google.shopping.content.source;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.format.StructuredRecordStringConverter;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A BatchSource plugin to read products from Google Shopping content API.
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("ShoppingContent")
@Description("A Google shopping content API Batch Source")
public class ShoppingContentBatchSource extends BatchSource<Text, ProductWritable, StructuredRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentBatchSource.class);
    private final ShoppingContentConfig config;

    public ShoppingContentBatchSource(ShoppingContentConfig config) {
        this.config = config;
    }

    @Override
    public void configurePipeline(PipelineConfigurer configurer) {
        configurer.getStageConfigurer().setOutputSchema(null);
    }

    @Override
    public void prepareRun(BatchSourceContext context) {
        context.setInput(Input.of(config.referenceName, new ShoppingContentInputFormatProvider(config)));
    }

    @Override
    public void transform(KeyValue<Text, ProductWritable> input,
                          Emitter<StructuredRecord> emitter) throws Exception {
        Schema productSchema = Schema.parseJson(ShoppingContentConstants.PRODUCT_SCHEMA);

//        Schema productIdSchema = Schema.of(Schema.Type.STRING);
//        Schema.Field productIdField = Schema.Field.of("id", productIdSchema);
//        Schema productColorSchema = Schema.of(Schema.Type.STRING);
//        Schema.Field productColorField = Schema.Field.of("color", productColorSchema);
//
//        Schema productSchema = Schema.recordOf("product", productIdField, productColorField);

        String productString = new Gson().toJson(input.getValue().getProduct());
        LOGGER.debug("product string is " + productString);

        StructuredRecord productRecord = StructuredRecordStringConverter.fromJsonString(productString, productSchema);

        String structureString = StructuredRecordStringConverter.toJsonString(productRecord);

        LOGGER.debug("structure string is " + structureString);
//        StructuredRecord.Builder builder = StructuredRecord.builder(productSchema);
//        builder.set("id", input.getValue().getProduct().getId());
//        builder.set("color", input.getValue().getProduct().getColor());
//        emitter.emit(builder.build());
        emitter.emit(productRecord);
    }
}
