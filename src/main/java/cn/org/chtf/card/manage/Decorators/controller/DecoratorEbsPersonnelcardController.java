package cn.org.chtf.card.manage.Decorators.controller;

import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.AuditRecord.model.LogDocumentaudit;
import cn.org.chtf.card.manage.AuditRecord.service.LogDocumentauditService;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsPersonnelcardService;
import cn.org.chtf.card.manage.Exhibitors.dao.EbsPersonnelcardMapper;
import cn.org.chtf.card.manage.Exhibitors.model.EbsPersonnelcard;
import cn.org.chtf.card.manage.Exhibitors.model.EbsVehiclecard;
import cn.org.chtf.card.manage.Exhibitors.service.EbsCompanyinfoService;
import cn.org.chtf.card.manage.Exhibitors.service.EbsVehiclecardService;
import cn.org.chtf.card.manage.MakeEvidence.model.CmCertificateType;
import cn.org.chtf.card.manage.MakeEvidence.service.CmCertificateTypeService;
import cn.org.chtf.card.manage.common.service.CommonService;
import cn.org.chtf.card.manage.member.pojo.Member;
import cn.org.chtf.card.manage.system.model.SysPrintTemplate;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysPrintTemplateService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.CryptographyUtil;
import cn.org.chtf.card.support.util.WConst;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 搭建商管理-人员证件管理Controller
 * 
 * @author ggwudivs
 */
@RestController
@RequestMapping("/manage/Decorators/ebsPersonnelcard")
public class DecoratorEbsPersonnelcardController {

	@Autowired
	private DecoratorEbsPersonnelcardService decoratorEbsPersonnelcardService;

	@Autowired
	private EbsCompanyinfoService ebsCompanyinfoService;

	@Autowired
	private CmCertificateTypeService cmCertificateTypeService;

	@Autowired
	private LogDocumentauditService logDocumentauditService;

	@Autowired
	private SysSessionService sysSessionService;

	@Autowired
	private EbsPersonnelcardMapper ebsPersonnelcardDao;

	@Autowired
	private SysOperationLogService sysOperationLogService;

	@Autowired
	private SysPrintTemplateService sysPrintTemplateService;

	@Autowired
	private EbsVehiclecardService ebsVehiclecardService;
	
	@Autowired
    private CommonService commonService;

    /**批量重审,批量审核通过
     * @param isStr
     * @param status
     * @param session
     * @return
     */
    @RequestMapping(value = {"/batchAgainAudit"})
	@Transactional(rollbackFor = Exception.class)
    public R batchAgainAudit(@RequestParam(value = "isStr") String isStr, @RequestParam(value = "status") int status, HttpSession session){
    	try {
    		User user = (User) session.getAttribute("user");
    		if (isStr != null && !isStr.equals("")) {
    			String[] ids = isStr.split(",");
    			for (String id : ids) {
    				if (id != null && !id.equals("")) {
    					EbsPersonnelcard ebs = new EbsPersonnelcard();
    					ebs.setId(Integer.valueOf(id));
    					ebs.setStatus(status);
    					ebs.setRemark("");
    					decoratorEbsPersonnelcardService.update(ebs);

    					LogDocumentaudit log = new LogDocumentaudit();
    					log.setAct(0);
    					log.setReviewer(user.getId());
    					log.setStatus(status);
    					log.setRemark("");
    					log.setDocumentid(Integer.valueOf(id));
    					logDocumentauditService.save(log);
    				}
    			}
    		}
    		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
    	} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
    }
	
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			// 找当前届次证件类型为参展证ID
			// Map<String,Object> czz = new HashMap<String,Object>();
			// czz.put("type", 0);
			// czz.put("useable", 1);
			// czz.put("isexhibitor", 1);
			// czz.put("session", strSessionid);
			// CmCertificateType result =
			// cmCertificateTypeService.findByMap(czz);
			// map.put("zhegjianleixing", result.getId());
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	@RequestMapping("/Previouslist")
	public R Previouslist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	// 搭建商证制作
	@RequestMapping(value={"/ExhibitorBadgeProductionlist","/ExhibitorBadgePlasticlist"})
	public R ExhibitorBadgeProductionlist(
			@RequestParam Map<String, Object> map, HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.ExhibitorBadgeProductionlist(map);
			int count = decoratorEbsPersonnelcardService
					.ExhibitorBadgeProductionlistcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
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
			List<Map<String, Object>> list = decoratorEbsPersonnelcardService
					.GetDownLoadInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("YYZJSH");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(
						String.valueOf(list.get(j).get("name")));
				row.createCell(2).setCellValue(
						String.valueOf(list.get(j).get("tel")));
				row.createCell(3).setCellValue(
						String.valueOf(list.get(j).get("phone")));
				row.createCell(4).setCellValue(
						String.valueOf(list.get(j).get("website")));
				row.createCell(5).setCellValue(
						String.valueOf(list.get(j).get("email")));
				row.createCell(6).setCellValue(
						String.valueOf(list.get(j).get("fax")));
				row.createCell(7).setCellValue(
						String.valueOf(list.get(j).get("postcode")));
				row.createCell(8).setCellValue(
						String.valueOf(list.get(j).get("jobtitle")));
				row.createCell(9).setCellValue(
						String.valueOf(list.get(j).get("cardtypename")));
				row.createCell(10).setCellValue(
						String.valueOf(list.get(j).get("cardnumber")));
				row.createCell(11).setCellValue(
						String.valueOf(list.get(j).get("cardnaturename")));
				row.createCell(12).setCellValue(
						String.valueOf(list.get(j).get("companyname")));
				row.createCell(13).setCellValue(
						String.valueOf(list.get(j).get("industryname")));
				row.createCell(14).setCellValue(
						String.valueOf(list.get(j).get("chjsname")));

				row.createCell(15).setCellValue(
						String.valueOf(list.get(j).get("chmdname")));
				row.createCell(16).setCellValue(
						String.valueOf(list.get(j).get("cgdzqname")));
				row.createCell(17).setCellValue(
						String.valueOf(list.get(j).get("rhzdzhname")));
				row.createCell(18).setCellValue(
						String.valueOf(list.get(j).get("ywxzname")));

				row.createCell(19).setCellValue(
						String.valueOf(list.get(j).get("agentname")));

				row.createCell(20).setCellValue(
						String.valueOf(list.get(j).get("purchaseintention")));
				row.createCell(21).setCellValue(
						String.valueOf(list.get(j).get("cgflname")));

				row.createCell(22).setCellValue(
						String.valueOf(list.get(j).get("statusname")));
			}
			String fileName = "RYZJSH.xls";

