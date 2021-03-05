package cn.org.chtf.card.manage.Decorators.service.impl;

import cn.org.chtf.card.manage.Decorators.dao.DecoratorEbsNoticeMapper;
import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsNoticeService;
import cn.org.chtf.card.manage.Exhibitors.model.EbsNotice;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import cn.org.chtf.card.support.util.WConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DecoratorEbsNoticeServiceImpl implements DecoratorEbsNoticeService {

    @Autowired
    private DecoratorEbsNoticeMapper decoratorEbsNoticeMapper;

    @Override
    public ResultModel updateNotice(EbsNotice notice) {
        ResultModel result = null;
        try {
            if (notice.getId() == null || notice.getId() == 0) {
                decoratorEbsNoticeMapper.insertSelective(notice);
            } else {
                decoratorEbsNoticeMapper.updateByPrimaryKeySelective(notice);
            }
            result = new ResultModel(WConst.SUCCESS, WConst.SAVED, null);
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public ResultModel deleteNotices(Integer[] noticeIdList) {
        ResultModel result = null;
        if (noticeIdList.length == 0) {//验证传入数组的长度
            result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR, "没有传入要删除的文章");
            return result;
        }
        List<Integer> idList = Arrays.asList(noticeIdList);
        try {
            result = new ResultModel(WConst.SUCCESS, WConst.SAVED, null);

            decoratorEbsNoticeMapper.deleteNotices(idList);
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.SAVEDERROR, e.getMessage());
        }

        return result;
    }

    @Override
    public ResultModel getNotices(PageModel page) {
        ResultModel result = null;
        try {
            result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
            result.setResult(decoratorEbsNoticeMapper.getNotices(page));
            ;
            result.setCount(decoratorEbsNoticeMapper.getTotal(page));
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e.getMessage());
        }

        return result;
    }

    @Override
    public ResultModel getNotice(Integer noticeId) {
        ResultModel result = null;
        try {
            result = new ResultModel(WConst.SUCCESS, WConst.QUERYSUCCESS, null);
            result.setResult(decoratorEbsNoticeMapper.selectByPrimaryKey(noticeId));
            ;
        } catch (Exception e) {
            result = new ResultModel(WConst.ERROR, WConst.QUERYFAILD, e.getMessage());
        }

        return result;
    }
}
