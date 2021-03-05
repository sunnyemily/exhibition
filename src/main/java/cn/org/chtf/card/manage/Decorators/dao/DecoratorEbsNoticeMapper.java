package cn.org.chtf.card.manage.Decorators.dao;

import cn.org.chtf.card.manage.Exhibitors.model.EbsNotice;
import cn.org.chtf.card.support.util.PageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DecoratorEbsNoticeMapper {

    int deleteByPrimaryKey(Integer noticeId);

    int insert(EbsNotice record);

    int insertSelective(EbsNotice record);

    EbsNotice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(EbsNotice record);

    List<EbsNotice> getNotices(PageModel page);

    int getTotal(PageModel page);

    int deleteNotices(@Param("noticeIdList") List<Integer> noticeIdList);

}