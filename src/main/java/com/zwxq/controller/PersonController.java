package com.zwxq.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zwxq.bean.Confession;
import com.zwxq.bean.SchoolImage;
import com.zwxq.bean.Wxuserinfo;
import com.zwxq.bean.ana.ScConfessionJson;
import com.zwxq.bean.ana.User;
import com.zwxq.service.LikedService;
import com.zwxq.service.SchoolConfessionService;
import com.zwxq.service.SchoolImageService;
import com.zwxq.service.WxUserInfoService;
import com.zwxq.utils.DataTimeUtils;
import com.zwxq.utils.JacksonUtil;

/**
 * 个人信息控制层 消息列表的查询 我的表白墙 我分享的照片
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("person")
public class PersonController {
	@Autowired
	private SchoolImageService schoolImageService;
	
	@Autowired
	private SchoolConfessionService schoolConfessionService;

	@Autowired
	private WxUserInfoService wxUserInfoService;

	@Autowired
	private LikedService likedService;

	// 图片地址前缀
	@Value("${qiniu.imageAddress}")
	private String imageAddress;

	@RequestMapping("personimage")
	public String personImageData(Integer page, Integer pageSize, String openid) {
		// List<String>images = new ArrayList<>();
		// 封装的返回数据的集合
		List<ScConfessionJson> jsons = new ArrayList<>();
		// 存放图片信息
		List<SchoolImage> confList = new ArrayList<>();
		// 查询数据 分页查询
		confList = schoolImageService.findSchoolImageOfMyImage(page, pageSize,openid);
		for (SchoolImage conf : confList) {
			User user = new User();
			ScConfessionJson scConfessionJson = new ScConfessionJson();
			Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(conf.getOpenid());
			user.setAvatar(wxuserinfo.getAvatarurl());
			user.setUsername(wxuserinfo.getNickname()); //
			user.setUserId(wxuserinfo.getOpenid());
			scConfessionJson.setId(conf.getId());
			scConfessionJson.setContent(conf.getContent());
			// 点赞状态 是要查询当前的用户 是否对此文章点过赞
			boolean liked = likedService.isLiked(openid, conf.getId());
			scConfessionJson.setLiked(liked);

			scConfessionJson.setLikedCount(likedService.likedCount(conf.getId()));
			scConfessionJson.setTime(
					Integer.parseInt(DataTimeUtils.date2TimeStamp(conf.getTime().toString(), "yyyy-MM-dd HH:mm:ss")));
			scConfessionJson.setUser(user);
			List<String> images = new ArrayList<>();
			if (conf.getImage() != null) {
				String[] split = conf.getImage().split(";");
				for (String string : split) {
					images.add(imageAddress + string.replace(";", ""));
				}
			}
			scConfessionJson.setImages(images);
			jsons.add(scConfessionJson);
		}
		String jSon = JacksonUtil.toJSon(jsons);
		return jSon;
	}
	
	/**
	 * 查找我的表白信息
	 * @param page
	 * @param pageSize
	 * @param openid
	 * @return
	 */
	@RequestMapping("personlove")
	public String personLoveData(Integer page, Integer pageSize, String openid) {
		// List<String>images = new ArrayList<>();
		// 封装的返回数据的集合
		List<ScConfessionJson> jsons = new ArrayList<>();
		// 存放图片信息
		List<Confession> confList = new ArrayList<>();
		// 查询数据 分页查询
		confList = schoolConfessionService.findschoolConfessionOfMyLove(page, pageSize,openid);
		for (Confession conf : confList) {
			User user = new User();
			ScConfessionJson scConfessionJson = new ScConfessionJson();
			Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(conf.getOpenid());
			user.setAvatar(wxuserinfo.getAvatarurl());
			user.setUsername(wxuserinfo.getNickname()); //
			user.setUserId(wxuserinfo.getOpenid());
			scConfessionJson.setId(conf.getId());
			scConfessionJson.setContent(conf.getContent());
			// 点赞状态 是要查询当前的用户 是否对此文章点过赞
			boolean liked = likedService.isLiked(openid, conf.getId());
			scConfessionJson.setLiked(liked);

			scConfessionJson.setLikedCount(likedService.likedCount(conf.getId()));
			scConfessionJson.setTime(
					Integer.parseInt(DataTimeUtils.date2TimeStamp(conf.getTime().toString(), "yyyy-MM-dd HH:mm:ss")));
			scConfessionJson.setUser(user);
			List<String>images = new ArrayList<>();
			if(conf.getImage()!=null){
				images.add(imageAddress+conf.getImage());
			}
			scConfessionJson.setImages(images);
			jsons.add(scConfessionJson);
		}
		String jSon = JacksonUtil.toJSon(jsons);
		return jSon;
	}

}
