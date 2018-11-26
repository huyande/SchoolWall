package com.zwxq.bean;

import java.util.Date;

/**
 * 
 * 
 * @author 
 * 
 * @date 2018-11-24
 */
public class Notification {
    private String id;

    private String fromopenid;

    private String confid;

    private String toopenid;

    /**
     * 1 为点赞 0 为 分享
     */
    private Integer type;

    private String createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFromopenid() {
        return fromopenid;
    }

    public void setFromopenid(String fromopenid) {
        this.fromopenid = fromopenid == null ? null : fromopenid.trim();
    }

    public String getConfid() {
        return confid;
    }

    public void setConfid(String confid) {
        this.confid = confid == null ? null : confid.trim();
    }

    public String getToopenid() {
        return toopenid;
    }

    public void setToopenid(String toopenid) {
        this.toopenid = toopenid == null ? null : toopenid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}