package com.youtu;

import org.json.JSONObject;

public class Demo {

	// appid, secretid secretkey请到http://open.youtu.qq.com/获取
	// 请正确填写把下面的APP_ID、SECRET_ID和SECRET_KEY
	public static final String APP_ID = "10097839";
	public static final String SECRET_ID = "AKIDYAd0BEisPjrmVKGcfrshMp6Dd1ElAQUL";
	public static final String SECRET_KEY = "nOn486wb4MSL3aTDxVr1zvjLpBdzA9vY";
	public static final String USER_ID = "";

	public static void main(String[] args) {

		try {
			
			//https://api.youtu.qq.com/youtu/ocrapi/generalocr
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
			JSONObject respose;
			//respose= faceYoutu.FaceCompareUrl("http://open.youtu.qq.com/content/img/slide-1.jpg","http://open.youtu.qq.com/content/img/slide-1.jpg");
			
			respose = faceYoutu.GeneralOcr("c:\\www\\222222.png");
			//respose = faceYoutu.DetectFace("test.jpg",1);
			//get respose
			System.out.println(respose);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
