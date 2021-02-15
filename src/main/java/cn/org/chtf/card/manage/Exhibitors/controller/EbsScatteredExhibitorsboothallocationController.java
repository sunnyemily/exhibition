package cn.org.chtf.card.manage.Exhibitors.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.ExcelUtils;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApply;
import cn.org.chtf.card.manage.Exhibitors.model.EbsBoothApplyList;
import cn.org.chtf.card.manage.Exhibitors.model.EbsCompanyinfo;
import cn.org.chtf.card.manage.Exhibitors.model.EbsTradinggroup;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyListService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothApplyService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsBoothService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsBoothAuditService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsManageService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsScatteredExhibitorsboothallocationService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsTradinggroupService;
import cn.org.chtf.card.manage.member.dao.MemberDAO;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.system.service.SysSmsTemplateService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.StringUtil;
import cn.org.chtf.card.support.util.WConst;

/**
 * 参展商管理-零散参展商展位分配Controller
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Exhibitors/ebsScatteredExhibitorsboothallocationController")
public class EbsScatteredExhibitorsboothallocationController {
	
	@Autowired
	private EbsTradinggroupService ebsTradinggroupService;
	
	@Autowired
	private EbsBoothApplyService ebsBoothApplyService;
	
	@Autowired
	private EbsBoothApplyListService ebsBoothApplyListService;
	
	@Autowired
	private EbsBoothService ebsBoothService;
	
	@Autowired
	private EbsCompanyinfoService ebsCompanyinfoService;
	
	@Autowired
	private SysSmsTemplateService sysSmsTemplateService;

    @Autowired
    private EbsScatteredExhibitorsboothallocationService ebsScatteredExhibitorsboothallocationService;
    
    @Autowired
    private EbsScatteredExhibitorsBoothAuditService ebsScatteredExhibitorsBoothAuditService;
    
    @Autowired
    private SysSessionService sysSessionService;
    
    @Autowired
    private EbsScatteredExhibitorsManageService ebsScatteredExhibitorsManageService;
    
    @Autowired
    private SysOperationLogService sysOperationLogService;
    
    @Autowired
    private MemberDAO memberDAO;
    
    /**查询零散参展商信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getScatteredExhibitorsList")
    public R getScatteredExhibitorsList(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session",strSessionid);
			User user = (User) session.getAttribute("user");
			map.put("userId",user.getId());
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String,Object>> list = ebsScatteredExhibitorsboothallocationService.getScatteredExhibitorsList(map);
			list = StringUtil.calculateBoothRegion(list);
			int count = ebsScatteredExhibitorsboothallocationService.getScatteredExhibitorsListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }
    
    /**获取展位申请信息及剩余展位信息
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/getApplyBoothInfo")
    public R getApplyBoothInfo(@RequestParam Map<String,Object> map,HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		List<Map<String,Object>> list = ebsScatteredExhibitorsboothallocationService.getApplyBoothInfo(map);			
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    /**零散参展商分配展位
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/allocationBooth")
    public R allocationBooth(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		String selectBoothId = map.get("selectBoothId").toString();
    		String[] selectBoothIds = selectBoothId.split(",");
    		map.put("selectBoothIds", selectBoothIds);
    		map.put("session",strSessionid);
    		ebsScatteredExhibitorsboothallocationService.allocationBooth(map);
    		//重置展览处审核信息
    		Map<String,Object> map1 = new HashMap<String, Object>();
    		map1.put("companyId",map.get("companyId"));
    		map1.put("sessionId",strSessionid);
    		User user = (User) session.getAttribute("user");
    		map1.put("exhibitionOfficeAuditor", user.getId());
			map1.put("exhibitionOfficeAuditStatus", 0);
			map1.put("exhibitionOfficeAuditRemark", "");
    		ebsScatteredExhibitorsBoothAuditService.audit(map1);
    		
    		sysOperationLogService.CreateEntity("零散参展商分配展位", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**取消分配
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/cancleAllocationBooth")
    public R cancleAllocationBooth(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		ebsScatteredExhibitorsboothallocationService.cancleAllocationBooth(map);
    		
    		sysOperationLogService.CreateEntity("取消展位分配", strSessionid, 0, user.getId(), 
	        		0, JSONObject.toJSONString(map));
    		
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**统计展位数量和产品数量
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
    		Map<String,Object> data = ebsScatteredExhibitorsboothallocationService.loadCount(map);
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", data);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    /**
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/selectScatteredTraddingGroup")
    public R selectScatteredTraddingGroup(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		User user = (User) session.getAttribute("user");
    		map.put("userId",user.getId());
    		List<Map<String,Object>> list = ebsScatteredExhibitorsboothallocationService.selectScatteredTraddingGroup(map);			
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", list);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }
    
    /**零散参展商转出到交易团
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/rollOut")
	@Transactional(rollbackFor = Exception.class)
    public R rollOut(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		String strSessionid = sysSessionService.getSessionID(request);
    		//更新,企业信息,修改企业交易团id,企业类型id修改为0
    		EbsCompanyinfo ebsCompany = new EbsCompanyinfo();
    		Integer companyId = Integer.valueOf(map.get("companyId").toString());
    		ebsCompany.setId(companyId);
    		ebsCompany.setTradinggroupid(Integer.valueOf(map.get("tradingGroupId").toString()));
    		ebsCompany.setComanyTypeId(0);
    		
    		//前台会员登录账号状态修改为1
    		Member member = new Member();
    		Integer memberId = Integer.valueOf(map.get("memberId").toString());
    		member.setMemberId(memberId);
    		member.setMemberStatus(1);
    		
    		//添加展位申请信息记录
    		EbsBoothApply ebp = new EbsBoothApply();
    		ebp.setCompanyId(companyId);
    		//查询所转交易团代办员的登录账号
    		map.put("session", strSessionid);
    		List<Map<String, Object>> memberList = memberDAO.getMemberByTradingGroupId(map);
    		if(memberList.size()!=1) return R.error().put("code", WConst.ERROR).put("msg", "转入交易团的代办员登录账号不唯一，请联系管理员核实");
    		ebp.setMemberId(Integer.valueOf(memberList.get(0).get("memberId").toString()));
    		ebp.setSessionId(Integer.valueOf(strSessionid));
    		
    		EbsCompanyinfo ebsCompanyinfo = ebsCompanyinfoService.findById(Integer.valueOf(map.get("companyId").toString()));
    		EbsTradinggroup ebsTradinggroup = ebsTradinggroupService.findById(Integer.valueOf(map.get("tradingGroupId").toString()));
    		
    		//删除展位申请信息
    		Map<String, Object> ebsBoothApply = ebsBoothApplyService.findByCompanyId(Integer.valueOf(map.get("companyId").toString()), Integer.valueOf(strSessionid));
    		if(ebsBoothApply!=null) {
    			ebsBoothApplyService.deleteById(Integer.valueOf(ebsBoothApply.get("applyId").toString()));
    			ebsBoothApplyListService.deleteById(Integer.valueOf(ebsBoothApply.get("applyId").toString()));
    		}
    		//发送转出短信
    		sysSmsTemplateService.sendExhibitionToTraddingGroupSMS(ebsCompanyinfo.getPhone(), ebsCompanyinfo.getChinesename(), ebsTradinggroup.getName(), ebsTradinggroup.getContactperson(), ebsTradinggroup.getPhone(), Integer.valueOf(strSessionid));
    		ebsCompanyinfoService.update(ebsCompany);//更新,企业信息,修改企业交易团id,企业类型id修改为0
    		memberDAO.update(member);//前台会员登录账号状态修改为1
    		ebsBoothApplyService.save(ebp);//查询所转交易团的会员id
    		//记录日志
    		sysOperationLogService.CreateEntity("零散参展商转出到交易团", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**
     * @Description: 更新展位申请信息
     * @date: 2020年10月12日 下午8:41:07
     * @author: ggwudivs
     * @param map
     * @param request
     * @return: R
     */
    @RequestMapping("/updateApplyInfo")
	@Transactional(rollbackFor = Exception.class)
    public R updateApplyInfo(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("list");
    		String applyId = (String) list.get(0).get("applyId");
    		if(applyId != null && !"".equals(applyId)) {
    			ebsBoothApplyListService.deleteById(Integer.valueOf(applyId));//删除原有展位申请信息
    			//修改参展展品
        		EbsBoothApply ebsBoothApply = new EbsBoothApply();
        		ebsBoothApply.setApplyId(Integer.valueOf(applyId));
        		ebsBoothApply.setApplyProducts(list.get(0).get("applyProducts").toString());
        		ebsBoothApplyService.update(ebsBoothApply);
    		}else {
    			//新增参展展品信息
        		EbsBoothApply ebsBoothApply = new EbsBoothApply();
        		ebsBoothApply.setSessionId(Integer.valueOf(strSessionid));
        		ebsBoothApply.setApplyProducts(list.get(0).get("applyProducts").toString());
        		ebsBoothApply.setApplyLicense((String) map.get("applyLicense"));
        		ebsBoothApply.setApplyFile((String) map.get("applyFile"));
        		String companyId = (String) map.get("companyId");
        		ebsBoothApply.setCompanyId(Integer.valueOf(companyId));
        		Map<String,Object> scatterExhibitionMember = memberDAO.getScatterExhibition(Integer.valueOf(companyId), Integer.valueOf(strSessionid));
        		ebsBoothApply.setMemberId((int) scatterExhibitionMember.get("memberId"));
        		ebsBoothApplyService.save(ebsBoothApply);
        		applyId = String.valueOf(ebsBoothApply.getApplyId());
    		}
    		for (Map<String, Object> map2 : list) {
    			EbsBoothApplyList ebsBoothApplyList = new EbsBoothApplyList();
    			ebsBoothApplyList.setApplyId(Integer.valueOf(applyId));
    			ebsBoothApplyList.setApplyArea(map2.get("applyArea")==null?null:Double.valueOf((String) map2.get("applyArea")));
    			ebsBoothApplyList.setApplyBuildType(map2.get("applyBuildType")==null?null:Integer.valueOf((String) map2.get("applyBuildType")));
    			ebsBoothApplyList.setApplyCount(map2.get("applyCount")==null?null:Integer.valueOf((String) map2.get("applyCount")));
    			ebsBoothApplyList.setApplyDeviceHigh(Double.valueOf((String) map2.get("applyDeviceHigh")));
    			ebsBoothApplyList.setApplyDeviceLength(Double.valueOf((String) map2.get("applyDeviceLength")));
    			ebsBoothApplyList.setApplyDeviceWidth(Double.valueOf((String) map2.get("applyDeviceWidth")));
    			ebsBoothApplyList.setApplyElectricityAmount(Double.valueOf((String) map2.get("applyElectricityAmount")));
    			ebsBoothApplyList.setApplyRemoveSeparator(map2.get("applyRemoveSeparator")==null?null:Integer.valueOf((String) map2.get("applyRemoveSeparator")));
    			ebsBoothApplyList.setApplyVoltage((String) map2.get("applyVoltage"));
    			ebsBoothApplyList.setShowroomTypeId(Integer.valueOf((String) map2.get("showRoomTypeId")));
    			ebsBoothApplyList.setTradinggroupId(Integer.valueOf((String) map2.get("tradingGroupId")));
				ebsBoothApplyListService.save(ebsBoothApplyList);
			}
    		//记录日志
    		User user = (User) session.getAttribute("user");
    		sysOperationLogService.CreateEntity("更新展位申请信息", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
    
    /**
     * @Description: 导出excel功能
     * @date: 2020年9月27日 下午4:35:43
     * @author: ggwudivs
     * @param map
     * @param request
     * @param session
     * @return: R
     */
    @RequestMapping("/exportExcel")
    public R exportExcel(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map.put("userId", user.getId());
			List<Map<String, Object>> list = ebsScatteredExhibitorsboothallocationService.queryExportInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("零散参展商展位分配");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("chinesename")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("apply_products")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("industryName")));
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellType(CellType.STRING);
				cell4.setCellValue(String.valueOf(list.get(j).get("boothApplyNumber")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("booths")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("contactperson")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("phone")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("tel")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("fax")));
				row.createCell(10).setCellValue(String.valueOf(list.get(j).get("province")));
				row.createCell(11).setCellValue(String.valueOf(list.get(j).get("purchaseintention")));
				row.createCell(12).setCellValue(String.valueOf(list.get(j).get("remark")));
			}
			String fileName = "LSCZSZWFP.xls";

			// 生成excel文件
			ExcelUtils.buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("零散参展商展位分配-导出excel", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));

			// 浏览器下载excel
			// buildExcelDocument(fileName, workbook, response);
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("src", "/excel/" + fileName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
		}
    }

	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, int totalRow) {
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
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("参展展品");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("展品类别");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("申请数");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("分配展位");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("手机");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("联系传真");
		cell.setCellStyle(style);
		cell = row.createCell(10);
		cell.setCellValue("省别");
		cell.setCellStyle(style);
		cell = row.createCell(11);
		cell.setCellValue("采购意向");
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
	}
}