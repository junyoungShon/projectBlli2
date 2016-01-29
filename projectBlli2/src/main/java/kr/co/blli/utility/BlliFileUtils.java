package kr.co.blli.utility;
 
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 

import javax.servlet.http.HttpServletRequest;
 

import kr.co.blli.model.vo.BlliBabyVO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
@Component("fileUtils")
public class BlliFileUtils {
    private static final String filePath = "C:\\Users\\junyoung\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\babyphoto\\";
    public String parseInsertFileInfo(HttpServletRequest request,BlliBabyVO blliBabyVO) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        System.out.println("파일 업로드 위치");
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                System.out.println("파일 업로드 위치"+originalFileName);
                //프로필 사진은 멤버아이디_아이이름.확장자로 저장된다.
                storedFileName = blliBabyVO.getMemberId()+"_"+blliBabyVO.getBabyName()+ originalFileExtension;
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
            }
        }
        return storedFileName;
    }
}