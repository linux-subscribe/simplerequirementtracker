package srt.data.repository;

import java.util.List;

import srt.data.domain.Module;

public interface IModuleRepository {

	public void addModule(Module module);
	
	public void updateModule(Module module);
	
	public Module getModuleByModuleId(String moduleId);
	
	public List<Module> getAllModules();
	
	public void removeModuleByModuleId(String moduleId);
}