			// 生成excel文件
			buildExcelFile(fileName, workbook);

			sysOperationLogService.CreateEntity("导出人员证件", strSessionid, 0,
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
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("电话");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("公司网址");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("电子邮件");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("传真");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("邮编");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("职务");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("证件类型");
		cell.setCellStyle(style);
		cell = row.createCell(10);
		cell.setCellValue("证件号码");
		cell.setCellStyle(style);
		cell = row.createCell(11);
		cell.setCellValue("类型");
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("所属企业");
		cell.setCellStyle(style);
		cell = row.createCell(13);
		cell.setCellValue("业务领域");
		cell.setCellStyle(style);
		cell = row.createCell(14);
		cell.setCellValue("参会角色");
		cell.setCellStyle(style);
		cell = row.createCell(15);
		cell.setCellValue("参会目的");
		cell.setCellStyle(style);
		cell = row.createCell(16);
		cell.setCellValue("您想参观的展区");
		cell.setCellStyle(style);
		cell = row.createCell(17);
		cell.setCellValue("您如何知道展会");
		cell.setCellStyle(style);
		cell = row.createCell(18);
		cell.setCellValue("业务性质");
		cell.setCellStyle(style);
		cell = row.createCell(19);
		cell.setCellValue("代办员");
		cell.setCellStyle(style);
		cell = row.createCell(20);
		cell.setCellValue("采购意向");
		cell.setCellStyle(style);
		cell = row.createCell(21);
		cell.setCellValue("采购归类");
		cell.setCellStyle(style);
		cell = row.createCell(22);
		cell.setCellValue("状态");
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

	// 参展证绿色通道
	@RequestMapping("/Greenlist")
	public R Greenlist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			// 找当前届次证件类型为参展证ID
			Map<String, Object> czz = new HashMap<String, Object>();
			czz.put("type", 0);
			czz.put("useable", 1);
			czz.put("isexhibitor", 1);
			czz.put("session", strSessionid);
			CmCertificateType result = cmCertificateTypeService.findByMap(czz);
			map.put("cardtype", result.getId());

			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService
					.Greenlist(map);
			int count = decoratorEbsPersonnelcardService.Greenlistcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	// 修改参展证打印状态
	@RequestMapping("/Exhibitorbadgeprintinglist")
	public R Exhibitorbadgeprintinglist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			// 找当前届次证件类型为参展证ID
			/*
			 * Map<String, Object> czz = new HashMap<String, Object>();
			 * czz.put("type", 0); czz.put("useable", 1); czz.put("isexhibitor",
			 * 1); czz.put("session", strSessionid); CmCertificateType result =
			 * cmCertificateTypeService.findByMap(czz); map.put("cardtype",
			 * result.getId());
			 */

			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	@RequestMapping(value={"/ExhibitorbadgeprintingUpdate","/ExhibitorbadgeprintingUpdateBack","/ExhibitorbadgeprintingUpdateBack_ZtoY","/ExhibitorbadgeprintingUpdateBack_ZtoW","/ExhibitorbadgeprintingUpdate_WtoY"})
	public R ExhibitorbadgeprintingUpdate(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpSession session) {
		EbsPersonnelcard per = new EbsPersonnelcard();
		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		per.setPrintstatus(Integer.valueOf(String.valueOf(map.get("printstatus"))));
		
		if(per.getPrintstatus()==2){
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			String time = formater1.format(new Date());
			per.setMakecardtime(time);
			per.setPrinttime(time);		
			per.setPrinttype(0);
		}
		
		decoratorEbsPersonnelcardService.update(per);
		String strSessionid = sysSessionService.getSessionID(request);
		
		
		
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("修改人员证件打印状态", strSessionid, 0,
				user.getId(), per.getId(), JSONObject.toJSONString(map));
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	/**************************************************** 人员证件制作START ***************************************************************************/
	// 人员证件制作
	@RequestMapping("/Personnelcardproductionlist")
	public R Personnelcardproductionlist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	/**************************************************** 人员证件制作END ***************************************************************************/

	/**************************************************** 人员证件查询START ***************************************************************************/
	// 人员证件查询
	@RequestMapping("/PersonnelCardQuerylist")
	public R PersonnelCardQuerylist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			if (map.get("caidan") == null
					|| map.get("caidan").toString().equals("")) {
				List<Map<String, Object>> list = decoratorEbsPersonnelcardService.PersonnelCardQuerylist(map);
				int count = decoratorEbsPersonnelcardService.PersonnelCardQuerylistcount(map);
				return R.ok().put("data", list).put("code", WConst.SUCCESS)
						.put("msg", WConst.QUERYSUCCESS).put("count", count);
			} else {// 人员证件取证标记
				List<Map<String, Object>> list = decoratorEbsPersonnelcardService.PersonnelCertificateCollectionList(map);
				int count = decoratorEbsPersonnelcardService.PersonnelCertificateCollectionListCount(map);
				return R.ok().put("data", list).put("code", WConst.SUCCESS)
						.put("msg", WConst.QUERYSUCCESS).put("count", count);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	/**************************************************** 人员证件查询END ***************************************************************************/

	// 证件检索
	@RequestMapping("/DocumentRetrievallist")
	public R DocumentRetrievallist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);

			if (String.valueOf(map.get("ZJLX")).equals("3")) {
				List<EbsVehiclecard> list = ebsVehiclecardService.list(map);
				int count = ebsVehiclecardService.listcount(map);

				return R.ok().put("data", list).put("code", WConst.SUCCESS)
						.put("msg", WConst.QUERYSUCCESS).put("count", count);
			} else {
				List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
				int count = decoratorEbsPersonnelcardService.listcount(map);

				return R.ok().put("data", list).put("code", WConst.SUCCESS)
						.put("msg", WConst.QUERYSUCCESS).put("count", count);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	/**************************************************** 证件IC卡号修复START ***************************************************************************/
	// 人员证件查询
	@RequestMapping("/IDCardNumberRepairlist")
	public R IDCardNumberRepairlist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);

			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);

			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}

