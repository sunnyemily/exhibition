package cn.org.chtf.card.manage.job;

import java.io.IOException;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.org.chtf.card.manage.common.dao.CommonMapper;

@Component
public class jobController implements ApplicationContextAware {

	@Autowired
	private CommonMapper commonDao;

	@Resource
	private UploadTencent uploadTencent;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Resource
	private PushCardToAccessControl pushCardToAccessControl;

	@Resource
	private PushCardToAccessControlForError pushCardToAccessControlForError;

	private static ApplicationContext context;

	// 企业视频转码定时任务
	@Scheduled(cron = "0 */2 * * * ?")
	// 5分钟一次
	public void scheduler() throws IOException, ParserConfigurationException,
			TransformerException {
		Runnable oneRunnable = uploadTencent;
		Thread oneThread = new Thread(oneRunnable);
		oneThread.start();
	}

	// 推送人员证件到门禁系统
	@Scheduled(cron = "*/20 * * * * ?")
	// 30秒一次
	public void scheduler1() throws IOException, ParserConfigurationException,
			TransformerException {
		Runnable oneRunnable = pushCardToAccessControl;
		Thread oneThread = new Thread(oneRunnable);
		oneThread.start();
		
	}

	// 推送失败的人员证件到门禁系统
	@Scheduled(cron = "0 */1 * * * ?")
	// 1分钟一次
	public void scheduler0() throws IOException, ParserConfigurationException,
			TransformerException {
		Runnable oneRunnable = pushCardToAccessControlForError;
		Thread oneThread = new Thread(oneRunnable);
		oneThread.start();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
}
