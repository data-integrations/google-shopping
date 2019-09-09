package io.cdap.plugin.google.shopping.content.source;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.content.ShoppingContentScopes;
import com.google.api.services.content.model.Product;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link RecordReader} to read products from Google Shopping Content API.
 */
public class ShoppingContentRecordReader extends RecordReader<Text, Product> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingContentRecordReader.class);

  private List<Product> productList;
  private int currentIndex = -1;
  private ShoppingContentClient shoppingContentClient;

  @Override
  public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) {
    productList = new ArrayList<>();

    // Initialize a ShoppingContentClient.
    ShoppingContentSplit shoppingContentSplit = (ShoppingContentSplit) inputSplit;
    Credential credential;

    try {
      HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

      if (shoppingContentSplit.getServiceAccountPath().isEmpty()) {
        // Uses default credential is service account file is not provided.
        credential = GoogleCredential.getApplicationDefault();
      } else {
        credential = loadCredential(shoppingContentSplit.getServiceAccountPath(), httpTransport);
      }
      shoppingContentClient = new ShoppingContentClient(
              shoppingContentSplit.getMerchantId(), httpTransport, credential);
    } catch (GeneralSecurityException | IOException e) {
      LOGGER.debug("Failed to initialize Shopping Content API client: " + e.getMessage());
    }
  }

  @Override
  public boolean nextKeyValue() throws IOException {
    ++currentIndex;

    // Gets next product from current page.
    if (currentIndex < productList.size()) {
      return true;
    }

    // Fetches a next page.
    if (!shoppingContentClient.hasNextPage()) {
      return false;
    }
    productList = shoppingContentClient.getNextPage();
    currentIndex = 0;
    return true;
  }

  @Override
  public Text getCurrentKey() {
    return new Text(productList.get(currentIndex).getId());
  }

  @Override
  public Product getCurrentValue() {
    return productList.get(currentIndex);
  }

  /**
   * Creates a {@link Credential} from the path to a service account file,
   *
   * @param serviceAccountPath The full path to the service account file.
   * @param transport          A HttpTransport.
   * @return An authorized {@link Credential} object or NULL.
   */
  private Credential loadCredential(String serviceAccountPath, HttpTransport transport)
          throws IOException {
    File serviceAccountFile = new File(serviceAccountPath);

    if (!serviceAccountFile.exists()) {
      throw new IOException("Failed to locate service account file from the given path: " + serviceAccountPath);
    }

    LOGGER.info("Loading service account credentials.");

    try (InputStream inputStream = new FileInputStream(serviceAccountFile)) {
      GoogleCredential credential =
              GoogleCredential
                      .fromStream(inputStream, transport, JacksonFactory.getDefaultInstance())
                      .createScoped(ShoppingContentScopes.all());

      if (!credential.refreshToken()) {
        LOGGER.debug("The service account access token could not be refreshed. Please ensure that the " +
                "service account exists.");
        throw new IOException("Failed to refresh service account token");
      }
      return credential;
    }
  }

  @Override
  public float getProgress() {
    return 0.0f;
  }

  @Override
  public void close() {
    // no-op
  }
}
