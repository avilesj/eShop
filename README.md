# Installation

## Global variables configuration

- MINIO_ACCESS_KEY
    - Minio File Server access key.
- MINIO_SECRET_KEY
    - Minio File Server secret key.
- MINIO_BUCKET
    - Minio File Server chosen bucket's name.
- MINIO_URL
    - Minio File Server url.
- STRIPE_KEY
    - Stripe secret key for payments.

### Example configuration

For the sake of clarity, here is a definition of how the application expects the values.

    MINIO_ACCESS_KEY=long_access_key
    MINIO_SECRET_KEY=extremely_long_secret_key
    MINIO_BUCKET=bucketname
    MINIO_URL=https://yourminioserveraddress.com
    STRIPE_KEY=stripe_super_secret_key
