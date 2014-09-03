package com.examw.collector.quartz;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.examw.collector.domain.UpdateLog;
import com.examw.collector.service.IGradeUpdateService;
import com.examw.collector.service.IPackageUpdateService;
import com.examw.collector.service.ISubjectUpdateService;
import com.examw.collector.service.IUpdateLogService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月28日 上午8:47:09.
 */
public class QuartzJob extends QuartzJobBean{
	private static final Logger logger = Logger.getLogger(QuartzJob.class); 
	private ISubjectUpdateService subjectUpdateService;
	private IGradeUpdateService gradeUpdateService;
	private IPackageUpdateService packageUpdateService;
	private IUpdateLogService updateLogService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("================开始执行定时任务====================");
		try{
			//获取JobExecutionContext中的service对象    
            SchedulerContext skedCtx = context.getScheduler().getContext();     
            //获取SchedulerContext中的service    
            //这里的service就是通过配置文件 配置的    
            gradeUpdateService = (IGradeUpdateService)skedCtx.get("gradeUpdateService"); 
            subjectUpdateService = (ISubjectUpdateService)skedCtx.get("subjectUpdateService");
            packageUpdateService = (IPackageUpdateService)skedCtx.get("packageUpdateService");
            updateLogService = (IUpdateLogService)skedCtx.get("updateLogService");    
            //获取 当前的trigger 名称，    
//            Trigger trigger = context.getTrigger();
//            String name = trigger.getName();  
            try{
            	logger.info("开始进行科目的比对更新................");
            	this.subjectUpdateService.update("quartz");	//定时器更新
            	//增加更新记录
            	UpdateLog log1 = new UpdateLog(UUID.randomUUID().toString(),"科目更新比对",UpdateLog.TYPE_UPDATE_SUBJECT,new Date());
            	this.updateLogService.save(log1);
            	logger.info("科目比对更新任务完成");
            }catch(Exception e){
            	e.printStackTrace();
            }
            try{
            	logger.info("开始进行班级的比对更新................");
            	this.gradeUpdateService.update("quartz");	//定时器更新
            	//增加更新记录
            	UpdateLog log2 = new UpdateLog(UUID.randomUUID().toString(),"班级更新比对",UpdateLog.TYPE_UPDATE_GRADE,new Date());
            	this.updateLogService.save(log2);
            	logger.info("班级比对更新任务完成");
            }catch(Exception e){
            	e.printStackTrace();
            }
            try{
            	logger.info("开始进行套餐的比对更新................");
            	this.packageUpdateService.update("quartz");	//定时器更新
            	//增加更新记录
            	UpdateLog log3 = new UpdateLog(UUID.randomUUID().toString(),"套餐更新比对",UpdateLog.TYPE_UPDATE_PACK,new Date());
            	this.updateLogService.save(log3);
            	logger.info("套餐比对更新任务完成");
            }catch(Exception e){
            	e.printStackTrace();
            }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
