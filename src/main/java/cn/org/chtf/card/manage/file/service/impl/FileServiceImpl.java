package cn.org.chtf.card.manage.file.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.common.dao.CommonMapper;
import cn.org.chtf.card.manage.file.service.FileService;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;
import cn.org.chtf.card.support.util.WebFileUtil;

@Service("FileServiceImpl")
public class FileServiceImpl implements FileService {

	protected List paths;
	
	@Autowired
    private CommonMapper commonDao;

	public ResultModel updateCKEditorPicture(MultipartFile file) {
		String contentType = file.getContentType();
		if (!(contentType.equals("image/pjpeg")
				|| contentType.equals("image/jpeg")
				|| contentType.equals("image/png")
				|| contentType.equals("image/x-png")
				|| contentType.equals("image/gif") || contentType
					.equals("image/bmp"))) {
			return new ResultModel(WConst.ERROR, "上传的图片包含不支持的格式", null);
		}
		try {
			String picturePath = WebFileUtil.uploadFile(file,
					WConst.CKEDITORROOT);

			return new ResultModel(WConst.SUCCESS, WConst.SAVED, picturePath);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
	}

	public ResultModel updateCKEditorFile(MultipartFile file) {
		String contentType = file.getContentType();
		// if(!(contentType.equals("application/octet-stream") ||
		// contentType.equals("application/zip")||contentType.equals("application/pdf")
		// || contentType.equals("text/plain") ||
		// contentType.equals("application/msword"))) {
		// return new ResultModel(WConst.ERROR,"上传的文件包含不支持的格式",null);
		// }
		try {
			String picturePath = WebFileUtil.uploadFile(file,
					WConst.CKEDITORROOT);

			return new ResultModel(WConst.SUCCESS, WConst.SAVED, picturePath);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
	}

	public ResultModel updatePicture(MultipartFile file) {
		String contentType = file.getContentType();
		if (!(contentType.equals("image/pjpeg")
				|| contentType.equals("image/jpeg")
				|| contentType.equals("image/png")
				|| contentType.equals("image/x-png")
				|| contentType.equals("image/gif") || contentType
					.equals("image/bmp"))) {
			return new ResultModel(WConst.ERROR, "上传的图片包含不支持的格式", null);
		}
//		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//		String type = getMimeType(file);
//
//		System.out.println(type);
//		
//		if(!contentType.contains(suffix)) {
//			System.out.println(contentType);
//			System.out.println(suffix);
//			return new ResultModel(WConst.ERROR, "图片格式于后缀名不符，请用专业制图软件转换后重新上传。", null);
//		}
		try {
			String picturePath = WebFileUtil.uploadFile(file,
					WConst.PICTUREROOT);

			return new ResultModel(WConst.SUCCESS, WConst.SAVED, picturePath);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e);
		}
	}

	public ResultModel updatePictureLimit(MultipartFile file, Integer width,
			Integer height, Long size) {

		String contentType = file.getContentType();
		if (!(contentType.equals("image/pjpeg")
				|| contentType.equals("image/jpeg")
				|| contentType.equals("image/png")
				|| contentType.equals("image/x-png")
				|| contentType.equals("image/gif") || contentType
					.equals("image/bmp"))) {
			return new ResultModel(WConst.ERROR, "上传的图片包含不支持的格式", null);
		}
		try {
			BufferedImage sourceImg = ImageIO.read(file.getInputStream());
			Integer w = sourceImg.getWidth();
			Integer h = sourceImg.getHeight();
			if ((width != null && !w.equals(width))
					|| (h != null && !h.equals(height))) {
				return new ResultModel(WConst.ERROR, "图片尺寸必须为" + width + "*"
						+ height + "(宽*高)像素。", "w:" + w + ",width:" + width);
			}
			Long s = file.getSize();
			if (s != null && s > size * 1204) {
				return new ResultModel(WConst.ERROR, "图片大小必须小于" + size + "kb。",
						"s" + s);
			}

		} catch (IOException e1) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e1);
		}

