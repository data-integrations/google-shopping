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

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.content.ShoppingContent;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductsListResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client to read from Google Shopping Content API in a paginated fashion.
 */
public class ShoppingContentClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentClient.class);

  // Gets 10 products per page.
  private static final long MAX_PRODUCTS = 10L;

  private final BigInteger merchantId;

  private final ShoppingContent shoppingContent;

  private String nextPageToken = null;

  private boolean end = false;

  private List<Product> currentPage;

  public ShoppingContentClient(
      BigInteger merchantId, HttpTransport transport, Credential credential) {
    this.merchantId = merchantId;
    ShoppingContent.Builder builder =
        new ShoppingContent.Builder(transport, JacksonFactory.getDefaultInstance(),
                                    credential);
    this.shoppingContent = builder.build();
  }

  /**
   * If true, we can call getNextPage().
   */
  public boolean hasNextPage() throws IOException {
    ShoppingContent.Products.List productsList = shoppingContent.products().list(merchantId)
                                                                .setIncludeInvalidInsertedItems(true);

    if (end) {
      return false;
    }

    currentPage = new ArrayList<>();

    if (MAX_PRODUCTS > 0L) {
      productsList.setMaxResults(MAX_PRODUCTS);
    }

    if (nextPageToken != null) {
      productsList.setPageToken(nextPageToken);
    }

    ProductsListResponse page = productsList.execute();

    if (page.getResources() == null || page.getResources().isEmpty()) {
      // Expects to enter here iff there is no products found for this merchant.
      LOGGER.info("No products found.");
      end = true;
      return false;
    }

    for (Product product : page.getResources()) {
      currentPage.add(product);
    }

    if (page.getNextPageToken() != null) {
      nextPageToken = page.getNextPageToken();
    } else {
      nextPageToken = null;
      end = true;
    }

    return true;
  }

  /**
   * Lists a page of products given a merchant has uploaded to Google Shopping. Valid iff hasNextPage() returns true.
   *
   * @return A list of products given the merchant has uploaded to Google Shopping.
   */
  public List<Product> getNextPage() {
    return currentPage;
  }
}
