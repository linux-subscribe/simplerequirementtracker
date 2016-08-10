package srt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import srt.data.domain.Module;
import srt.data.repository.IModuleRepository;

@Controller
public class RemoveModuleController {

	@Autowired
	private IModuleRepository moduleRepository;
	
	@RequestMapping(value="/removeModule", method=RequestMethod.POST)
	public @ResponseBody String removeModule(@RequestBody String moduleId){
		Module module=moduleRepository.getModuleByModuleId(moduleId);
		if(module!=null){
			removeModule(module);
		}
		return "";
	}
	
	private void removeModule(Module module){
		if(module.getSubModuleIdList()!=null && module.getSubModuleIdList().size()>0){
			for(String subMododuleId:module.getSubModuleIdList()){
				Module subModule=moduleRepository.getModuleByModuleId(subMododuleId);
				if(subModule!=null){
					removeModule(subModule);
				}
			}
		}
		String parentId=module.getParentModuleId();
		if(parentId!=null && (!parentId.isEmpty())){
			Module parentModule=moduleRepository.getModuleByModuleId(parentId);
			if(parentModule!=null){
				parentModule.removeSubModuleId(module.getId());
				moduleRepository.updateModule(parentModule);
			}
		}
		moduleRepository.removeModuleByModuleId(module.getId());
	}
}
