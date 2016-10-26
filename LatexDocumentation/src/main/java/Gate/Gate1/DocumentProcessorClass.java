package Gate.Gate1;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Resource;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;

public class DocumentProcessorClass {

	public static void processDocuments(SerialAnalyserController sac, File[] allFiles, 
			ArrayList<ArrayList<String>> rows, int fileClusterLength) 
					throws ResourceInstantiationException, ExecutionException, MalformedURLException{
		
		// create a corpus for storing results
		Corpus corpus = Factory.newCorpus("Test Data Corpus");
		
		// arraylist to store document resources
		ArrayList<Document> documentResList = new ArrayList<Document>();
		
		
		int i=0;	// variable to name each doc uniquely
		Integer uniqueColKey = 0;	// unique column key for each arraylist of each column
		int rowCount=0;
		
		// processing of docs starts
		long docsStartTime=System.currentTimeMillis();
		int countFiles=0;	// keep counting till max cluster size
				
		int totalFilesProcessed = 0;
		
		//for each file
		for (File f : allFiles) {
			
			URL sourceUrl = f.toURI().toURL();
			countFiles++; // increment for each file
			totalFilesProcessed++;
			
			FeatureMap params = Factory.newFeatureMap();
			params.put(Document.DOCUMENT_URL_PARAMETER_NAME, sourceUrl);
			params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "UTF-8");
			
			
			FeatureMap features = Factory.newFeatureMap();
			features.put("createdOn", new Date());
			
			
			//increment i to name each doc and corpus uniquely
			i++;
			
			//create doc with specified params, features and unique name
			Document doc = (Document) Factory.createResource("gate.corpora.DocumentImpl", params, features, f.getName() + "TestDoc" + i);
		
			//add doc to corpus
			corpus.add(doc);
			
			//also maintain a list of these documents
			documentResList.add(doc);
			
			//if file cluster is full , proces the corpus and analyze de documents inside corpus
			if (countFiles == fileClusterLength || totalFilesProcessed == allFiles.length) {
				long docsEndTime=System.currentTimeMillis();
				long fileTime = docsEndTime-docsStartTime;
				
				System.out.println("Time to retrieve " + totalFilesProcessed + " files: " + fileTime +"ms");

				//add the corpuss to sac
				sac.setCorpus(corpus);
				
				//execute the sac on the corpus
				System.out.println("Executing SAC on corpus....");
				long sacStartTime = System.currentTimeMillis();
				sac.execute();
				long sacEndTime = System.currentTimeMillis();
				long sacTime = sacEndTime-sacStartTime;
			
				System.out.println("Time to execute SAC on a corpus of "+totalFilesProcessed+" files: "+sacTime+"ms");
			
				System.out.println("Analysing processed docs..");
				
				
				int currentDOc=1;
				long docsProcessStartTime = System.currentTimeMillis();
				
				//start analyzing each data from the corpus			
				for (Iterator<Document> corpIterator = corpus.iterator(); corpIterator.hasNext();) {
						
					//increment rowCount since doc row added; represents current document number
					rowCount++;
					
					//get the document from corpus
					Document corpDoc = corpIterator.next();
					
					//getting default set of annotations
					AnnotationSet defaultSet = corpDoc.getAnnotations();
					
					//Getting annotations of Comments
					AnnotationSet commentTypeAnnotations = defaultSet.get("CommentContent");
					
					//Getting ProductTitle Annotations
					AnnotationSet titleTypeAnnotations = defaultSet.get("ProductName");
					
					//Getting annotations of PositiveComments
					AnnotationSet positiveCommentTypeAnnotations = defaultSet.get("PositiveComment");

					//Getting annotations of NegativeComments
					AnnotationSet negativeCommentTypeAnnotations = defaultSet.get("NegativeComment");

					
					//ArrayList for annotations to be saved in the Excel
					ArrayList<String> annList = new ArrayList<String>();
					
					//Adding the number of commentAnnotations
					annList.add(String.valueOf(commentTypeAnnotations.size()));
					
					//Adding the number of positiveCommentAnnotations
					annList.add(String.valueOf(positiveCommentTypeAnnotations.size()));

					//Adding the number of negativeCommentAnnotations
					annList.add(String.valueOf(negativeCommentTypeAnnotations.size()));

					
					//Adding the title of the product to the annList
					if (titleTypeAnnotations.size() > 1) {
						annList.add("NoTitle");
					}
					else {
						for (Annotation titleAnnotation : titleTypeAnnotations) {
							FeatureMap titleFeatureMap = titleAnnotation.getFeatures();
							String titleNamesString = titleFeatureMap.get("title").toString();
							annList.add(titleNamesString.trim());
						}
					}
					
					//iterate over these commentAnnotations and add them to list
					for (Annotation commentAnnotation : commentTypeAnnotations) {
						FeatureMap commentFeatureMap = commentAnnotation.getFeatures();
						String commentString = commentFeatureMap.get("string").toString();
						annList.add(commentString.trim());
					}

					rows.add(annList);
					currentDOc++;
				}

						
						System.out.println(currentDOc+" processed docs analysed!");
						long docsProcessEndTime = System.currentTimeMillis();
						long docsProcessTime = docsProcessEndTime-docsProcessStartTime;
						System.out.println("Time to analyse processed docs: "+docsProcessTime+"ms");
					
						//reset the counter to zero
						countFiles = 0;
					docsStartTime = System.currentTimeMillis();
					}
			}
					
		}

}
			
