package io.cdap.plugin.google.shopping.content.source;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.content.ShoppingContent;
import com.google.api.services.content.ShoppingContentScopes;
import com.google.api.services.content.model.ProductsListResponse;
import com.google.api.services.content.model.Product;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client to read from Google Shopping Content API.
 */
public class ShoppingContentClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentClient.class);

  // Service account file path.
  private String serviceAccountPath;

  public ShoppingContentClient(String serviceAccountPath) {
    this.serviceAccountPath = serviceAccountPath;
  }

  /**
   * Lists the products given a merchant has uploaded to Google Shopping.
   *
   * @param merchantId is the unique identifier for shopping to identify a merchant.
   * @return A list of products given the merchant has uploaded to Google Shopping.
   */
  public List<Product> listProductForMerchant(BigInteger merchantId) throws Exception {
    ShoppingContent shoppingContent;

    // Initializes a ShoppingContent.
    try {
      HttpTransport httpTransport = createHttpTransport();
      HttpRequestInitializer initializer = loadCredential(serviceAccountPath, httpTransport);

      ShoppingContent.Builder builder =
          new ShoppingContent.Builder(httpTransport, JacksonFactory.getDefaultInstance(),
              initializer);
      shoppingContent = builder.build();
    } catch (IOException e) {
      throw new IOException(
          "Could not initialize shopping content client.");
    }

    ShoppingContent.Products.List productsList = shoppingContent.products().list(merchantId)
        .setIncludeInvalidInsertedItems(true);
    List<Product> results = new ArrayList<>();

    do {
      ProductsListResponse page = productsList.execute();
      if (page.getResources() == null) {
        LOGGER.info("No products found.");
        break;
      }
      for (Product product : page.getResources()) {
        results.add(product);
      }
      if (page.getNextPageToken() == null) {
        break;
      }
      productsList.setPageToken(page.getNextPageToken());
    } while (true);

    return results;
  }

  /**
   * Given the path to service account file, it loads a file named service-account.json and uses
   * that json to authenticate with Google Shopping.
   *
   * @param serviceAccountPath The path to the service account file.
   * @param transport A HttpTransport.
   * @return An authorized {@link Credential} object.
   */
  private Credential loadCredential(String serviceAccountPath, HttpTransport transport)
      throws IOException {
    File serviceAccountFile = new File(serviceAccountPath, "service-account.json");

    if (serviceAccountFile.exists()) {
      LOGGER.info("Loading service account credentials.");

      try (InputStream inputStream = new FileInputStream(serviceAccountFile)) {
        GoogleCredential credential =
            GoogleCredential
                .fromStream(inputStream, transport, JacksonFactory.getDefaultInstance())
                .createScoped(ShoppingContentScopes.all());

        // GoogleCredential.fromStream does NOT refresh, since scopes must be added after.
        if (!credential.refreshToken()) {
          LOGGER.debug("The service account access token could not be refreshed.");
          LOGGER.debug("The service account key may have been deleted in the API Console.");
          throw new IOException("Failed to refresh service account token");
        }
        return credential;
      }
    } else {
      LOGGER.debug("The service account path is not valid");
    }
    return null;
  }

  private HttpTransport createHttpTransport() throws IOException {
    try {
      return GoogleNetHttpTransport.newTrustedTransport();
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }
}
