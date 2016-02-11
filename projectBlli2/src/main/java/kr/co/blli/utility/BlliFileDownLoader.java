package kr.co.blli.utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component("fileDownLoader")
public class BlliFileDownLoader {
	public String imgFileDownLoader(String url,String productId,String fileKind){
		String localPath = "C:\\Users\\junyoung\\git\\projectBlli2\\projectBlli2\\src\\main\\webapp\\scrawlImage\\";
		String fileEXT = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf(".")+4).toLowerCase();
		if(!fileEXT.equals("jpeg")){
			fileEXT.substring(0, 2);
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(new URL(url));
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(),image.getHeight(), BufferedImage.TYPE_INT_BGR);
			Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
			graphics.setBackground(Color.WHITE);
			graphics.drawImage(image, 0, 0, null);
			ImageIO.write(bufferedImage, fileEXT, new File(localPath+"\\"+fileKind+"\\"+productId+"."+fileEXT));
			System.out.println(fileKind+productId+fileEXT+" 다운완료");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "scrawlImage"+"/"+fileKind+"/"+productId+"."+fileEXT;
	}
}
