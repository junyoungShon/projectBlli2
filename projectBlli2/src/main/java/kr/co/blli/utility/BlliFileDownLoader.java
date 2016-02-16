package kr.co.blli.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component("fileDownLoader")
public class BlliFileDownLoader {
	public String imgFileDownLoader(String url,String productId,String fileKind){
		String localPath = "C:\\Users\\용호\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\scrawlImage\\";
		System.out.println(url);
		String fileEXT = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf(".")+4).toLowerCase();
		if(!fileEXT.equals("jpeg")){
			fileEXT.substring(0, 2);
		}
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
		    FileOutputStream fos = new FileOutputStream(localPath+"\\"+fileKind+"\\"+productId+"."+fileEXT);
		    fos.write(response);
		    fos.close();
		} catch (IOException e) {
		}
		return "scrawlImage"+"/"+fileKind+"/"+productId+"."+fileEXT;
	}
}
