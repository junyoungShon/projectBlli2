package kr.co.blli.model.scheduler;

import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.vo.BlliBabyVO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailScheduler {
	@Resource
	private MemberDAO memberDAO;
	
	@Scheduled(cron = "00 37 17 * * *")
	public void schedulerTester(){
		System.out.println("메일보낸다.");
	}
}
