package cn.org.chtf.card.manage.Decorators.service.impl;

import cn.org.chtf.card.manage.Decorators.dao.DecoratorEbsStadiumManageMapper;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsStadiumManageService;
import cn.org.chtf.card.support.util.WConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 搭建商报馆信息ServiceImpl
 * 
 * @author ggwudivs
 */
@Service
public class DecoratorEbsStadiumManageServiceImpl implements DecoratorEbsStadiumManageService {

	@Autowired
	private DecoratorEbsStadiumManageMapper decoratorEbsStadiumManageDao;

	/**
	 * 查询企业信息列表
	 */
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.list(map);
	}

	/**
	 * 数量企业信息
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.listcount(map);
	}

	@Override
	public int updateStadiumInfo(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.updateStadiumInfo(map);
	}

	@Override
	public Map<String, Object> selectStadiumInfo(Map<String, Object> map) {
		return decoratorEbsStadiumManageDao.selectStadiumInfo(map);
	}

	@Override
	public String downloadAttachment(Map<String, Object> map) {
		FileOutputStream fous = null;
		ZipOutputStream out = null;
		try {
			Map<String, Object> stadiumInfo = decoratorEbsStadiumManageDao.selectStadiumInfo(map);
			String filePath = WConst.FILEROOT;
			String fileName = UUID.randomUUID().toString() + ".zip";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String rootPath = "./";
			String childPath = sdf.format(new Date()) + "/";
			String absolutePath = rootPath + filePath + childPath;
			File targetDir = new File(absolutePath);
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			File targetFile = new File(absolutePath + "/" + fileName);
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}

			String safetyresponsibilitycommit = getValue(stadiumInfo.get("safetyresponsibilitycommit"));
			String fitmentapplication = getValue(stadiumInfo.get("fitmentapplication"));
			String effectdiagram = getValue(stadiumInfo.get("effectdiagram"));
			String constructiondiagram = getValue(stadiumInfo.get("constructiondiagram"));
			String pointdiagram = getValue(stadiumInfo.get("pointdiagram"));
			String circuitdiagram = getValue(stadiumInfo.get("circuitdiagram"));
			String workercertificatecopy = getValue(stadiumInfo.get("workercertificatecopy"));
			String equipmentqualifiedprove = getValue(stadiumInfo.get("equipmentqualifiedprove"));
			String workercasualtyprove = getValue(stadiumInfo.get("workercasualtyprove"));
			List<File> fileList = new ArrayList<>(6);
			if (safetyresponsibilitycommit != null && !"".equals(safetyresponsibilitycommit)) {
				fileList.add(new File("./static" + safetyresponsibilitycommit));
			}
			if (fitmentapplication != null && !"".equals(fitmentapplication)) {
				fileList.add(new File("./static" + fitmentapplication));
			}
			if (effectdiagram != null && !"".equals(effectdiagram)) {
				fileList.add(new File("./static" + effectdiagram));
			}
			if (constructiondiagram != null && !"".equals(constructiondiagram)) {
				fileList.add(new File("./static" + constructiondiagram));
			}
			if (pointdiagram != null && !"".equals(pointdiagram)) {
				fileList.add(new File("./static" + pointdiagram));
			}
			if (circuitdiagram != null && !"".equals(circuitdiagram)) {
				fileList.add(new File("./static" + circuitdiagram));
			}
			if (workercertificatecopy != null && !"".equals(workercertificatecopy)) {
				fileList.add(new File("./static" + workercertificatecopy));
			}
			if (equipmentqualifiedprove != null && !"".equals(equipmentqualifiedprove)) {
				fileList.add(new File("./static" + equipmentqualifiedprove));
			}
			if (workercasualtyprove != null && !"".equals(workercasualtyprove)) {
				fileList.add(new File("./static" + workercasualtyprove));
			}
			fous = new FileOutputStream(targetFile);
			out = new ZipOutputStream(fous);
			for (File file : fileList) {
				if (file.exists()) {
					if (file.isFile()) {
						FileInputStream fis = new FileInputStream(file);
						BufferedInputStream bins = new BufferedInputStream(fis, 512);
						ZipEntry entry = new ZipEntry(file.getName());
						out.putNextEntry(entry);
						// 向压缩文件中输出数据
						int nNumber;
						byte[] buffer = new byte[512];
						while ((nNumber = bins.read(buffer)) != -1) {
							out.write(buffer, 0, nNumber);
						}
						// 关闭创建的流对象
						bins.close();
						fis.close();
					}
				}
			}
			out.flush();
			String zipPath = filePath.replace("/static", "") + childPath + fileName;
			return zipPath;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fous != null) {
				try {
					fous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private String getValue(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}
}
