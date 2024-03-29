package cn.org.chtf.card.manage.Decorators.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.Decorators.dao.DecoratorEbsDecoratorManageMapper;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsDecoratorManageService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsCompanyinfoMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.membersession.pojo.MemberSession;
import cn.org.chtf.card.support.util.WConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 企业信息ServiceImpl
 *
 * @author ggwudivs
 */
@Service
public class DecoratorEbsDecoratorManageServiceImpl implements DecoratorEbsDecoratorManageService {

    @Autowired
    private DecoratorEbsDecoratorManageMapper decoratorEbsDecoratorManageDao;

    @Autowired
    private MemberSessionMapper memberSessionMapper;

    @Autowired
    private EbsCompanyinfoMapper ebsCompanyinfoDao;

    /**
     * 查询企业信息列表
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.list(map);
    }

    /**
     * 数量企业信息
     */
    @Override
    public int listcount(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.listcount(map);
    }

    @Override
    public int updateCompanyInfo(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.updateCompanyInfo(map);
    }

    @Override
    public Map<String, Object> selectCompanyInfo(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.selectCompanyInfo(map);
    }

    @Override
    public String downloadAttachment(Map<String, Object> map) {
        FileOutputStream fous = null;
        ZipOutputStream out = null;
        try {
            Map<String, Object> companyInfo = decoratorEbsDecoratorManageDao.selectCompanyInfo(map);
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

            String legalpersoncardpath = getValue(companyInfo.get("legalpersoncardpath"));
            String admissionapplicationform = getValue(companyInfo.get("admissionapplicationform"));
            String workshopcertificate = getValue(companyInfo.get("workshopcertificate"));
            String technicalproofdocuments = getValue(companyInfo.get("technicalproofdocuments"));
            String othersupportingdocuments = getValue(companyInfo.get("othersupportingdocuments"));
            String previouscase = getValue(companyInfo.get("previouscase"));
            List<File> fileList = new ArrayList<>(6);
            if (legalpersoncardpath != null && !"".equals(legalpersoncardpath)) {
                fileList.add(new File("./static" + legalpersoncardpath));
            }
            if (admissionapplicationform != null && !"".equals(admissionapplicationform)) {
                fileList.add(new File("./static" + admissionapplicationform));
            }
            if (workshopcertificate != null && !"".equals(workshopcertificate)) {
                fileList.add(new File("./static" + workshopcertificate));
            }
            if (technicalproofdocuments != null && !"".equals(technicalproofdocuments)) {
                fileList.add(new File("./static" + technicalproofdocuments));
            }
            if (othersupportingdocuments != null && !"".equals(othersupportingdocuments)) {
                fileList.add(new File("./static" + othersupportingdocuments));
            }
            if (previouscase != null && !"".equals(previouscase)) {
                fileList.add(new File("./static" + previouscase));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R UseScatteredExhibitors(Map<String, Object> map) {
        int itotal = 0;
        int isuccess = 0;
        int ipass = 0;
        try {
            String newSession = String.valueOf(map.get("newsession"));
            String oldSession = "";
            String isStr = String.valueOf(map.get("isStr"));

            if (isStr != null && !isStr.equals("")) {
                String[] ids = isStr.split(",");
                Map<String, Object> params = new HashMap<String, Object>();
                for (String companyid : ids) {
                    if (companyid != null && !companyid.equals("")) {
                        itotal++;
                        params.put("companyid", companyid);
                        EbsCompanyinfo eci = ebsCompanyinfoDao.findById(Integer.valueOf(companyid));
                        String qymc = eci.getChinesename();
                        oldSession = eci.getSession();
                        params.put("session", oldSession);
                        List<MemberSession> list = memberSessionMapper.getMemberSessionsByMemberId(params);
                        int MemberId = list.get(0).getMemberId();

                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put("chinesename", qymc);
                        param.put("session", newSession);
                        List<EbsCompanyinfo> lit = ebsCompanyinfoDao.findByMap(param);
                        if (lit.size() == 0) {
                            eci.setSession(newSession);
                            eci.setId(null);
                            ebsCompanyinfoDao.save(eci);
                            MemberSession ms = new MemberSession();
                            ms.setId(Integer.valueOf(newSession));
                            ms.setMemberId(MemberId);
                            ms.setOrgnizationId(eci.getId());
                            memberSessionMapper.insert(ms);
                            isuccess++;
                        } else {
                            Map<String, Object> par = new HashMap<String, Object>();
                            par.put("session", newSession);
                            par.put("memberid", MemberId);
                            par.put("companyid", lit.get(0).getId());
                            List<MemberSession> lits = memberSessionMapper.findByMap(par);
                            if (lits.size() == 0) {
                                MemberSession ms = new MemberSession();
                                ms.setId(Integer.valueOf(newSession));
                                ms.setMemberId(MemberId);
                                ms.setOrgnizationId(lit.get(0).getId());
                                memberSessionMapper.insert(ms);
                            }
                            ipass++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
        }
        String msg = "共选中 " + itotal + " 家企业，本届已存在 " + ipass + " 家，成功提取 " + isuccess + " 家";
        return R.ok().put("code", WConst.SUCCESS).put("msg", msg);
    }

    @Override
    public List<Map<String, Object>> Previouslist(Map<String, Object> map) {
        List<Map<String, Object>> list = decoratorEbsDecoratorManageDao.Previouslist(map);
        Map<String, Object> par = new HashMap<String, Object>();
        par.put("session", map.get("nowsession"));
        for (int j = 0; j < list.size(); j++) {
            par.put("chinesename", list.get(j).get("chinesename").toString());
            List<EbsCompanyinfo> company = ebsCompanyinfoDao.findByMap(par);
            if (company.size() > 0) {
                list.get(j).put("newcompanyid", company.get(0).getId());
            } else {
                list.get(j).put("newcompanyid", 0);
            }
        }
        return list;
    }

    @Override
    public int Previouslistcount(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.Previouslistcount(map);
    }

    @Override
    public Map<String, Object> loadCount(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.loadCount(map);
    }

    @Override
    public List<Map<String, Object>> GetDownLoadInfo(Map<String, Object> map) {
        return decoratorEbsDecoratorManageDao.GetDownLoadInfo(map);
    }

    /**
     * 查询认证过期搭建商数据列表
     */
    @Override
    public List<Map<String, Object>> listApproveExpiredData() {
        List<Map<String, Object>> dataList = decoratorEbsDecoratorManageDao.listApproveExpiredData();
        if (CollectionUtil.isEmpty(dataList)) {
            return new ArrayList<>();
        }
        return dataList;
    }
}
