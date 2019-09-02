package io.cdap.plugin.google.shopping.content.source;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.content.ShoppingContent;
import com.google.api.services.content.ShoppingContentScopes;
import com.google.api.services.content.model.Product;
import com.google.api.services.content.model.ProductsListResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Client to read from Google Shopping Content API.
 * It provides an iterator interface to get Products from Shopping Content API.
 */
public class ShoppingContentClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentClient.class);

  private static final long MAX_PRODUCTS = 10L;

  private final String serviceAccountPath;

  private final BigInteger merchantId;

  private final ShoppingContent shoppingContent;

  private final HttpTransport httpTransport;

  private String nextPageToken = null;

  private boolean end = false;

  private List<Product> currentPage;

  public ShoppingContentClient(String serviceAccountPath,
      String merchantId) throws IOException, GeneralSecurityException {
    this.serviceAccountPath = serviceAccountPath;
    this.merchantId = new BigInteger(merchantId);
    this.httpTransport = createHttpTransport();

    HttpRequestInitializer initializer;

    if (serviceAccountPath.isEmpty()) {
      // Uses default credential is service account file is not provided.
      initializer = GoogleCredential.getApplicationDefault();
    } else {
      initializer = loadCredential(serviceAccountPath, httpTransport);
    }

    ShoppingContent.Builder builder =
        new ShoppingContent.Builder(httpTransport, JacksonFactory.getDefaultInstance(),
            initializer);
    this.shoppingContent = builder.build();
  }

  /**
   * If true, we can call getNextPage().
   * */
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
      LOGGER.info("No products found.");
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
   * Lists a page of products given a merchant has uploaded to Google Shopping.
   * Valid iff hasNextPage() returns true.
   * @return A list of products given the merchant has uploaded to Google Shopping.
   */
  public List<Product> getNextPage() {
    return currentPage;
  }

  /**
   * Given the path to service account file, it loads a file named service-account.json and uses that json to
   * authenticate with Google Shopping.
   *
   * @param serviceAccountPath The full path to the service account file.
   * @param transport A HttpTransport.
   * @return An authorized {@link Credential} object.
   */
  private Credential loadCredential(String serviceAccountPath, HttpTransport transport)
      throws IOException {
    File serviceAccountFile = new File(serviceAccountPath);

    if (serviceAccountFile.exists()) {
      LOGGER.info("Loading service account credentials.");

      try (InputStream inputStream = new FileInputStream(serviceAccountFile)) {
        GoogleCredential credential =
            GoogleCredential
                .fromStream(inputStream, transport, JacksonFactory.getDefaultInstance())
                .createScoped(ShoppingContentScopes.all());

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

  private HttpTransport createHttpTransport() throws IOException, GeneralSecurityException {
    return GoogleNetHttpTransport.newTrustedTransport();
  }
}
