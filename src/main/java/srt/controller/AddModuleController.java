package srt.controller;

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
public class AddModuleController {

	
	@Autowired
	private IModuleRepository moduleRepository;
	
	@RequestMapping(value="addModule", method=RequestMethod.POST)
	public @ResponseBody ModuleWrapper addModule(@RequestBody Module module){
		moduleRepository.addModule(module);
		if(module.getParentModuleId()!=null && (!module.getParentModuleId().isEmpty())){
			Module parentModule=moduleRepository.getModuleByModuleId(module.getParentModuleId());
			parentModule.addSubModuleId(module.getId());
			moduleRepository.updateModule(parentModule);
		}
		return new ModuleWrapper(module);
	}
}
