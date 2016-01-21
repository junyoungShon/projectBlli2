package kr.co.blli.model.scheduler;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailScheduler {
	@Resource
	private MemberDAO memberDAO;
	
	@Scheduled(cron = "00/10 * * * * *")
	public void schedulerTester(){
	}
}
