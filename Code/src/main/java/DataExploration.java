import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;

public class DataExploration {
   
	static ArrayList<String> raw_tweets = new ArrayList<String>();
	static ArrayList<String> filtered_raw_tweets = new ArrayList<String>();
	static ArrayList<String> noun_tweets = new ArrayList<String>();
	static HashMap<String, Integer> hashtags = new HashMap<>();
	static HashMap<String, Integer> atTheRates = new HashMap<>();
	
	static ArrayList<String> topHashTags = new ArrayList<String>();
	static ArrayList<String> topAtTheRates = new ArrayList<String>();
	
	static ArrayList<String> positive_tweets = new ArrayList<String>();
	static ArrayList<String> negative_tweets = new ArrayList<String>();
	
	final static int size = 20;
	
	
	public void dataExploration(FileReader file) throws IOException
	{
		@SuppressWarnings("resource")
		CSVReader csvReader = new CSVReader(file);
		String[] values;
		
		while((values = csvReader.readNext())!=null)
		{
			String tweet = values[5];
			int sentiment = Integer.parseInt(values[0]);
			
			if(sentiment==4)
			{
			    String ptweet = tweet;
			    String positive_tweet = filterTweets(ptweet);
			    positive_tweets.add(positive_tweet);
			}
			else if(sentiment==0)
			{
				String ntweet = tweet;
				String negative_tweet = filterTweets(ntweet);
			    negative_tweets.add(negative_tweet);	
			}
			
			
			raw_tweets.add(tweet);
			String filtered_tweet = filterTweets(tweet);
			String noun_tweet = getNounTweets(filtered_tweet);
			filtered_raw_tweets.add(filtered_tweet);
			
			//extract(filtered_tweet);
			
			
			noun_tweets.add(noun_tweet);
			
			getHashTags(tweet);
			getAtTheRates(tweet);
		}
		
		getTopHashTags(hashtags);
		getTopAtTheRates(atTheRates);
	}

	public void getHashTags(String line) 
	{	
		Pattern pattern = Pattern.compile("#[\\S]+");
		Matcher matcher = pattern.matcher(line);
		
		 while (matcher.find()) {
			 
			 String s = matcher.group();
			 if(hashtags.containsKey(s))
				{
					hashtags.put(s, hashtags.get(s)+1);
				}
				else
				{
					hashtags.put(s, 1);
				}
		 }
	}
	
	public ArrayList<String> getPositive_tweets() {
		return positive_tweets;
	}

	public ArrayList<String> getNegative_tweets() {
		return negative_tweets;
	}

	public ArrayList<String> getNoun_tweets() {
		return noun_tweets;
	}
	
	public ArrayList<String> getRaw_tweets() {
		return raw_tweets;
	}

	public ArrayList<String> getFiltered_raw_tweets() {
		return filtered_raw_tweets;
	}

	public HashMap<String, Integer> getHashtags() {
		return hashtags;
	}

	public HashMap<String, Integer> getAtTheRates() {
		return atTheRates;
	}

	public ArrayList<String> getTopHashTags() {
		return topHashTags;
	}

	public ArrayList<String> getTopAtTheRates() {
		return topAtTheRates;
	}

	public void getAtTheRates(String line)
	{
		
		Pattern pattern = Pattern.compile("@[\\S]+");
		Matcher matcher = pattern.matcher(line);
		
		 while (matcher.find()) {
			 
			 String s = matcher.group();
			 if(atTheRates.containsKey(s))
				{
					atTheRates.put(s, atTheRates.get(s)+1);
				}
				else
				{
					atTheRates.put(s, 1);
				}
		 }	
	}
	
	public void getTopHashTags(HashMap<String, Integer> hashtags)
	{
		for(int i=0;i<size;i++)
		{
			int maxValue = Integer.MIN_VALUE;
			String hashtag = new String();
			for(String key:hashtags.keySet())
			{
				int count = hashtags.get(key);
				if(count>maxValue)
				{
					maxValue = count;
					hashtag = key;
				}
			}
			DataExploration.topHashTags.add(hashtag);
			hashtags.remove(hashtag);
		}
	}
	
	public void getTopAtTheRates(HashMap<String, Integer> atTheRates)
	{
		for(int i=0;i<size;i++)
		{
			int maxValue = Integer.MIN_VALUE;
			String atTheRate = new String();
			for(String key:atTheRates.keySet())
			{
				int count = atTheRates.get(key);
				if(count>maxValue)
				{
					maxValue = count;
					atTheRate = key;
				}
			}
			DataExploration.topAtTheRates.add(atTheRate);
			atTheRates.remove(atTheRate);
		}
	}
	
	public String filterTweets(String tweet)
	{
		String filtered_tweet = "";
		filtered_tweet = tweet.replaceAll("http.*?[\\S]+", "").replaceAll("@[\\S]+", "").replaceAll("#", "").replaceAll("[^\\x00-\\x7f-\\x80-\\xad]", "");
		filtered_tweet = filtered_tweet.replaceAll("[^a-zA-Z0-9 ]+", "").trim();
		return filtered_tweet;
	}
	
	public String getNounTweets(String tweet)
	{
		String noun_tweet = "";
		String[] arr = tweet.split(" ");
		KeyExtraction ke = new KeyExtraction();
		
		for(String s:arr)
		{
			if(ke.isNoun(s))
			{
				noun_tweet = noun_tweet + s + " ";
			}
		}
		return noun_tweet;
	}
	
	
}
