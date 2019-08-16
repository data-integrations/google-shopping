package io.cdap.plugin.google.shopping.content.source;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;

import io.cdap.cdap.api.plugin.PluginConfig;

/**
 * Configurations for making Shopping Content API calls.
 */
public class ShoppingContentConfig extends PluginConfig {

    @Name("referenceName")
    @Description("This will be used to uniquely identify this source/sink for lineage, annotating metadata, etc.")
    public String referenceName;

    @Name("merchantID")
    @Description("The unique identifier of the merchant")
    @Macro
    private String merchantId;

    @Name("serviceAccountPath")
    @Description("The path to locate service-account.json file which is used to authenticate with Shopping Content API.")
    @Macro
    private String serviceAccountPath;

    public String getMerchantId() {
        return merchantId;
    }

    public String getServiceAccountPath() {
        return serviceAccountPath;
    }
}
