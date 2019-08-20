# Shopping Content Batch Source


Description
-----------
This source reads product data from Google Shopping Content API.

Configuration
-------------

**Merchant ID:** ID used to uniquely identify a merchant.

**Service Account File Path**: Path on the local file system of the service account key used for
authorization. When running on other clusters, the file must be present on every node in the cluster.

**Max Products:** The maximum number of products to process in a mapper. We use this as the
maxResults when talking to Content API, which is specifying the pagination size.
