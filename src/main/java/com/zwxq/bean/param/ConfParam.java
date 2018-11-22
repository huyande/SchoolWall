package com.zwxq.bean.param;

import java.util.List;

/**
 * 表白墙&吐槽&祝福 参数对象
 * @author Administrator
 *
 */
public class ConfParam {

	private String content;
    private List<String> attachments;
    private Boolean stateNiming;
    private String username;
    private String openid;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public Boolean getStateNiming() {
		return stateNiming;
	}
	public void setStateNiming(Boolean stateNiming) {
		this.stateNiming = stateNiming;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Override
	public String toString() {
		return "ConfParam [content=" + content + ", attachments=" + attachments + ", stateNiming=" + stateNiming
				+ ", username=" + username + ", openid=" + openid + "]";
	}
	
}
