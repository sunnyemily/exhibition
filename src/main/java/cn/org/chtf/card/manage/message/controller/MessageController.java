package cn.org.chtf.card.manage.message.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.chtf.card.manage.message.service.MessageService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

@RestController
public class MessageController {

	@Resource(name="MessageServiceImpl")
	private MessageService messageService;
	/**
	 * 批量删除
	 * @param articleIdList主键列表
	 * @return
	 */
	@RequestMapping(value="/manage/message/deleteMessages")
	public ResultModel deleteMessages(@RequestParam(value = "messageIdList[]") Integer[] messageIdList) {
		return messageService.deleteMessages(messageIdList);
	}
	/**
	 * 获取文章列表，分页
	 * @param page 分页及搜索实体
	 * @return
	 */
	@RequestMapping(value="/manage/message/getMessages/{menuId}")
	public ResultModel getMessages(@PathVariable Integer menuId,PageModel page) {
		page.setMenuId(menuId);
		return messageService.getMessages(page);
	}
}
