package kr.co.blli.model.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.blli.model.posting.PostingDAO;
import kr.co.blli.model.product.ProductDAO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.springframework.stereotype.Service;

@Service
public class PostingMarker {
	@Resource
	private PostingDAO postingDAO;
	@Resource
	private ProductDAO productDAO;
	
	//포스팅 채점 기준 상수
	//체류시간 배점0
	final int RESIDENCETIMEPOINTDISTRIBUTION = 16;
	//스크랩 배점8
	final int SCRAPEPOINTDISTRIBUTION = 8;
	//조회수 배점4
	final int VIEWCOUNTPOINTDISTRIBUTION = 4;
	//네이버 댓글 수 배점8
	final int NAVERREPLYPOINTDISTRIBUTION = 8;
	//작성일 기준 점수 배점0
	final int WRITTENDATEPOINTDISTRIBUTION = 8;
	//미디어 수 기준 점수 배점16
	final int MEDIAPOINTDISTRIBUTION = 16;
	//포스팅 좋아요 싫어요 기준 점수 배점16
	final int LIKEDISLIKEPOINTDISTRIBUTION = 16;
	//네이버 검색 순위 기준 점수 배점24
	final int NAVERSEARCHRANKINGDISTRIBUTION = 24;
	
	//소제품 채점 기준 상수
	//출시일 기준 배점
	final int REALEASEDATEPOINTDISTRIBUTION = 10;
	//상세페이지 조회수 기준 배점
	final int DETAILVIEWCOUNTPOINTDISTRIBUTION = 15;
	//찜수 기준 배점
	final int DIBSCOUNTPOINTDISTRIBUTION = 20;
	//구매링크 클릭 수
	final int BUYLINKCLICKPOINTDISTRIBUTION = 25;
	//네이버 쇼핑 순위 기준 점수 배점
	final int NAVERSHOPINGRANKINGDISTRIBUTION = 30;
	
