package cn.org.chtf.card.manage.ReportManagement.controller;

import java.util.ArrayList;
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
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.org.chtf.card.common.utils.ExcelUtils;
import cn.org.chtf.card.common.utils.R;
import cn.org.chtf.card.common.utils.ResultVOUtil;
import cn.org.chtf.card.manage.ReportManagement.service.RepeatCertificateStatisticsTableService;
import cn.org.chtf.card.manage.system.service.SysOperationLogService;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.WConst;
/**
 * 重复证件统计总数
 * @author guo
 *
 */
@RestController
@RequestMapping("/manage/repeatCertificateStatisticsTable")
public class RepeatCertificateStatisticsTableController {
	
	@Autowired
	private RepeatCertificateStatisticsTableService repeatCertificateStatisticsTableService;
	
	@Autowired
    private SysSessionService sysSessionService;
	
	@Autowired
    private SysOperationLogService sysOperationLogService;
	/**
	 * 查询证件类型
	 * @return
	 */
	@RequestMapping("/getCardType")
	public R getCardType() {
	    List<Map<String,Object>> list = repeatCertificateStatisticsTableService.getCardList();
	    return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	/**
	 * 查询届次
	 * @return
	 */
	@RequestMapping("/getSession")
	public R getSession() {
		List<Map<String,Object>> list = repeatCertificateStatisticsTableService.getSessionList();
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	/**
	 * 查询table
	 * @return
	 */
	@RequestMapping("/selectInfo")
	public R selectInfo(@RequestParam Map<String, Object> map) {
		int count =0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (map.get("session") != null && !"".equals(map.get("session"))) {
			map = ResultVOUtil.TiaoZhengFenYe(map);
			list = repeatCertificateStatisticsTableService.selectInfoList(map);
			count = repeatCertificateStatisticsTableService.ListCount(map);
		}
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
	}
	/**
	 * 查询重复率
	 * @return
	 */
	@RequestMapping("/repeatNumber")
	public R repeatNumber(@RequestParam Map<String, Object> map) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (map.get("session") != null && !"".equals(map.get("session"))) {
			 list = repeatCertificateStatisticsTableService.repeatNumber(map);
		}else {
			map.put("repeatNumber", 0);
			list.add(map);
		}
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	
    @RequestMapping("/exportExcel")
    public R exportExcel(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			if (map.get("session") == null || "".equals(map.get("session"))) {
				return R.error().put("code", WConst.ERROR).put("msg", "暂无数据");
			}
			List<Map<String, Object>> list = repeatCertificateStatisticsTableService.queryExportInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("重复证件信息");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("personName")));
				row.createCell(2).setCellValue(String.valueOf(list.get(j).get("tel")));
				row.createCell(3).setCellValue(String.valueOf(list.get(j).get("phone")));
				row.createCell(4).setCellValue(String.valueOf(list.get(j).get("website")));
				row.createCell(5).setCellValue(String.valueOf(list.get(j).get("email")));
				row.createCell(6).setCellValue(String.valueOf(list.get(j).get("fax")));
				row.createCell(7).setCellValue(String.valueOf(list.get(j).get("postcode")));
				row.createCell(8).setCellValue(String.valueOf(list.get(j).get("jobtitle")));
				row.createCell(9).setCellValue(String.valueOf(list.get(j).get("chinesename")));
				row.createCell(10).setCellValue(String.valueOf(list.get(j).get("cardnumber")));
				row.createCell(11).setCellValue(String.valueOf(list.get(j).get("country")));
				row.createCell(12).setCellValue(String.valueOf(list.get(j).get("province")));
				row.createCell(13).setCellValue(String.valueOf(list.get(j).get("companyname")));
				row.createCell(14).setCellValue(String.valueOf(list.get(j).get("bussinessarea")));
				row.createCell(15).setCellValue(String.valueOf(list.get(j).get("participants")));
				row.createCell(16).setCellValue(String.valueOf(list.get(j).get("purpose")));
				row.createCell(17).setCellValue(String.valueOf(list.get(j).get("visitexhibition")));
				row.createCell(18).setCellValue(String.valueOf(list.get(j).get("knowexhibition")));
				row.createCell(19).setCellValue(String.valueOf(list.get(j).get("businessnature")));
				row.createCell(20).setCellValue(String.valueOf(list.get(j).get("agentName")));
				row.createCell(21).setCellValue(String.valueOf(list.get(j).get("purchasingintention")));
				}
			String fileName = "重复证件统计列表.xls";

			// 生成excel文件
			ExcelUtils.buildExcelFile(fileName, workbook);
			User user = (User)session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			sysOperationLogService.CreateEntity("重复证件统计列表", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));

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
		cell.setCellValue("国别");
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("省份");
		cell.setCellStyle(style);
		cell = row.createCell(13);
		cell.setCellValue("所属企业");
		cell.setCellStyle(style);
		cell = row.createCell(14);
		cell.setCellValue("业务领域");
		cell.setCellStyle(style);
		cell = row.createCell(15);
		cell.setCellValue("参会角色");
		cell.setCellStyle(style);
		cell = row.createCell(16);
		cell.setCellValue("参会目的");
		cell.setCellStyle(style);
		cell = row.createCell(17);
		cell.setCellValue("您想参观的展区");
		cell.setCellStyle(style);
		cell = row.createCell(18);
		cell.setCellValue("您如何知道展会");
		cell.setCellStyle(style);
		cell = row.createCell(19);
		cell.setCellValue("业务性质");
		cell.setCellStyle(style);
		cell = row.createCell(20);
		cell.setCellValue("代办员");
		cell.setCellStyle(style);
		cell = row.createCell(21);
		cell.setCellValue("采购意向");
		cell.setCellStyle(style);
	}
}
