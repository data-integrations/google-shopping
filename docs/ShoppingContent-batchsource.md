# Shopping Content Batch Source


Description
-----------
This source reads product data from Google Shopping Content API.

It outputs Products read from Shopping Content API in StructureRecord format, the output schema is aligned with Shopping
Content Product definition. You can checkout the schema definition at productschema.json file.

Configuration
-------------

**Merchant ID:** ID used to uniquely identify a merchant.

**Service Account File Path**: Path on the local file system of the service account key used for
authorization. When running on other clusters, the file must be present on every node in the cluster.
