package com.zwxq.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qiniu.util.Auth;

@RestController()
public class ImageController {
	@Value("${qiniu.accessKey}")
	private String accessKey;
	
	@Value("${qiniu.secretKey}")
	private String secretKey;
	
	@Value("${qiniu.bucket}")
	private String bucket;

	//写入配置文件
	
	@RequestMapping("/upload_token")
	public String uploadToken(){
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		return upToken;
	}
}
