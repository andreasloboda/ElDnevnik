package com.iktpreobuka.el_ucionica_AS.controllers.ResponseDTOs;

public class StudentDTO extends TeacherDTO{

	private Integer parentId;
	private String parentName;
	private Integer groupId;
	private String group;
	public StudentDTO() {
		super();
	}
	public StudentDTO(Integer parentId, String parentName, String group) {
		super();
		this.parentId = parentId;
		this.parentName = parentName;
		this.group = group;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
