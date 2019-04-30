package com.x.wm.pdf;

import com.foxit.sdk.PDFException;
import com.foxit.sdk.common.Bitmap;
import com.foxit.sdk.common.Font;
import com.foxit.sdk.common.Image;
import com.foxit.sdk.common.Library;
import com.foxit.sdk.pdf.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.foxit.sdk.common.Constants;

import static com.foxit.sdk.common.Constants.e_AlignmentCenter;
import static com.foxit.sdk.common.Constants.e_ErrSuccess;
import static com.foxit.sdk.common.Font.e_StdIDTimes;
import static com.foxit.sdk.pdf.PDFDoc.e_SaveFlagNoOriginal;
import static com.foxit.sdk.pdf.PDFPage.e_ParsePageNormal;
import static com.foxit.sdk.pdf.WatermarkSettings.*;
import static com.foxit.sdk.pdf.WatermarkTextProperties.e_FontStyleNormal;


public class WaterMarkUtil {


    private static String key = "8f0YFEGMvWsN+DetnKjesHrQ1PDR4DckewHV8ABK8ho6d92ed8cMoZZ8nqmGjrZokX1GfSpY9UKRKROEN0vg3VvVGIPkh88eDOCRdgyYgLaCVZ2dKjEO+qKm9ZsJ5nvGB90QrsRZH+PdyLBssJwXqgcEGKE5QfmzVp+sXIHpQgle2ngOS0hPsrSIMPZXrvw+4qriwHMa4ZNDT4W44KXSw0TTaECQX5CW0VUjt5dol3BJ5dJsBehuN6Lue250dIfhTFYVzu7VzwuS79tJX25OWdXO0J0BRkQZFkyYAi3PYvqgQCGLVbGz/rgz6uYC/wM0i3bMqDNPNW+nE1uXXZkXF1ehWulHwFRhafg7CEMHjwsSI8Cam8e5BzP82A8XZwEddU4aylHDffyehNXuMamOmJBvX9jBmnFmAYIS5R6c2tC7h7qYX0iykp5dy1+QBcELoKsd/lEbe6pn9cGBJOBUIUmfX8/qubiawfgZTSrXIlOd/HNt3Bw8OxNleT7V3nqtxDQlh0/vAwDTjY4T8Omz9Th2q0aShO2DPzkRH35WtKR57l+L5/H4pxl11MX5s582/AVzneqxw98q7nYUVZeT6XeBEyO6rzF/E+QJwfhlbLcJ9DVW+lftJ6US+T3Pi4isq6bgZjW3TJgSt61M3WyJpXnyVFhEYdPyqLZImf/B+kAa86/0h9Co2PK+Vy9wBMkpbzBGcqQ/a9j+bbPzfNYDTiK5RoEhSAuzapmgt5RuSIU3COn/nqA8epA5KAgmxO+xz38RkRoZmI5wZVIOZmW9dXPy/m6lszyPfCtubuyeAd7qHF+N8yGtEpytxNzWD/6MaFtuOOu87OQVJQWdGp14Dg2rsp9He7yFG6tTNWkVTh2xXvbB+KJ5U9qt2f7F7bs2nDdi+W8Ywtaax0FsMUkm8FtFEeR+3cMmKjmhUynDzOVwRl86g1qlhe1CUCxG++J3AAw2zoOvC73mX7PjiEQvzBoc3ZPG1ssFctgELGCRbqJajOZzbST3TXsJCU3XDfIRA3kNY7cintP/XnBu8GzMtuBAtfX2CQjEYSQvteq/mEiAtOgr1ISP7VWEcmykaEM0Wk2RM2+uSWJidjbwq5kl4nShifk7OXOvlK1nFV+Z155Jrkh1x4oC1iGIybVr5slHM3/9yaSJjdG4YpPeovtY1NKUYyViIkd3BUE="; 
    private static String sn = "k7nuUk7j4hj3CdAJEWSg1zYOAMhjdrERJCW6YugyV8dqrRNcITXUBg==";

    private static void createResultFolder(String outPutDir) {
        File myPath = Paths.get(outPutDir).toFile();
        if (!myPath.exists()) {
            myPath.mkdir();
        }
    }

    static void addTextWatermark(PDFDoc doc, PDFPage page, String wmText,int position) throws PDFException {
        WatermarkSettings settings = new WatermarkSettings();
        settings.setFlags(e_FlagASPageContents | e_FlagOnTop);
        settings.setOffset_x(3);
        settings.setOffset_y(4);
        settings.setOpacity(90);
        settings.setPosition(position);
        settings.setRotation(0f);
        settings.setScale_x(1.f);
        settings.setScale_y(1.f);

        WatermarkTextProperties textProperties = new WatermarkTextProperties();
        textProperties.setAlignment(e_AlignmentCenter);
        textProperties.setColor(0x000000);
        textProperties.setFont_size(e_FontStyleNormal);
        textProperties.setLine_space(1);
        textProperties.setFont_size(10.f);
        textProperties.setFont(new Font(e_StdIDTimes));

        Watermark watermark = new Watermark(doc, wmText, textProperties, settings);
        watermark.insertToPage(page);

    }

    static void addBitmapWatermark(PDFDoc doc, PDFPage page, String bitmapFile) throws PDFException {
        WatermarkSettings settings = new WatermarkSettings();
        settings.setFlags(e_FlagASPageContents | e_FlagOnTop);
        settings.setOffset_y(0.f);
        settings.setOffset_y(0.f);
        settings.setOpacity(60);
        settings.setPosition(Constants.e_PosCenterLeft);
        settings.setRotation(90.f);

        Image image = new Image(bitmapFile);
        Bitmap bitmap = image.getFrameBitmap(0);
        settings.setScale_x(page.getHeight() * 1.0f / bitmap.getWidth());
        settings.setScale_y(settings.getScale_x());
        Watermark watermark = new Watermark(doc, bitmap, settings);
        watermark.insertToPage(page);
    }


