package kr.co.blli.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

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
		System.out.println("여기오냐");
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
				paraMap.put("recommMid", blliMidCategoryVOList.get(i).getMidCategory());
				paraMap.put("babyMonthAge",Integer.toString(blliBabyVO.getBabyMonthAge()));
				// 중제품 당 찜 상위 2개씩을 가져온다.
				List<BlliSmallProductVO> tempList = productDAO.selectSameAgeMomBestPickedSmallProductList(paraMap);
				for(int j=0;j<tempList.size();j++){
					blliSmallProductVOList.add(tempList.get(j));
				}
			}
		}
		//출력해줄 소분류 제품이 추출 된 후 그 리스트 중에 이미 엄마가 찜한 상품이 있는지 파악
		String memberId = blliBabyVO.getMemberId();
		BlliMemberDibsVO blliMemberDibsVO= new BlliMemberDibsVO();
		blliMemberDibsVO.setMemberId(memberId);
		for(int i=0;i<blliSmallProductVOList.size();i++){
			blliMemberDibsVO.setSmallProductId(blliSmallProductVOList.get(i).getSmallProductId());
			if(productDAO.selectMemberDibsSmallProduct(blliMemberDibsVO)!=0){
				blliSmallProductVOList.get(i).setIsDib(1);
			}else{
				blliSmallProductVOList.get(i).setIsDib(0);
			}
		}
		System.out.println(blliSmallProductVOList);
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
	public List<BlliPostingVO> selectPostingBySmallProductList(List<BlliSmallProductVO> blliSmallProductVOList,String memberId) {
		List<BlliPostingVO> blliPostingVOList = new ArrayList<BlliPostingVO>();
		//점수순 노출 , 상태(confirmed) , 포스팅 대상 소제품 등을 기준으로 출력<!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
		for(int i=0;i<blliSmallProductVOList.size();i++){
			List<BlliPostingVO> tempList = productDAO.selectPostingBySmallProductList(blliSmallProductVOList.get(i).getSmallProductId());
			System.out.println(blliSmallProductVOList.get(i).getMidCategoryId());
			if(tempList!=null){
				for(int j=0;j<tempList.size();j++){
					blliPostingVOList.add(tempList.get(j));
				}
			}
		}
		//포스팅을 가져올 때 해당 회원이 포스팅을 스크램,좋아요,싫어요 했는지 여부를 파악해준다.
		BlliMemberScrapVO blliMemberScrapVO = new BlliMemberScrapVO();
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
			if(productDAO.selectThisPostingScrap(blliMemberScrapVO)!=0)
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
	public int postingScrap(BlliMemberScrapVO blliMemberScrapVO) {
		int result = 0;
		if(productDAO.deletePostingScrapInfo(blliMemberScrapVO)==0){
			result = productDAO.insertPostingScrap(blliMemberScrapVO);
			productDAO.updatePlusPostingScrapCount(blliMemberScrapVO);
		}else{
			productDAO.updateMinusPostingScrapCount(blliMemberScrapVO);
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

}