	/**************************************************** 证件IC卡号修复END ***************************************************************************/

	/**
	 * 通过id查询单个搭建商管理-人员证件管理
	 */
	@GetMapping("/findByMap")
	public R findByMap(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		String strSessionid = sysSessionService.getSessionID(request);
		map.put("session", strSessionid);
		List<EbsPersonnelcard> ebsPersonnelcard = decoratorEbsPersonnelcardService.findByMap(map);
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsPersonnelcard);
	}
	
	@RequestMapping("/findListByCompanyId")
    public R findListByCompanyId(@RequestParam Map<String,Object> map, HttpServletRequest request){
    	try {
    		String strSessionid = sysSessionService.getSessionID(request);
    		map.put("session",strSessionid);
    		map = ResultVOUtil.TiaoZhengFenYe(map);
    		List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
    		int count = decoratorEbsPersonnelcardService.listcount(map);
    		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.QUERYFAILD);
    	}
    }

	/**
	 * 通过id查询单个搭建商管理-人员证件管理
	 */
	@GetMapping("/findById")
	public R findById(@RequestParam(value = "id") Integer id) {
		EbsPersonnelcard ebsPersonnelcard = decoratorEbsPersonnelcardService
				.findById(id);
		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("data", ebsPersonnelcard);
	}

	@RequestMapping("/CardVerificationfindByMap")
	public R CardVerificationfindByMap(@RequestBody Map<String, Object> map, HttpServletRequest request) throws Exception {
		String strSessionid = sysSessionService.getSessionID(request);
		map.put("session", strSessionid);
		String iccode = map.get("iccode").toString();
		String[] strArgs = iccode.split("=");
		String Value = CryptographyUtil.decrypt(strArgs[1], Charset.forName("utf8"), "lbh@MaoC");
		map.put("iccode", Value);
		List<EbsPersonnelcard> ebsPersonnelcard = decoratorEbsPersonnelcardService.findByMap(map);
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("data", ebsPersonnelcard);
	}

	/**
	 * 添加搭建商管理-人员证件管理
	 */
	@PostMapping("/save")
	public R save(@RequestBody EbsPersonnelcard ebsPersonnelcard,
			HttpServletRequest request, HttpSession session) {
		String strSessionid = sysSessionService.getSessionID(request);
		ebsPersonnelcard.setSession(strSessionid);
		decoratorEbsPersonnelcardService.save(ebsPersonnelcard);
		// 记录操作日志
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("添加人员证件", strSessionid, 0,
				user.getId(), ebsPersonnelcard.getId(),
				JSONObject.toJSONString(ebsPersonnelcard));

		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
	}

	@PostMapping("/saveGreen")
	@Transactional(rollbackFor = Exception.class)
	public R saveGreen(@RequestBody EbsPersonnelcard ebsPersonnelcard,
			HttpServletRequest request, HttpSession session) {
		try{
			String strSessionid = sysSessionService.getSessionID(request);
			// 找当前届次证件类型为参展证ID
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 0);
			map.put("useable", 1);
			map.put("isexhibitor", 1);
			map.put("session", strSessionid);
			CmCertificateType result = cmCertificateTypeService.findByMap(map);
			ebsPersonnelcard.setCardtype(result.getId());
			ebsPersonnelcard.setSession(strSessionid);
	
			int companyid = ebsPersonnelcard.getCompanyid();
			// 通过企业ID得到代办员memberid
			Map<String, Object> par = new HashMap<String, Object>();
			par.put("companyid", companyid);
			par.put("session", strSessionid);
			Member member = ebsCompanyinfoService.GetMemberInfoByCompanyId(par);
			ebsPersonnelcard.setAgent(member.getMemberId());
			ebsPersonnelcard.setIsback(1);
	
			//生成二维码票号
			Map<String,Object> exhibitorInfo = new HashMap<String,Object>();
			String strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
			ebsPersonnelcard.setIccode(strPiaoHao);
			
			decoratorEbsPersonnelcardService.save(ebsPersonnelcard);
			
			//非布撤展证加入门禁推送队列
			if(ebsPersonnelcard.getCardtype()!=28){
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("session", strSessionid);
				obj.put("ticketnum",ebsPersonnelcard.getIccode());
				obj.put("name",ebsPersonnelcard.getName());
				obj.put("islimit",0);
				obj.put("limits",0);
				obj.put("isvip",0);
				obj.put("idno",ebsPersonnelcard.getCardnumber());				
				obj.put("picture",ebsPersonnelcard.getImagepath());
				commonService.AddCardPushInfo(obj);
			}
			
			User user = (User) session.getAttribute("user");
			sysOperationLogService.CreateEntity("添加参展证", strSessionid, 0,
					user.getId(), ebsPersonnelcard.getId(),
					JSONObject.toJSONString(ebsPersonnelcard));
	
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
		}
		catch (Exception e) {
	    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    	System.out.println(e.getMessage());
	    	return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
	    }	
	}

	/**
	 * 人员证件制作（现场）
	 */
	@PostMapping("/PersonnelCardProductionOnSitesave")
	@Transactional(rollbackFor = Exception.class)
	public R PersonnelCardProductionOnSitesave(
			@RequestBody EbsPersonnelcard ebsPersonnelcard,
			HttpServletRequest request, HttpSession session) {
		try{
			String strSessionid = sysSessionService.getSessionID(request);
			ebsPersonnelcard.setSession(strSessionid);
			Map<String,Object> exhibitorInfo = new HashMap<String,Object>();
			String strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
			ebsPersonnelcard.setIccode(strPiaoHao);
			decoratorEbsPersonnelcardService.save(ebsPersonnelcard);
			
			//非布撤展证加入门禁推送队列
			if(ebsPersonnelcard.getCardtype()!=28){
				Map<String,Object> obj = new HashMap<String,Object>();
				obj.put("session", strSessionid);
				obj.put("ticketnum",ebsPersonnelcard.getIccode());
				obj.put("name",ebsPersonnelcard.getName());
				obj.put("islimit",0);
				obj.put("limits",0);
				obj.put("isvip",0);
				obj.put("idno",ebsPersonnelcard.getCardnumber());
				obj.put("picture",ebsPersonnelcard.getImagepath());
				commonService.AddCardPushInfo(obj);
			}
	
			User user = (User) session.getAttribute("user");
			sysOperationLogService.CreateEntity("添加人员证件（现场）", strSessionid, 0,
					user.getId(), ebsPersonnelcard.getId(),
					JSONObject.toJSONString(ebsPersonnelcard));
			return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SAVED);
		} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SAVEDERROR);
    	}	
	}

	/**
	 * 修改搭建商管理-人员证件管理
	 */
	@PostMapping("/update")
	public R update(@RequestBody EbsPersonnelcard ebsPersonnelcard,
			HttpSession session) {
		decoratorEbsPersonnelcardService.update(ebsPersonnelcard);

		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("更新人员证件",
				ebsPersonnelcard.getSession(), 0, user.getId(),
				ebsPersonnelcard.getId(),
				JSONObject.toJSONString(ebsPersonnelcard));

		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.SETED);
	}

	/**
	 * 删除搭建商管理-人员证件管理
	 */
	@GetMapping("/deleteById")
	public R deleteById(@RequestParam(value = "id") Integer id,
			HttpSession session) {
		EbsPersonnelcard epc = decoratorEbsPersonnelcardService.findById(id);
		decoratorEbsPersonnelcardService.deleteById(id);

		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("删除人员证件", epc.getSession(), 0,
				user.getId(), id, JSONObject.toJSONString(epc));

		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

	}

	@GetMapping("/deleteGreenById")
	public R deleteGreenById(@RequestParam(value = "id") Integer id,
			HttpSession session) {
		EbsPersonnelcard epc = decoratorEbsPersonnelcardService.findById(id);
		decoratorEbsPersonnelcardService.deleteById(id);
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("删除人员证件", epc.getSession(), 0,
				user.getId(), id, JSONObject.toJSONString(epc));
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);

	}

	/**
	 * 批量删除搭建商管理-人员证件管理
	 */
	@GetMapping("/delAll")
	public R delAll(@RequestParam(value = "isStr") String isStr,
			HttpSession session) {
		if (isStr != null && !isStr.equals("")) {
			String[] ids = isStr.split(",");
			for (String id : ids) {
				if (id != null && !id.equals("")) {
					EbsPersonnelcard epc = decoratorEbsPersonnelcardService
							.findById(Integer.valueOf(id));
					decoratorEbsPersonnelcardService.deleteById(Integer.valueOf(id));
					User user = (User) session.getAttribute("user");
					sysOperationLogService.CreateEntity("删除人员证件",
							epc.getSession(), 0, user.getId(),
							Integer.valueOf(id), JSONObject.toJSONString(epc));
				}
			}
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
	}

	// 批量审核通过
	@GetMapping("/AuditAll")
	public R AuditAll(@RequestParam(value = "isStr") String isStr, HttpSession session, HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		String strSessionid = sysSessionService.getSessionID(request);
		if (isStr != null && !isStr.equals("")) {
			String[] ids = isStr.split(",");
			for (String id : ids) {
				if (id != null && !id.equals("")) {
					EbsPersonnelcard ebs = new EbsPersonnelcard();
					ebs.setId(Integer.valueOf(id));
					ebs.setStatus(1);
					
					//审核通过时，iccode为空则生成
					EbsPersonnelcard eplc = decoratorEbsPersonnelcardService.findById(ebs.getId());
					if(eplc.getIccode() == null || "".equals(eplc.getIccode())){
						Map<String,Object> exhibitorInfo = new HashMap<String,Object>();
						String strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
						Map<String,Object> par = new HashMap<String,Object>();
						par.put("iccode", strPiaoHao);
						par.put("session", strSessionid);
						List<EbsPersonnelcard> lit = decoratorEbsPersonnelcardService.findByMap(par);
						if(lit.size()>0){
							strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
						}						
						ebs.setIccode(strPiaoHao);
					}	
					
					
					decoratorEbsPersonnelcardService.update(ebs);

					LogDocumentaudit log = new LogDocumentaudit();
					log.setAct(0);
					log.setReviewer(user.getId());
					log.setStatus(1);
					log.setRemark("");
					log.setDocumentid(Integer.valueOf(id));
					logDocumentauditService.save(log);
				}
			}
			sysOperationLogService.CreateEntity("批量审核人员证件", strSessionid, 0,
					user.getId(), 0, isStr);
		}
		return R.ok().put("code", WConst.SUCCESS).put("msg", WConst.DELETED);
	}

	@RequestMapping("/UpdatePrintStatus")
	public R UpdatePrintStatus(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpSession session) {
		EbsPersonnelcard per = new EbsPersonnelcard();
		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		per.setPrintstatus(Integer.valueOf(String.valueOf(map.get("printstatus"))));
		if (map.get("printtype") != null
				&& !String.valueOf(map.get("printtype")).equals("")) {
			per.setPrinttype(Integer.valueOf(String.valueOf(map
					.get("printtype"))));
		}
		if (map.get("iccode") != null) {
			per.setIccode(String.valueOf(map.get("iccode")));
		}
		if (map.get("vipcode") != null) {
			per.setVipcode(String.valueOf(map.get("vipcode")));
		}
		if (map.get("makecardtime") != null) {
			per.setMakecardtime(String.valueOf(map.get("makecardtime")));
		}
		if (map.get("printtime") != null) {
			per.setPrinttime(String.valueOf(map.get("printtime")));
		}
		if (!String.valueOf(map.get("iccode")).equals("")) {
			Map<String, Object> pcs = new HashMap<String, Object>();
			pcs.put("iccode", String.valueOf(map.get("iccode")));
			pcs.put("oldid", String.valueOf(map.get("id")));
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService
					.findByMap(pcs);
			if (list.size() > 0) {
				return R.error().put("code", -100)
						.put("msg", "输入的IC卡号已存在，请确认后重试");
			}
		}
		decoratorEbsPersonnelcardService.update(per);
		String strSessionid = sysSessionService.getSessionID(request);
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("人员证件打印", strSessionid, 0,
				user.getId(), per.getId(), JSONObject.toJSONString(map));

		return R.ok().put("code", 200).put("msg", "成功");

	}

	@RequestMapping("/UpdateVerificationstatus")
	public R UpdateVerificationstatus(@RequestBody Map<String, Object> map,
			HttpServletRequest request, HttpSession session) {
		EbsPersonnelcard per = new EbsPersonnelcard();
		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		per.setVerificationstatus(Integer.valueOf(String.valueOf(map
				.get("verificationstatus"))));
		decoratorEbsPersonnelcardService.update(per);

		String strSessionid = sysSessionService.getSessionID(request);
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("更新人员证件验证状态", strSessionid, 0,
				user.getId(), per.getId(), JSONObject.toJSONString(map));

		return R.ok().put("code", 200).put("msg", "成功");
	}

	@RequestMapping( value={"/PersonnelCardForensicMarkUpdate","/PersonnelCardForensicMarkUpdateBack"})
	public R PersonnelCardForensicMarkUpdate(
			@RequestBody Map<String, Object> map, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		EbsPersonnelcard per = new EbsPersonnelcard();
		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		per.setIsforensics(Integer.valueOf(String.valueOf(map.get("isforensics"))));
		per.setForensicstime(String.valueOf(map.get("forensicstime")));
		decoratorEbsPersonnelcardService.update(per);
		if(map.get("phone")!=null && !"".equals(map.get("phone"))){
			Map<String,Object> para = new HashMap<String,Object>();
			para.put("primaryid", per.getId());
			para.put("phone", map.get("phone"));
			para.put("createby", user.getId());
			para.put("act", "person");
			commonService.addMarkLog(para);
		}

		String strSessionid = sysSessionService.getSessionID(request);
		
		sysOperationLogService.CreateEntity("更新人员证件取证状态", strSessionid, 0,
				user.getId(), per.getId(), JSONObject.toJSONString(map));

		return R.ok().put("code", 200).put("msg", "成功");
	}

	@RequestMapping("/PersonnelCardForensicMarkBiaoJi")
	@Transactional(rollbackFor = Exception.class)
	public R PersonnelCardForensicMarkBiaoJi(
			@RequestBody Map<String, Object> map, HttpServletRequest request,
			HttpSession session) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			User user = (User) session.getAttribute("user");
			String value = String.valueOf(map.get("ids"));
			String[] ids = value.split(",");
			for (String id : ids) {
				EbsPersonnelcard per = new EbsPersonnelcard();
				if (id != null && !id.equals("")) {
					per.setId(Integer.valueOf(id));
					per.setIsforensics(1);
					SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
					String time = formater1.format(new Date());
					per.setForensicstime(time);
					// ebsVehiclecard.
					decoratorEbsPersonnelcardService.update(per);
					Map<String,Object> para = new HashMap<String,Object>();
					para.put("primaryid", id);
					para.put("phone", map.get("phone"));
					para.put("createby", user.getId());
					para.put("act", "person");
					commonService.addMarkLog(para);
					
					sysOperationLogService.CreateEntity("批量更新人员证件取证状态",
							strSessionid, 0, user.getId(), per.getId(),
							JSONObject.toJSONString(per));
	
				}
			}
			return R.ok().put("code", 200).put("msg", "标记成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
		}
	}

	@RequestMapping(value = {"/UpdateAuditStatus","/againAudit"})
	@Transactional(rollbackFor = Exception.class)
	public R UpdateAuditStatus(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			EbsPersonnelcard per = new EbsPersonnelcard();
			per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
			if ("1".equals(map.get("status"))) {//审核通过时
				per.setRemark("");
				per.setAudittime(new java.sql.Timestamp(System.currentTimeMillis()));
				//审核通过时,iccode为空则生成
				EbsPersonnelcard eplc = decoratorEbsPersonnelcardService.findById(per.getId());
				if(eplc.getIccode() == null || "".equals(eplc.getIccode())){
					Map<String,Object> exhibitorInfo = new HashMap<String,Object>();
					String strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
					
					Map<String,Object> par = new HashMap<String,Object>();
					par.put("iccode", strPiaoHao);
					par.put("session", strSessionid);
					List<EbsPersonnelcard> lit = decoratorEbsPersonnelcardService.findByMap(par);
					if(lit.size()>0){
						strPiaoHao = commonService.GetTicketNumber(exhibitorInfo);
					}			
					
					per.setIccode(strPiaoHao);
				}	
			} else if ("-1".equals(map.get("status"))) {
				per.setRemark(String.valueOf(map.get("remark")));
				per.setAudittime(new java.sql.Timestamp(System.currentTimeMillis()));
			} else {
				per.setRemark("");
				per.setAudittime(new java.sql.Timestamp(System.currentTimeMillis()));
			}
			per.setStatus(Integer.valueOf(String.valueOf(map.get("status"))));
	
			LogDocumentaudit log = new LogDocumentaudit();
			log.setAct(Integer.valueOf(String.valueOf(map.get("act"))));
			log.setReviewer(user.getId());
			log.setStatus(Integer.valueOf(String.valueOf(map.get("status"))));
			log.setRemark(String.valueOf(map.get("remark")));
			log.setDocumentid(Integer.valueOf(String.valueOf(map.get("id"))));
	
			decoratorEbsPersonnelcardService.update(per);
	
			logDocumentauditService.save(log);
	
			sysOperationLogService.CreateEntity("人员证件修改审核状态", strSessionid, 0, user.getId(), per.getId(), JSONObject.toJSONString(map));
	
			return R.ok().put("code", 200).put("msg", "成功");
		} catch (Exception e) {
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    		System.out.println(e.getMessage());
    		return R.error().put("code", WConst.ERROR).put("msg", WConst.SETEDERROR);
    	}
	}

	@RequestMapping("/printAll")	
	public R printAll( String isStr,String leixing,
			HttpSession session, HttpServletRequest request) throws Exception {
		String strSessionid = sysSessionService.getSessionID(request);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (isStr != null && !isStr.equals("")) {
			String[] ids = isStr.split(",");
			int count = 0;
			String title="";
			for (String strid : ids) {
				if (strid != null && !strid.equals("")) {
					
					String id = strid;				

					EbsPersonnelcard epc = decoratorEbsPersonnelcardService.findById(Integer.valueOf(id));
					Map<String, Object> par = new HashMap<String, Object>();
					par.put("session", strSessionid);
					par.put("cardtypeid", epc.getCardtype());
					List<SysPrintTemplate> sysPrintTemplate = sysPrintTemplateService.findByMap(par);
					if (sysPrintTemplate != null) {
						String temp = sysPrintTemplate.get(0).getPrintTemplate();
						if(count==0){
							title=temp.split(";")[0]+";";
						}
						if (count > 0) {
							//temp="LODOP.NewPage();"+temp;
							temp = temp.replace(title, "");
						}
						temp = temp.replace("/images/1.jpg", epc.getImagepath());
						//生成二维码内容
						String secretkey = "https://card.hljlbh.org.cn/acs?t="+CryptographyUtil.encrypt(epc.getIccode(), Charset.forName("utf8"), "lbh@MaoC");//+"&ss="+UUID.randomUUID().toString();
						temp = temp.replace("/images/evm.png", "/common/getQrCode?value=" + secretkey + "&logo=");
						temp = temp.replace("${UserName}", epc.getName());
						temp = temp.replace("${CompanyName}", epc.getCompanyname());
						temp = temp.replace("${IdCardNumber}", epc.getCardnumber());
						temp = temp.replace("${BoothNumber}", epc.getBoothcode());

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("temp", temp);
						list.add(map);
						count++;
					}
				}
			}
			User user = (User) session.getAttribute("user");
			sysOperationLogService.CreateEntity("批量打印人员证件", strSessionid, 0,
					user.getId(), 0, JSONObject.toJSONString(isStr));
		}
		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("data", list);
	}

	@RequestMapping("/printAllCZZ")
	public R printAllCZZ(String isStr, String leixing,
			HttpSession session, HttpServletRequest request) throws Exception {
		String strSessionid = sysSessionService.getSessionID(request);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (isStr != null && !isStr.equals("")) {
			String[] ids = isStr.split(",");
			int count = 0;
			String title="";
			for (String strid : ids) {
				if (strid != null && !strid.equals("")) {
					
					String id = strid;

					EbsPersonnelcard epc = decoratorEbsPersonnelcardService.findById(Integer.valueOf(id));
					Map<String, Object> par = new HashMap<String, Object>();
					par.put("session", strSessionid);
					par.put("cardtypeid", epc.getCardtype());
					List<SysPrintTemplate> sysPrintTemplate = sysPrintTemplateService.findByMap(par);
					if (sysPrintTemplate.size() == 1) {
						String temp = sysPrintTemplate.get(0).getPrintTemplate();
						if(count==0){
							title=temp.split(";")[0]+";";
						}
						if (count > 0) {
							//temp="LODOP.NewPage();"+temp;
							temp = temp.replace(title, "");
						}
						temp = temp.replace("/images/1.jpg", epc.getImagepath());
						String secretkey = "https://card.hljlbh.org.cn/acs?t="+CryptographyUtil.encrypt(epc.getIccode(), Charset.forName("utf8"), "lbh@MaoC");//+"&ss="+UUID.randomUUID().toString();
						temp = temp.replace("/images/evm.png","/common/getQrCode?value=" + secretkey + "&logo=");
						temp = temp.replace("${UserName}", epc.getName());
						temp = temp.replace("${CompanyName}", epc.getCompanyname());
						
						temp = temp.replace("${IdCardNumber}", epc.getCardnumber());						
						temp = temp.replace("${BoothNumber}", epc.getBoothcode());
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("temp", temp);
						list.add(map);
						count++;
					}

				}
			}
			User user = (User) session.getAttribute("user");
			sysOperationLogService.CreateEntity("批量打印人员证件", strSessionid, 0,
					user.getId(), 0, JSONObject.toJSONString(isStr));
		}
		return R.ok().put("code", WConst.SUCCESS)
				.put("msg", WConst.QUERYSUCCESS).put("data", list);
	}

	@RequestMapping(value = { "/getPersonnelCardList",
			"/getCGPersonnelCardList" })
	public R getPersonnelCardList(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<Map<String, Object>> list = decoratorEbsPersonnelcardService
					.getPersonnelCardList(map);
			int count = decoratorEbsPersonnelcardService.getPersonnelCardListCount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}
	
	//塑封列表
	@RequestMapping("/PersonnelCardPlasticPackagelist")
	public R PersonnelCardPlasticPackagelist(@RequestParam Map<String, Object> map,
			HttpServletRequest request) {
		try {
			String strSessionid = sysSessionService.getSessionID(request);
			map.put("session", strSessionid);
			map = ResultVOUtil.TiaoZhengFenYe(map);
			List<EbsPersonnelcard> list = decoratorEbsPersonnelcardService.list(map);
			int count = decoratorEbsPersonnelcardService.listcount(map);
			return R.ok().put("data", list).put("code", WConst.SUCCESS)
					.put("msg", WConst.QUERYSUCCESS).put("count", count);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return R.error().put("code", WConst.ERROR)
					.put("msg", WConst.QUERYFAILD);
		}
	}
	
	@RequestMapping(value={"/PersonnelCardPlasticPackageUpdate","/PersonnelCardPlasticPackageUpdate_CZZ","/PersonnelCardPlasticPackageUpdateBack","/PersonnelCardPlasticPackageUpdateBack_CZZ"})
	public R PersonnelCardPlasticPackageUpdate(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpSession session) {
		EbsPersonnelcard per = new EbsPersonnelcard();
		String strSessionid = sysSessionService.getSessionID(request);
		per.setId(Integer.valueOf(String.valueOf(map.get("id"))));
		per.setIsplastic(Integer.valueOf(String.valueOf(map.get("isplastic"))));
		if(per.getIsplastic()==1){
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			String time = formater1.format(new Date());
			per.setPlastictime(time);
		}
		decoratorEbsPersonnelcardService.update(per);
		if(per.getIsplastic()==1){//打印完成给用户发短信取证
			commonService.SendSMS(per.getId(),"person",strSessionid);
		}
		
		User user = (User) session.getAttribute("user");
		sysOperationLogService.CreateEntity("人员证件塑封标记", strSessionid, 0,
				user.getId(), per.getId(), JSONObject.toJSONString(map));

		return R.ok().put("code", 200).put("msg", "成功");

	}
}