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

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.plugin.PluginConfig;
import io.cdap.cdap.etl.api.validation.InvalidConfigPropertyException;
import java.math.BigInteger;
import javax.annotation.Nullable;

/**
 * Configurations for making Shopping Content API calls.
 */
public class ShoppingContentConfig extends PluginConfig {

  @Name("referenceName")
  @Description("This will be used to uniquely identify this source/sink for lineage, annotating"
      + " metadata, etc.")
  public String referenceName;

  @Name("merchantID")
  @Description("The unique identifier of the merchant.")
  @Macro
  private String merchantId;

  @Name("serviceAccountPath")
  @Description("The path to locate service-account.json file which is used to authenticate with "
      + "Shopping Content API.")
  @Macro
  @Nullable
  private String serviceAccountPath;

  public String getMerchantId() {
    return merchantId;
  }

  public String getServiceAccountPath() {
    return serviceAccountPath;
  }

  public void validate() {
    try {
      BigInteger converted = new BigInteger(merchantId);
    } catch (NumberFormatException e) {
      throw new InvalidConfigPropertyException("Merchant Id can't be converted into a BigInteger",
                                               "merchantId");
    }
  }
}
