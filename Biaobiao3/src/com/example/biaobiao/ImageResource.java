package com.example.biaobiao;

import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class ImageResource {
	
	private Map<Integer, Drawable> drawableResource;
	private Map<Integer, Bitmap> bitmapResource;
	private static ImageResource imageResource;
	
	private ImageResource(){
		drawableResource = new HashMap<Integer, Drawable>();
		bitmapResource = new HashMap<Integer, Bitmap>();
	}
	
	public static ImageResource getImageResource(){
		if(imageResource==null){
			imageResource = new ImageResource();
		}
		return imageResource;
	}
	
	public Drawable getDrawable(Resources resources, int id){
		
		Drawable drawable = drawableResource.get(id);
		
		if(drawable==null){
			drawable = resources.getDrawable(id);
			drawableResource.put(id, drawable);
		}
		
		return drawable;
	}
	
	public Bitmap getBitmap(Resources resources, int id){
		
		Bitmap bitmap = bitmapResource.get(id);
		
		if(bitmap==null){
			bitmap = BitmapFactory.decodeResource(resources, id);
			bitmapResource.put(id, bitmap);
		}
		
		return bitmap;
	}

}