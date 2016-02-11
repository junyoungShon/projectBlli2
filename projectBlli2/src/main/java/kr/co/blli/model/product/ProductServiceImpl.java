package kr.co.blli.model.product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBuyLinkClickVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapeVO;
import kr.co.blli.model.vo.BlliMemberVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPagingBean;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
import kr.co.blli.model.vo.BlliSmallProductVO;
import kr.co.blli.model.vo.ListVO;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
	@Resource
	private ProductDAO productDAO;

	/**
	  * @Method Name : selectRecommendingMidCategory
	  * @Method 설명 : 회원의 추천받을 아이이름과 , 아이디를 이용해 추천대상인 중분류 제품을 선정한다.(회원이 추천을 기피했던 중제품 제외)
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliMidCategoryVO> selectRecommendingMidCategory(BlliBabyVO blliBabyVO) {
		//회원의 추천받을 아이이름과 , 아이디를 이용해 추천대상인 중분류 제품을 선정한다.(회원이 추천을 기피했던 중제품 제외)
		List<BlliMidCategoryVO> blliMidCategoryVOList = productDAO.selectRecommendingMidCategory(blliBabyVO);
		//기피 중분류들을 추출한다.
		List<BlliNotRecommMidCategoryVO> notRecommMidCategoryList = productDAO.selectNotRecommMidCategoryList(blliBabyVO); 
		//이중 for문으로 추천받을 중분류 제품에서 기피한 중제품을 제거한다.
		if(blliMidCategoryVOList!=null&&notRecommMidCategoryList!=null)
		for(int i=0;i<notRecommMidCategoryList.size();i++){
			for(int j=0;j<blliMidCategoryVOList.size();j++){
				if(notRecommMidCategoryList.get(i).getMidCategoryId().equals(blliMidCategoryVOList.get(j).getMidCategoryId())){
					blliMidCategoryVOList.remove(j);
				}
			}
		}
		return blliMidCategoryVOList;
	}
	/**
	  * @Method Name : deleteRecommendMidCategory
	  * @Method 설명 : 회원이 중분류 카테고리를 추천받고 싶지않을 때 추천 받지 않을 중분류 제품을 삭제해준다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliNotRecommMidCategoryVO
	 */
	@Override
	public void deleteRecommendMidCategory(BlliNotRecommMidCategoryVO blliNotRecommMidCategoryVO) {
		productDAO.deleteRecommendMidCategory(blliNotRecommMidCategoryVO);
	}
	/**
	  * @Method Name : selectSameAgeMomBestPickedSmallProductList
	  * @Method 설명 : 현재 추천 받고 있는 중분류를 기준으로 또래 엄마들이 가장많이 찜한 상품들을 보여준다.
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param blliMidCategoryVOList
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			List<BlliMidCategoryVO> blliMidCategoryVOList, BlliBabyVO blliBabyVO) {
		List<BlliSmallProductVO> blliSmallProductVOList = new ArrayList<BlliSmallProductVO>();
		int recommMidNumber = blliMidCategoryVOList.size();
		if(recommMidNumber>9){
			for(int i=0;i<blliMidCategoryVOList.size();i++){
				HashMap<String,String> paraMap = new HashMap<String, String>();
				paraMap.put("recommMid", blliMidCategoryVOList.get(i).getMidCategory());
				paraMap.put("babyMonthAge",Integer.toString(blliBabyVO.getBabyMonthAge()));
				//중제품 당 찜 상위 1개 만을 가져온다.
				blliSmallProductVOList.add(productDAO.selectSameAgeMomBestPickedSmallProduct(paraMap));
			}
		}else{
			for(int i=0;i<blliMidCategoryVOList.size();i++){
				HashMap<String,String> paraMap = new HashMap<String, String>();
				paraMap.put("recommMid", blliMidCategoryVOList.get(i).getMidCategoryId());
				paraMap.put("babyMonthAge",Integer.toString(blliBabyVO.getBabyMonthAge()));
				// 중제품 당 찜 상위 2개씩을 가져온다.
				List<BlliSmallProductVO> tempList = productDAO.selectSameAgeMomBestPickedSmallProductList(paraMap);
				for(int j=0;j<tempList.size();j++){
					System.out.println(tempList.get(j).getSmallProduct());
					blliSmallProductVOList.add(tempList.get(j));
				}
			}
		}
		for(int i=0;i<blliSmallProductVOList.size();i++){
			blliSmallProductVOList.set(i, productDibChecker(blliBabyVO.getMemberId(), blliSmallProductVOList.get(i)));
		}
		return blliSmallProductVOList;
	}
	/**
	  * @Method Name : selectPostingBySmallProductList
	  * @Method 설명 : <!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param blliMidCategoryVOList
	  * @return
	 */
	@Override
	public List<BlliPostingVO> selectPostingBySmallProductList(List<BlliSmallProductVO> blliSmallProductVOList,String memberId,String pageNum) {
		List<BlliPostingVO> blliPostingVOList = new ArrayList<BlliPostingVO>();
		//파라미터로 사용할 맵을 생성하고, 페이지 번호를 넣는다.
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("pageNum", pageNum);
		//점수순 노출 , 상태(confirmed) , 포스팅 대상 소제품 등을 기준으로 출력<!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
		for(int i=0;i<blliSmallProductVOList.size();i++){
			paraMap.put("smallProductId", blliSmallProductVOList.get(i).getSmallProductId());
			List<BlliPostingVO> tempList = productDAO.selectPostingBySmallProductList(paraMap);
			if(tempList!=null){
				for(int j=0;j<tempList.size();j++){
					blliPostingVOList.add(tempList.get(j));
				}
			}
		}
		//포스팅을 가져올 때 해당 회원이 포스팅을 스크램,좋아요,싫어요 했는지 여부를 파악해준다.
		BlliMemberScrapeVO blliMemberScrapVO = new BlliMemberScrapeVO();
		blliMemberScrapVO.setMemberId(memberId);
		BlliPostingLikeVO blliPostingLikeVO = new BlliPostingLikeVO();
		blliPostingLikeVO.setMemberId(memberId);
		BlliPostingDisLikeVO blliPostingDisLikeVO = new BlliPostingDisLikeVO();
		blliPostingDisLikeVO.setMemberId(memberId);
		
		for(int i=0;i<blliPostingVOList.size();i++){
			blliMemberScrapVO.setPostingUrl(blliPostingVOList.get(i).getPostingUrl());
			blliMemberScrapVO.setSmallProductId(blliPostingVOList.get(i).getSmallProductId());
			blliPostingLikeVO.setPostingUrl(blliPostingVOList.get(i).getPostingUrl());
			blliPostingLikeVO.setSmallProductId(blliPostingVOList.get(i).getSmallProductId());
			blliPostingDisLikeVO.setPostingUrl(blliPostingVOList.get(i).getPostingUrl());
			blliPostingDisLikeVO.setSmallProductId(blliPostingVOList.get(i).getSmallProductId());
			//스크랩 여부 판단.
			if(productDAO.selectThisPostingScrape(blliMemberScrapVO)!=0)
				blliPostingVOList.get(i).setIsScrapped(1);
			else
				blliPostingVOList.get(i).setIsScrapped(0);
			//좋아요 여부판단
			if(productDAO.selectThisPostingLike(blliPostingLikeVO)!=0)
				blliPostingVOList.get(i).setIsLike(1);
			else
				blliPostingVOList.get(i).setIsLike(0);
			//싫어요 여부판단
			if(productDAO.selectThisPostingDisLike(blliPostingDisLikeVO)!=0)
				blliPostingVOList.get(i).setIsDisLike(1);
			else
				blliPostingVOList.get(i).setIsDisLike(0);
			
		}
		return blliPostingVOList;
	}

	@Override
	public int smallProductDib(BlliMemberDibsVO blliMemberDibsVO) {
		int result = 0;
		if(productDAO.deleteDipsInfo(blliMemberDibsVO)==0){
			result = productDAO.insertDipsInfo(blliMemberDibsVO);
			productDAO.updatePlusSmallProductDibsCount(blliMemberDibsVO);
		}else{
			productDAO.updateMinusSmallProductDibsCount(blliMemberDibsVO);
		}
		return result;
	}

	@Override
	public int postingScrape(BlliMemberScrapeVO blliMemberScrapVO) {
		int result = 0;
		if(productDAO.deletePostingScrapeInfo(blliMemberScrapVO)==0){
			result = productDAO.insertPostingScrape(blliMemberScrapVO);
			productDAO.updatePlusPostingScrapeCount(blliMemberScrapVO);
		}else{
			productDAO.updateMinusPostingScrapeCount(blliMemberScrapVO);
		}
		return result;
	}

	@Override
	public int postingLike(BlliPostingLikeVO blliPostingLikeVO) {
		int result = 0;
		if(productDAO.deletePostingLikeInfo(blliPostingLikeVO)==0){
			result = productDAO.insertPostingLikeInfo(blliPostingLikeVO);
			productDAO.updatePlusPostingLikeCount(blliPostingLikeVO);
		}else{
			productDAO.updateMinusPostingLikeCount(blliPostingLikeVO);
		}
		return result;
	}

	@Override
	public int postingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		int result = 0;
		if(productDAO.deletePostingDisLikeInfo(blliPostingDisLikeVO)==0){
			result = productDAO.insertPostingDisLikeInfo(blliPostingDisLikeVO);
			productDAO.updatePlusPostingDisLikeCount(blliPostingDisLikeVO);
		}else{
			productDAO.updateMinusPostingDisLikeCount(blliPostingDisLikeVO);
		}
		return result;
	}
	/**
	 * 
	 * @Method Name : searchMidCategory
	 * @Method 설명 : 검색어가 중분류명과 일치할 시 해당 중분류에 포함되는 소제품 리스트를 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param searchWord
	 * @return
	 */
	@Override
	public ArrayList<BlliSmallProductVO> searchMidCategory(String pageNo, String searchWord) {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageNo);
		map.put("searchWord", searchWord);
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)productDAO.searchMidCategory(map);
		//가격에 , 붙여서 다시 저장 후 반환
		for(int i=0;i<smallProductList.size();i++){
			DecimalFormat df = new DecimalFormat("#,##0");
			smallProductList.get(i).setMinPrice(df.format(Integer.parseInt(smallProductList.get(i).getMinPrice())));
		}
		return smallProductList;
	}
	/**
	 * 
	 * @Method Name : searchSmallProduct
	 * @Method 설명 : 검색어가 소제품명과 일치할 시 소제품 페이지 출력을 위한 정보들을 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public HashMap<String, Object> searchSmallProduct(String searchWord) {
		HashMap<String, Object> smallProductInfo = new HashMap<String, Object>();
		BlliSmallProductVO smallProduct = productDAO.searchSmallProduct(searchWord);
		ArrayList<BlliSmallProductBuyLinkVO> buyLink = null;
		ArrayList<BlliSmallProductVO> otherSmallProductList = null;
		String midCategory = "";
		if(smallProduct != null){
			midCategory = smallProduct.getMidCategory();
			//가격에 , 붙여서 다시 저장 후 반환
			DecimalFormat df = new DecimalFormat("#,##0");
			smallProduct.setMinPrice(df.format(Integer.parseInt(smallProduct.getMinPrice())));
			buyLink = (ArrayList<BlliSmallProductBuyLinkVO>)productDAO.getSmallProductBuyLink(smallProduct.getSmallProductId());
			if(!buyLink.isEmpty()){
				for(int i=0;i<buyLink.size();i++){
					buyLink.get(i).setBuyLinkPrice(df.format(Integer.parseInt(buyLink.get(i).getBuyLinkPrice())));
				}
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("midCategory", midCategory);
			map.put("smallProduct", searchWord);
			otherSmallProductList = (ArrayList<BlliSmallProductVO>)productDAO.getOtherSmallProductList(map);
			//smallProductDetailCount를 올려줍니다.
			productDAO.updateSmallProductDetailViewCount(smallProduct.getSmallProductId());
		}
		smallProductInfo.put("smallProduct", smallProduct);
		smallProductInfo.put("buyLink", buyLink);
		smallProductInfo.put("otherSmallProductList", otherSmallProductList);
		return smallProductInfo;
	}
	/**
	 * 
	 * @Method Name : searchSmallProductList
	 * @Method 설명 : 검색어와 일치하는 중분류명과 소제품명이 없을 시 검색어를 포함하는 소제품 리스트를 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param searchWord
	 * @return
	 */
	@Override
	public ArrayList<BlliSmallProductVO> searchSmallProductList(String pageNo, String searchWord) {
		if(pageNo == null || pageNo == ""){
			pageNo = "1";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("pageNo", pageNo);
		map.put("searchWord", searchWord);
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)productDAO.searchSmallProductList(map);
		//가격에 , 붙여서 다시 저장 후 반환
		for(int i=0;i<smallProductList.size();i++){
			DecimalFormat df = new DecimalFormat("#,##0");
			smallProductList.get(i).setMinPrice(df.format(Integer.parseInt(smallProductList.get(i).getMinPrice())));
		}
		return smallProductList;
	}
	/**
	 * 
	 * @Method Name : getOtherProductList
	 * @Method 설명 : 소제품 상세 페이지에 같이 출력될 해당 소제품과 같은 중분류를 가지고 있는 소제품 리스트를 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param pageNo
	 * @param smallProduct
	 * @return
	 */
	@Override
	public ListVO getOtherProductList(String pageNo, String smallProduct) {
		BlliSmallProductVO smallProductVO = productDAO.searchSmallProduct(smallProduct);
		HashMap<String, Object> map = new HashMap<String, Object>();
		String midCategory = smallProductVO.getMidCategory();
		map.put("midCategory", midCategory);
		map.put("smallProduct", smallProduct);
		map.put("pageNo", pageNo);
		ArrayList<BlliSmallProductVO> smallProductList = (ArrayList<BlliSmallProductVO>)productDAO.getOtherSmallProductList(map);
		int total = productDAO.totalOtherSmallProduct(midCategory);
		BlliPagingBean paging = new BlliPagingBean(total, Integer.parseInt(pageNo));
		paging.setNumberOfPageGroup(1);
		ListVO lvo = new ListVO(smallProductList, paging);
		return lvo;
	}
	/**
	 * 
	 * @Method Name : totalPageOfSmallProductOfMidCategory
	 * @Method 설명 : 중분류 상세페이지의 총 페이지 수를 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public int totalPageOfSmallProductOfMidCategory(String searchWord) {
		return productDAO.totalPageOfSmallProductOfMidCategory(searchWord);
	}
	/**
	 * 
	 * @Method Name : totalPageOfSmallProductRelatedSearchWord
	 * @Method 설명 : 소제품 리스트 페이지(검색어가 중분류명, 소제품명과 일치하지 않는 경우)의 총 페이지 수를 반환해주는 메서드
	 * @작성일 : 2016. 2. 3.
	 * @작성자 : hyunseok
	 * @param searchWord
	 * @return
	 */
	@Override
	public int totalPageOfSmallProductRelatedSearchWord(String searchWord) {
		return productDAO.totalPageOfSmallProductRelatedSearchWord(searchWord);
	}
	@Override
	public List<BlliSmallProductVO> selectSmallProductRank(String midCategoryId) {
		return productDAO.selectSmallProductRank(midCategoryId);
	}
	@Override
	public BlliSmallProductVO productDibChecker(String memberId, BlliSmallProductVO blliSmallProductVO) {
		//출력해줄 소분류 제품이 추출 된 후 그 리스트 중에 이미 엄마가 찜한 상품이 있는지 파악
		BlliMemberDibsVO blliMemberDibsVO= new BlliMemberDibsVO();
		blliMemberDibsVO.setMemberId(memberId);
		blliMemberDibsVO.setSmallProductId(blliSmallProductVO.getSmallProductId());
		if(productDAO.selectMemberDibsSmallProduct(blliMemberDibsVO)!=0){
			blliSmallProductVO.setIsDib(1);
		}else{
			blliSmallProductVO.setIsDib(0);
		}
		return blliSmallProductVO;
	}
	/**
	  * @Method Name : buyLinkClick
	  * @Method 설명 : 구매링크 클릭시 구매링크 기록을 디비에 남기고 클릭 카운트를 늘려준다.
	  * @작성일 : 2016. 2. 6.
	  * @작성자 : junyoung
	  * @param blliBuyLinkClickVO
	 */
	@Override
	public void buyLinkClick(BlliBuyLinkClickVO blliBuyLinkClickVO) {
		if(blliBuyLinkClickVO.getMemberId()==null){
			blliBuyLinkClickVO.setMemberId("anonyMousUser");
		}
		productDAO.insertBlliBuyLinkClick(blliBuyLinkClickVO);
		productDAO.updateBlliBuyLinkClickCount(blliBuyLinkClickVO.getSmallProductId());
	}

}
