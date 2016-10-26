package Gate.Gate1;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.ProcessingResource;
import gate.creole.SerialAnalyserController;
import gate.gui.MainFrame;
import gate.util.GateException;


public class LoadPRClass {

	public static SerialAnalyserController getProcessingResources() throws MalformedURLException, GateException{
		long startLoadResourcesTime = System.currentTimeMillis();	//start time


		//Init Gate Library
		Gate.init();	
		
		// to show the GATE Developer interface
		MainFrame.getInstance().setVisible(true);	
		
		File pluginDir = Gate.getPluginsHome();	// get plugins home directory
		URL anniePlugin = new File(pluginDir, "ANNIE").toURI().toURL();	// specify plugin to be loaded
		Gate.getCreoleRegister().registerDirectories(anniePlugin);	// finally register the plugin
		
		// setting up searialAnalyserController
		SerialAnalyserController sac = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");

		// setting up processing resources, tokeniser needed
		ProcessingResource aEngTokeniser = (ProcessingResource) Factory.createResource("gate.creole.tokeniser.DefaultTokeniser");
		
		//setting up processing resources, gazetteer needed
		ProcessingResource annGazetteer = (ProcessingResource) Factory.createResource("gate.creole.gazetteer.DefaultGazetteer");
		
				
		//get positive adjectives
		FeatureMap positiveAdjectiveJapeFeature = Factory.newFeatureMap();
		positiveAdjectiveJapeFeature.put("grammarURL", new File("src/grammar/goodAdjective.jape").toURI().toURL());
		
		//get negative adjectives
		FeatureMap badAdjectiveJapeFeature = Factory.newFeatureMap();
		badAdjectiveJapeFeature.put("grammarURL", new File("src/grammar/badAdjective.jape").toURI().toURL());

		//get all comments
		FeatureMap commentsJapeFeature = Factory.newFeatureMap();
		commentsJapeFeature.put("grammarURL", new File("src/grammar/comments.jape").toURI().toURL());

		//get Product title
		FeatureMap titleJapeFeature = Factory.newFeatureMap();
		titleJapeFeature.put("grammarURL", new File("src/grammar/title.jape").toURI().toURL());
		
		//get positive comments
		FeatureMap positiveCommentsJapeFeature = Factory.newFeatureMap();
		positiveCommentsJapeFeature.put("grammarURL", new File("src/grammar/positiveComments.jape").toURI().toURL());
		
		//get negative comments
		FeatureMap negativeCommentsJapeFeature = Factory.newFeatureMap();
		negativeCommentsJapeFeature.put("grammarURL", new File("src/grammar/negativeComments.jape").toURI().toURL());
		
		
		
		
		// load JAPE language resources with specified features 
		LanguageAnalyser commentJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", commentsJapeFeature);				
		LanguageAnalyser titleJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", titleJapeFeature);				
		
		LanguageAnalyser positiveAdjectivesJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", positiveAdjectiveJapeFeature);
		LanguageAnalyser badAdjectiveJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", badAdjectiveJapeFeature);				
		
		
		LanguageAnalyser positiveCommentsJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", positiveCommentsJapeFeature);				
		LanguageAnalyser negativeCommentsJape = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", negativeCommentsJapeFeature);				
		
		//LanguageAnalyser testNameJape2 = (LanguageAnalyser) Factory.createResource("gate.creole.Transducer", testJapeFeature2);				

		
		// add the language resources to application, here SerialAccessController
		sac.add(aEngTokeniser);
		sac.add(annGazetteer);

		//Add JAPE file
		
		//Comment detector
		sac.add(commentJape);
		
		//Product title detector
		sac.add(titleJape);
		
		//Positive adjs detect
		sac.add(positiveAdjectivesJape);
		
		//Negative adjs detect
		sac.add(badAdjectiveJape);
		
		//Positive comments Jape
		sac.add(positiveCommentsJape);
		
		//Negative comments Jape
		sac.add(negativeCommentsJape);

		long endLoadResourcesTime = System.currentTimeMillis();	// end time
		long loadResourcesTime = endLoadResourcesTime-startLoadResourcesTime;	// total time
		System.out.println("Time to load Processing resources: "+loadResourcesTime+"ms");
		
		return sac;
	}
}