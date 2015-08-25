package co.com.neomedios.OCRApi;

import java.io.IOException;

public class OcrService {
	
	public OCR getOcrText(String cedula) throws IOException{	
		OCR ocr = new OCR(cedula);	
		return ocr;
	}
	
}
