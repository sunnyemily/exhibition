package cn.org.chtf.card.manage.basic.service;

import cn.org.chtf.card.manage.basic.pojo.Basic;
import cn.org.chtf.card.support.util.ResultModel;

public interface BasicService {
	public ResultModel updateBasic(Basic basic);
	public ResultModel getBasic(int menuId);
}
