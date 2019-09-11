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

import com.google.api.services.content.model.Product;
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
import io.cdap.plugin.common.LineageRecorder;
import java.util.stream.Collectors;
import org.apache.hadoop.io.NullWritable;

/**
 * A BatchSource plugin to read products from Google Shopping content API. https://developers.google.com/shopping-content/v2/reference/v2.1/products
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name("ShoppingContent")
@Description("A Google shopping content API Batch Source")
public class ShoppingContentBatchSource extends BatchSource<NullWritable, Product, StructuredRecord> {

  private final ShoppingContentConfig config;

  public ShoppingContentBatchSource(ShoppingContentConfig config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer configurer) {
    config.validate();
    configurer.getStageConfigurer().setOutputSchema(ShoppingContentConstants.PRODUCT_SCHEMA);
  }


  @Override
  public void prepareRun(BatchSourceContext context) throws Exception {
    context.setInput(Input.of(config.referenceName, new ShoppingContentInputFormatProvider(config)));

    // Record lineage.
    LineageRecorder lineageRecorder = new LineageRecorder(context, config.referenceName);
    lineageRecorder.recordRead("Read", "Read from Google Shopping Content API.",
                               ShoppingContentConstants.PRODUCT_SCHEMA.getFields().stream().map(Schema.Field::getName)
                                                                      .collect(Collectors.toList()));
  }

  @Override
  public void transform(KeyValue<NullWritable, Product> input,
      Emitter<StructuredRecord> emitter) {
    emitter.emit(ProductToStructureRecordTransform.productToStructureRecord(input.getValue()));
  }
}
