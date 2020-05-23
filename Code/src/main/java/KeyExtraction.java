import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.TypesafeMap;

public class KeyExtraction {

	
//	 my three pos patterns:
//  	   1) adjective noun
//       2) determinant noun
//       3) verb noun
 
	HashMap<String, Integer> POSPattern1 = new HashMap<>();
	HashMap<String, Integer> POSPattern2 = new HashMap<>();
	HashMap<String, Integer> POSPattern3 = new HashMap<>();
	
	HashMap<String, Integer> FrequentNERS = new HashMap<>();
	
	HashMap<String, Integer> dependencyParser = new HashMap<>();
	
	public HashMap<String, Integer> getFrequentNERS() {
		return FrequentNERS;
	}
	
	public HashMap<String, Integer> getDependencyParser() {
		return dependencyParser;
	}

	public HashMap<String, Integer> getPOSPattern1() {
		return POSPattern1;
	}

	public HashMap<String, Integer> getPOSPattern2() {
		return POSPattern2;
	}

	public HashMap<String, Integer> getPOSPattern3() {
		return POSPattern3;
	}

	NGrams ng = new NGrams();
	
	public void RulesBased()
	{
		getPOSPatterns(ng.getNgrams_2());
	}
	
	public void getPOSPatterns(HashMap<String, Integer> map)
	{
		for(String key:map.keySet())
		{
			String[] arr = key.split(" ");
			if(isAdjective(arr[0]) && isNoun(arr[1]))
			{
				if(POSPattern1.containsKey(key))
				{
					POSPattern1.put(key, POSPattern1.get(key)+1);
				}
				else
				{
					POSPattern1.put(key, 1);
				}
			}
			else if(isDet(arr[0]) && isNoun(arr[1]))
			{
				if(POSPattern2.containsKey(key))
				{
					POSPattern2.put(key, POSPattern2.get(key)+1);
				}
				else
				{
					POSPattern2.put(key, 1);
				}
			}
			else if(isVerb(arr[0]) && isNoun(arr[1]))
			{
				if(POSPattern3.containsKey(key))
				{
					POSPattern3.put(key, POSPattern3.get(key)+1);
				}
				else
				{
					POSPattern3.put(key, 1);
				}
			}
		}
	}

