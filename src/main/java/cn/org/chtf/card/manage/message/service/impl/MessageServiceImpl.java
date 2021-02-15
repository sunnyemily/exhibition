package cn.org.chtf.card.manage.message.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.chtf.card.manage.message.dao.MessageMapper;
import cn.org.chtf.card.manage.message.pojo.Message;
import cn.org.chtf.card.manage.message.service.MessageService;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;

@Service("MessageServiceImpl")

public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageDAO;
	public ResultModel insertMessage(Message message) {
		ResultModel result = new ResultModel();
		try {
			if(message.getMessageAddtime()==null) {
				message.setMessageAddtime(new Date());
			}
			messageDAO.insertSelective(message);
			result.setStatus(WConst.SUCCESS);
			result.setMsg(WConst.SAVED);
		}
		catch(Exception e) {
			result.setStatus(WConst.ERROR);
			result.setMsg(WConst.SAVEDERROR);
			result.setResult(e.getMessage());
		}
		return result;
	}
	public ResultModel deleteMessages(Integer[] messageIdList) {
		ResultModel result = null;
		if(messageIdList.length==0) {//验证传入数组的长度
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,"没有传入要删除的信息");
			return result;
		}
		List<Integer> idList = Arrays.asList(messageIdList);
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.SAVED,null);

			messageDAO.deleteMessages(idList);
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.SAVEDERROR,e.getMessage());
		}
		
		return result;
	}
	public ResultModel getMessages(PageModel page) {
		ResultModel result = null;
		try {
			result = new ResultModel(WConst.SUCCESS,WConst.QUERYSUCCESS,null);
			result.setResult(messageDAO.getMessages(page));;
			result.setCount(messageDAO.getTotal(page));
		}
		catch(Exception e) {
			result = new ResultModel(WConst.ERROR,WConst.QUERYFAILD,e.getMessage());
		}
		
		return result;
	}
}
