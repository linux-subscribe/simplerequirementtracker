package srt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import srt.data.domain.ModuleWrapper;
import srt.data.repository.IModuleRepository;

@Controller
public class UpdateModuleController {

	@Autowired
	private IModuleRepository moduleRepository;
	
	@RequestMapping(value="/updateModules", method=RequestMethod.POST)
	public @ResponseBody String updateModules(@RequestBody ModuleWrapper moduleWrapper){
		moduleRepository.updateModule(moduleWrapper.getModule());
		return "";
	}
}