	public void productMarkering() throws ParseException{
		List<BlliSmallProductVO> blliProductVOList = productDAO.selectAllSmallProduct();
		//전체 소제품 갯수
		int productNum = blliProductVOList.size();
		//상세보기 조회수 관련 맴 객체 생성
		HashMap<String, Double> detailViewCountMap = new HashMap<String, Double>();
		//찜 수 관련 맵 객체 생성
		HashMap<String, Double> dibsCountMap = new HashMap<String, Double>();
		//구매링크 클릭 수 맵 객체 생성
		HashMap<String, Double> buyLinkClickCountMap = new HashMap<String, Double>();
		
		for(int i=0;i<productNum;i++){
			BlliSmallProductVO blliSmallProductVO = blliProductVOList.get(i);
			//포스팅 점수 초기화
			blliSmallProductVO.setSmallProductScore(0);
			
			String smallProductId = blliSmallProductVO.getSmallProductId();
			
			//출시일과 현재 시간의 차이를 기록한 변수
			//double diffNowFromReleaseDate = dayCounter(blliSmallProductVO.getProductRegisterDay());
			//디비 삽입일과 현재 시간의 차이를 기록한 변수
			double diffNowFromDbInsertDate = dayCounter(blliSmallProductVO.getProductDbInsertDate());
			//하루 평균 상세페이지 조회수를 기록할 변수
			double avgDetailViewCount = 0.0;
			//하루 평균 상세페이지 조회수와 smallProductId를 넣어준다.
			avgDetailViewCount = (Math.round(((double)blliSmallProductVO.getDetailViewCount()/diffNowFromDbInsertDate)*100))*0.01;
			detailViewCountMap.put(smallProductId, avgDetailViewCount);
			
			
			//하루 평균 찜수를 기록할 변수
			double avgDibsCount = 0.0;
			if(diffNowFromDbInsertDate!=0){
				avgDibsCount = (double)blliSmallProductVO.getSmallProductDibsCount() / (double) diffNowFromDbInsertDate;
			}
			//하루 평균 찜수를 기록
			dibsCountMap.put(smallProductId, avgDibsCount);
			
			//하루 평균 구매링크 클릭 수
			double avgBuyLinkClickCount = 0.0;
			//소제품 구매링크 클릭 총 합
			int selectBuyLinkClickCount = 0;
			if(smallProductId!=null){
				try {
					selectBuyLinkClickCount = productDAO.selectBuyLinkClickCountBySmallProductId(smallProductId);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if(diffNowFromDbInsertDate!=0){
				avgBuyLinkClickCount = (double)selectBuyLinkClickCount / (double) diffNowFromDbInsertDate;
			}
			if(smallProductId!=null){
				System.out.println("전체사이즈는 ?"+productNum+" "+smallProductId+" : "+i);
			//포스팅의 url과 smallProductId를 복합키로하여 하루 평균 스크랩수를 값에 넣는다.
			buyLinkClickCountMap.put(smallProductId, avgBuyLinkClickCount);
			System.out.println(buyLinkClickCountMap.size());
			//하루 평균 스크랩 수를 맵에 넣어준다.
			}
		}
		
		for(int i=0;i<productNum;i++){
			BlliSmallProductVO blliSmallProductVO = blliProductVOList.get(i);
			//String compositeKey = blliPostingVO.getPostingUrl()+","+blliPostingVO.getSmallProductId();
			//출시일과 현재 시간의 차이를 기록한 변수
			double diffNowFromReleaseDate = dayCounter(blliSmallProductVO.getProductRegisterDay());
			//디비 삽입일과 현재 시간의 차이를 기록한 변수
			//double diffNowFromDbInsertDate = dayCounter(blliSmallProductVO.getProductDbInsertDate());	
		
			//상세 보기 조회수를 통한 
			detailViewCountMap = (HashMap<String,Double>)sortByValue(detailViewCountMap);
			//맵에 담긴 값을 통해 순위를 매긴다.
			detailViewCountMap = mapRankingMaker(detailViewCountMap);
			//맵에 담긴 순위를 통해 점수를 할당하고, 포스팅 리스트에 세팅해준다.
			markByRanking(detailViewCountMap, blliSmallProductVO, productNum, DETAILVIEWCOUNTPOINTDISTRIBUTION);
			
			//하루평균 찜수를 통한 채점
			dibsCountMap = (HashMap<String,Double>)sortByValue(dibsCountMap);
			dibsCountMap = mapRankingMaker(dibsCountMap);
			markByRanking(dibsCountMap, blliSmallProductVO, productNum,DIBSCOUNTPOINTDISTRIBUTION);
			
			//구매링크 클릭  수 채점
			buyLinkClickCountMap = (HashMap<String,Double>)sortByValue(buyLinkClickCountMap);
			buyLinkClickCountMap = mapRankingMaker(buyLinkClickCountMap);
			markByRanking(buyLinkClickCountMap, blliSmallProductVO, productNum,BUYLINKCLICKPOINTDISTRIBUTION);
			
			
			//제품 출시일을 기준으로한 채점
			int releaseDatePoint = (int) (REALEASEDATEPOINTDISTRIBUTION-(diffNowFromReleaseDate/180));
			if(releaseDatePoint<0){
				releaseDatePoint = 0;
			}
			System.out.println("출시일 기준 점수 : "+releaseDatePoint);
			blliSmallProductVO.setSmallProductScore(blliSmallProductVO.getSmallProductScore()+releaseDatePoint);
			
			//네이버 쇼핑 순위를 통한 채점
			int totalSmallProductNum = productDAO.selectSmallProductNumByMidCategoryId(blliSmallProductVO.getMidCategoryId());
			int naverShoppingRank = blliSmallProductVO.getNaverShoppingRank();
			int naverShoppinRankPoint = (1-(naverShoppingRank/totalSmallProductNum))*NAVERSHOPINGRANKINGDISTRIBUTION;
			System.out.println("네이버 쇼핑 기준 점수 : "+naverShoppinRankPoint);
			blliSmallProductVO.setSmallProductScore(blliSmallProductVO.getSmallProductScore()+naverShoppinRankPoint);
			System.out.println("총점 : "+blliSmallProductVO.getSmallProductScore());
			productDAO.updateProductScore(blliSmallProductVO);
		}
		
	}
	
	/**
	  * @Method Name : dayCounter
	  * @Method 설명 : 기준 날짜 입력 시 현재 날짜와 차이를 계산해줍니다.
	  * @작성일 : 2016. 2. 5.
	  * @작성자 : junyoung
	  * @param standardDate
	  * @return
	  * @throws ParseException
	 */
	public int dayCounter(String standardDate) throws ParseException{
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDay = null;
		birthDay = formatter.parse(standardDate);
		Date nowDate = new Date();
		long diff = nowDate.getTime() - birthDay.getTime() ;
		long diffDays = diff/(24*60*60*1000);
		if(diffDays<0){
			diffDays = diffDays-1;
		}
		return (int) diffDays;
	}
	/**
	  * @Method Name : markByRanking
	  * @Method 설명 : 랭킹과 배점에 따라 점수를 부여합니다.
	  * @작성일 : 2016. 2. 5.
	  * @작성자 : junyoung
	  * @param map
	  * @param blliPostingVO
	  * @param postingNum
	  * @param pointDistribution
	  * @return
	 */
	public Object markByRanking(HashMap<String, Double> map,Object vo,int postingNum,int pointDistribution){
		if(vo instanceof BlliPostingVO){
			BlliPostingVO blliPostingVO = (BlliPostingVO) vo;
			String key = blliPostingVO.getPostingUrl()+","+blliPostingVO.getSmallProductId();
			double ranking = map.get(key);
			double givenPoint = (Math.round((((1-(ranking/postingNum))*pointDistribution)*10)))*0.1;
			blliPostingVO.setPostingScore((int) (blliPostingVO.getPostingScore()+givenPoint));
			System.out.println("복합키"+key+"점수"+givenPoint+"랭킹:"+ranking);
			vo = blliPostingVO;
		}else if(vo instanceof BlliSmallProductVO){
			BlliSmallProductVO blliSmallProductVO =  (BlliSmallProductVO) vo;
			String key = blliSmallProductVO.getSmallProductId();
			double ranking = map.get(key);
			double givenPoint = (Math.round((((1-(ranking/postingNum))*pointDistribution)*10)))*0.1;
			blliSmallProductVO.setSmallProductScore((int) ((blliSmallProductVO.getSmallProductScore())+givenPoint));
			System.out.println("복합키"+key+"점수"+givenPoint+"랭킹:"+ranking);
			vo = blliSmallProductVO;
		}
		return vo;
	}
	/**
	  * @Method Name : mapRankingMaker
	  * @Method 설명 : 값에 따라 전체에서의 랭킹을 설정해줍니다..
	  * @작성일 : 2016. 2. 5.
	  * @작성자 : junyoung
	  * @param map
	  * @return
	 */
	public HashMap<String, Double> mapRankingMaker(HashMap<String,Double> map){
		Iterator<String> it = map.keySet().iterator();
		double tempResidenceTime = 0.0;
		double ranking = 0.0;
		double interval = 0.0;
			while(it.hasNext()){
				Boolean flag =  false;
				String key = it.next();
				if(map.get(key)!=tempResidenceTime){
					tempResidenceTime = map.get(key);
					double currentRanking = (ranking+1.0)+interval;
					map.replace(key, currentRanking);
					flag = true;
				}else{
					interval++;
					if(interval==0.0){
						map.replace(key, ranking+1);
					}else{
						map.replace(key, ranking);
					}
					flag = false;
				}
				if(flag){
					ranking= ranking+1+interval;
					interval=0.0;
				}
			}
			return map;
	}
	
	/**
	  * @Method Name : sortByValue
	  * @Method 설명 : 맵의 밸류에 따라 정렬해 줍니다.
	  * @작성일 : 2016. 2. 5.
	  * @작성자 : junyoung
	  * @param map
	  * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){
	    List<Map.Entry<K, V>> list =
	        new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );
	
	    Map<K, V> result = new LinkedHashMap<K, V>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	return result;

	}
	
	/**
	  * @Method Name : productMarkering
	  * @Method 설명 : posting 채점 메서드
	  * @작성일 : 2016. 2. 5.
	  * @작성자 : junyoung
	  * @throws ParseException
	 */
	public void postingMarkering() throws ParseException{
		//전체 포스팅 리스트 출력 
		List<BlliPostingVO> allPostingList = postingDAO.selectAllPosting();
		//전체 포스팅 갯수
		int postingNum = allPostingList.size();
		//체류 시간 맵 객체 생성
		HashMap<String, Double> residenceTimeMap = new HashMap<String, Double>();
		//스크랩 수 맵 객체 생성
		HashMap<String, Double> scrapeNumMap = new HashMap<String, Double>();
		//조회 수 맵 객체 생성
		HashMap<String, Double> viewCountMap = new HashMap<String, Double>();
		//네이버 댓글 수 맵 객체 생성
		HashMap<String, Double> replyCountMap = new HashMap<String, Double>();
	
		for(int i=0;i<postingNum;i++){
			BlliPostingVO blliPostingVO = allPostingList.get(i);
			//포스팅 점수 초기화
			blliPostingVO.setPostingScore(0);
			
			String compositeKey = blliPostingVO.getPostingUrl()+","+blliPostingVO.getSmallProductId();
			//평균 체류시작을 기록할 변수
			double avgResidenceTime = 0.0;
			//작성일과 현재 시간의 차이를 기록한 변수
			double diffNowFromWrittenDate = dayCounter(blliPostingVO.getPostingDate());
			//디비 삽입일과 현재 시간의 차이를 기록한 변수
			System.out.println(blliPostingVO.getPostingDbInsertDate());
			double diffNowFromDbInsertDate = dayCounter(blliPostingVO.getPostingDbInsertDate());
			
			//체류 시간과 포스팅 복합키를 맵에 넣어준다
			if(blliPostingVO.getPostingViewCount()!=0){
				avgResidenceTime = (Math.round(((double)blliPostingVO.getPostingTotalResidenceTime()/(double)blliPostingVO.getPostingViewCount())*100))*0.01;
			}
			//포스팅의 url과 smallProductId를 복합키로하여 평균 시간을 값에 넣는다.
			residenceTimeMap.put(compositeKey, avgResidenceTime);
			
			//하루 평균 스크랩 수와 복합키를 맵에 넣어준다.
			double avgScrapeNum = 0.0;
			if(diffNowFromDbInsertDate!=0){
				avgScrapeNum = (double)blliPostingVO.getIsScrapped() / (double) diffNowFromDbInsertDate;
			}
			//포스팅의 url과 smallProductId를 복합키로하여 하루 평균 스크랩수를 값에 넣는다.
			scrapeNumMap.put(compositeKey, avgScrapeNum);
			//하루 평균 스크랩 수를 맵에 넣어준다.
			
			//하루 평균 조회수와 복합키를 맵에 넣어준다.
			double avgViewCount = 0.0;
			if(diffNowFromDbInsertDate!=0){
				avgViewCount = (double)blliPostingVO.getPostingViewCount() / (double) diffNowFromDbInsertDate;
			}
			//포스팅의 url과 smallProductId를 복합키로하여 하루 평균 스크랩수를 값에 넣는다.
			viewCountMap.put(compositeKey, avgViewCount);
			//하루 평균 스크랩 수를 맵에 넣어준다.
			
			//하루 평균 네이버 댓글 수 와 복합키를 맵에 넣어준다.
			double avgReplyCount = 0.0;
			if(diffNowFromDbInsertDate!=0){
				avgReplyCount = (double)blliPostingVO.getPostingReplyCount() / (double) diffNowFromWrittenDate;
			}
			//포스팅의 url과 smallProductId를 복합키로하여 하루 평균 네이버 댓글 수를 값에 넣는다.
			replyCountMap.put(compositeKey, avgReplyCount);
			//하루 평균 스크랩 수를 맵에 넣어준다.
		}
		
		for(int i=0;i<postingNum;i++){
			BlliPostingVO blliPostingVO = allPostingList.get(i);
			//String compositeKey = blliPostingVO.getPostingUrl()+","+blliPostingVO.getSmallProductId();
			//작성일과 현재 시간의 차이를 기록한 변수
			double diffNowFromWrittenDate = dayCounter(blliPostingVO.getPostingDate());
			//디비 삽입일과 현재 시간의 차이를 기록한 변수
			//double diffNowFromDbInsertDate = dayCounter(blliPostingVO.getPostingDbInsertDate());			
			
			System.out.println(blliPostingVO.getPostingDbInsertDate());
			//체류시간을 통한 채점
			//맵의 값을s 기준으로 정렬한다.
			residenceTimeMap = (HashMap<String,Double>)sortByValue(residenceTimeMap);
			//맵에 담긴 값을 통해 순위를 매긴다.
			residenceTimeMap = mapRankingMaker(residenceTimeMap);
			//맵에 담긴 순위를 통해 점수를 할당하고, 포스팅 리스트에 세팅해준다.
			markByRanking(residenceTimeMap, blliPostingVO, postingNum,RESIDENCETIMEPOINTDISTRIBUTION);
			
			//스크랩수를 통한 채점
			scrapeNumMap = (HashMap<String,Double>)sortByValue(scrapeNumMap);
			scrapeNumMap = mapRankingMaker(scrapeNumMap);
			markByRanking(scrapeNumMap, blliPostingVO, postingNum,SCRAPEPOINTDISTRIBUTION);
			
			//조회수를 통한 채점
			viewCountMap = (HashMap<String,Double>)sortByValue(viewCountMap);
			viewCountMap = mapRankingMaker(viewCountMap);
			markByRanking(viewCountMap, blliPostingVO, postingNum,VIEWCOUNTPOINTDISTRIBUTION);
			
			//네이버 댓글 수를 통한 채점
			replyCountMap = (HashMap<String,Double>)sortByValue(replyCountMap);
			replyCountMap = mapRankingMaker(replyCountMap);
			markByRanking(replyCountMap, blliPostingVO, postingNum,NAVERREPLYPOINTDISTRIBUTION);
			
			//포스팅 작성일을 기준으로한 채점
			int writtenDatePoint = (int) (WRITTENDATEPOINTDISTRIBUTION-(diffNowFromWrittenDate/90));
			if(writtenDatePoint<0){
				writtenDatePoint = 0;
			}
			System.out.println("작성일 기준 점수 : "+writtenDatePoint);
			blliPostingVO.setPostingScore(blliPostingVO.getPostingScore()+writtenDatePoint);
			
			//미디어 수를 기준으로한 채점
			int mediaCount = blliPostingVO.getPostingMediaCount();
			int mediaPoint = 0;
			if(mediaCount>=MEDIAPOINTDISTRIBUTION){
				mediaPoint = MEDIAPOINTDISTRIBUTION;
			}else if(mediaCount>0&&mediaCount<16){
				mediaPoint = MEDIAPOINTDISTRIBUTION - (16-mediaCount);
			}
			System.out.println("미디어 기준 점수 : "+mediaPoint);
			blliPostingVO.setPostingScore(blliPostingVO.getPostingScore()+mediaPoint);
			
			//포스팅 좋아요 싫어요를 통한 채점
			int postingLike = blliPostingVO.getPostingLikeCount();
			int postingDisLike = blliPostingVO.getPostingDislikeCount();
			int postingLikePoint = 0;
			if((postingLike-(postingDisLike*2))>0&&(postingLike-(postingDisLike*2))<16){
				postingLikePoint = postingLike-(postingDisLike*2);
			}else if((postingLike-(postingDisLike*2)>=LIKEDISLIKEPOINTDISTRIBUTION)){
				postingLikePoint = LIKEDISLIKEPOINTDISTRIBUTION;
			}else if((postingLike-(postingDisLike*2))<0){
				postingLikePoint = 0;
			}
			System.out.println("좋아요 싫어요 점수: "+postingLikePoint);
			blliPostingVO.setPostingScore(blliPostingVO.getPostingScore()+postingLikePoint);
			//네이버 검색 순위를 통한 채점
			int postingNumInSameSmallProduct = productDAO.selectPostingNumBySmallProductId(blliPostingVO.getSmallProductId());
			int postingSearchRankPoint = (1-(blliPostingVO.getPostingRank()/postingNumInSameSmallProduct))*NAVERSEARCHRANKINGDISTRIBUTION;
			System.out.println("네이버 순위를 통한 점수: "+postingSearchRankPoint);
			blliPostingVO.setPostingScore(blliPostingVO.getPostingScore()+postingSearchRankPoint);
			System.out.println("총점 : "+blliPostingVO.getPostingScore());
			postingDAO.updatePostingScore(blliPostingVO);
			System.out.println(i);
		}
		
	}
}
