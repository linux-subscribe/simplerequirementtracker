package srt.data.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import srt.data.domain.Module;

@Repository
public class SimpleModuleRepository implements IModuleRepository{

	@Autowired
	private MongoTemplate mongo;
	
	@Override
	public void updateModule(Module module) {
		mongo.findAndModify(Query.query(Criteria.where("id").is(module.getId())), 
				Update.update("name", module.getName())
					.set("description", module.getDescription())
					.set("status", module.getStatus())
					.set("parentModuleId", module.getParentModuleId())
					.set("subModuleIdList", module.getSubModuleIdList())
					.set("attachmentPathList", module.getAttachmentPathList()), 
				Module.class);
	}

	@Override
	public void addModule(Module module) {
		mongo.insert(module);	
	}

	@Override
	public List<Module> getAllModules(){
		return mongo.findAll(Module.class);
	}
	
	@Override
	public Module getModuleByModuleId(String moduleId) {
		return mongo.findById(moduleId, Module.class);
	}

	@Override
	public void removeModuleByModuleId(String moduleId) {
		mongo.findAndRemove(Query.query(Criteria.where("id").is(moduleId)), Module.class);
	}

}
