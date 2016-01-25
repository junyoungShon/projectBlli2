package kr.co.blli.model.product;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMemberDibsVO;
import kr.co.blli.model.vo.BlliMemberScrapVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
import kr.co.blli.model.vo.BlliPostingDisLikeVO;
import kr.co.blli.model.vo.BlliPostingLikeVO;
import kr.co.blli.model.vo.BlliPostingVO;
import kr.co.blli.model.vo.BlliSmallProductBuyLinkVO;
import kr.co.blli.model.vo.BlliSmallProductVO;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl implements ProductDAO{
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<BlliSmallProductVO> getSmallProduct() {
		return sqlSessionTemplate.selectList("product.getSmallProduct");
	}

	@Override
	public void insertBigCategory(BlliBigCategoryVO blliBigCategoryVO) {
		sqlSessionTemplate.insert("product.insertBigCategory", blliBigCategoryVO);
	}

	@Override
	public List<BlliBigCategoryVO> getBigCategory() {
		return sqlSessionTemplate.selectList("product.getBigCategory");
	}

	@Override
	public void insertMidCategory(BlliMidCategoryVO blliMidCategoryVO) {
		sqlSessionTemplate.insert("product.insertMidCategory", blliMidCategoryVO);
	}

	@Override
	public int updateBigCategory(BlliBigCategoryVO blliBigCategoryVO) {
		return sqlSessionTemplate.update("product.updateBigCategory", blliBigCategoryVO);
	}

	@Override
	public int updateMidCategory(BlliMidCategoryVO blliMidCategoryVO) {
		return sqlSessionTemplate.update("product.updateMidCategory", blliMidCategoryVO);
	}

	@Override
	public List<BlliMidCategoryVO> getMidCategory() {
		return sqlSessionTemplate.selectList("product.getMidCategory");
	}
	/**
	  * @Method Name : selectRecommendingMidCategory
	  * @Method 설명 : 추천 상품 리스트를 출력한다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliMidCategoryVO> selectRecommendingMidCategory(
			BlliBabyVO blliBabyVO) {
		return sqlSessionTemplate.selectList("product.selectRecommendingMidCategory",blliBabyVO);
	}
	/**
	  * @Method Name : selectNotRecommMidCategoryList
	  * @Method 설명 : 사용자가 제거 요청한 카테고리를 가져온다.
	  * @작성일 : 2016. 1. 20.
	  * @작성자 : junyoung
	  * @param blliBabyVO
	  * @return
	 */
	@Override
	public List<BlliNotRecommMidCategoryVO> selectNotRecommMidCategoryList(
			BlliBabyVO blliBabyVO) {
		return sqlSessionTemplate.selectList("product.selectNotRecommMidCategoryList",blliBabyVO);
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
		sqlSessionTemplate.insert("product.deleteRecommendMidCategory", blliNotRecommMidCategoryVO);
	}
	/**
	  * @Method Name : selectSameAgeMomBestPickedSmallProductList
	  * @Method 설명 : 현재 
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param put
	 */
	@Override
	public void selectSameAgeMomBestPickedSmallProductList(Integer put) {
		// TODO Auto-generated method stub
		
	}
	/**
	  * @Method Name : selectSameAgeMomBestPickedSmallProductList
	  * @Method 설명 : 추천받고 있는 중분류 제품에 속하는 소분류 중 찜수가 많은 제품들을 가져온다.(두개씩 가져온다.) 
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param paraMap
	  * @return
	 */
	@Override
	public List<BlliSmallProductVO> selectSameAgeMomBestPickedSmallProductList(
			HashMap<String, String> paraMap) {
		return sqlSessionTemplate.selectList("product.selectSameAgeMomBestPickedSmallProductList", paraMap);
	}
	/**
	  * @Method Name : selectSameAgeMomBestPickedSmallProduct
	  * @Method 설명 : 추천 받고 있는 중분류 제품에 속하는 소분류 중 찜수가 많은 제품들 중 찜수가 가장많은 1개만 가져온다.
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param paraMap
	  * @return
	 */
	@Override
	public BlliSmallProductVO selectSameAgeMomBestPickedSmallProduct(
			HashMap<String, String> paraMap) {
		return sqlSessionTemplate.selectOne("product.selectSameAgeMomBestPickedSmallProduct", paraMap);
	}

	@Override
	public void insertSmallProduct(BlliSmallProductVO blliSmallProductVO) {
		sqlSessionTemplate.insert("product.insertSmallProduct", blliSmallProductVO);
	}

	@Override
	public int updateSmallProduct(BlliSmallProductVO blliSmallProductVO) {
		return sqlSessionTemplate.update("product.updateSmallProduct", blliSmallProductVO);
	}

	@Override
	public void insertSmallProductBuyLink(BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO) {
		sqlSessionTemplate.insert("product.insertSmallProductBuyLink", blliSmallProductBuyLinkVO);
	}

	@Override
	public int updateSmallProductBuyLink(BlliSmallProductBuyLinkVO blliSmallProductBuyLinkVO) {
		return sqlSessionTemplate.update("product.updateSmallProductBuyLink", blliSmallProductBuyLinkVO);
	}
	/**
	  * @Method Name : selectPostingBySmallProductList
	  * @Method 설명 : 점수순 노출 , 상태(confirmed) , 포스팅 대상 소제품 등을 기준으로 출력<!극혐주의!> 포스팅 관련 이므로 여기있으면 안되지만 구조상 여기왔다 . 상의해보자
	  * @작성일 : 2016. 1. 21.
	  * @작성자 : junyoung
	  * @param midCategoryId
	  * @return
	 */
	@Override
	public List<BlliPostingVO> selectPostingBySmallProductList(String smallProductId) {
		System.out.println("DAO"+smallProductId);
		return sqlSessionTemplate.selectList("posting.selectPostingBySmallProductList", smallProductId);
	}
	/**
	  * @Method Name : deleteDipsInfo
	  * @Method 설명 : 찜 정보가 있을 경우 삭제한다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	  * @return
	 */
	@Override
	public int deleteDipsInfo(BlliMemberDibsVO blliMemberDibsVO) {
		return sqlSessionTemplate.delete("product.deleteDipsInfo", blliMemberDibsVO);
	}
	/**
	  * @Method Name : insertDipsInfo
	  * @Method 설명 : 찜정보가 없을 경우 삽입한다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	  * @return
	 */
	@Override
	public int insertDipsInfo(BlliMemberDibsVO blliMemberDibsVO) {
		return sqlSessionTemplate.delete("product.insertDipsInfo", blliMemberDibsVO);
	}
	/**
	  * @Method Name : updatePlusSmallProductDibsCount
	  * @Method 설명 : 찜횟수를 올려줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	 */
	@Override
	public void updatePlusSmallProductDibsCount(BlliMemberDibsVO blliMemberDibsVO) {
		sqlSessionTemplate.update("product.updatePlusSmallProductDibsCount", blliMemberDibsVO);
	}
	/**
	  * @Method Name : updateMinusSmallProductDibsCount
	  * @Method 설명 : 찜횟수를 줄여줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	 */
	@Override
	public void updateMinusSmallProductDibsCount(BlliMemberDibsVO blliMemberDibsVO) {
		sqlSessionTemplate.update("product.updateMinusSmallProductDibsCount", blliMemberDibsVO);
	}
	
	
	// 포스팅 스크랩, 좋아요 , 싫어요 관련 메서드 시작
	/**
	  * @Method Name : deletePostingScrapInfo
	  * @Method 설명 : 회원이 포스팅한 정보가 있을 경우 지워줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberScrapVO
	  * @return
	 */
	@Override
	public int deletePostingScrapInfo(BlliMemberScrapVO blliMemberScrapVO) {
		return sqlSessionTemplate.delete("posting.deletePostingScrapInfo", blliMemberScrapVO);
	}
	/**
	  * @Method Name : insertPostingScrap
	  * @Method 설명 : 회원이 해당 포스팅을 스크랩한 정보가 없다면 넣어줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberScrapVO
	  * @return
	 */
	@Override
	public int insertPostingScrap(BlliMemberScrapVO blliMemberScrapVO) {
		return sqlSessionTemplate.delete("posting.insertPostingScrap", blliMemberScrapVO);
	}
	/**
	  * @Method Name : updatePlusPostingScrapCount
	  * @Method 설명 : 회원이 스크랩한 경우 스크랩 수를 늘려줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberScrapVO
	 */
	@Override
	public void updatePlusPostingScrapCount(BlliMemberScrapVO blliMemberScrapVO) {
		sqlSessionTemplate.update("posting.updatePlusPostingScrapCount", blliMemberScrapVO);
	}
	/**
	  * @Method Name : updateMinusPostingScrapCount
	  * @Method 설명 : 회원의 스크랩을 취소할 경우 그 수를 줄여줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberScrapVO
	 */
	@Override
	public void updateMinusPostingScrapCount(BlliMemberScrapVO blliMemberScrapVO) {
		sqlSessionTemplate.update("posting.updateMinusPostingScrapCount", blliMemberScrapVO);
	}
	/**
	  * @Method Name : deletePostingLikeInfo
	  * @Method 설명 : 회원이 포스팅을 좋아한 정보가 있을 경우 지워줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingLikeVO
	  * @return
	 */
	@Override
	public int deletePostingLikeInfo(BlliPostingLikeVO blliPostingLikeVO) {
		return sqlSessionTemplate.delete("posting.deletePostingLikeInfo", blliPostingLikeVO);
	}
	/**
	  * @Method Name : insertPostingLikeInfo
	  * @Method 설명 : 회원이 포스팅을 좋아한 정보가 없을 경우 지워줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingLikeVO
	  * @return
	 */
	@Override
	public int insertPostingLikeInfo(BlliPostingLikeVO blliPostingLikeVO) {
		return sqlSessionTemplate.delete("posting.insertPostingLikeInfo", blliPostingLikeVO);
	}
	/**
	  * @Method Name : updatePlusPostingLikeCount
	  * @Method 설명 : 포스팅의 좋아요수를 올려줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingLikeVO
	 */
	@Override
	public void updatePlusPostingLikeCount(BlliPostingLikeVO blliPostingLikeVO) {
		sqlSessionTemplate.update("posting.updatePlusPostingLikeCount", blliPostingLikeVO);
	}
	/**
	  * @Method Name : updateMinusPostingLikeCount
	  * @Method 설명 : 포스팅의 좋아요 수를 줄여줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingLikeVO
	 */
	@Override
	public void updateMinusPostingLikeCount(BlliPostingLikeVO blliPostingLikeVO) {
		sqlSessionTemplate.update("posting.updateMinusPostingLikeCount", blliPostingLikeVO);
	}
	/**
	  * @Method Name : deletePostingDisLikeInfo
	  * @Method 설명 : 포스팅의 싫어요 정보를 지워줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingDisLikeVO
	  * @return
	 */
	@Override
	public int deletePostingDisLikeInfo(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		return sqlSessionTemplate.delete("posting.deletePostingDisLikeInfo", blliPostingDisLikeVO);
	}
	/**
	  * @Method Name : insertPostingDisLikeInfo
	  * @Method 설명 : 포스팅의 싫어요 정보를 넣어줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingDisLikeVO
	  * @return
	 */
	@Override
	public int insertPostingDisLikeInfo(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		return sqlSessionTemplate.delete("posting.insertPostingDisLikeInfo", blliPostingDisLikeVO);
	}
	/**
	  * @Method Name : updatePlusPostingDisLikeCount
	  * @Method 설명 : 포스팅의 싫어요 수를 올려줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingDisLikeVO
	 */
	@Override
	public void updatePlusPostingDisLikeCount(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		sqlSessionTemplate.update("posting.updatePlusPostingDisLikeCount", blliPostingDisLikeVO);
	}
	/**
	  * @Method Name : updateMinusPostingDisLikeCount
	  * @Method 설명 : 포스팅의 싫어요 수를 줄여줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingDisLikeVO
	 */
	@Override
	public void updateMinusPostingDisLikeCount(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		sqlSessionTemplate.update("posting.updateMinusPostingDisLikeCount", blliPostingDisLikeVO);
	}
	/**
	  * @Method Name : selectThisPostingScrap
	  * @Method 설명 : 포스팅의 스크랩을 이미했는지 파악해줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberScrapVO
	  * @return
	 */
	@Override
	public int selectThisPostingScrap(BlliMemberScrapVO blliMemberScrapVO) {
		return sqlSessionTemplate.selectOne("posting.selectThisPostingScrap", blliMemberScrapVO);
	}
	/**
	  * @Method Name : selectThisPostingLike
	  * @Method 설명 : 유저가 포스팅을 좋아했는지 여부를 파악해줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingLikeVO
	  * @return
	 */
	@Override
	public int selectThisPostingLike(BlliPostingLikeVO blliPostingLikeVO) {
		return sqlSessionTemplate.selectOne("posting.selectThisPostingLike", blliPostingLikeVO);
	}
	/**
	  * @Method Name : selectThisPostingDisLike
	  * @Method 설명 : 유저가 포스팅을 싫어했는지 여부를 파악해줍니다.
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliPostingDisLikeVO
	  * @return
	 */
	@Override
	public int selectThisPostingDisLike(BlliPostingDisLikeVO blliPostingDisLikeVO) {
		return sqlSessionTemplate.selectOne("posting.selectThisPostingDisLike", blliPostingDisLikeVO);
	}

	
	// 포스팅 스크랩, 좋아요 , 싫어요 관련 메서드 끝

	/**
	  * @Method Name : selectMemberDibsSmallProduct
	  * @Method 설명 : 회원이 소제품을 찜했는지 여부를 리턴해준다. 0 찜안함 / 1 찜함
	  * @작성일 : 2016. 1. 22.
	  * @작성자 : junyoung
	  * @param blliMemberDibsVO
	  * @return
	 */
	@Override
	public int selectMemberDibsSmallProduct(BlliMemberDibsVO blliMemberDibsVO) {
		return sqlSessionTemplate.selectOne("product.selectMemberDibsSmallProduct", blliMemberDibsVO);
	}

	}