    static void addImageWatermark(PDFDoc doc, PDFPage page, String imageFile) throws PDFException {
        WatermarkSettings settings = new WatermarkSettings();
        settings.setFlags(e_FlagASPageContents | e_FlagOnTop);
        settings.setOffset_x(0.f);
        settings.setOffset_y(0.f);
        settings.setOpacity(20);
        settings.setPosition(Constants.e_PosCenter);
        settings.setRotation(0.0f);

        Image image = new Image(imageFile);
        Bitmap bitmap = image.getFrameBitmap(0);
        settings.setScale_x(page.getWidth() * 0.618f / bitmap.getWidth());
        settings.setScale_y(settings.getScale_x());

        Watermark watermark = new Watermark(doc, image, 0, settings);
        watermark.insertToPage(page);
    }

    static void addSingleWatermark(PDFDoc doc, PDFPage page) throws PDFException {
        WatermarkSettings settings = new WatermarkSettings();
        settings.setFlags(e_FlagASPageContents | e_FlagOnTop);
        settings.setOffset_x(0.f);
        settings.setOffset_y(0.f);
        settings.setOpacity(90);
        settings.setPosition(Constants.e_PosBottomRight);
        settings.setRotation(0.0f);
        settings.setScale_x(0.1f);
        settings.setScale_y(0.1f);

        Watermark watermark = new Watermark(doc, page, settings);
        watermark.insertToPage(page);
    }


    public static void main(String[] args) throws PDFException {
    	try {   		
    		Path p = Paths.get(System.getProperty("waterwmutil.key.path"));
     		Properties propSN = new Properties();
    		Properties propKey = new Properties();
    		propSN.load(new FileReader(p.resolve("gsdk_sn.txt").toFile()));
    		propKey.load(new FileReader(p.resolve("gsdk_key.txt").toFile()));
    		sn = propSN.getProperty("SN");
    		System.out.println(sn);
			key = propKey.getProperty("Sign");
			System.out.println(key);
			System.out.println("Sn and key file read success!!!");
			System.out.println(Arrays.toString(args));
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new PDFException(100,"Not able to load sn or key files. "+e1.getMessage());
		}
    	if(args.length<3) {
    		throw new PDFException(99, "Need atleast 3 Arguments for WM.");
    	}
    	
    	String outFileDir = args[1];
    	String inFile = args[0];
    	String wmImage = args[2];
    	Map<String,String> paramMap = new HashMap<>();
    	String [] valAr;
    	if(args.length>3) {
			for (int j = 3; j < args.length; j++) {
				valAr = args[j].split("#");
				paramMap.put(valAr[0], valAr.length > 1 ? valAr[1] : "");
			}
    	}
    	createResultFolder(outFileDir);
        // Initialize library
        int errorCode = Library.initialize(sn, key);
        if (errorCode != e_ErrSuccess) {
            System.out.println("Library Initialize Error: " + errorCode);
            return;
        }

        try {
            PDFDoc doc = new PDFDoc(inFile);
            errorCode = doc.load(null);

            if (errorCode != e_ErrSuccess) {
                System.out.println("The Doc " + inFile + " Error: " + errorCode);
                return;
            }
            int nCount = doc.getPageCount();
            for (int i = 0; i < nCount; i++) {
                PDFPage page = doc.getPage(i);
                page.startParse(e_ParsePageNormal, null, false);
                addHeaderFooter(doc,page,paramMap);
               
                //String wm_bmp = input_path + "watermark.bmp";
                //addBitmapWatermark(doc, page, wm_bmp);
                //String wm_image = INPUTPATH + "sdk.png";
                addImageWatermark(doc, page, wmImage);
               //addSingleWatermark(doc, page);
            }
            String outputFile = Paths.get(outFileDir).resolve(Paths.get(inFile).getFileName()).toString();
            doc.saveAs(outputFile, e_SaveFlagNoOriginal);
            System.out.println("Add watermarks to PDF file.");

        } catch (Exception e) {
        	e.printStackTrace();
        	throw new PDFException(100, e.getMessage());
        }
        Library.release();
    }

	private static void addHeaderFooter(PDFDoc doc, PDFPage page, Map<String, String> paramMap) throws PDFException {
		 for(Entry<String,String> ent: paramMap.entrySet()) {
			 int position;
			 switch(ent.getKey()) {
			 case "TOP_LEFT": position = Constants.e_PosTopLeft;break;
			 case "TOP_CENTER": position = Constants.e_PosTopCenter;break;
			 case "TOP_RIGHT": position = Constants.e_PosTopRight;break;
			 case "BOTTOM_LEFT": position = Constants.e_PosBottomLeft;break;
			 case "BOTTOM_CENTER": position = Constants.e_PosBottomCenter;break;
			 case "BOTTOM_RIGHT": position = Constants.e_PosBottomRight;break;
			 default : position = 9999;break;
			 }
			 if(position != 9999)
				 addTextWatermark(doc, page, ent.getValue(), position);
         }		
	}
	
	private static String readFromInputStream(InputStream inputStream)
			  throws IOException {
			    StringBuilder resultStringBuilder = new StringBuilder();
			    try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(inputStream))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            resultStringBuilder.append(line).append("\n");
			        }
			    }
			  return resultStringBuilder.toString();
			}
}
