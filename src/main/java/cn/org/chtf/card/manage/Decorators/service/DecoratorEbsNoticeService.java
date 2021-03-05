package cn.org.chtf.card.manage.Decorators.service;

import cn.org.chtf.card.manage.Exhibitors.model.EbsNotice;
import cn.org.chtf.card.manage.article.pojo.Article;
import cn.org.chtf.card.manage.article.pojo.ArticlePageModel;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DecoratorEbsNoticeService {

    /**
     * 更新文章，包括标题
     *
     * @param notice 文章实体，根据主键是否为0，判断是插入还是更新
     * @return
     */
    public ResultModel updateNotice(EbsNotice notice);

    /**
     * 批量删除
     *
     * @param noticeIdList 主键列表
     * @return
     */
    public ResultModel deleteNotices(Integer[] noticeIdList);

    /**
     * 获取文章列表，分页
     *
     * @param page 分页及搜索实体
     * @return
     */
    public ResultModel getNotices(PageModel page);

    /**
     * 获取文章实体
     *
     * @param noticeId 文章主键
     * @return
     */
    public ResultModel getNotice(Integer noticeId);
}
