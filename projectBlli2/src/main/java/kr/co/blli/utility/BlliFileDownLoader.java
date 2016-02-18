package kr.co.blli.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
<<<<<<< HEAD
=======



>>>>>>> branch 'master' of https://github.com/junyoungShon/projectBlli2.git

import org.springframework.stereotype.Component;

@Component("fileDownLoader")
public class BlliFileDownLoader {
	public String imgFileDownLoader(String url,String productId,String fileKind){
<<<<<<< HEAD
		String localPath = "C:\\Users\\"+System.getProperty("user.name")+"\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\scrawlImage\\";
		System.out.println(url);
=======
		String localPath = null;
		if(System.getProperty("os.name").contains("Windows")){
			localPath = "C:\\Users\\junyoung\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\scrawlImage\\";
		}else{
			//서버 환경일 경우 path
			localPath = "/usr/bin/apache-tomcat-7.0.64/webapps/projectBlli2/scrawlImage";
		}
>>>>>>> branch 'master' of https://github.com/junyoungShon/projectBlli2.git
		String fileEXT = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf(".")+4).toLowerCase();
		if(!fileEXT.equals("jpeg")){
			fileEXT.substring(0, 2);
		}
<<<<<<< HEAD
		/*BufferedImage image = null; 
		try {
			image = ImageIO.read(new URL(url));
			BufferedImage bufferedImage = image;
			Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
			graphics.setBackground(Color.WHITE);
			graphics.drawImage(image, 0, 0, null);
			ImageIO.write(bufferedImage, fileEXT, new File(localPath+"\\"+fileKind+"\\"+productId+"."+fileEXT));
			System.out.println(fileKind+productId+fileEXT+" 다운완료");
		}catch(Exception e){
			e.printStackTrace();
		}*/
=======
>>>>>>> branch 'master' of https://github.com/junyoungShon/projectBlli2.git
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