		try {
			String picturePath = WebFileUtil.uploadFile(file,
					WConst.PICTUREROOT);

			return new ResultModel(WConst.SUCCESS, WConst.SAVED, picturePath);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e);
		}
	}

	public ResultModel updateFile(MultipartFile file) {
		String contentType = file.getContentType();
		// if(!(contentType.equals("application/octet-stream") ||
		// contentType.equals("application/zip")||contentType.equals("application/pdf")
		// || contentType.equals("text/plain") ||
		// contentType.equals("audio/mp3"))) {
		// return new ResultModel(WConst.ERROR,"上传的文件包含不支持的格式",null);
		// }
		try {
			String picturePath = WebFileUtil.uploadFile(file, WConst.FILEROOT);

			return new ResultModel(WConst.SUCCESS, WConst.SAVED, picturePath);
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR,
					e.getMessage());
		}
	}

	public ResultModel getPath() {
		ResultModel result = new ResultModel();
		Path path = Paths.get(WConst.BROWSERROOT);
		paths = new ArrayList();
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir,
						BasicFileAttributes attrs) throws IOException {
					String strDir = dir.toString().replaceAll("\\\\", "/");
					String[] arrayDir = strDir.split("/");
					Map o = new HashMap();
					o.put("text", arrayDir[arrayDir.length - 1]);
					o.put("path",
							strDir.replace("/virtualhost/web/static/", ""));
					o.put("children", new ArrayList());
					o.put("files", new ArrayList());
					if (arrayDir.length == 5) {
						paths.add(o);
					} else {
						List child = paths;
						for (int i = 0; i < arrayDir.length - 5; i++) {
							child = (List) ((Map) child.get(child.size() - 1))
									.get("children");// 去除最后一个Map的children作为List返回，等待下次遍历取值
							if (i == arrayDir.length - 6) {// 遍历到最后一层，将新对象put进子集中
								child.add(o);
							}
						}
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					String strDir = file.toString().replaceAll("\\\\", "/");
					String[] arrayDir = strDir.split("/");
					// 1.\static\\upload\file
					Map o = new HashMap();
					o.put("text", Paths.get(strDir).getFileName().toString());
					o.put("path",
							strDir.replace("/virtualhost/web/static/", ""));
					if (arrayDir.length == 5) {
						paths.add(o);
					} else {
						List child = paths;
						for (int i = 0; i < arrayDir.length - 5; i++) {
							if (i == arrayDir.length - 6) {// 遍历到最后一层，将新对象put进子集中
								((List) ((Map) child.get(child.size() - 1))
										.get("files")).add(o);
							} else {
								child = (List) ((Map) child.get(child.size() - 1))
										.get("children");// 去除最后一个Map的children作为List返回，等待下次遍历取值
							}
						}
					}
					return FileVisitResult.CONTINUE; // 没找到继续找
				}

			});
		} catch (IOException e1) {
			e1.printStackTrace();
			result.setStatus(WConst.ERROR);
			result.setMsg(e1.getMessage());
			result.setResult(e1);
		}
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		result.setResult(paths);
		return result;
	}

	public ResultModel deleteFiles(String[] path) {
		ResultModel result = new ResultModel();
		try {
			for (String p : path) {
				Files.deleteIfExists(Paths.get(WConst.BROWSERROOT
						+ p.replace("upload", "").replaceAll("\\\\", "/")));
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.setStatus(WConst.ERROR);
			result.setMsg(e.getMessage());
			result.setResult(e);
		}
		result.setStatus(WConst.SUCCESS);
		result.setMsg(WConst.QUERYSUCCESS);
		result.setResult(paths);
		return result;
	}

	@Override
	public ResultModel uploadVideo(MultipartFile file) {
		try {
			R r = WebFileUtil.uploadVideo(file, WConst.VIDEOROOT);
			return new ResultModel(WConst.SUCCESS, WConst.SAVED, r.get("url"));
		} catch (Exception e) {
			return new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e);
		}
	}

	@Override
	public boolean uploadTencent(Integer zhujianid, String act,String ZhuJian, String localpath) {
		if(localpath==null || "".equals(localpath)){
			return false;
		}
		//验证文件路径是否已存在
		Map<String,Object> para = new HashMap<String,Object>();
		para.put("zhujianid", zhujianid);
		para.put("act", act);
		para.put("localpath", localpath);
		para.put("zhujian", ZhuJian);
		int iRet = commonDao.GetTencentFileCount(para);
		if(iRet==0){//不存在，增加记录并上传文件到腾讯云			
			//String fileID = ShuangChuanWenJian(localpath);
			//para.put("fileid", fileID);
			commonDao.AddTencentFile(para);
		}
		return true;
	}
	//以下方法为获取真实文件类型的方法
	private static boolean isBMP(byte[] buf){
        byte[] markBuf = "BM".getBytes();  //BMP图片文件的前两个字节
        return compare(buf, markBuf);
    }
    
    private static boolean isICON(byte[] buf) {
        byte[] markBuf = {0, 0, 1, 0, 1, 0, 32, 32};
        return compare(buf, markBuf);
    }
    private static boolean isWEBP(byte[] buf) {
        byte[] markBuf = "RIFF".getBytes(); //WebP图片识别符
        return compare(buf, markBuf);
    }

    private static boolean isGIF(byte[] buf) {
        
        byte[] markBuf = "GIF89a".getBytes(); //GIF识别符
        if(compare(buf, markBuf))
        {
            return true;
        }
        markBuf = "GIF87a".getBytes(); //GIF识别符
        if(compare(buf, markBuf))
        {
            return true;
        }
        return false;
    }

    
    private static boolean isPNG(byte[] buf) {
        
        byte[] markBuf = {(byte) 0x89,0x50,0x4E,0x47,0x0D,0x0A,0x1A,0x0A}; //PNG识别符
         // new String(buf).indexOf("PNG")>0 //也可以使用这种方式
        return compare(buf, markBuf);
    }

    private static boolean isJPEGHeader(byte[] buf) {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd8}; //JPEG开始符
        
        return compare(buf, markBuf);
    }
    
    private static boolean isJPEGFooter(byte[] buf)//JPEG结束符
    {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd9}; 
        return compare(buf, markBuf);
    }

