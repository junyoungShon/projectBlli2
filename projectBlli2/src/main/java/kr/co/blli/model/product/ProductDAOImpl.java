package kr.co.blli.model.product;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliBigCategoryVO;
import kr.co.blli.model.vo.BlliMidCategoryVO;
import kr.co.blli.model.vo.BlliNotRecommMidCategoryVO;
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
}
