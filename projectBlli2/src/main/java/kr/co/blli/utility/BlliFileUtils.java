package kr.co.blli.utility;
 
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 



import javax.servlet.http.HttpServletRequest;
 



import kr.co.blli.model.vo.BlliBabyVO;
import kr.co.blli.model.vo.BlliMemberVO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@Component("fileUtils")
public class BlliFileUtils {
    public ArrayList<BlliBabyVO> parseInsertFileInfo
    (HttpServletRequest request,ArrayList<BlliBabyVO> list) throws Exception{
    	String filePath = null;
    	if(System.getProperty("os.name").contains("Windows")){
    		filePath = "C:\\Users\\junyoung\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\babyphoto\\";
    	}else{
    		//서버 환경일 경우 path
    		filePath = "/usr/bin/apache-tomcat-7.0.64/webapps/projectBlli2/babyphoto";
    	}
        MultipartHttpServletRequest multipartHttpServletRequest  = (MultipartHttpServletRequest)request;
        
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
        for(int i=0;i<list.size();i++){
        	multipartFile = multipartHttpServletRequest.getFile("BlliBabyVO["+i+"].babyPhoto");
            if(!multipartFile.getOriginalFilename().equals("")){
            	originalFileName = multipartFile.getOriginalFilename();
                System.out.println(originalFileName);
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                System.out.println("파일 업로드 위치"+originalFileName);
                //프로필 사진은 멤버아이디_아이이름.확장자로 저장된다.
                storedFileName = list.get(i).getMemberId()+"_"+list.get(i).getBabyName()+ originalFileExtension;
                list.get(i).setBabyPhoto(storedFileName);
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
            }else{
            	list.get(i).setBabyPhoto("default");
            }
        }
        
        return list;
    }
}



