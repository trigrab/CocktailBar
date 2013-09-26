package com.venezilu.barkeeper;

public class Cocktail {
	private String mName;
	private String mDescription;
	private int mImageId;
	private int mId;
	
	public Cocktail(String name, String description, int imageId, int id){
		mName = name;
		mDescription = description;
		mImageId = imageId;
		mId = id;
	}
	
	public String getName(){
		return mName;
	}
	
	public String getDescription(){
		return mDescription;
	}
	
	public int getImageUri() {
		return mImageId;
	}
	
	public int getId() {
		return mId;
	}
}
