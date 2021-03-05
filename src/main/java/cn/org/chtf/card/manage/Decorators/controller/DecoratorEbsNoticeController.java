package cn.org.chtf.card.manage.Decorators.controller;

import cn.org.chtf.card.manage.Decorators.service.DecoratorEbsNoticeService;
import cn.org.chtf.card.manage.Exhibitors.model.EbsNotice;
import cn.org.chtf.card.manage.system.service.SysSessionService;
import cn.org.chtf.card.manage.user.pojo.User;
import cn.org.chtf.card.support.util.PageModel;
import cn.org.chtf.card.support.util.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/Decorators/ebsNoticeManage")
public class DecoratorEbsNoticeController {

    @Autowired
    private DecoratorEbsNoticeService decoratorEbsNoticeService;
    
    @Autowired
    private SysSessionService sysSessionService;

    /**
     * 更新文章，包括标题
     *
     * @param notice 文章实体，根据主键是否为0，判断是插入还是更新
     * @return
     */
    @RequestMapping(value = "/updateNotice")
    public ResultModel updateNotice(EbsNotice notice, HttpServletRequest request, HttpSession session) {
        String sessionId = sysSessionService.getSessionID(request);
        User user = (User) session.getAttribute("user");
        notice.setSession(sessionId);
        notice.setAuthorId(user.getId());
        notice.setAuthorName(user.getName());
        notice.setAuthorAccount(user.getUsername());
        return decoratorEbsNoticeService.updateNotice(notice);
    }

    /**
     * 更新文章，包括标题
     *
     * @param notice 文章实体，根据主键是否为0，判断是插入还是更新
     * @return
     */
    @RequestMapping(value = {"/onlineNotice", "/offlineNotice"})
    public ResultModel updateNoticeStatus(EbsNotice notice, HttpServletRequest request, HttpSession session) {
        String sessionId = sysSessionService.getSessionID(request);
        User user = (User) session.getAttribute("user");
        notice.setSession(sessionId);
        notice.setAuthorId(user.getId());
        notice.setAuthorName(user.getName());
        notice.setAuthorAccount(user.getUsername());
        return decoratorEbsNoticeService.updateNotice(notice);
    }

    /**
     * 批量删除
     *
     * @param noticeIdList 主键列表
     * @return
     */
    @RequestMapping(value = "/deleteNotices")
    public ResultModel deleteNotices(@RequestParam(value = "noticeIdList[]") Integer[] noticeIdList) {
        return decoratorEbsNoticeService.deleteNotices(noticeIdList);
    }

    /**
     * 获取文章列表，分页
     *
     * @param page 分页及搜索实体
     * @return
     */
    @RequestMapping(value = "/getNotices")
    public ResultModel getNotices(PageModel page, HttpServletRequest request) {
        String sessionId = sysSessionService.getSessionID(request);
        page.setSession(sessionId);
        return decoratorEbsNoticeService.getNotices(page);
    }

    /**
     * 获取文章实体
     *
     * @param noticeId 文章主键
     * @return
     */
    @RequestMapping(value = "/getNotice")
    public ResultModel getNotice(Integer noticeId) {
        return decoratorEbsNoticeService.getNotice(noticeId);
    }
}
