package cn.org.chtf.card.manage.system.service.impl;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.manage.system.model.SysSurveyLog;
import cn.org.chtf.card.manage.system.model.SysSurveyResult;
import cn.org.chtf.card.manage.system.service.SysSurveyResultService;
import cn.org.chtf.card.manage.system.dao.SysSurveyLogMapper;
import cn.org.chtf.card.manage.system.dao.SysSurveyResultMapper;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 调查结果表ServiceImpl
 * 
 * @author lm
 */
@Service
public class SysSurveyResultServiceImpl implements SysSurveyResultService {

	@Autowired
	private SysSurveyResultMapper sysSurveyResultDao;

	@Autowired
	private SysSurveyLogMapper sysSurveyLogMapper;

	/**
	 * 查询调查结果表列表
	 */
	@Override
	public List<SysSurveyResult> list(Map<String, Object> map) {
		return sysSurveyResultDao.list(map);
	}

	/**
	 * 数量调查结果表
	 */
	@Override
	public int listcount(Map<String, Object> map) {
		return sysSurveyResultDao.listcount(map);
	}

	/**
	 * 通过surveyid查询单个调查结果表
	 */
	@Override
	public SysSurveyResult findById(Integer surveyid) {
		return sysSurveyResultDao.findById(surveyid);
	}

	/**
	 * 通过map查询单个调查结果表
	 */
	@Override
	public List<SysSurveyResult> findByMap(Map<String, Object> map) {
		return sysSurveyResultDao.findByMap(map);
	}

	/**
	 * 新增调查结果表
	 */
	@Override
	public int save(SysSurveyResult sysSurveyResult) {
		return sysSurveyResultDao.save(sysSurveyResult);
	}

	/**
	 * 修改调查结果表
	 */
	@Override
	public int update(SysSurveyResult sysSurveyResult) {
		return sysSurveyResultDao.update(sysSurveyResult);
	}

	/**
	 * 删除调查结果表
	 */
	@Override
	public int deleteById(Integer surveyid) {
		return sysSurveyResultDao.deleteById(surveyid);
	}

	@Override
	public List<Map<String, Object>> GetQuestionInfoBySurveyID(
			Map<String, Object> map) {
		return sysSurveyResultDao.GetQuestionInfoBySurveyID(map);
	}

	@Override
	public List<Map<String, Object>> GetAnswerInfoByQuestionID(
			Map<String, Object> map) {
		return sysSurveyResultDao.GetAnswerInfoByQuestionID(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveSurveyAnswer(Map<String, Object> map, HttpSession session) {
		try {
			int surveyid = Integer.valueOf(String.valueOf(map.get("surveyid")));
			int companyid = Integer
					.valueOf(String.valueOf(map.get("companyid")));
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("companyid", companyid);
			par.put("surveyid", surveyid);
			sysSurveyLogMapper.deleteLog(par);
			User user = (User) session.getAttribute("user");
			int userid = user.getId();
			// 插入选择类文件
			String chkvalue = String.valueOf(map.get("chkvalue"));
			String[] ids = chkvalue.split(",");
			for (String answerid : ids) {
				if (answerid != null && !answerid.equals("")) {
					String[] answer = answerid.split("\\|");
					SysSurveyResult ssr = new SysSurveyResult();
					ssr.setAnswerid(Integer.valueOf(answer[1]));
					ssr.setQuestionid(Integer.valueOf(answer[0]));
					ssr.setSurveyid(surveyid);
					ssr.setUserid(userid);
					ssr.setCompanyid(companyid);
					ssr.setRemark("");
					sysSurveyResultDao.save(ssr);
				}
			}
			// 插入文本类答案
			String content = String.valueOf(map.get("content"));
			String[] answerids = content.split("@");
			for (String answerinfo : answerids) {
				if (answerinfo != null && !answerinfo.equals("")) {
					String[] array = answerinfo.split("\\|");
					String questionid = array[0];
					String answerid = array[1];
					String remark = array[2];
					SysSurveyResult ssr = new SysSurveyResult();
					ssr.setAnswerid(Integer.valueOf(answerid));
					ssr.setSurveyid(surveyid);
					ssr.setUserid(userid);
					ssr.setCompanyid(companyid);
					ssr.setRemark(remark);
					ssr.setQuestionid(Integer.valueOf(questionid));
					sysSurveyResultDao.save(ssr);
				}
			}
			// 插入日志
			SysSurveyLog ssl = new SysSurveyLog();
			ssl.setAct(String.valueOf(map.get("logtype")));
			ssl.setIsback(Integer.valueOf(String.valueOf(map.get("isback"))));
			ssl.setUserid(userid);
			ssl.setSurveyid(surveyid);
			ssl.setCompanyid(companyid);
			sysSurveyLogMapper.save(ssl);
		} catch (Exception ee) {
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}

	@Override
	public List<Map<String, Object>> GetAnswerTongJiInfoByQuestionID(
			Map<String, Object> map) {
		return sysSurveyResultDao.GetAnswerTongJiInfoByQuestionID(map);
	}

}