	public String dependencyParser(String tweet)
	{
		String modelPath = DependencyParser.DEFAULT_MODEL;
		MaxentTagger tagger = new MaxentTagger("wsj-0-18-bidirectional-nodistsim.tagger");
		//MaxentTagger tagger = new MaxentTagger("C:\\Users\\Admin\\Desktop\\NYU Courant(2nd sem)\\BDS\\hsw268_BDS_HW3\\hsw268_BDS_hw3\\wsj-0-18-bidirectional-nodistsim.tagger");
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);
		
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(tweet));
		String Parsed_text = "";
		
		for (List<HasWord> sentence : tokenizer) 
		   {
		    List<TaggedWord> tagged = tagger.tagSentence(sentence); 
		    GrammaticalStructure gs = parser.predict(tagged);
		    Parsed_text = gs.typedDependencies().toString();
		   }

		return Parsed_text;
	}
	
	public void extract(String Parsed_text)
	{
	    String nounPhrase = getNounPhrase(Parsed_text);
	    if(nounPhrase.length()==0)
	    {
	    	return;
	    }
	    
	    String rootPhrase = getrootPhrase(Parsed_text);
	    if(rootPhrase.length()==0)
	    {
	    	return;
	    }
	    
	    String objPhrase = getobjPhrase(Parsed_text);
	    if(objPhrase.length()==0)
	    {
	    	return;
	    }
	    
	    String nounPos = getWordPos(nounPhrase);
	    String rootPos = getWordPos(rootPhrase);
	    String objPos = getWordPos(objPhrase); 
	    
	    String[] nounarr = nounPos.split("-");
	    if(nounarr.length<2)
	    {
	    	return;
	    }
	    String[] rootarr = rootPos.split("-");
	    if(rootarr.length<2)
	    {
	    	return;
	    }
	    String[] objarr = objPos.split("-");
	    if(objarr.length<2)
	    {
	    	return;
	    }
	    
	    int nounNb = Integer.parseInt(nounarr[1]);
	    int rootNb = Integer.parseInt(rootarr[1]);
	    int objNb = Integer.parseInt(objarr[1]);
	    
	    String noun = nounarr[0];
	    String root = rootarr[0];
	    String obj = objarr[0];
	    
	    if((rootNb - nounNb == 1) && (objNb - rootNb ==1))
	    {
	    	String store = noun + " " + root + " " + obj;
	    	if(dependencyParser.containsKey(store))
	    	{
	    		dependencyParser.put(store, dependencyParser.get(store)+1);
	    	}
	    	else
	    	{
	    		dependencyParser.put(store, 1);
	    	}
	    }
	}
	
	public String getWordPos(String Phrase)
	{
		String s = "";
		Pattern pattern = Pattern.compile(" [A-Za-z-0-9]+");
		Matcher matcher = pattern.matcher(Phrase);
		
		 while (matcher.find()) {
			 s = matcher.group();
		 }	
		 
		s = s.trim(); 
		return s;
	}
	
	public String getNounPhrase(String Parsed_text)
	{
		String s = "";
		Pattern pattern = Pattern.compile("nsubj\\([A-Za-z-0-9, )]+[A-Za-z]+-[0-9)]+");
		Matcher matcher = pattern.matcher(Parsed_text);
		
		 while (matcher.find()) {
			 s = matcher.group();
		 }	
		return s;
	}
	
	public String getrootPhrase(String Parsed_text)
	{
		String s = "";
		Pattern pattern = Pattern.compile("root\\([A-Za-z-0-9, )]+[A-Za-z]+-[0-9)]+");
		Matcher matcher = pattern.matcher(Parsed_text);
		
		 while (matcher.find()) {
			 s = matcher.group();
		 }	
		return s;
	}
	
	public String getobjPhrase(String Parsed_text)
	{
		String s = "";
		Pattern pattern = Pattern.compile("dobj\\([A-Za-z-0-9, )]+[A-Za-z]+-[0-9)]+");
		Matcher matcher = pattern.matcher(Parsed_text);
		
		 while (matcher.find()) {
			 s = matcher.group();
		 }	
		return s;
	}
	
	public void getNER(ArrayList<String> tweets)
	{
		
		for(String tweet:tweets)
		{
		  Properties props = new Properties();
		  props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		  StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		  CoreDocument document  = new CoreDocument(tweet);
		//Annotation document = new Annotation(tweet);
		  pipeline.annotate(document);
		
		  List<CoreEntityMention> em = document.sentences().get(0).entityMentions();
		
		  for(CoreEntityMention s:em)
		  {
			  System.out.println(s);
			  String s1 = s.text();
			  if(FrequentNERS.containsKey(s1))
			 {
				 FrequentNERS.put(s1, FrequentNERS.get(s1)+1);
			 }
			  else
			  {
				  FrequentNERS.put(s1, 1);
			  }
		  }
		}
		//System.out.print(document.sentences().get(0).entityMentions());
//		List<CoreMap> sentences = ((TypesafeMap) document).get(CoreAnnotations.SentencesAnnotation.class);
//		
//		for (CoreMap sentence : sentences) {
//			System.out.println(sentence.entityMention);
//			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//				ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//				System.out.println(token + " " + ne);
//			}
//		}
//		return ne;
	}
	
	
	
	public List<String> getPos(String word)
	{
		Sentence sen = new Sentence(word);
		List<String> pos = sen.posTags();
		return pos;
	}
	
	public boolean isNoun(String word)
	{
		if(word.length()==0)
		{
			return false;
		}
		
		Sentence sen = new Sentence(word);
		List<String> pos = sen.posTags();
		String noun = "NN";
		
		for(String s:pos)
		{
			if(!s.contains(noun))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isDet(String word)
	{
		Sentence sen = new Sentence(word);
		List<String> pos = sen.posTags();
		String noun = "DT";
		
		for(String s:pos)
		{
			if(!s.contains(noun))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isAdjective(String word)
	{
		Sentence sen = new Sentence(word);
		List<String> pos = sen.posTags();
		String noun = "JJ";
		
		for(String s:pos)
		{
			if(!s.contains(noun))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isVerb(String word)
	{
		Sentence sen = new Sentence(word);
		List<String> pos = sen.posTags();
		String noun = "VB";
		
		for(String s:pos)
		{
			if(!s.contains(noun))
			{
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		KeyExtraction ke = new KeyExtraction();
		boolean ans = ke.isNoun("cant");
		System.out.print(ans);
	}
	
}
