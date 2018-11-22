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
import com.zwxq.bean.Wxuserinfo;
import com.zwxq.bean.ana.ScConfessionJson;
import com.zwxq.bean.ana.User;
import com.zwxq.bean.param.ConfParam;
import com.zwxq.service.LikedService;
import com.zwxq.service.SchoolConfessionService;
import com.zwxq.service.WxUserInfoService;
import com.zwxq.utils.DataTimeUtils;
import com.zwxq.utils.JacksonUtil;
import com.zwxq.utils.MyHttpUtils;

/**
 * 表白墙
 * @author Administrator
 *
 */
@RestController()
@RequestMapping("/conf")
public class SchoolConfession {
	Logger log = LoggerFactory.getLogger(SchoolConfession.class);
	
	@Autowired
	private SchoolConfessionService schoolConfessionService;
	
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
	private LikedService likedService;
	
	//图片地址前缀 
	@Value("${qiniu.imageAddress}")
	private String imageAddress;
	
	/**
	 * 分页获取数据  
	 * 分页参数暂未传到后台
	 * openid 是当前用户
	 * @return
	 */
	@GetMapping("/confessionDataSet")
	public String confDataSet(Integer page,Integer pageSize,String openid){
		//List<String>images = new ArrayList<>();
		//集合
		List<ScConfessionJson> jsons = new ArrayList<>();
		List<Confession> confList = new ArrayList<>();
		//查询数据  分页查询    
		confList=schoolConfessionService.findConfession(page,pageSize);
		for (Confession conf : confList) {
			User user = new User();
			ScConfessionJson scConfessionJson = new ScConfessionJson();
			Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(conf.getOpenid());
			if(conf.getStateniming()==1){//需要匿名
				user.setAvatar("http://pi1dtt9jo.bkt.clouddn.com/tmp/wx66c3b6978169a23c.o6zAJs_2Bm5IzKp-p1KikDf36saQ.Xet08bfZifV591f2ef6ae50c8379d92a9136b029104f.png");
				user.setUsername("TO:"+conf.getTouser()); //暂时 界面显示to 某某
			}else{
				user.setAvatar(wxuserinfo.getAvatarurl());
				user.setUsername("TO:"+conf.getTouser()); //wxuserinfo.getNickname()
			}
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
				images.add(imageAddress+conf.getImage());
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
		String fromStream = MyHttpUtils.getStringFromStream(req);
		ConfParam confParam = JacksonUtil.readValue(fromStream, ConfParam.class);
		Confession confession = new Confession();
		//设置内容
		confession.setContent(confParam.getContent());
		//拿到用户的上传的图片
		if(confParam.getAttachments()!=null && confParam.getAttachments().size()>0){
			confession.setImage(confParam.getAttachments().get(0));
		}
		//拿到用户的 openid
		confession.setOpenid(confParam.getOpenid());
		//设置这条是否匿名
		confession.setStateniming((confParam.getStateNiming()==true)?1:0);
		//设置touser 
		confession.setTouser(confParam.getUsername());
		log.info(confParam.toString());
		//添加数据  
		int num=schoolConfessionService.postAdd(confession);
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
			return "{\"likedstate\":true,\"likecount\":1}";
		}
	}
	
}
