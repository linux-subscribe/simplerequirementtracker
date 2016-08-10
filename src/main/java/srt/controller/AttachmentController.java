package srt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import srt.data.domain.Module;
import srt.data.repository.IModuleRepository;

@Controller
public class AttachmentController {

	@Autowired
	private IModuleRepository moduleRepository;
	
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	public @ResponseBody String uploadFile(String id, MultipartFile file, HttpSession session) throws IllegalStateException, IOException{
		String sessionRootPath=session.getServletContext().getRealPath("/");
		Module module=moduleRepository.getModuleByModuleId(id);
		File fileToSave=parepareAndGetAttachmentSavingFile(sessionRootPath, file.getOriginalFilename(), module);
		file.transferTo(fileToSave);
		module.addAttachmentPath(fileToSave.getName());
		moduleRepository.updateModule(module);
		return fileToSave.getName();
	}
	
	@RequestMapping(value="/removeFile", method=RequestMethod.POST)
	public @ResponseBody String removeFile(String moduleId, String name, HttpSession session){
		String sessionRootPath=session.getServletContext().getRealPath("/");
		Module module=moduleRepository.getModuleByModuleId(moduleId);
		File savingFolder=prepareAndGetAttachmentSavingFolder(sessionRootPath, module);
		for(File file:savingFolder.listFiles()){
			if(file.getName().equals(name)){
				file.delete();
				break;
			}
		}
		module.removeAttachmentPath(name);
		moduleRepository.updateModule(module);
		return "";
	}
	
	@RequestMapping(value="/downloadFile", method=RequestMethod.GET)
	public String downloadFile(HttpServletResponse response,HttpSession session, @RequestParam String id, @RequestParam String name){
		Module module=moduleRepository.getModuleByModuleId(id);
		if(module!=null){
			File savingFolder=prepareAndGetAttachmentSavingFolder(session.getServletContext().getRealPath("/"), module);
			String filePath=preparePath(savingFolder.getAbsolutePath())+name;
			BufferedInputStream bis=null;
			OutputStream os=null;
			try {
				bis=new BufferedInputStream(new FileInputStream(new File(filePath)));
				byte[] bytes=new byte[bis.available()];
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.addHeader("Content-Disposition", "attachment;fileName="+new String(name.getBytes("utf8"),"iso-8859-1"));
				os=response.getOutputStream();
				bis.read(bytes);
				os.write(bytes);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(os!=null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
	private File prepareAndGetAttachmentSavingFolder(String sessionRootPath, Module module){
		String savingFolderPath=preparePath(prepareAndGetAttachmentRootFolder(sessionRootPath).getAbsolutePath());
		List<String> idList=new ArrayList<String>();
		fillIdList(module, idList);
		for(int i=idList.size()-1;i>=0;--i){
			savingFolderPath+=idList.get(i)+File.separator;
		}
		File savingFolder=new File(savingFolderPath);
		if(!savingFolder.isDirectory()){
			savingFolder.mkdirs();
		}
		return savingFolder;
	}
	
	private File parepareAndGetAttachmentSavingFile(String sessionRootPath, String fileName, Module module){
		String savingFilePath=prepareAndGetAttachmentSavingFolder(sessionRootPath, module).getAbsolutePath();
		savingFilePath=preparePath(savingFilePath);
		savingFilePath+=fileName;
		return new File(savingFilePath);
	}
	
	private void fillIdList(Module module, List<String> idList){
		if(module!=null){
			idList.add(module.getId());
			String parentId=module.getParentModuleId();
			if(parentId!=null && (!parentId.isEmpty())){
				Module parentModule=moduleRepository.getModuleByModuleId(parentId);
				fillIdList(parentModule,idList);
			}
		}
	}
	
	private File prepareAndGetAttachmentRootFolder(String sessionRootPath){
		sessionRootPath=preparePath(sessionRootPath);
		String path=sessionRootPath+"AttachmentsFolder/";
		File file=new File(path);
		if(!file.isDirectory()){
			file.mkdirs();
		}
		return file;
	}
	
	private String preparePath(String path){
		return (path.endsWith(File.separator)?path:(path+File.separator));
	}
}
