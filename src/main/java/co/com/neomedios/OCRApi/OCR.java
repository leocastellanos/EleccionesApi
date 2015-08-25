package co.com.neomedios.OCRApi;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@XmlRootElement
public class OCR {
	private String cedula;
	private ArrayList<String> information;
	
	public OCR(){}
	
	public OCR(String cedula) throws IOException{
		this.cedula=cedula;
		this.information= getInfo();
	}
	

	public ArrayList<String> getInformation() {
		return information;
	}

	public void setInformation(ArrayList<String> information) {
		this.information = information;
	}

	public String getCedula() {
		return this.cedula;
	}
	
	public void setCedula(String cedula) {
		this.cedula=cedula;
	}
	
	public String makeOCR(){
		String text;
		Tesseract instance = Tesseract.getInstance(); 
		instance.setDatapath("D://2 - Desarrollo1//Java//workspace//OCRApi//tessdata");
		try{        	
            text = instance.doOCR(new File("Captcha.jpg")).replaceAll("\\n", "");
            text = text.replaceAll(" ", "");
        } catch (TesseractException e) {
        	text =e.getMessage();
        }
		return text;
	}
	
	public ArrayList<String> getInfo() throws IOException{ 
		String text;
		ArrayList<String> list= new ArrayList<String>();
		WebClient webClient1 = new WebClient(BrowserVersion.CHROME);
	    final HtmlPage page1;
	    try (final WebClient webClient = webClient1) {

	        // Get the page
	        page1 = webClient.getPage("http://inscripcionelectoral.carvajal.com:8280/srvidc-webcon/");
	        
	        HtmlImage image1 = page1.<HtmlImage>getFirstByXPath("//img[@src='/srvidc-webcon/srvidc-webcon/../Captcha.jpg']");
	        File imageFile = new File("Captcha.jpg");
	        image1.saveAs(imageFile);
	        webClient1 = webClient;
	        text=makeOCR();
	        
	        final HtmlForm form = page1.getFormByName("f1");
	        final HtmlTextInput nroDcto = form.getInputByName("f1:nroDocto");
	        final HtmlTextInput jCaptcha = form.getInputByName("f1:jcaptcha");
	        
	        nroDcto.setValueAttribute(getCedula());
	        jCaptcha.setValueAttribute(text);
	        
	        final HtmlSubmitInput button = (HtmlSubmitInput)form.getInputByName("f1:j_idt44");
	        final HtmlPage page3=button.click();
	        
	        try{
	        	final HtmlTable table = page3.getFirstByXPath("//table[@id='pg1']/tbody/tr/td/table");
	        	final HtmlTable table1 = page3.getFirstByXPath("//table[@id='j_idt85']");
		         list.add(table1.getRow(2).getCell(6).asText());
		         list.add(table.getRow(2).getCell(1).asText());
		         list.add(table.getRow(3).getCell(1).asText());
		         list.add(table.getRow(4).getCell(1).asText());
		         list.add(table1.getRow(2).getCell(0).asText());
		         list.add(table1.getRow(2).getCell(1).asText());
		         list.add(table1.getRow(2).getCell(4).asText());
		         list.add(table1.getRow(2).getCell(5).asText());
		         
	        }catch(NullPointerException e){
	        	try{
		        	final HtmlTable table = page3.getFirstByXPath("//table[@id='pg1']/tbody/tr/td/table");
			         list.add(table.getRow(1).getCell(1).asText());
			         list.add(table.getRow(2).getCell(1).asText());
			         list.add(table.getRow(3).getCell(1).asText());
			         list.add(table.getRow(4).getCell(1).asText());
			         list.add(table.getRow(5).getCell(1).asText());
			         list.add(table.getRow(6).getCell(1).asText());
			         list.add(table.getRow(7).getCell(1).asText());
			         list.add(table.getRow(8).getCell(1).asText());
			        for (String a:list){
			        	System.out.println(a);
			        }
			        
		        }catch(NullPointerException a){
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        	list.add("NO APTO");
		        }
	        }
	        
	        
	        
	        
	        
	        
	    }
	    
	   
		return list;
	}

	
}
