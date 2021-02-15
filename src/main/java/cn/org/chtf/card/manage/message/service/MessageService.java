package cn.org.chtf.card.manage.message.service;
import cn.org.chtf.card.manage.message.pojo.Message;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

public interface MessageService {
	public ResultModel insertMessage(Message message);
	public ResultModel deleteMessages(Integer[] messageIdList);
	public ResultModel getMessages(PageModel page);
}
