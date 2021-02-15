package cn.org.chtf.card.manage.file.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.file.service.FileService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@RestController
public class FileController {

	@Resource(name = "FileServiceImpl")
	FileService fileService;
	/**
	 * 上传编辑器图片
	 * @param file 编辑器图片
	 * @return
	 */
	
	@RequestMapping(value={"/manage/uploadEditorPicture","/api/uploadEditorPicture"})
	@ResponseBody 
	public String updateCKEditorPicture(@RequestParam("upload") MultipartFile file,@RequestParam("CKEditorFuncNum") String CKEditorFuncNum) {
		ResultModel result = fileService.updateCKEditorPicture(file);
		String returnScript = "<script>window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+",'{#filePath}');</script>";
		if(result.getStatus()!=WConst.SUCCESS) {
			returnScript = returnScript.replace("{#filePath}", "','"+result.getMsg());
		}
		else {
			returnScript = returnScript.replace("{#filePath}", result.getResult().toString());
		}
        return returnScript;
	}
	/**
	 * 上传编辑器文件
	 * @param file 编辑器文件
	 * @return
	 */
	
	@RequestMapping(value={"/manage/uploadEditorFile","/api/uploadEditorFile"})
	@ResponseBody 
	public String uploadEditorFile(@RequestParam("upload") MultipartFile file,@RequestParam("CKEditorFuncNum") String CKEditorFuncNum) {
		ResultModel result = fileService.updateCKEditorFile(file);
		String returnScript = "<script>window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+",'{#filePath}');</script>";
		if(result.getStatus()!=WConst.SUCCESS) {
			returnScript = returnScript.replace("{#filePath}", "','"+result.getMsg());
		}
		else {
			returnScript = returnScript.replace("{#filePath}", result.getResult().toString());
		}
        return returnScript;
	}
	@RequestMapping(value= {"/manage/uploadPicture","/uploadPicture"})
	public ResultModel updatePicture(@RequestParam("file") MultipartFile file) {
		ResultModel result = fileService.updatePicture(file);
        return result;
	}
	@RequestMapping(value= {"/updatePictureLimit"})
	public ResultModel updatePictureLimit(MultipartFile file,Integer width,Integer height,Long size) {
		ResultModel result = fileService.updatePictureLimit(file,width,height,size);
        return result;
	}
	@RequestMapping(value= {"/manage/uploadFile","/uploadFile"})
	public ResultModel updateFile(@RequestParam("file") MultipartFile file) {
		ResultModel result = fileService.updateFile(file);
        return result;
	}
	@RequestMapping(value="/getPath")
	public ResultModel getPath() {
		return fileService.getPath();
	}
	@RequestMapping(value="/manage/deleteFiles")
	public ResultModel deleteFiles(@RequestParam("path[]") String[] path) {
		System.out.println(path);
		return fileService.deleteFiles(path);
	}
	
	@RequestMapping(value="/manage/uploadVideo")
	public ResultModel uploadVideo(@RequestParam  MultipartFile file) {
		ResultModel result = fileService.uploadVideo(file);
        return result;
	}
	
}
