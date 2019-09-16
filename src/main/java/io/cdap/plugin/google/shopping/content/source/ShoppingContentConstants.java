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

import io.cdap.cdap.api.data.schema.Schema;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants used by Shopping content related files.
 */
public class ShoppingContentConstants {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentConstants.class);

  public static final String SERVICE_ACCOUNT_PATH = "serviceaccount.path";
  public static final String MERCHANT_ID = "merchant id";
  // Schema of product to represent Product(https://developers.google.com/shopping-content/v2/reference/v2.1/products).
  public static final Schema PRODUCT_SCHEMA;

  static {
    PRODUCT_SCHEMA = loadSchema("productschema.json");
  }

  private static Schema loadSchema(String path) {
    ClassLoader classLoader = new ShoppingContentConstants().getClass().getClassLoader();

    try {
      File file = new File(classLoader.getResource(path).getFile());
      String stringSchema = new String(Files.readAllBytes(file.toPath()));
      return Schema.parseJson(stringSchema);
    } catch (IOException e) {
      LOGGER.debug("Load schema failed: " + e.getMessage());
      return null;
    }
  }
}
