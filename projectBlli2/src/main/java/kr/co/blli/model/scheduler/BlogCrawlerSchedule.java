package kr.co.blli.model.scheduler;

import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.vo.BlliBabyVO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BlogCrawlerSchedule {
	@Resource
	private MemberDAO memberDAO;
	
	@Scheduled(cron = "00 47 17 * * *")
	public void schedulerTester(){
		System.out.println("블로그 크롤링한다.");
	}
}
