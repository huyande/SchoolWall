package com.zwxq.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zwxq.bean.Wxuserinfo;
import com.zwxq.bean.param.ConfParam;
import com.zwxq.service.WxUserInfoService;
import com.zwxq.utils.JacksonUtil;
import com.zwxq.utils.MyHttpUtils;

/**
 * 反馈信息
 * @author Administrator
 *
 */
@RestController
@RequestMapping("back")
public class BackMessageController {
	Logger log = LoggerFactory.getLogger(BackMessageController.class);
	@Autowired
	private WxUserInfoService wxUserInfoService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	//配置邮件发送方 
	@Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数
	
	//配置邮件接收方法
	@Value("${backmessage.mail.accept}")
	private String mailAccept; 

	/**
	 * 发送邮件
	 * @param req
	 * @return
	 */
	@RequestMapping("/backmessage")	
	public String backMessage(HttpServletRequest req){
		log.info("发送反馈信息到邮箱");
		//获取提交过来的参数
		String fromStream = MyHttpUtils.getStringFromStream(req);
		ConfParam confParam = JacksonUtil.readValue(fromStream, ConfParam.class);
		//查询当前用户的名称
		Wxuserinfo wxuserinfo = wxUserInfoService.findByOpenId(confParam.getOpenid());
		String message = wxuserinfo.getNickname()+"反馈问题啦："+confParam.getContent();
		SimpleMailMessage simmessage = new SimpleMailMessage();
		simmessage.setFrom(Sender);
		simmessage.setTo(mailAccept);
		//设置邮件的标题
		simmessage.setSubject("来自校园墙用户   "+wxuserinfo.getNickname()+"  的问题反馈 ：");
		//设置邮件内容
		simmessage.setText(message);
		//发送邮件
        mailSender.send(simmessage);
        
        return "{\"error_code\":0,\"error_message\":\"反馈成功\"}";
	}

}
