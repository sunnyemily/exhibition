package cn.org.chtf.card.support.util.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.org.chtf.card.support.util.IpUtils;

@Aspect
@Component
public class WebLogAspect {
	private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
   
	
//	ThreadLocal是什么
//	　　早在JDK 1.2的版本中就提供Java.lang.ThreadLocal，ThreadLocal为解决多线程程序的并发问题提供了一种新的思路。使用这个工具类可以很简洁地编写出优美的多线程程序。
//	　　当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
//	　　从线程的角度看，目标变量就象是线程的本地变量，这也是类名中“Local”所要表达的意思。
//	　　所以，在Java中编写线程局部变量的代码相对来说要笨拙一些，因此造成线程局部变量没有在Java开发者中得到很好的普及。
//	ThreadLocal的接口方法
//	ThreadLocal类接口很简单，只有4个方法，我们先来了解一下：
//	void set(Object value)设置当前线程的线程局部变量的值。
//	public Object get()该方法返回当前线程所对应的线程局部变量。
//	public void remove()将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
//	protected Object initialValue()返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。
//	　　值得一提的是，在JDK5.0中，ThreadLocal已经支持泛型，该类的类名已经变为ThreadLocal<T>。API方法也相应进行了调整，新版本的API方法分别是void set(T value)、T get()以及T initialValue()。
    ThreadLocal<LoggerEntity> loggerEntityThreadLocal = new ThreadLocal<LoggerEntity>();
   
    
  //请求开始时间标识
//    使用@Aspect注解将一个java类定义为切面类
//    使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
//    根据需要在切入点不同位置的切入内容
//    使用@Before在切入点开始处切入内容
//    使用@After在切入点结尾处切入内容
//    使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
//    使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
//    使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
    
    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
     @Pointcut("execution(public * c1n.org.chtf.card.*.*Controller.*(..)) || execution(public * c1n.org.chtf.card.manage.*.controller.*Controller.*(..))")
     public void webLog(){}
     
     /** 
      * 前置通知，方法调用前被调用 
      * @param joinPoint 
      */  
     @Before("webLog()")
     public void doBefore(JoinPoint joinPoint){
    	 
        
       // 接收到请求，记录请求内容
        logger.info("WebLogAspect.doBefore()");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //创建日志实体
        LoggerEntity logger = new LoggerEntity();
        //请求开始时间
        logger.setStartTime(System.currentTimeMillis());
        //获取请求sessionId
        String sessionId = request.getRequestedSessionId();
        //请求路径
        String url = request.getRequestURI();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        //设置客户端ip
        logger.setClientIp(IpUtils.getIpAddr(request));
        //设置请求方法
        logger.setMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        logger.setType(IpUtils.getRequestType(request));
        //设置请求地址
        logger.setUri(url);
        //设置sessionId
        logger.setSessionId(sessionId);
        //请求的类及名称
        logger.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //设置请求参数内容json字符串
        logger.setParamData(paramData);
        loggerEntityThreadLocal.set(logger);
        
     }
     
     /** 
      * 后置返回通知 
      * 这里需要注意的是: 
      *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息 
      *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 
      * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值 
      * @param joinPoint 
      * @param keys 
      */  
     @AfterReturning(value = "webLog()",returning = "returnData")
     public void  doAfterReturning(JoinPoint joinPoint,Object returnData){
       // 处理完请求，返回内容
        logger.info("WebLogAspect.doAfterReturning()");
        logger.info("RETURN DATA:"+ returnData);
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        
        //获取请求错误码
        int status = response.getStatus();
        //获取本次请求日志实体
        LoggerEntity loggerEntity = loggerEntityThreadLocal.get();
        //请求结束时间
        loggerEntity.setEndTime(System.currentTimeMillis());
        //设置请求时间差
        loggerEntity.setTimeConsuming(Integer.valueOf((loggerEntity.getEndTime() - loggerEntity.getStartTime())+""));
        //设置返回时间
        loggerEntity.setReturnTime(loggerEntity.getEndTime() + "");
        //设置返回错误码
        loggerEntity.setHttpStatusCode(status+"");
        //设置返回值
        loggerEntity.setReturnData(JSON.toJSONString(returnData,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue));
        //执行将日志写入数据库
        logger.info(JSON.toJSONString(loggerEntity));
        
     }
     
     
     /** 
      * 后置异常通知 
      *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法； 
      *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行， 
      *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。 
      * @param joinPoint 
      * @param exception 
      */  
     @AfterThrowing(value = "webLog()",throwing = "exception")  
     public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){  
         //目标方法名：  
    	 LoggerEntity loggerEntity = loggerEntityThreadLocal.get();
    	 loggerEntity.setExceptionMessage(exception.getMessage());
    	 logger.info(JSON.toJSONString(loggerEntity));
//         if(exception instanceof NullPointerException){  
//             System.out.println("发生了空指针异常!!!!!");  
//         }  
     }  
   
     /** 
      * 后置最终通知（目标方法只要执行完了就会执行后置通知方法） 
      * @param joinPoint 
      */  
//     @After(value = "webLog()")  
//     public void doAfterAdvice(JoinPoint joinPoint){  
//         System.out.println("后置通知执行了!!!!");  
//     }  
   
     /** 
      * 环绕通知： 
      *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。 
      *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型 
      */  
//     @Around(value = "webLog()")  
//     public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){  
//         System.out.println("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());  
//         try {//obj之前可以写目标方法执行前的逻辑  
//             Object obj = proceedingJoinPoint.proceed();//调用执行目标方法  
//             return obj;  
//         } catch (Throwable throwable) {  
//             throwable.printStackTrace();  
//         }  
//         return null;  
//     }  
}
