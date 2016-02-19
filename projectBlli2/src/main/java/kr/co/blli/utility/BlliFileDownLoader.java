package kr.co.blli.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component("fileDownLoader")
public class BlliFileDownLoader {
	public String imgFileDownLoader(String url,String productId,String fileKind){
		String localPath = null;
		if(System.getProperty("os.name").contains("Windows")){
			localPath = "C:\\Users\\junyoung\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\scrawlImage\\";
		}else{
			//서버 환경일 경우 path
			localPath = "/usr/bin/apache-tomcat-7.0.64/webapps/projectBlli2/scrawlImage";
		}
		String fileEXT = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf(".")+4).toLowerCase();
		if(!fileEXT.equals("jpeg")){
			fileEXT.substring(0, 2);
		}
		try {
			URL urlObject = new URL(url);
		    InputStream in = new BufferedInputStream(urlObject.openStream());
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    byte[] buf = new byte[1024];
		    int n = 0;
		    while (-1!=(n=in.read(buf)))
		    {
		       out.write(buf, 0, n);
		    }
		    out.close();
		    in.close();
		    byte[] response = out.toByteArray();
		    FileOutputStream fos = null;
		    if(System.getProperty("os.name").contains("Windows")){
		    	fos = new FileOutputStream(localPath+"\\"+fileKind+"\\"+productId+"."+fileEXT);
			}else{
				fos = new FileOutputStream(localPath+"/"+fileKind+"/"+productId+"."+fileEXT);
			}
		    fos.write(response);
		    fos.close();
		} catch (IOException e) {
		}
		return "scrawlImage"+"/"+fileKind+"/"+productId+"."+fileEXT;
	}
}
