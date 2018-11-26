package com.zwxq.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwxq.bean.Confession;
import com.zwxq.bean.Liked;
import com.zwxq.bean.Notification;
import com.zwxq.bean.SchoolImage;
import com.zwxq.bean.Wxuserinfo;
import com.zwxq.bean.ana.ScConfessionJson;
import com.zwxq.bean.ana.User;
import com.zwxq.bean.param.ConfParam;
import com.zwxq.service.LikedService;
import com.zwxq.service.NotificationService;
import com.zwxq.service.SchoolConfessionService;
import com.zwxq.service.SchoolImageService;
import com.zwxq.service.WxUserInfoService;
import com.zwxq.utils.DataTimeUtils;
import com.zwxq.utils.JacksonUtil;
import com.zwxq.utils.MyHttpUtils;

/**
 * 校园照片墙 
 * @author Administrator
 *
 */
@RestController()
@RequestMapping("/scImage")
public class SchoolImageConfession {
	Logger log = LoggerFactory.getLogger(SchoolImageConfession.class);
	
	@Autowired
	private SchoolImageService schoolImageService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private LikedService likedService;
	
	@Autowired
	private NotificationService notificationService;
	
	//图片地址前缀 
	@Value("${qiniu.imageAddress}")
	private String imageAddress;
	
	/**
	 * 分页获取数据  
	 * 
	 * openid 是当前用户
	 * @return
	 */
	@GetMapping("/imageDataSet")
	public String confDataSet(Integer page,Integer pageSize,String openid){
		//List<String>images = new ArrayList<>();
		//封装的返回数据的集合
		List<ScConfessionJson> jsons = new ArrayList<>();
		//存放图片信息 
		List<SchoolImage> confList = new ArrayList<>();
		//查询数据  分页查询    
		confList=schoolImageService.findschoolImage(page,pageSize);
		for (SchoolImage conf : confList) {
			User user = new User();
			ScConfessionJson scConfessionJson = new ScConfessionJson();
			Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(conf.getOpenid());
			user.setAvatar(wxuserinfo.getAvatarurl());
			user.setUsername(wxuserinfo.getNickname()); //
			user.setUserId(wxuserinfo.getOpenid());
			scConfessionJson.setId(conf.getId());
			scConfessionJson.setContent(conf.getContent());
			//点赞状态 是要查询当前的用户 是否对此文章点过赞
			boolean liked=likedService.isLiked(openid,conf.getId());
			scConfessionJson.setLiked(liked);
			
			scConfessionJson.setLikedCount(likedService.likedCount(conf.getId())); 
			scConfessionJson.setTime(Integer.parseInt(DataTimeUtils.date2TimeStamp(conf.getTime().toString(), "yyyy-MM-dd HH:mm:ss")));
			scConfessionJson.setUser(user);
			List<String>images = new ArrayList<>();
			if(conf.getImage()!=null){
				String[] split = conf.getImage().split(";");
				for (String string : split) {
					images.add(imageAddress+string.replace(";", ""));
				}
			}
			scConfessionJson.setImages(images);
			jsons.add(scConfessionJson);
		}
		String jSon = JacksonUtil.toJSon(jsons);
		return jSon;
	}
	
	/**
	 * 添加数据 
	 * @param req
	 * @return
	 */
	@PostMapping("/post")
	public String postAdd(HttpServletRequest req){
		//获取用户提交的
		String fromStream = MyHttpUtils.getStringFromStream(req);
		ConfParam confParam = JacksonUtil.readValue(fromStream, ConfParam.class);
		SchoolImage schoolImage = new SchoolImage();
		//设置内容
		schoolImage.setContent(confParam.getContent());
		//拿到用户的上传的图片
		if(confParam.getAttachments()!=null && confParam.getAttachments().size()>0){
			StringBuffer sb = new StringBuffer();
			for (String strimg : confParam.getAttachments()) {
				sb.append(strimg).append(";");
			}
			schoolImage.setImage(sb.toString());
		}
		//拿到用户的 openid
		schoolImage.setOpenid(confParam.getOpenid());
		//设置这条是否匿名
		log.info(confParam.toString());
		//添加数据  
		int num=schoolImageService.postAdd(schoolImage);
		if(num>0){
			return "{\"error_code\":0,\"error_message\":\"添加成功\"}";
		}else{
			return "{\"error_code\":1,\"error_message\":\"发布失败\"}";
		}
	}
	
	/**
	 * 点赞处理
	 * @param req
	 * @return
	 */
	@GetMapping("/tapLike")
	public String tapLike(String confid,String openid,Boolean likedstate){
		log.info(confid+"==="+openid+"===="+likedstate);
		if(likedstate){ //如果状态为true 则为取消点赞   0 为取消点赞
			int taplikeNum=likedService.updataLikeState(confid,openid,0);
			return "{\"likedstate\":false,\"likecount\":-1}";
		}else{//否则是点赞  点赞分为 无此数据 和 已有此条数据 修改此状态
			Liked liked=likedService.ishasLikedData(confid, openid);
			if(liked==null){
				Liked like = new Liked();
				like.setOpenid(openid);
				like.setConfid(confid);
				like.setLiked(1); //1为 点赞
				int num =likedService.addLiked(like);
			}else{
				int taplikeNum=likedService.updataLikeState(confid,openid,1);
			}
			//添加通知消息信息
			Notification notification = new Notification();
			//保存信息id
			notification.setConfid(confid);
			//保存添加点赞用户id
			notification.setFromopenid(openid);
			//根据信息id查询此信息
			SchoolImage schoolImage =schoolImageService.findSchoolByImageid(confid);
			//保存当前信息 添加者的id 
			notification.setToopenid(schoolImage.getOpenid());
			//设置消息类型 是表白
			notification.setType(0);
			if(! openid.equals(schoolImage.getOpenid())){ //自己点赞 不记录
				int num =notificationService.addNotificationMessage(notification);
			}
			return "{\"likedstate\":true,\"likecount\":1}";
		}
	}
	
}
