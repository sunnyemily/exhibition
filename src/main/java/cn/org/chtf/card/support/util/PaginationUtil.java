package cn.org.chtf.card.support.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 * @author wushixing
 *
 */
public class PaginationUtil {
	private Integer pageIndex;
	private Integer pageAmount;
	public PaginationUtil(Integer pageIndex,Integer pageAmount) {
		this.pageAmount=pageAmount;
		this.pageIndex = pageIndex;
	}
	/**
	 * 生成分页列表
	 * @return
	 */
	public List<Map<String,Object>> getPageList(){
		List<Map<String,Object>> pageList = new ArrayList<Map<String,Object>>();
		//1.总页数小于等于5页的时候
		Integer pageStart = 1;
		Integer pageEnd = this.pageAmount;
		//2.总页数大于5页的时候
		if(this.pageAmount>5) {
			//3.先按照开始页码大于等于1进行试运算
			if(this.pageIndex-2 >= 1) {
				pageStart = this.pageIndex-2;
			}
			pageEnd = pageStart +4;
			//4.结束页码超出总页数，则反过来按结束页码等于总页码进行计算
			if(pageEnd > this.pageAmount) {
				pageEnd = this.pageAmount;
				pageStart = pageEnd -4;
			}
		}
		//5.加入上一页
		if(pageStart>1) {
			Map<String,Object> pre = new HashMap<String,Object>();
			pre.put("title", "<");
			pre.put("yema", this.pageIndex-1);
			pageList.add(pre);
			//6.加入第一页
			Map<String,Object> first = new HashMap<String,Object>();
			first.put("title", "1");
			first.put("yema", 1);
			pageList.add(first);
		}
		//6.加入前面的省略号
		if(pageStart>2) {
			Map<String,Object> preMore = new HashMap<String,Object>();
			preMore.put("title", "...");
			preMore.put("yema", 0);
			pageList.add(preMore);
		}
		//7.加入中间页码
		for(int i=pageStart;i<=pageEnd;i++) {
			Map<String,Object> page = new HashMap<String,Object>();
			page.put("title", i);
			page.put("yema", i);
			pageList.add(page);
		}
		//8.加入后面的省略号
		if(this.pageAmount-pageEnd>1) {
			Map<String,Object> nextMore = new HashMap<String,Object>();
			nextMore.put("title", "...");
			nextMore.put("yema", 0);
			pageList.add(nextMore);
		}
		//9.加入最后一页
		if(this.pageAmount-pageEnd>=1) {
			Map<String,Object> last = new HashMap<String,Object>();
			last.put("title", this.pageAmount);
			last.put("yema", this.pageAmount);
			pageList.add(last);
			//10.加入下一页
			Map<String,Object> next = new HashMap<String,Object>();
			next.put("title", ">");
			next.put("yema", this.pageIndex+1);
			pageList.add(next);
		}
		return pageList;
	}
}
