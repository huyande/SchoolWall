package com.zwxq.bean;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-11-11
 */
public class Liked {
    private Integer id;

    private String confid;

    private String openid;

    private Integer liked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfid() {
        return confid;
    }

    public void setConfid(String confid) {
        this.confid = confid == null ? null : confid.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }
}