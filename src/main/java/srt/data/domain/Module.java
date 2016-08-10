package srt.data.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Module {

	private String id;
	
	private String parentModuleId;
	private List<String> subModuleIdList;
	
	private String name;
	private String description;
	private String status;
	private List<String> attachmentPathList;
	
	public void addSubModuleId(String subModuleId){
		if(subModuleIdList==null){
			subModuleIdList=new ArrayList<String>();
		}
		subModuleIdList.add(subModuleId);
	}
	
	public void removeSubModuleId(String subModuleId){
		if(subModuleIdList!=null){
			subModuleIdList.remove(subModuleId);
		}
	}
	
	public void addAttachmentPath(String attachmentPath){
		if(attachmentPathList==null){
			attachmentPathList=new ArrayList<String>();
		}
		attachmentPathList.add(attachmentPath);
	}

	public void removeAttachmentPath(String attachmentPath){
		if(attachmentPathList!=null){
			attachmentPathList.remove(attachmentPath);
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentModuleId() {
		return parentModuleId;
	}

	public void setParentModuleId(String parentModuleId) {
		this.parentModuleId = parentModuleId;
	}

	public List<String> getSubModuleIdList() {
		return subModuleIdList;
	}

	public void setSubModuleIdList(List<String> subModuleIdList) {
		this.subModuleIdList = subModuleIdList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getAttachmentPathList() {
		return attachmentPathList;
	}

	public void setAttachmentPathList(List<String> attachmentPathList) {
		this.attachmentPathList = attachmentPathList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachmentPathList == null) ? 0 : attachmentPathList.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentModuleId == null) ? 0 : parentModuleId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subModuleIdList == null) ? 0 : subModuleIdList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Module other = (Module) obj;
		if (attachmentPathList == null) {
			if (other.attachmentPathList != null)
				return false;
		} else if (!attachmentPathList.equals(other.attachmentPathList))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentModuleId == null) {
			if (other.parentModuleId != null)
				return false;
		} else if (!parentModuleId.equals(other.parentModuleId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subModuleIdList == null) {
			if (other.subModuleIdList != null)
				return false;
		} else if (!subModuleIdList.equals(other.subModuleIdList))
			return false;
		return true;
	}

}
