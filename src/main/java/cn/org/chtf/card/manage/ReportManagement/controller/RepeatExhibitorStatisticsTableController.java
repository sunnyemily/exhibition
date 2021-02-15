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
import cn.org.chtf.card.manage.ReportManagement.service.RepeatExhibitorStatisticsTableService;
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
@RequestMapping("/manage/repeatExhibitorStatisticsTable")
public class RepeatExhibitorStatisticsTableController {
	
	@Autowired
	private RepeatExhibitorStatisticsTableService repeatExhibitorStatisticsTableService;
	
	@Autowired
    private SysSessionService sysSessionService;
	
	@Autowired
    private SysOperationLogService sysOperationLogService;
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
			list = repeatExhibitorStatisticsTableService.selectInfoList(map);
			count = repeatExhibitorStatisticsTableService.ListCount(map);
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
			 list = repeatExhibitorStatisticsTableService.repeatNumber(map);
		}else {
			map.put("repeatNumber", 0);
			list.add(map);
		}
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS);
	}
	
	/**重复企业信息列表
	 * @param map
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/repeatExhibitorTable")
	public R repeatExhibitorTable(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
		int count =0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (map.get("session") != null && !"".equals(map.get("session"))) {
			map = ResultVOUtil.TiaoZhengFenYe(map);
			list = repeatExhibitorStatisticsTableService.repeatExhibitorTable(map);
			count = repeatExhibitorStatisticsTableService.repeatExhibitorTableCount(map);
		}
		return R.ok().put("data", list).put("code", WConst.SUCCESS).put("msg", WConst.QUERYSUCCESS).put("count", count);
	}
	
    /**导出excel
     * @param map
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/exportExcel")
    public R exportExcel(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpSession session){
    	try {
			if (map.get("session") == null || "".equals(map.get("session"))) {
				return R.error().put("code", WConst.ERROR).put("msg", "暂无数据");
			}
			List<Map<String, Object>> list = repeatExhibitorStatisticsTableService.queryExportInfo(map);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("重复参展企业信息");
			createTitle(workbook, sheet, list.size());
			for (int j = 0; j < list.size(); j++) {
				HSSFRow row = sheet.createRow(1 + j);
				row.setHeightInPoints(18);
				row.createCell(0).setCellValue(j + 1);
				row.createCell(1).setCellValue(String.valueOf(list.get(j).get("chinesename")));
				}
			String fileName = "重复参展企业统计列表.xls";

			// 生成excel文件
			ExcelUtils.buildExcelFile(fileName, workbook);
			User user = (User)session.getAttribute("user");
			String strSessionid = sysSessionService.getSessionID(request);
			sysOperationLogService.CreateEntity("重复参展企业统计列表", strSessionid, 0, user.getId(), 0, JSONObject.toJSONString(map));

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
	}
}
