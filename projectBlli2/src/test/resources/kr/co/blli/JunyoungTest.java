package kr.co.blli;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.member.MemberDAO;
import kr.co.blli.model.member.MemberService;
import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.product.ProductService;
import kr.co.blli.model.vo.BlliBabyVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
*    TDD : 테스트 주도 개발(test-driven development, TDD)은 
*            매우 짧은 개발 사이클을 반복하는 소프트웨어 개발 프로세스
*            
*    JUnit: 자바 단위 테스트를 위한 TDD 프레임워크
*    
*    아래 라이브러리가 maven의 pom.xml에 추가되어야 한다. 
      <!-- spring junit  -->
 <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-test</artifactId>
           <version>${org.springframework-version}</version>            
       </dependency>
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class JunyoungTest {
	@Resource
	private MemberService memberService;
	@Resource
	private MemberDAO memberDAO;
	@Resource
	private ProductService productService;
	@Resource
	private ProductDAO productDAO;
	
	@Test
	public void test() throws ParseException{
		List<BlliBabyVO> list =  memberService.selectBabyListByMemberId("imvestt@hanmail.net");
		System.out.println(list);
		for(int i=0;i<list.size();i++){
			list.get(i).getBabyBirthday();
			SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDay = null;
			try {
				birthDay = formatter.parse(list.get(i).getBabyBirthday());
			} catch (ParseException e) {
			}
			Date NowDate = new Date();
			long diff = NowDate.getTime() - birthDay.getTime() ;
			long diffDays = diff/(24*60*60*1000);
			if(diffDays==0){
				System.out.println("오늘 생일입니다.");
			}else if(diffDays>0){
				System.out.println(i+"번째아이는 태어난지"+diffDays);
			}else if(diffDays<0){
				System.out.println(i+"번째아이의 예정일은"+diffDays+"남았습니다.");
			}
		}
	}
}