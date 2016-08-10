package srt.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import srt.data.domain.Module;
import srt.data.domain.ModuleWrapper;
import srt.data.repository.IModuleRepository;

@Controller
public class FetchModulesController {

	
	@Autowired
	private IModuleRepository moduleRepository;
	
	@RequestMapping(value="/fetchModules", method=RequestMethod.POST)
	public @ResponseBody List<ModuleWrapper> fetchModules(@RequestBody Long userId){
		List<Module> moduleList=moduleRepository.getAllModules();
		return createTopModuleWrapperList(moduleList);
	}

	private List<ModuleWrapper> createTopModuleWrapperList(Collection<Module> moduleCollection){
		List<Module> topModuleList=new ArrayList<Module>();
		for(Module module:moduleCollection){
			if(module.getParentModuleId()==null || module.getParentModuleId().isEmpty()){
				topModuleList.add(module);
			}
		}
		List<ModuleWrapper> topModuleWrapperList=new ArrayList<ModuleWrapper>();
		for(Module topModule:topModuleList){
			topModuleWrapperList.add(getModuleWrapper(topModule));
		}
		return topModuleWrapperList;
	}
	
	private ModuleWrapper getModuleWrapper(Module module){
		ModuleWrapper moduleWrapper=new ModuleWrapper(module);
		if(module.getSubModuleIdList()!=null && !module.getSubModuleIdList().isEmpty()){
			for(String subModuleId:module.getSubModuleIdList()){
				Module subModule=moduleRepository.getModuleByModuleId(subModuleId);
				if(subModule!=null){
					ModuleWrapper subModuleWrapper = getModuleWrapper(subModule);
					moduleWrapper.addSubModuleWrapper(subModuleWrapper);
				}
			}
		}
		return moduleWrapper;
	}
	
}
