# GlideImageSizeResearch
# 샘플 예제
```
final ImageView image1 = (ImageView) findViewById(R.id.image1);
final ImageView image2 = (ImageView) findViewById(R.id.image2);
final ImageView image3 = (ImageView) findViewById(R.id.image3);
        
// 정상 
Glide.with(this).load(imageUrl).into(image1);

// 이미지가 퀄리티가 낮아지게 되는 경우 
image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
Glide.with(this).load(imageUrl).fitCenter().into(image2);

// 정상 
image3.setScaleType(ImageView.ScaleType.CENTER_CROP);
Glide.with(this).load(imageUrl).centerCrop().into(image3);
```
* 원본 이미지가 화면사이즈보다 크게 올 경우 glide에서 어떻게 해야 이미지가 정상으로 보이게 될 경우가 있는지 확인해보자가 하는 샘플 소스임 

# 이미지 퀄리티가 낮아지는 이유
HD 단말 (갤럭시 노트2, 베가레이서 R3)과 같은 저사양해상도(720X1280)에서 Glide로 이미지가 가져올때 fitCenter로 큰이미지를 imageview 영역안에 맞춰서 downscale하여 이미지를 로드를 했고 이미지를 늘리고 자르는 옵션인 
ImageView.ScaleType.CENTER_CROP 으로 설정으로 작은 이미지를 glide로 로드했고 이를 이미지를 늘려서 잘랐기 때문에 이미지 퀄리티가 낮아지는 현상을 발견함. 
따라서 이를 감안하여 glide 활용을 잘 하여야함 

