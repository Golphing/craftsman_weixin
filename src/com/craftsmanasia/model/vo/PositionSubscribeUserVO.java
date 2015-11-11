package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.utils.DateTimeUtility;

public class PositionSubscribeUserVO {

	private int id;
	private int positionId;
	private int userId;
	private String createTime;

	private PositionVO position;

	private List<String> allStatus;
	private List<String> statusTime;
	private List<String> replies;
	private String positionTitle;
	private String companyName;
	private int companyId;

	public static PositionSubscribeUserVO toVO(PositionSubscribeUser positionSubscribeUser) {
		if(positionSubscribeUser == null) {
			return null;
		}
		PositionSubscribeUserVO vo = new PositionSubscribeUserVO();
		vo.setId(positionSubscribeUser.getId());
		vo.setPositionId(positionSubscribeUser.getPositionId());
		vo.setUserId(positionSubscribeUser.getUserId());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getCreateTime()));
		vo.setPosition(PositionVO.toVO(positionSubscribeUser.getPosition()));
		
		Position position = positionSubscribeUser.getPosition();
		vo.setPositionTitle(position.getTitle());
		vo.setCompanyName(position.getCompany().getName());
		vo.setCompanyId(position.getCompanyId());
		
		List<ResumeSubscribeStatus> statuses = positionSubscribeUser.getStatuses();
		List<String> allStatus = new ArrayList<String>();
		List<String> replies = new ArrayList<String>();
		List<String> statusTime = new ArrayList<String>();
		if(statuses != null) {
			for(ResumeSubscribeStatus status : statuses) {
				allStatus.add(status.getStatus());
				if(status.getReply() == null) {
					replies.add("");
				} else {
					replies.add(status.getReply());
				}
				statusTime.add(DateTimeUtility.formatYYYYMMDDHHMMSS(status.getCreateTime()));
			}
		}
		vo.setAllStatus(allStatus);
		vo.setStatusTime(statusTime);
		vo.setReplies(replies);
		
		return vo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<String> getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(List<String> allStatus) {
		this.allStatus = allStatus;
	}

	public List<String> getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(List<String> statusTime) {
		this.statusTime = statusTime;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public List<String> getReplies() {
		return replies;
	}

	public void setReplies(List<String> replies) {
		this.replies = replies;
	}
}
