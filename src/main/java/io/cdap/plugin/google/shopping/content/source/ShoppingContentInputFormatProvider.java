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

import com.google.common.collect.ImmutableMap;
import io.cdap.cdap.api.data.batch.InputFormatProvider;
import java.util.Map;

/**
 * An {@link InputFormatProvider} for ShoppingContent API.
 */
public class ShoppingContentInputFormatProvider implements InputFormatProvider {

  private final Map<String, String> conf;

  public ShoppingContentInputFormatProvider(ShoppingContentConfig config) {
    ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();
    builder.put(ShoppingContentConstants.MERCHANT_ID, config.getMerchantId());
    builder.put(ShoppingContentConstants.SERVICE_ACCOUNT_PATH, config.getServiceAccountPath());
    this.conf = builder.build();
  }

  @Override
  public Map<String, String> getInputFormatConfiguration() {
    return conf;
  }

  @Override
  public String getInputFormatClassName() {
    return ShoppingContentInputFormat.class.getName();
  }
}
