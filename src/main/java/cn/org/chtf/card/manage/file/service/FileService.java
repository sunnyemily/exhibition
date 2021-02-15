package cn.org.chtf.card.manage.file.service;

import org.springframework.web.multipart.MultipartFile;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.support.util.ResultModel;

public interface FileService {
	public ResultModel updateCKEditorPicture(MultipartFile file);
	public ResultModel updatePicture(MultipartFile file);
	public ResultModel updateFile(MultipartFile file);
	public ResultModel updateCKEditorFile(MultipartFile file);
	public ResultModel getPath();
	public ResultModel deleteFiles(String[] path);
	 public ResultModel updatePictureLimit(MultipartFile file,Integer width,Integer height,Long size);
	public ResultModel uploadVideo(MultipartFile file);
	public boolean uploadTencent(Integer zhujianid, String act,String ZhuJian,
			String localpath);
}
