package srt.data.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModuleWrapper {

	private Module module;

	private List<ModuleWrapper> subModuleWrapperList;
	
	public ModuleWrapper(){
		this(new Module());
	}
	
	public ModuleWrapper(Module module) {
		super();
		this.module = module;
	}

	public void addSubModule(Module subModule){
		if(subModuleWrapperList==null){
			subModuleWrapperList=new ArrayList<ModuleWrapper>();
		}
		subModuleWrapperList.add(new ModuleWrapper(subModule));
	}
	
	public void addSubModuleWrapper(ModuleWrapper subModuleWrapper){
		if(subModuleWrapperList==null){
			subModuleWrapperList=new ArrayList<ModuleWrapper>();
		}
		subModuleWrapperList.add(subModuleWrapper);
	}

	public void removeSubModuleId(String subModuleId) {
		module.removeSubModuleId(subModuleId);
	}

	public void removeAttachmentPath(String attachmentPath) {
		module.removeAttachmentPath(attachmentPath);
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<ModuleWrapper> getSubModuleWrapperList() {
		return subModuleWrapperList;
	}

	public void setSubModuleWrapperList(List<ModuleWrapper> subModuleWrapperList) {
		this.subModuleWrapperList = subModuleWrapperList;
	}

	public void addSubModuleId(String subModuleId) {
		module.addSubModuleId(subModuleId);
	}

	public void addAttachmentPath(String attachmentPath) {
		module.addAttachmentPath(attachmentPath);
	}

	public String getId() {
		return module.getId();
	}

	public void setId(String id) {
		module.setId(id);
	}

	public String getParentModuleId() {
		return module.getParentModuleId();
	}

	public void setParentModuleId(String parentModuleId) {
		module.setParentModuleId(parentModuleId);
	}

	public List<String> getSubModuleIdList() {
		return module.getSubModuleIdList();
	}

	public void setSubModuleIdList(List<String> subModuleIdList) {
		module.setSubModuleIdList(subModuleIdList);
	}

	public String getName() {
		return module.getName();
	}

	public void setName(String name) {
		module.setName(name);
	}

	public String getDescription() {
		return module.getDescription();
	}

	public void setDescription(String description) {
		module.setDescription(description);
	}

	public String getStatus() {
		return module.getStatus();
	}

	public void setStatus(String status) {
		module.setStatus(status);
	}

	public List<String> getAttachmentPathList() {
		return module.getAttachmentPathList();
	}

	public void setAttachmentPathList(List<String> attachmentPathList) {
		module.setAttachmentPathList(attachmentPathList);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((subModuleWrapperList == null) ? 0 : subModuleWrapperList.hashCode());
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
		ModuleWrapper other = (ModuleWrapper) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (subModuleWrapperList == null) {
			if (other.subModuleWrapperList != null)
				return false;
		} else if (!subModuleWrapperList.equals(other.subModuleWrapperList))
			return false;
		return true;
	}

	
}
