package cn.org.chtf.card;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import cn.hutool.core.lang.Console;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.online.service.OnlineService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.SMSUtil;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WebFileUtil;
import cn.org.chtf.card.support.util.QRCode.QRCodeUtil;
import cn.org.chtf.card.support.util.http.HttpUtil;
import cn.org.chtf.card.support.util.tencentyun.TencentSMSUtil;

@RunWith(SpringRunner.class)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 3600, redisNamespace = "exhibition")
@SpringBootTest
public class ExhibitionApplicationTests {
	@Resource
    private SMSUtil sMSUtil;
	
	@Autowired
    private OnlineService onlineService;
	
	@Autowired
    private CommonService commonService;
	
	@Autowired
    private SysSmsTemplateService sysSmsTemplateService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}
	@Test
	public void makePassword() {
		String password = CryptographyUtil.md5("111111","shixing51");
		Console.log(password);
	}
	@Test
	public void testSMS() throws Exception {
//		String[] arr = {"18618126110","18610829129"};
//		HttpResult hr = sMSUtil.sendSMS(arr, "您好，哈洽会开幕日期临近，请贵方及时向搭建商确认一下贵公司展台搭建的进度，以确保展台搭建能够如期完成。哈洽会办公室0451-82340100");
//		Console.log(hr.getCode()+"-");
//		Console.log(hr.getBody());
	}
	public static void main(String[] args) throws Exception {
//		String[] arr = {"13321116255"};
//		SMSUtil.sendSMS(arr, "ceshi");
	}
	@Test
	public void test() throws Exception {
		
		//boolean isok = sysSmsTemplateService.sendRegistValidateSMS("23423","18631622520",1);
		 
		//System.out.println("https://card.hljlbh.org.cn/acs?t="+CryptographyUtil.encrypt("20010050662", Charset.forName("utf8"), "lbh@MaoC"));
		/*
		List<Map<String,Object>> list = onlineService.GetHaoMa();
		for(int j=0;j<list.size();j++){
			String hao = list.get(j).get("secretkey").toString();
			String mi = "https://card.hljlbh.org.cn/acs?t="+CryptographyUtil.encrypt(hao, Charset.forName("utf8"), "lbh@MaoC");
			onlineService.updateZT(hao,mi);
			System.out.println(j);
		}
		String temp = "20010023898,20010023897,20010023896,20010023895,20010023894,20010023893,20010023892,20010023891,20010023890,20010023889";
		String[] strArgs = temp.split(",");
		for(int j=0;j<strArgs.length;j++){
			String hao = strArgs[j];
			String mi = "https://card.hljlbh.org.cn/acs?t="+CryptographyUtil.encrypt(hao, Charset.forName("utf8"), "lbh@MaoC");
			//new HttpUtil().doGet("http://localhost:7080/common/createQrCode?value="+mi+"&logo=");
			QRCodeUtil.encode1(mi,"",hao);
			//onlineService.updateZT(hao,mi);
			System.out.println(j);
		}*/

		//WebFileUtil.ImageToBase64("./static/upload/1.png");
		//System.out.println("2020-10-03 10:47".compareTo("2020-10-06 11:05"));
		//Console.log(CryptographyUtil.decrypt("D47C1CC6CE8ACCA147C53AEED6C1E8A8", Charset.forName("utf8"), "lbh@MaoC"));
		
		//System.out.println(RedisKeyUtil.getKey("sys_receipt_code", "secretkey", "001"));
		/*for(int j=0;j<2;j++){
			System.out.println(SeqGenerator("2001",7));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
        Date date = new Date();
        System.out.println(sdf.format(date));
		*/
		//ShuangChuanWenJian("./static/upload/file/2020-09-30/4acf21f5-95a6-4c3f-9893-19a1dcd714c6.mp4");
		
		Map<String,Object> map = new HashMap<String,Object>();
		//下载文件
		/*map.put("type", 1);
		List<Map<String,Object>> list = commonService.GetPersonPicForTest(map);
		for(int j=0;j<list.size();j++){
			String imagepath = list.get(j).get("imagepath").toString();
			String netpath = "http://card.hljlbh.org.cn"+imagepath;
			String filename = list.get(j).get("filename").toString();
			String path = imagepath.replace(filename, "").replace("/", "\\");
			download(netpath, filename,"d:\\image3"+path,"");  
			System.out.println(j);
		}*/
		
		/*
		map.put("type", 3);
		List<Map<String,Object>> list = commonService.GetPersonPicForTest(map);
		int iCount=0;
		for(int j=0;j<list.size();j++){
			String imagepath = list.get(j).get("imagepath").toString();
			String netpath = "http://card.hljlbh.org.cn"+imagepath;
			String newfilename = list.get(j).get("newfilename").toString();
			String newpath = "d:\\mark";
			download(netpath, newfilename,newpath,"");  
			System.out.println(j);
		
		}*/
		//检测大小，复制
		/*map.put("type", 2);
		List<Map<String,Object>> list = commonService.GetPersonPicForTest(map);
		int iCount=0;
		for(int j=0;j<list.size();j++){
			String imagepath = list.get(j).get("imagepath").toString();
			String filetype = getMimeType(imagepath);
			String filename = list.get(j).get("filename").toString();
			String newpath = list.get(j).get("newpath").toString();
			if("image/png".equals(filetype)){
				resizeImage(imagepath,newpath,filename,600,749);
				File file = new File(imagepath);
				file.delete();
				iCount++;
				System.out.println(iCount);
			}
			try{
				
				System.out.println(imagepath);		
				String filename = list.get(j).get("filename").toString();
				String newpath = list.get(j).get("newpath").toString();
				int iwidth = getPixel(imagepath);
				if(iwidth<600){
					resizeImage(imagepath,newpath,filename,600,749);
					File file = new File(imagepath);
					file.delete();
				}				
			}
			catch(Exception ee){
				FileOutputStream fileOutputStream = null;
			    File file = new File("./log/card-photo.txt");
			    if(file.exists()){
			        //判断文件是否存在，如果不存在就新建一个txt
			        file.createNewFile();
			    }
			    fileOutputStream = new FileOutputStream(file);
			    fileOutputStream.write((imagepath+"\r\n").getBytes());
			    fileOutputStream.flush();
			    fileOutputStream.close();
			}
			//System.out.println(j);
		}*/
		//changeSize(600,749,"d:\\data\\4d07558f-fde7-4954-86b7-28ffb2282931.jpg");
		
		//compressPictureByQality(new File("d:\\data\\ec9cf1ff-c4e6-4ae4-8762-5f45d3e1bfda.jpg"),1,"jpg");
		//turnTo();
		//getPixel("d:\\data\\ec9cf1ff-c4e6-4ae4-8762-5f45d3e1bfda.jpg");
		//getPixel("d:\\data\\4d07558f-fde7-4954-86b7-28ffb2282931.jpg");
		//resizeImage("d:/image/renyuan/7/UploadImages/7/dby_q_nkzj/zj193173.jpg","d:/image1/renyuan/7/UploadImages/7/dby_q_nkzj/","zj193173.jpg",600,749);
		
		//traverseFolder2("./static/upload/picture/");
		
		List<Map<String,Object>> list = commonService.getcompanybooth();
		
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("YYZJSH");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(
						String.valueOf(list.get(j).get("boothname")));
				row.createCell(2).setCellValue(
						String.valueOf(list.get(j).get("companyname")));				
			}
			String fileName = "RYZJSH.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);
		
		
	}
	
	// 创建表头
		private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet,
				int totalRow) {
			// 合并单元格
			// 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
			// CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 0);
			// sheet.addMergedRegion(region1);
			HSSFRow row = sheet.createRow(0);
			// 设置行高
			row.setHeightInPoints(18);
			// 设置列宽度
			sheet.setColumnWidth(0, 30 * 256);
			// 设置为居中加粗
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFCell cell;
			cell = row.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);
			cell = row.createCell(2);
			cell.setCellValue("电话");
			cell.setCellStyle(style);
			cell = row.createCell(3);			
		}

		// 生成excel文件
		public void buildExcelFile(String filename, HSSFWorkbook workbook)
				throws Exception {
			String filePath = "./static/excel/";
			File targetFile = new File(filePath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(filePath + filename);
			workbook.write(fos);
			fos.flush();
			fos.close();
		}
	
	public static List<File> traverseFolder2(String path) {
        List<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());    
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        String filename = file2.getName();
                        String[] strArgs = filename.split("-");
                        String NewFileName = strArgs[2]+"-"+strArgs[0]+"-"+strArgs[3]+"-"+strArgs[1]+"-"+strArgs[4];//1,3,0,2,4
                        File endFile=new File(file2.getAbsolutePath().replace(filename, NewFileName));
                        String[] newnewnames = NewFileName.split("-");
                        String newnew = newnewnames[1]+"-"+newnewnames[3]+"-"+newnewnames[0]+"-"+newnewnames[2]+"-"+newnewnames[4];//1,3,0,2,4
                        file2.renameTo(endFile);
                        System.out.println("文件:" + filename+" "+NewFileName+"  "+newnew);
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        return fileList;
    }
	
	/**
     * 
     * @param srcPath 原图片路径 
     * @param desPath  转换大小后图片路径 
     * @param width   转换后图片宽度 
     * @param height  转换后图片高度 
     */  
    public static void resizeImage(String srcPath, String desPath, String filename,  
            int width, int height) throws IOException {  

        File srcFile = new File(srcPath);  
        Image srcImg = ImageIO.read(srcFile);  
        BufferedImage buffImg = null;  
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //使用TYPE_INT_RGB修改的图片会变色 
        buffImg.getGraphics().drawImage(  
                srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,  
                0, null);  
        File sf=new File(desPath);  
        if(!sf.exists()){  
            sf.mkdirs();  
        }  
        ImageIO.write(buffImg, "JPEG", new File(desPath+"/"+filename));  
    }  
	
	//获取图片像素
	public static int getPixel(String filePath){
        File file = new File(filePath);
        BufferedImage bi = null;
        try {
        	bi = ImageIO.read(file);
        } catch (Exception e) {
        	return 300;
        }
        int width = bi.getWidth(); // 像素
        int height = bi.getHeight(); // 像素
        return width;
    }
	
	public static void turnTo(){
		BufferedImage bufferedImage;

	    try {

	      //1.读取图片
	      bufferedImage = ImageIO.read(new File("D:\\data\\ec9cf1ff-c4e6-4ae4-8762-5f45d3e1bfda.jpg"));

	      //2.创建一个空白大小相同的RGB背景
	      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
	            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
	      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

	      //3.再次写入的时候以jpeg图片格式
	      ImageIO.write(newBufferedImage, "jpg", new File("D:\\2.jpg"));

	      System.out.println("成功将png格式图片转换为jpg格式");

	    } catch (IOException e) {

	      e.printStackTrace();

	    }
	}
	
	/**
     * 改变图片 像素
     *
     * @param file
     * @param qality 参数qality是取值0~1范围内  清晰程度  数值越小分辨率越低
     * @param imageType 图片写出类型 比如 jpg
     * @return
     * @throws IOException
     */
    public static File compressPictureByQality(File file, float qality,String imageType) throws IOException {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        System.out.println("开始设定压缩图片参数");
        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName(imageType).next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(qality);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(file).getColorModel();// ColorModel.getRGBdefault();
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                colorModel, colorModel.createCompatibleSampleModel(600, 749)));
        System.out.println("结束设定压缩图片参数");
        if (!file.exists()) {
        	System.out.println("Not Found Img File,文件不存在");
            throw new FileNotFoundException("Not Found Img File,文件不存在");
        } else {
            System.out.println("图片转换前大小" + file.length() + "字节");
            src = ImageIO.read(file);
            out = new FileOutputStream(file);
            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
            out.flush();
            out.close();
            System.out.println("图片转换后大小" + file.length() + "字节");           
            
            return file;
        }
    }
	
	
	public boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "jpg", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
	
	
	public static void download(String urlString, String filename,String savePath, String NewFileName) throws Exception {  
        // 构造URL  
        URL url = new URL(urlString);  
        // 打开连接  
        URLConnection con = url.openConnection();  
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        // 输入流  
        InputStream is = con.getInputStream();  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       // 获取图片的扩展名
       String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
       // 新的图片文件名 = 编号 +"."图片扩展名
       String newFileName = filename;//NewFileName+ "." + extensionName;
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
        // 开始读取  
        while ((len = is.read(bs)) != -1) {  
          os.write(bs, 0, len);  
        }  
        // 完毕，关闭所有链接  
        os.close();  
        is.close();  
    }  
	
	
	 private static String getMimeType(String filename){
	        try {
	            String mimeType = readType(filename);
	            return String.format("image/%s", mimeType);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 
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
     * 读取文件类型
     * @param filename
     * @return
     * @throws IOException
     */
    private static String readType(String filename) throws IOException {
        
        FileInputStream fis = null;
        try {
            File f = new File(filename);
            if(!f.exists() || f.isDirectory() || f.length()<8) {
                throw new IOException("the file ["+f.getAbsolutePath()+"] is not image !");
            }
            
            fis= new FileInputStream(f);
            byte[] bufHeaders = readInputStreamAt(fis,0,8);
            if(isJPEGHeader(bufHeaders))
            {    
                long skiplength = f.length()-2-8; //第一次读取时已经读了8个byte,因此需要减掉
                byte[] bufFooters = readInputStreamAt(fis, skiplength, 2);
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
	
	
	
	public static void query(String sql) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/db_exhibition?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
							"root", "root");
			// PreparedStatement preparedStatement=
			// connection.prepareStatement(sql);
			Statement st = connection.createStatement();
			st.executeUpdate(sql);

			st.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void WriteFile(String content){
		try{	
	        File file =new File("test_appendfile.txt"); 
	        if(!file.exists()){
	        	file.createNewFile();
	        } 
	        //使用true，即进行append file 
	 
	        FileWriter fileWritter = new FileWriter(file.getName(),true);  
	        fileWritter.write(content); 
	        fileWritter.close(); 
	        System.out.println("finish");
	 
	    }catch(IOException e){	 
	        e.printStackTrace();	 
	    }
	}
	
}
