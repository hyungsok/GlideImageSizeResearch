# GlideImageSizeResearch

```
        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .into(image1);

        image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .fitCenter()
                .into(image2);

        image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(imageUrl).listener(mRequestListener)
                .centerCrop()
                .into(image3);
```

