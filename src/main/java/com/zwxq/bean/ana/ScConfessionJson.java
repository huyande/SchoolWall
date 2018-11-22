package com.zwxq.bean.ana;

import java.util.List;

/**
 * 表白信息JSON
 * @author Administrator
 *
 */
public class ScConfessionJson {

	//数据id
	private String id;
	//内容
	private String content;
	//发表时间
	private Integer time;
	//点赞数据量
	private Integer likedCount;
	//是否被点赞
	private Boolean liked;
	//用户信息
	private User user;
	//图片列表
	private List<String> images;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getLikedCount() {
		return likedCount;
	}
	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
}
