package cn.mxlog.sscloud.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseActor
{
	protected final Logger logger = LoggerFactory.getLogger(this
			.getClass());

//	public TaskRunningSessionLock taskLock = new TaskRunningSessionLock();
//
////	@Transactional
////	public void runTaskSafely(Runnable executer)
////	{
////		executer.run();
////	}
//
//	@Transactional
//	public void runTaskSafely(Runnable executer)
//	{
//		// if (!running) {
//		// return;
//		// }
//		boolean sessionLocked = taskLock.takeSession();
//		try
//		{
//			if (sessionLocked)
//			{
//				executer.run();
//			} else
//			{
//				logger.debug("Task is running, couldn't lock the Session");
//			}
//		} catch (Exception e)
//		{
//			logger.error("Unexpected exception!!!", e);
//		} finally
//		{
//			if (sessionLocked)
//			{
//				logger.debug("Releasing the locked Session... ");
//				taskLock.releaseSession();
//			}
//		}
//	}
}
