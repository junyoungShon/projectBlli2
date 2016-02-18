package kr.co.blli.utility;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;
@Component
public class BlliWordCounter {
	//한글체크 정규식
	String koreanRegex = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
	//한글자음체크 정규식
	String koreanConsonant = ".*[ㄱ-ㅎ]+.*";
	//한글모음체크 정규식
	String koreanVowel = ".*[ㅏ-ㅣ]+.*";
	//형태소 분리 워크 플로우 설정
	@Autowired
	Workflow workflow;
	
	public HashMap<String, Integer> wordCounting(StringBuffer sb){
		long start = System.currentTimeMillis(); // 시작시간 
		
		workflow.appendPlainTextProcessor(new SentenceSegmentor(), null);
		workflow.appendPlainTextProcessor(new InformalSentenceFilter(), null);
		workflow.setMorphAnalyzer(new ChartMorphAnalyzer(), "conf/plugin/MajorPlugin/MorphAnalyzer/ChartMorphAnalyzer.json");
		workflow.appendMorphemeProcessor(new UnknownProcessor(), null);
		workflow.setPosTagger(new HMMTagger(), "conf/plugin/MajorPlugin/PosTagger/HmmPosTagger.json");
		//워크 플로우 실행
		try {
			workflow.activateWorkflow(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//분석 텍스트
			String document = sb.toString();
			//분석
			workflow.analyze(document);
			//분석결과 문자로 출력
			String result = workflow.getResultOfDocument();
			//단어 및 카운트를 저장하기 위한 맵 객체
			HashMap<String, Integer> wordAndCountMap = new HashMap<String, Integer>();
			//개행문자로 품사태그를 기준으로 배열을 만든다.
			String resultList [] = result.split("\n");
			
			for(int i=0;i<resultList.length;i++){
				if(resultList[i].contains("/n")){
					if(resultList[i].contains("/n")){
						if(resultList[i].substring(0, resultList[i].indexOf("/n")).contains("/")){
							if(i!=0)
								resultList[i] = resultList[i-1];
						}else{
							resultList[i] = resultList[i].substring(0, resultList[i].indexOf("/n")).trim();
						}
						for(int j=0;j<resultList[i].length();j++){
							String temp = resultList[i].charAt(j)+"";
							if(!temp.matches(koreanRegex)){
								resultList[i]=resultList[i].replace(temp, "");
								j--;
							}else if(temp.matches(koreanConsonant)){
								resultList[i]=resultList[i].replace(temp, "");
								j--;
							}else if(temp.matches(koreanVowel)){
								resultList[i]=resultList[i].replace(temp, "");
								j--;
							}
						}
						
						if(resultList[i].length()>1&&resultList[i].length()<11){
							if(wordAndCountMap.get(resultList[i])!=null){
								wordAndCountMap.replace(resultList[i], wordAndCountMap.get(resultList[i])+1);
							}else{
								wordAndCountMap.put(resultList[i], 1);
							}
						}
					}
				
				
				//형용사 추출	
				}else if(!resultList[i].contains("/n")&&resultList[i].contains("/paa")){
					if(i!=0)
						resultList[i] = resultList[i-1];
					for(int j=0;j<resultList[i].length();j++){
						String temp = resultList[i].charAt(j)+"";
						if(!temp.matches(koreanRegex)){
							resultList[i]=resultList[i].replace(temp, "");
							j--;
						}else if(temp.matches(koreanConsonant)){
							resultList[i]=resultList[i].replace(temp, "");
							j--;
						}else if(temp.matches(koreanVowel)){
							resultList[i]=resultList[i].replace(temp, "");
							j--;
						}
					}
					if(resultList[i].length()>1&&resultList[i].length()<11){
						if(wordAndCountMap.get(resultList[i])!=null){
							wordAndCountMap.replace(resultList[i], wordAndCountMap.get(resultList[i])+1);
						}else{
							wordAndCountMap.put(resultList[i], 1);
						}
					}
				}
				//System.out.println(i+"번째"+resultList[i].toString());
			}
			
			//System.out.println(result);
			
			System.out.println("총 추출 단어 수:"+wordAndCountMap.size());
			
			workflow.close();
			workflow.clear();
			wordAndCountMap = (HashMap<String, Integer>) sortByValue(wordAndCountMap);
			System.out.println(wordAndCountMap.size());
			Iterator<String> it = wordAndCountMap.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				int value = wordAndCountMap.get(key);
				System.out.println("단어 : "+key+" 횟수 : "+value);
			}
			long end = System.currentTimeMillis();  //종료시간
			
			//종료-시작=실행시간		
			System.out.println("실행시간  : "+(end-start)/1000.0+"초");
			return wordAndCountMap;
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
	    int cnt = 0;
	    for (Map.Entry<K, V> entry : list){
	    	result.put( entry.getKey(), entry.getValue() );
	    	
	    }
	return result;

	}
}
