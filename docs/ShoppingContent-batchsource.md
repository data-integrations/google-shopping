# Shopping Content Batch Source


Description
-----------
This source reads product data from Google Shopping Content API.

Configuration
-------------

**Merchant ID:** ID used to uniquely identify a merchant.

**Service Account File Path**: Path on the local file system of the service account key used for
authorization. Can be set to 'auto-detect' when running on a Dataproc cluster.
When running on other clusters, the file must be present on every node in the cluster.