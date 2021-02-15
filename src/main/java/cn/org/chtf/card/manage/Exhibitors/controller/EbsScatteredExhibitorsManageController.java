package cn.org.chtf.card.manage.Exhibitors.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.common.vo.ResultVO;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsGuestbexhibition;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyListService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsGuestbexhibitionService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsBoothAuditService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsManageService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.member.service.MemberService;
import cn.org.chtf.card.manage.membersession.dao.MemberSessionMapper;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 企业信息Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsScatteredExhibitorsManage")
public class EbsScatteredExhibitorsManageController {

    @Autowired
    private EbsScatteredExhibitorsManageService ebsScatteredExhibitorsManageService;
    
    @Autowired
    private EbsGuestbexhibitionService ebsGuestbexhibitionService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private EbsCompanyinfoService ebsCompanyinfoService;
    
    @Autowired
    private EbsBoothApplyService ebsBoothApplyService;
    
    @Autowired
    private EbsBoothApplyListService ebsBoothApplyListService;
    
    @Autowired
    private EbsBoothService ebsBoothService;
    
    @Autowired
    private EbsScatteredExhibitorsBoothAuditService ebsScatteredExhibitorsBoothAuditService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @Autowired
    private MemberSessionMapper memberSessionMapper;
    
    @RequestMapping("/list")
	public R list(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsScatteredExhibitorsManageService.list(map);			
			int count = ebsScatteredExhibitorsManageService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
    
    @RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String,Object> map,HttpServletRequest request){
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("nowsession",strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsScatteredExhibitorsManageService.Previouslist(map);			
			int count = ebsScatteredExhibitorsManageService.Previouslistcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
	} 
	
    @RequestMapping(value = {"/updateCompanyInfo","/joinBlackList","/outBlackList"})
    public R updateCompanyInfo(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		User user = (User)session.getAttribute("user");	
    		if(map.get("phone")!=null && !"".equals(map.get("phone"))) {    			
    			//校验手机号是否存在
    			Map<String,Object> map2 = new HashMap<>();
    			map2.put("session",strSessionid);
    			map2.put("phone",map.get("phone"));
    			Map<String, Object> selectCompanyInfo1 = ebsScatteredExhibitorsManageService.selectCompanyInfo(map2);
    			if(selectCompanyInfo1 != null && !Integer.valueOf(selectCompanyInfo1.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) return R.error(WConst.ERROR, "您填写的手机号已存在，请重新填写");
    			//校验邮箱是否存在
    			map2.put("phone",null);
    			map2.put("email",map.get("email"));
    			Map<String, Object> selectCompanyInfo2 = ebsScatteredExhibitorsManageService.selectCompanyInfo(map2);
    			if(selectCompanyInfo2 != null && !Integer.valueOf(selectCompanyInfo2.get("companyId").toString()).equals(Integer.valueOf(map.get("id").toString()))) return R.error(WConst.ERROR, "您填写的邮箱已存在，请重新填写");
    		}
			//修改企业信息
			ebsScatteredExhibitorsManageService.updateCompanyInfo(map);
			
			sysOperationLogService.CreateEntity("修改企业信息", strSessionid, 0, user.getId(), 
					Integer.valueOf(map.get("id").toString()), JSONObject.toJSONString(map));
			
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
    	}
    }
    
    @RequestMapping("/deleteCompanyInfo")
    @Transactional(rollbackFor = Exception.class)
    public R deleteCompanyInfo(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		User user = (User)session.getAttribute("user");	
			int companyId = Integer.valueOf(map.get("id").toString());
			
			EbsCompanyinfo eci = ebsCompanyinfoService.findById(companyId);
    		ebsCompanyinfoService.deleteById(companyId);//删除企业信息
    		sysOperationLogService.CreateEntity("删除企业信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(eci));
    		
			//零散参展商,线上展商,外宾和布撤展企业,登录账号未关联其他届次企业时,删除该账号
    		Integer memberId = Integer.valueOf(map.get("memberId").toString());
    		Member member = memberService.findById(memberId);
    		if((member.getMemberType()==2 && member.getMemberStatus()==0) || member.getMemberType()==3 || member.getMemberType()==4 || member.getMemberType()==5 || member.getMemberType()==7) {
    			List<Map<String,Object>> memberSessionsByMemberId = memberSessionMapper.getMemberSessionsByMemId(memberId);
    			if(memberSessionsByMemberId.size()==1){
    				memberService.delete(memberId);//删除零散参展商的登录账号
    				sysOperationLogService.CreateEntity("删除企业登录账号", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(member));
    			}
    		}
    		
    		//零散参展商,线上展商,外宾和布撤展企业,删除当前届次关系
    		if((member.getMemberType()==2 && member.getMemberStatus()==0) || member.getMemberType()==3 || member.getMemberType()==4 || member.getMemberType()==5 || member.getMemberType()==7) {
    			Map<String,Object> par = new HashMap<String,Object>();
    			par.put("session", strSessionid);
    			par.put("memberid", memberId);
    			par.put("agentid", companyId);
    			memberSessionMapper.delete(par);
    			sysOperationLogService.CreateEntity("删除当前届次关系", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(par));
    		}
    		
    		//交易团企业和零散参展商，删除企业展位申请信息
    		if(member.getMemberType()==0 || member.getMemberType()==2){
    			Map<String, Object> applyIdMap = ebsBoothApplyService.findByCompanyId(companyId, Integer.valueOf(strSessionid));
    			if(applyIdMap != null && applyIdMap.get("applyId") != null){
    				ebsBoothApplyService.deleteById(Integer.valueOf(applyIdMap.get("applyId").toString()));
    				sysOperationLogService.CreateEntity("删除企业展位申请信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
    				ebsBoothApplyListService.deleteById(Integer.valueOf(applyIdMap.get("applyId").toString()));
    				sysOperationLogService.CreateEntity("删除企业展位申请详情", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
    			}
	    		// TODO 释放企业展位
	    		ebsBoothService.releaseCompanyBooth(companyId, Integer.valueOf(strSessionid));
	    		sysOperationLogService.CreateEntity("释放企业展位", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
	    		// TODO 删除企业展位审核信息
	    		ebsScatteredExhibitorsBoothAuditService.deleteBoothAuditInfo(companyId, Integer.valueOf(strSessionid));
	    		sysOperationLogService.CreateEntity("删除企业展位审核信息", strSessionid, 0, user.getId(), companyId, JSONObject.toJSONString(map));
    		}
    		
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @GetMapping("/selectCompanyInfo")
    public R selectCompanyInfo(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session", strSessionid);
    		
    		Map<String,Object> companyInfo = ebsScatteredExhibitorsManageService.selectCompanyInfo(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", companyInfo);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    } 
    
    @RequestMapping("/UseScatteredExhibitors")
	public R UseScatteredExhibitors(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session) {
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
		map.put("newsession", strSessionid);
		
		R r = ebsScatteredExhibitorsManageService.UseScatteredExhibitors(map);
		if(r.get("code").equals("1")){
			sysOperationLogService.CreateEntity("提取历届零散参展商", strSessionid, 0, user.getId(), 
					0, JSONObject.toJSONString(map));
		}
		return r;
		//return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
    
    /**
     * 重置密码
     * @param map
     * @return
     */
    @RequestMapping("/ResetPasswordlingsan")
	public ResultVO ResetPasswordlingsan(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD, String.valueOf(map.get("loginname")));
    	EbsGuestbexhibition ebsGuestbexhibition = new EbsGuestbexhibition();
    	ebsGuestbexhibition.setLoginpass(pass);
    	ebsGuestbexhibition.setMemberid(Integer.valueOf(String.valueOf(map.get("memberid"))));
    	ebsGuestbexhibitionService.ResetPassword(ebsGuestbexhibition);
    	sysOperationLogService.CreateEntity("初始化登录密码", strSessionid, 0, user.getId(), 
				0, JSONObject.toJSONString(map));
        return ResultVOUtil.success();
	} 
    
    /**
     * 重置密码
     * @param map
     * @return
     */
    @RequestMapping("/ResetPasswordcaigou")
	public ResultVO ResetPasswordcaigou(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	String strSessionid = sysSessionService.getSessionID(request);
    	User user = (User)session.getAttribute("user");	
    	String pass = CryptographyUtil.md5(WConst.DEFAULT_PASSWORD, String.valueOf(map.get("loginname")));
    	EbsGuestbexhibition ebsGuestbexhibition = new EbsGuestbexhibition();
    	ebsGuestbexhibition.setLoginpass(pass);
    	ebsGuestbexhibition.setMemberid(Integer.valueOf(String.valueOf(map.get("memberid"))));
    	ebsGuestbexhibitionService.ResetPassword(ebsGuestbexhibition);
    	sysOperationLogService.CreateEntity("初始化登录密码", strSessionid, 0, user.getId(), 
				0, JSONObject.toJSONString(map));
        return ResultVOUtil.success();
	} 
    
    /**采购商管理证件统计
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/loadCount")
    public R loadCount(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map.put("userId",user.getId());
    		map.put("type",5);
    		Map<String,Object> data = ebsScatteredExhibitorsManageService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    @RequestMapping("/DownLoadFile")
	public R DownLoadFile(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			List<Map<String, Object>> list = ebsScatteredExhibitorsManageService.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("YYZJSH");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(
						String.valueOf(list.get(j).get("chinesename")));
				row.createCell(2).setCellValue(
						String.valueOf(list.get(j).get("engname")));
				row.createCell(3).setCellValue(
						String.valueOf(list.get(j).get("russianname")));
				row.createCell(4).setCellValue(
						String.valueOf(list.get(j).get("chineseaddress")));
				row.createCell(5).setCellValue(
						String.valueOf(list.get(j).get("engaddress")));
				row.createCell(6).setCellValue(
						String.valueOf(list.get(j).get("postcode")));
				row.createCell(7).setCellValue(
						String.valueOf(list.get(j).get("phone")));
				row.createCell(8).setCellValue(
						String.valueOf(list.get(j).get("tel")));
				row.createCell(9).setCellValue(
						String.valueOf(list.get(j).get("fax")));
				row.createCell(10).setCellValue(
						String.valueOf(list.get(j).get("website")));
				row.createCell(11).setCellValue(
						String.valueOf(list.get(j).get("email")));
				row.createCell(12).setCellValue(
						String.valueOf(list.get(j).get("country")));
				row.createCell(13).setCellValue(
						String.valueOf(list.get(j).get("province")));
				row.createCell(14).setCellValue(
						String.valueOf(list.get(j).get("city")));

				row.createCell(15).setCellValue(
						String.valueOf(list.get(j).get("principal")));
				row.createCell(16).setCellValue(
						String.valueOf(list.get(j).get("contactperson")));
				row.createCell(17).setCellValue(
						String.valueOf(list.get(j).get("jobtitle")));
				row.createCell(18).setCellValue(
						String.valueOf(list.get(j).get("industry")));

				row.createCell(19).setCellValue(
						String.valueOf(list.get(j).get("companyNature")));

				row.createCell(20).setCellValue(
						String.valueOf(list.get(j).get("busscope")));
				row.createCell(21).setCellValue(
						String.valueOf(list.get(j).get("hopecustomers")));

				row.createCell(22).setCellValue(
						String.valueOf(list.get(j).get("companyprofile")));
				row.createCell(23).setCellValue(
						String.valueOf(list.get(j).get("purchaseintention")));
				row.createCell(24).setCellValue(
						String.valueOf(list.get(j).get("organizationcode")));
				
			}
			String fileName = "CAIGOUSHANG.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("采购商信息", strSessionid, 0,
					user.getId(), 0, JSONObject.toJSONString(map));

			// 浏览器下载excel
			// buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS)
					.put("src", "/excel/" + fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
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
 		cell.setCellValue("单位名称（中文）");
 		cell.setCellStyle(style);
 		cell = row.createCell(2);
 		cell.setCellValue("单位名称（英文）");
 		cell.setCellStyle(style);
 		cell = row.createCell(3);
 		cell.setCellValue("单位名称（俄文）");
 		cell.setCellStyle(style);
 		cell = row.createCell(4);
 		cell.setCellValue("单位地址（中文）");
 		cell.setCellStyle(style);
 		cell = row.createCell(5);
 		cell.setCellValue("单位地址（英文）");
 		cell.setCellStyle(style);
 		cell = row.createCell(6);
 		cell.setCellValue("邮编");
 		cell.setCellStyle(style);
 		cell = row.createCell(7);
 		cell.setCellValue("手机");
 		cell.setCellStyle(style);
 		cell = row.createCell(8);
 		cell.setCellValue("电话");
 		cell.setCellStyle(style);
 		cell = row.createCell(9);
 		cell.setCellValue("传真");
 		cell.setCellStyle(style);
 		cell = row.createCell(10);
 		cell.setCellValue("网址");
 		cell.setCellStyle(style);
 		cell = row.createCell(11);
 		cell.setCellValue("电子邮箱");
 		cell.setCellStyle(style);
 		cell = row.createCell(12);
 		cell.setCellValue("国家");
 		cell.setCellStyle(style);
 		cell = row.createCell(13);
 		cell.setCellValue("省份");
 		cell.setCellStyle(style);
 		cell = row.createCell(14);
 		cell.setCellValue("城市");
 		cell.setCellStyle(style);
 		cell = row.createCell(15);
 		cell.setCellValue("负责人");
 		cell.setCellStyle(style);
 		cell = row.createCell(16);
 		cell.setCellValue("联系人");
 		cell.setCellStyle(style);
 		cell = row.createCell(17);
 		cell.setCellValue("联系人职务");
 		cell.setCellStyle(style);
 		cell = row.createCell(18);
 		cell.setCellValue("行业分类");
 		cell.setCellStyle(style);
 		cell = row.createCell(19);
 		cell.setCellValue("参展企业性质");
 		cell.setCellStyle(style);
 		cell = row.createCell(20);
 		cell.setCellValue("经营范围");
 		cell.setCellStyle(style);
 		cell = row.createCell(21);
 		cell.setCellValue("希望结识客户");
 		cell.setCellStyle(style);
 		cell = row.createCell(22);
 		cell.setCellValue("企业简介");
 		cell.setCellStyle(style);
 		cell = row.createCell(23);
 		cell.setCellValue("采购意向");
 		cell.setCellStyle(style);
 		cell = row.createCell(24);
 		cell.setCellValue("统一社会信用代码");
 		cell.setCellStyle(style);
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

 	// 浏览器下载excel
 	public void buildExcelDocument(String filename, HSSFWorkbook workbook,
 			HttpServletResponse response) throws Exception {
 		response.reset();
 		response.setContentType("octets/stream");
 		// response.setHeader("Content-Disposition","attachment;filename="+exportFileName);
 		response.setHeader("Content-Disposition", "attachment;filename=\""
 				+ new String(filename.getBytes("UTF-8"), "iso8859-1") + "\"");
 	}
          
}