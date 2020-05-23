import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.pipeline.CoreEntityMention;

public class Main {

	
	public static HashMap<String, Integer> getTop(HashMap<String, Integer> map, int size)
	{
		//ArrayList<String> list = new ArrayList<>();
		HashMap<String, Integer> ngrams = new HashMap<>();
		HashMap<String, Integer> temp = new HashMap<>();
		 
		for(String key:map.keySet())
		{
			int val = map.get(key);
			temp.put(key, val);
		}
		
		for(int i=0;i<size;i++)
		{
			int maxValue = Integer.MIN_VALUE;
			String ngram = new String();
			for(String key:temp.keySet())
			{
				int count = temp.get(key);
				if(count>maxValue)
				{
					maxValue = count;
					ngram = key;
				}
			}
			//list.add(ngram);
			ngrams.put(ngram, maxValue);
			temp.remove(ngram);
		}
		return ngrams;
	}
	
	public static void getResults(String target, HashMap<String, Integer> map, FileWriter fstream) throws IOException
	{
		fstream.write("The top " + target + "are: ");
		System.out.println("The top " + target + "are: ");
		fstream.write("\n");
		
		for(String s:map.keySet())
		{
			fstream.write(s + ":" + map.get(s) + "\n");
			System.out.println(s + ":" + map.get(s) + "\n");
		}
		fstream.write("\n");
	}
	
	public static void getTweetResults(String target, ArrayList<String> list, FileWriter fstream) throws IOException
	{
		fstream.write("The top 20 " + target + "are: ");
		fstream.write("\n");
		System.out.println("The top " + target + "are: ");
		
		for(int i=0;i<list.size();i++)
		{
			fstream.write(i+1 + " " + list.get(i) + "\n");
			System.out.println(i+1 + " " + list.get(i) + "\n");
		}
		fstream.write("\n");
	}
	
	public static void main(String[] args) throws IOException
	{
		DataExploration de = new DataExploration();
		FileReader fileReader = new FileReader("sentiment140.csv");
		//FileReader fileReader = new FileReader("C:\\Users\\Admin\\Desktop\\NYU Courant(2nd sem)\\BDS\\hsw268_BDS_HW3\\sentiment140.csv");
		de.dataExploration(fileReader);
		
		ArrayList<String> raw_tweets = de.getRaw_tweets();
		ArrayList<String> tophashtags = de.getTopHashTags();
		ArrayList<String> topAtTheRates = de.getTopAtTheRates();
		ArrayList<String> filtered_tweets = de.getFiltered_raw_tweets();
		ArrayList<String> noun_tweets = de.getNoun_tweets();
		ArrayList<String> positive_tweets = de.getPositive_tweets();
		ArrayList<String> negative_tweets = de.getNegative_tweets();
		
		NGrams ng = new NGrams();
		ng.getNgrams(filtered_tweets);
		
		HashMap<String, Integer> topNgrams1 = getTop(ng.getNgrams_1(), 20);
		HashMap<String, Integer> topNgrams2 = getTop(ng.getNgrams_2(), 20);
		HashMap<String, Integer> topNgrams3 = getTop(ng.getNgrams_3(), 20);
		HashMap<String, Integer> topNgrams4 = getTop(ng.getNgrams_4(), 20);
		
		ng.getNounNgrams(noun_tweets);
		
		HashMap<String, Integer> topNounNgrams1 = getTop(ng.getNoun_ngrams_1(), 20);
		HashMap<String, Integer> topNounNgrams2 = getTop(ng.getNoun_ngrams_2(), 20);
		HashMap<String, Integer> topNounNgrams3 = getTop(ng.getNoun_ngrams_3(), 20);
		HashMap<String, Integer> topNounNgrams4 = getTop(ng.getNoun_ngrams_4(), 20);
		
		KeyExtraction ke = new KeyExtraction();
		ke.RulesBased();
		
		HashMap<String, Integer> POSadj_noun = getTop(ke.getPOSPattern1(), 10);
		HashMap<String, Integer> POSdet_noun = getTop(ke.getPOSPattern2(), 10);
		HashMap<String, Integer> POSverb_noun = getTop(ke.getPOSPattern3(), 10);
		
//		for(String s:filtered_tweets)
//		{
//		String Parsed_text = ke.dependencyParser(s);
//		ke.extract(Parsed_text);	
//		}
//		
//		ke.getNER(filtered_tweets);
//		
//		HashMap<String, Integer> dependencyParser = ke.getDependencyParser();
//		HashMap<String, Integer> NERS = ke.getFrequentNERS();
		
		//Applying ngram rule to positive and negative tweets
		ng.getPositiveNgrams(positive_tweets);
		ng.getNegativeNgrams(negative_tweets);
		HashMap<String, Integer> topPosNgrams1 = getTop(ng.getPositive_ngrams_1(), 20);
		HashMap<String, Integer> topPosNgrams2 = getTop(ng.getPositive_ngrams_2(), 20);
		HashMap<String, Integer> topPosNgrams3 = getTop(ng.getPositive_ngrams_3(), 20);
		HashMap<String, Integer> topPosNgrams4 = getTop(ng.getPositive_ngrams_4(), 20);
		
		HashMap<String, Integer> topNegNgrams1 = getTop(ng.getNegative_ngrams_1(), 20);
		HashMap<String, Integer> topNegNgrams2 = getTop(ng.getNegative_ngrams_2(), 20);
		HashMap<String, Integer> topNegNgrams3 = getTop(ng.getNegative_ngrams_3(), 20);
		HashMap<String, Integer> topNegNgrams4 = getTop(ng.getNegative_ngrams_4(), 20);
		
	
		FileWriter fstream = new FileWriter("report.txt");
		
		getTweetResults("hashtags ", tophashtags, fstream);
		getTweetResults("atTheRates ", topAtTheRates, fstream);
		
		getResults("20 topNGrams1 ", topNgrams1, fstream);
		getResults("20 topNGrams2 ", topNgrams2, fstream);
		getResults("20 topNGrams3 ", topNgrams3, fstream);
		getResults("20 topNGrams4 ", topNgrams4, fstream);
		getResults("20 topNounNGrams1 ", topNounNgrams1, fstream);
		getResults("20 topNounNGrams2 ", topNounNgrams2, fstream);
		getResults("20 topNounNGrams3 ", topNounNgrams3, fstream);
		getResults("20 topNounNGrams4 ", topNounNgrams4, fstream);
		getResults("10 POS Patterns for adjective noun ", POSadj_noun, fstream);
		getResults("10 POS Patterns for det noun ", POSdet_noun, fstream);
		getResults("10 POS Patterns for verb noun ", POSverb_noun, fstream);
		
		//Results for running ngram model rule of  positive and negative tweets
		getResults("20 topPosNGrams1 ", topPosNgrams1, fstream);
		getResults("20 topPosNGrams2 ", topPosNgrams2, fstream);
		getResults("20 topPosNGrams3 ", topPosNgrams3, fstream);
		getResults("20 topPosNGrams4 ", topPosNgrams4, fstream);
		getResults("20 topNegNGrams1 ", topNegNgrams1, fstream);
		getResults("20 topNegNGrams2 ", topNegNgrams2, fstream);
		getResults("20 topNegNGrams3 ", topNegNgrams3, fstream);
		getResults("20 topNegNGrams4 ", topNegNgrams4, fstream);
		
		
		//getResults("Frequent NERS ", NERS, fstream);
		//getResults("Frequent Subject Root Object Phrases ", dependencyParser, fstream);
		
		
		fstream.close();
		
		
		
		
		
	}
}
