package cn.org.chtf.card.support.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**
 * mp4转换m3u8工具类
 */
@Configuration
public class ConvertM3U8 {
	@Value("${ffmpegpath}")
	private static String ffmpegpath = "/usr/local/ffmpeg/bin/ffmpeg.exe"; // ffmpeg.exe的目录

	public static boolean convertOss(String folderUrl, String fileName) {
		System.out.println(folderUrl + fileName);
		if (!checkfile(folderUrl + fileName)) {
			System.out.println("文件不存在!");
			return false;
		}

		// 验证文件后缀
		String[] fileNameArray = fileName.split("\\."); //获取文件后缀
		String suffix = fileNameArray[fileNameArray.length-1];
		//String suffix = StringUtils.substringAfter(fileName, ".");
		String fileFullName = fileNameArray[fileNameArray.length-2];
		if (!validFileType(suffix)) {
			return false;
		}

		return processM3U8(folderUrl, fileName, fileFullName);
	}

	/**
	 * 验证上传文件后缀
	 * 
	 * @param type
	 * @return
	 */
	private static boolean validFileType(String type) {
		if ("mp4".equals(type)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否是文件格式
	 * 
	 * @param path
	 * @return
	 */
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		} else {
			return true;
		}
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）

	/**
	 * ffmpeg程序转换m3u8
	 * 
	 * @param folderUrl
	 * @param fileName
	 * @param fileFullName
	 * @return
	 */
	private static boolean processM3U8(String folderUrl, String fileName,
			String fileFullName) {
		// 这里就写入执行语句就可以了
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegpath);
		commend.add("-i");
		commend.add(folderUrl + fileName);
		commend.add("-c:v");
		commend.add("libx264");
		commend.add("-hls_time");
		commend.add("60");
		commend.add("-hls_list_size");
		commend.add("0");
		commend.add("-c:a");
		commend.add("aac");
		commend.add("-strict");
		commend.add("-2");
		commend.add("-f");
		commend.add("hls");
		commend.add(folderUrl + fileFullName + ".m3u8");
		try {
			ProcessBuilder builder = new ProcessBuilder();// java
			builder.command(commend);
			Process p = builder.start();
			int i = doWaitFor(p);
			System.out.println("------>" + i);
			p.destroy();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 监听ffmpeg运行过程
	 * 
	 * @param p
	 * @return
	 */
	public static int doWaitFor(Process p) {
		InputStream in = null;
		InputStream err = null;
		int exitValue = -1; // returned to caller when p is finished
		try {
			System.out.println("comeing");
			in = p.getInputStream();
			err = p.getErrorStream();
			boolean finished = false; // Set to true when p is finished

			while (!finished) {
				try {
					while (in.available() > 0) {
						Character c = new Character((char) in.read());
						System.out.print(c);
					}
					while (err.available() > 0) {
						Character c = new Character((char) err.read());
						System.out.print(c);
					}

					exitValue = p.exitValue();
					finished = true;

				} catch (IllegalThreadStateException e) {
					Thread.currentThread().sleep(500);
				}
			}
		} catch (Exception e) {
			System.err.println("doWaitFor();: unexpected exception - "
					+ e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (err != null) {
				try {
					err.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return exitValue;
	}
}
