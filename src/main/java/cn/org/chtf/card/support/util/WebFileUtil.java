package cn.org.chtf.card.support.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;  
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile; 

import com.alibaba.fastjson.JSON;

import cn.org.chtf.card.common.utils.R;
import sun.misc.BASE64Encoder;
public class WebFileUtil {
	  
    /** 
     * title:WebFileUtil.java 
     * description:获取web运行时项目的根路径 
     * time:2017年1月22日 下午10:00:08 
     * author:wushixing 
     * @param request 
     * @return 
     */  
    public static String getSystemRootPath(HttpServletRequest request){  
        String rootPath=request.getServletContext().getRealPath("/");  
        //request.getContextPath();  
        return rootPath;  
    }  
  
    /** 
     * title:WebFileUtil.java 
     * description:下载文件(包括网络文件) 
     * time:2017年1月22日 下午9:59:24 
     * author:wushixing 
     * @param request 
     * @param response 
     * @param url 文件路径 
     * @param fileName 文件名 
     */  
    public static void downloadFile(HttpServletRequest request,HttpServletResponse response,   
            String url,String fileName) {  
  
        String root = getSystemRootPath(request);  
  
        //用给定的fileName与url的后缀名拼成一新的字符串作为新的文件名  
        fileName = fileName + url.substring(url.lastIndexOf("."));  
          
        OutputStream os = null;  
        InputStream fis = null;  
        try {  
            //解决中文 文件名问题  
            fileName = new String(fileName.getBytes(), "iso8859-1");  
            response.setContentType("application/octet-stream");  
            response.addHeader("Content-Disposition", "attachment;filename=\""+ fileName + "\"");  
  
            os=response.getOutputStream();  
            if (url.startsWith("http:")) {  
                URL tmpURL = new URL(url);  
                URLConnection conn = tmpURL.openConnection();  
                fis = conn.getInputStream();  
            } else {  
                fis = new FileInputStream(new File(root + url));  
            }  
  
            byte[] b = new byte[1024];  
            int i = 0;  
  
            while ((i = fis.read(b)) > 0) {  
                os.write(b, 0, i);  
            }  
  
            os.flush();  
        } catch (Exception e) {  
            System.out.println("下载文件发生异常: "+e.getMessage());  
        } finally{  
            try {  
                os.close();  
                fis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
    /** 
     * title:WebFileUtil.java 
     * description:创建文件夹 
     * time:2017年2月6日 下午10:50:17 
     * author:wushixing 
     * @param dFile 
     */  
    public static void createFold(File file) {  
        if (!file.exists()) file.mkdirs();  
    }  
      
    /** 
     * title:WebFileUtil.java 
     * description:复制百度编辑器中的图片到指定的文件夹下，并返回图片存储的实际路径 
     * time:2017年2月6日 下午10:58:58 
     * author:wushixing 
     * @param request 
     * @param srcPath 
     * @param floder 
     * @return 
     */  
    public static String copyFileForUeditor(HttpServletRequest request,String srcPath, String folder){  
          
        String newFolder="";  
        String fileName="";  
          
        try {  
            File oldFile=new File(getSystemRootPath(request)+srcPath);  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
              
            newFolder="/files/"+folder+"/"+sdf.format(new Date())+"/";  
            String newFilePath=getSystemRootPath(request)+newFolder;  
            createFold(new File(newFilePath));  
              
            fileName=oldFile.getName();  
            File newFile=new File(newFilePath+fileName);  
            FileCopyUtils.copy(oldFile, newFile);  
            //FileUtils.copyFile(oldFile, saveFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        return newFolder+fileName;  
    }  
      
      
    /** 
     * title:WebFileUtil.java 
     * description:删除给定路径下的文件(包括单独的文件;文件夹) 
     * time:2017年2月6日 下午11:02:12 
     * author:wushixing 
     * @param path 
     * @return 
     * @throws Exception 
     */  
    public static boolean deleteFilePath(String path) throws Exception {    
          
        try {  
            File file = new File(path);  
              
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
            if(!file.isDirectory()){  
                file.delete();  
            }else if(file.isDirectory()){  
                String[] filelist = file.list();  
                  
                for(int i = 0; i < filelist.length; i++) {  
                    File delfile = new File(path + "/" + filelist[i]);  
                    if (!delfile.isDirectory()) {  
                        delfile.delete();  
                    } else if (delfile.isDirectory()) {  
                        deleteFilePath(path + "/" + filelist[i]);  
                    }  
                }  
                  
                file.delete();  
            }  
        } catch (Exception e) {  
        }  
          
        return true;    
    }
    /**
     * 上传文件
     * @param file 文件数据
     * @param filePath 保存路径
     * @param fileName 文件名
     * @throws IOException 
     * @throws Exception 异常
     */
    public static String uploadFile(MultipartFile file, String filePath) throws IOException{
        //String contentType = file.getContentType();
    	String[] fileNameArray = file.getOriginalFilename().split("\\.");
        String fileName = createFileName(fileNameArray[fileNameArray.length-1]);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String rootPath = "./";
        String childPath = sdf.format(new Date())+"/";
        String absolutePath = rootPath+ filePath + childPath;
    	File targetFile = new File(absolutePath);  
        if(!targetFile.exists()){    
        	targetFile.mkdirs();    
        }
        FileOutputStream out = new FileOutputStream(absolutePath+fileName);
        out.write(file.getBytes());
        out.flush();
        out.close();
        String resPath = filePath.replace("/static","")+childPath+fileName;
        System.out.print("上传了文件："+resPath);
        return resPath;
    }
    /**
     * 产生唯一文件名，复杂情况下可考虑同步锁synchronized
     * @param strExtension
     * @return
     */
    public static String createFileName(String strExtension) {

    	String fileName= UUID.randomUUID().toString() +"."+ strExtension; //extension, you can change it.
    	return fileName;
    }
	/**
	 * 拷贝文件夹及其内的文件
	 * @param oldPath
	 * @param newPath
	 * @param isContainLastFolder 是否连同文件夹复制
	 * @throws IOException
	 */
	public static void copyDir(String oldRootPath,String oldPath, String newPath) throws IOException {
		String path = oldPath.replace(oldRootPath, "");
        File file = new File(oldPath);
        String[] filePath = file.list();
        
        if (!(new File(newPath+file.separator+path)).exists()) {
            (new File(newPath+file.separator+path)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
                copyDir(oldRootPath,oldPath  + file.separator  + filePath[i], newPath);
            }
            
            if (new File(oldPath  + file.separator + filePath[i]).isFile()) {
                copyFile(oldRootPath,oldPath + file.separator + filePath[i], newPath);
            }
            
        }
    }
	/**
	 * 拷贝文件
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyFile(String oldRootPath,String oldPath, String newPath) throws IOException {
		System.out.println(oldRootPath);
		System.out.println(oldPath);
		System.out.println(newPath);
		String pathAndFile = oldPath.replace(oldRootPath, "");
		String[] arrayPath =  pathAndFile.split("\\\\");
		String path = pathAndFile.replace(arrayPath[arrayPath.length-1], "");
		if(!path.equals("")&&!(new File(newPath + File.separator + path)).exists()) {
			(new File(newPath+File.separator+path)).mkdirs();
		}
        FileChannel inputChannel = null;    
        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(oldPath).getChannel();
	        outputChannel = new FileOutputStream(newPath+File.separator+pathAndFile).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } finally {
	        inputChannel.close();
	        outputChannel.close();
	    }
    }

	public static R uploadVideo(MultipartFile file, String pictureroot) {
		String url=null;
		try {
			String f = file.getOriginalFilename(); //获取文件名
			String[] fileNameArray = file.getOriginalFilename().split("\\."); //获取文件后缀
			String suffix = fileNameArray[fileNameArray.length-1];					
			String filenameW = UUID.randomUUID().toString(); 		
			String folderUrl = pictureroot+filenameW; //文件夹路径
			String fileName = filenameW+"."+suffix;   //文件名带后缀
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        String rootPath = "./";
	        String childPath = sdf.format(new Date())+"/"+filenameW+"/";
	        String absolutePath = rootPath + pictureroot + childPath;			
			
			File fileFolder = new File(absolutePath);
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(absolutePath + "/" + fileName);
	        out.write(file.getBytes());
	        out.flush();
	        out.close();
			//mp4转m3u8
			boolean b = ConvertM3U8.convertOss(absolutePath + "/", fileName);
			//if (!b){
			//	return R.error("上传失败!系统转码异常!");
			//}	
	        
	        String filemk = absolutePath+fileName;
	        File fileMP4 = new File(filemk);
	        fileMP4.delete();	
	        url=pictureroot.replace("/static", "") + childPath + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			R.error("上传异常");
		}
		return R.ok().put("url", url);
	}
	
	/**
     * 将二进制转换成文件保存
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 
     *      1：保存正常
     *      0：保存失败
     */
    public static String saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        
        if(instreams != null){
            try {
            	String rootPath = "./";
                File file=new File(rootPath+imgPath,imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();                
            } catch (Exception e) {                
                e.printStackTrace();
            } finally {
            }
        }
        return imgPath.replace("/static", "") + imgName;
    }
    
    public static String downloadMiniCode(Map<String,Object> paramMap, String token){       
        String filepath = WConst.QRCODEROOT;
        String filename = createFileName("png");       
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String rootPath = "./";
        String childPath = sdf.format(new Date())+"/";
        String absolutePath = rootPath + filepath + childPath;			
		
		File fileFolder = new File(absolutePath);
		if (!fileFolder.exists()) {
			fileFolder.mkdirs();
		}
        try
        {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(JSON.toJSONString(paramMap));
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File(absolutePath+"/"+filename));
            int len;
            //设置缓冲写入
            byte[] arr = new byte[2048];
            while ((len = bis.read(arr)) != -1)
            {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return filepath.replace("/static", "") + childPath + filename;
    }
    /**
     * 本地图片转换Base64的方法
    *
    * @param imgPath     
    */
    
   public static String ImageToBase64(String imgPath) {
       byte[] data = null;
       // 读取图片字节数组
       try {
           File file=new File(imgPath); 

           InputStream in = new FileInputStream(file);
           
           data = new byte[in.available()];
           in.read(data);
           in.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       // 对字节数组Base64编码
       BASE64Encoder encoder = new BASE64Encoder();
       // 返回Base64编码过的字节数组字符串

//       System.out.println("数组总长度：");
       //切片大小
//       int size = 5000;
//       //数据总长度
//       int length = data.length;
//       //计算分片数量
//       int count = length / size;       
//       if(length % size !=0) {
//    	   count = count + 1;
//       }
       //
//       for(int i = 0;i < count;i++) {
//    	   if(i == count-1) {
//    		   size = length % size;
//    	   }
//    	   byte[] currentData = new byte[size];
//    	   System.arraycopy(data, i*size, currentData, 0, size);
//
//           System.out.println("第"+(i+1)+"片:");
//           System.out.println("第"+(i+1)+"片:" + encoder.encode(Objects.requireNonNull(currentData)));
//       }
       try {
           FileOutputStream fileOutputStream = null;
           File file = new File("./test.txt");
           if(file.exists()){
               //判断文件是否存在，如果不存在就新建一个txt
               file.createNewFile();
           }
           fileOutputStream = new FileOutputStream(file);
           fileOutputStream.write(encoder.encode(Objects.requireNonNull(data)).getBytes());
           fileOutputStream.flush();
           fileOutputStream.close();
       }
       catch(Exception e) {
    	   e.printStackTrace();
       }
       return encoder.encode(Objects.requireNonNull(data));
   }
   public static String turnToJPGBASE64(String imgPath) {
	   
	    try {
	      // read image file
	    	BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
	    	BufferedImage newBufferedImage = new BufferedImage(
	          bufferedImage.getWidth(), bufferedImage.getHeight(),
	          BufferedImage.TYPE_INT_RGB);
	      // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
	    	newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
	          Color.WHITE, null);

	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	ImageIO.write(newBufferedImage, "jpg", out);
	    	byte[] data = out.toByteArray();
	    	BASE64Encoder encoder = new BASE64Encoder();
	    	System.out.println("Done");
	    	return encoder.encode(Objects.requireNonNull(data));
	      // write to jpeg file
	      //ImageIO.write(newBufferedImage, "jpg", new File("./static/2.jpg"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    	System.out.println("Base64转换失败");
			return "";
	      }
   }
    
}
