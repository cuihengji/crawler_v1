package com.ruixuesoft.crawler.open.uurecognition;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64DecoderTest {

	
	public static void main(String[] args) {
		
		try{
		final Base64.Decoder decoder = Base64.getDecoder();
		final Base64.Encoder encoder = Base64.getEncoder();
		
		String encodedPic = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB0AAAAOCAYAAADT0Rc6AAABdklEQVR42qWUMUgDQRBFDwsrsbGwEBEsLIJIOpEgIkgQK7tUV4iQIoiFnYi9hcgVQZBUYmEnInIIIQQLEUFSiIgIIiGlnVVKZ+EffL4TTnHhkezfnZ3d2b8XRX7bMIrUL0L7bVsy4rxJNaNBvBlN6jeh8ZwaNtJwuDVeBoxVs6RzxgqRGvvU3zUu8H/VKCMmsOaQGNcDxriC0R6S3RgfoGd8Gm0sErh0Sn1F44EnxLOWankLctKMAyzojRUo/kvW2zTqovU1aYxJSrjL5wFjsSTNO2nfM9OIcS7cw0Cqz1LcAjawTtRRTtbCnHFNOma0jGEi3N2xaCnKy87XKrScCr3DSD+S9sTebcf6XUq6jPtTTpGYtQ5cXf5v0lDaisMJXgFrDzDmot7pDn4zqliAtW1jhuISnIy5M15F63rlnccHgHnEXaheoriS85yOnKemXogmxGkZiePCjEnZ9CgcWsFJt/K+vVMI+AvTssaQcYYndYircNs3bFSWiVYudnIAAAAASUVORK5CYII=";
		
		decodeToImage(encodedPic);
		
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	
	public static String encodeToString(String imagePath) throws IOException {
	    String type = StringUtils.substring(imagePath, imagePath.lastIndexOf(".") + 1);
	    BufferedImage image = ImageIO.read(new File(imagePath));
	    String imageString = null;
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
	        ImageIO.write(image, type, bos);
	        byte[] imageBytes = bos.toByteArray();
	        BASE64Encoder encoder = new BASE64Encoder();
	        imageString = encoder.encode(imageBytes);
	        bos.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return imageString;
	}

	
	public static void decodeToImage(String imagebytes) throws IOException {
		
		String[] splitedRawCssData = imagebytes.split(",");
		
		//data:image/png;base64,
		String metaDataPart = splitedRawCssData[0];
		String imagePart = splitedRawCssData[1];
		String fileformat = metaDataPart.replace("data:image/", "").replace(";base64", "");
		
		final Base64.Decoder decoder = Base64.getDecoder();
		byte[] imageBytes = decoder.decode(imagePart);
	    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	    BufferedImage restoredImage = ImageIO.read(bis);
	    ImageIO.write(restoredImage, fileformat, new File("c://d123122345.png"));
	   
	}
	
}