/**
     * 获取文件的mimeType
     * @param filename
     * @return
     */
    private static String getMimeType(MultipartFile file){
        try {
            String mimeType = readType(file);
            return String.format("image/%s", mimeType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件类型
     * @param filename
     * @return
     * @throws IOException
     */
    private static String readType(MultipartFile file) throws IOException {
        FileInputStream fis = null;
        try {
        	byte[] bytes = file.getBytes();
        	byte[] bufHeaders = new byte[8];
            System.arraycopy(bytes, 0, bufHeaders, 0, 8);
            if(isJPEGHeader(bufHeaders))
            {   byte[] bufFooters = new byte[2];
            	System.arraycopy(bytes, bytes.length-2, bufFooters, 0, 2);
                if(isJPEGFooter(bufFooters))
                {
                    return "jpeg";
                }
            }
            if(isPNG(bufHeaders))
            {
                return "png";
            }
            if(isGIF(bufHeaders)){
                
                return "gif";
            }
            if(isWEBP(bufHeaders))
            {
                return "webp";
            }
            if(isBMP(bufHeaders))
            {
                return "bmp";
            }
            if(isICON(bufHeaders))
            {
                return "ico";
            }
            throw new IOException("the image's format is unkown!");
            
        } catch (FileNotFoundException e) {
            throw e;
        }finally{
            try {
                if(fis!=null) fis.close();
            } catch (Exception e) {
            }
        }
        
    }

    
    /**
     * 标示一致性比较
     * @param buf  待检测标示
     * @param markBuf 标识符字节数组
     * @return 返回false标示标示不匹配
     */
    private static boolean compare(byte[] buf, byte[] markBuf) {
        for (int i = 0; i < markBuf.length; i++) {
            byte b = markBuf[i];
            byte a = buf[i];
            
            if(a!=b){
                return false;
            }
        }
        return true;
    }
    /**
     * 
     * @param fis 输入流对象
     * @param skiplength 跳过位置长度
     * @param length 要读取的长度
     * @return 字节数组
     * @throws IOException
     */
    private static byte[] readInputStreamAt(FileInputStream fis, long skiplength, int length) throws IOException
    {
        byte[] buf = new byte[length];
        fis.skip(skiplength);  //
        int read = fis.read(buf,0,length);
        return buf;
    }
    
}
