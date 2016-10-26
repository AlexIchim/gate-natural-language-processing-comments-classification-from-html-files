package main;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import gate.*;
import gate.creole.SerialAnalyserController;
import gate.gui.MainFrame;
import gate.util.GateException;

public class Main {
	
	public static void main (String[] args) throws GateException, IOException {
		/*Gate.init(); // prepare the library
		//MainFrame.getInstance().setVisible(true);

		 //show the main window

		  
		  
		  Document doc = Factory.newDocument("This is Home");
		  doc = Factory.newDocument(new URL("https://gate.ac.uk/"),"UTF-8"); 
		  
		  FeatureMap params = Factory.newFeatureMap();
		  params.put(
		  Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME, "This is a document!");
		  FeatureMap feats = Factory.newFeatureMap();
		  
		//get current date time with Calendar()
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 
		  Calendar cal = Calendar.getInstance();
		  feats.put("date", dateFormat.format(cal.getTime()));
		  Corpus corpus = Factory.newCorpus("Corpus Name");

		  //AnnotationSet defaultSet = doc.getAnnotations();
		  int nr=0;
	
		  
		  System.out.println(nr);

		  
		  Calendar cal = Calendar.getInstance();
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 
		  FeatureMap params = Factory.newFeatureMap();
		  params.put(Document.DOCUMENT_URL_PARAMETER_NAME, "https://gate.ac.uk/");
		  //params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME,"UTF-8");

		  
		  FeatureMap feats = Factory.newFeatureMap();
		  feats.put("date", dateFormat.format(cal.getTime()));
		  feats.put("created By","alex");

		  
		  Document doc = (Document) Factory.createResource("gate.corpora.DocumentImpl", params, feats, "My first Document !");

		 AnnotationSet anSet = doc.getAnnotations();
		 // Gate.
		 
			//MainFrame.getInstance().setVisible(false);
		 System.out.print("Size: " +  anSet.size());
			//MainFrame.getInstance().setVisible(true);


*/
		
		long startTime = System.currentTimeMillis();
		
		
		//load processing resources and get app ready, here Serial Analyser Controller
		//SerialAnalyserController sac = LoadPR.getProcessingREsources();
	
	
	
	}
}