import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.util.CollectionUtils;
import edu.stanford.nlp.util.StringUtils;

public class NGrams {
		
	static HashMap<String, Integer> ngrams_1 = new HashMap<>();
	static HashMap<String, Integer> ngrams_2 = new HashMap<>();
	static HashMap<String, Integer> ngrams_3 = new HashMap<>();
	static HashMap<String, Integer> ngrams_4 = new HashMap<>();
	
	static HashMap<String, Integer> noun_ngrams_1 = new HashMap<>();
	static HashMap<String, Integer> noun_ngrams_2 = new HashMap<>();
	static HashMap<String, Integer> noun_ngrams_3 = new HashMap<>();
	static HashMap<String, Integer> noun_ngrams_4 = new HashMap<>();
	
	static HashMap<String, Integer> positive_ngrams_1 = new HashMap<>();
	static HashMap<String, Integer> positive_ngrams_2 = new HashMap<>();
	static HashMap<String, Integer> positive_ngrams_3 = new HashMap<>();
	static HashMap<String, Integer> positive_ngrams_4 = new HashMap<>();
	
	static HashMap<String, Integer> negative_ngrams_1 = new HashMap<>();
	static HashMap<String, Integer> negative_ngrams_2 = new HashMap<>();
	static HashMap<String, Integer> negative_ngrams_3 = new HashMap<>();
	static HashMap<String, Integer> negative_ngrams_4 = new HashMap<>();
	

	public HashMap<String, Integer> getNoun_ngrams_1() {
		return noun_ngrams_1;
	}

	public HashMap<String, Integer> getNoun_ngrams_2() {
		return noun_ngrams_2;
	}

	public HashMap<String, Integer> getNoun_ngrams_3() {
		return noun_ngrams_3;
	}

	public HashMap<String, Integer> getNoun_ngrams_4() {
		return noun_ngrams_4;
	}
	
	public HashMap<String, Integer> getNgrams_1() {
		return ngrams_1;
	}

	public HashMap<String, Integer> getNgrams_2() {
		return ngrams_2;
	}

	public HashMap<String, Integer> getNgrams_3() {
		return ngrams_3;
	}
	
	public HashMap<String, Integer> getNgrams_4() {
		return ngrams_4;
	}

	public HashMap<String, Integer> getPositive_ngrams_1() {
		return positive_ngrams_1;
	}

	public HashMap<String, Integer> getPositive_ngrams_2() {
		return positive_ngrams_2;
	}

	public HashMap<String, Integer> getPositive_ngrams_3() {
		return positive_ngrams_3;
	}

	public HashMap<String, Integer> getPositive_ngrams_4() {
		return positive_ngrams_4;
	}

	public HashMap<String, Integer> getNegative_ngrams_1() {
		return negative_ngrams_1;
	}

	public HashMap<String, Integer> getNegative_ngrams_2() {
		return negative_ngrams_2;
	}

	public HashMap<String, Integer> getNegative_ngrams_3() {
		return negative_ngrams_3;
	}

	public HashMap<String, Integer> getNegative_ngrams_4() {
		return negative_ngrams_4;
	}
	
	public ArrayList<String> getNgrams(List<String> words, int minSize, int maxSize){
		 List<List<String>> ng = CollectionUtils.getNGrams(words, minSize, maxSize);
		 ArrayList<String> ngrams = new ArrayList<>();
		 for(List<String> n: ng)
		  ngrams.add(StringUtils.join(n," "));
		 return ngrams;
		}
	
	public void getNgrams(ArrayList<String> tweets)
	{
		for(String s:tweets)
		{
			s = s.toLowerCase();
			List<String> words = new ArrayList<>();
			String[] arr = s.split("[^\\S]+");
			for(String s1:arr)
			{
				words.add(s1);
			}
			
			ArrayList<String> list = getNgrams(words,1,4);
			for(String s2:list)
			{
				String[] arr1 = s2.split(" ");
				if(arr1.length==1)
				{
					if(ngrams_1.containsKey(s2))
					{
						ngrams_1.put(s2,ngrams_1.get(s2)+1);
					}
					else
					{
						ngrams_1.put(s2,1);
					}
				}
				else if(arr1.length==2)
				{
					if(ngrams_2.containsKey(s2))
					{
						ngrams_2.put(s2,ngrams_2.get(s2)+1);
					}
					else
					{
						ngrams_2.put(s2,1);
					}
				}
				else if(arr1.length==3)
				{
					if(ngrams_3.containsKey(s2))
					{
						ngrams_3.put(s2,ngrams_3.get(s2)+1);
					}
					else
					{
						ngrams_3.put(s2,1);
					}
				}
				else if(arr1.length==4)
				{
					if(ngrams_4.containsKey(s2))
					{
						ngrams_4.put(s2,ngrams_4.get(s2)+1);
					}
					else
					{
						ngrams_4.put(s2,1);
					}
				}

			}
		}
	}
	
	public void getNounNgrams(ArrayList<String> tweets)
	{
		for(String s:tweets)
		{
			if(s.length()==0)
			{
				continue;
			}
			
			s = s.toLowerCase();
			List<String> words = new ArrayList<>();
			String[] arr = s.split("[^\\S]+");
			for(String s1:arr)
			{
				words.add(s1);
			}
			
			ArrayList<String> list = getNgrams(words,1,4);
			for(String s2:list)
			{
				String[] arr1 = s2.split(" ");
				if(arr1.length==1)
				{
					if(noun_ngrams_1.containsKey(s2))
					{
						noun_ngrams_1.put(s2,noun_ngrams_1.get(s2)+1);
					}
					else
					{
						noun_ngrams_1.put(s2,1);
					}
				}
				else if(arr1.length==2)
				{
					if(noun_ngrams_2.containsKey(s2))
					{
						noun_ngrams_2.put(s2,noun_ngrams_2.get(s2)+1);
					}
					else
					{
						noun_ngrams_2.put(s2,1);
					}
				}
				else if(arr1.length==3)
				{
					if(noun_ngrams_3.containsKey(s2))
					{
						noun_ngrams_3.put(s2,noun_ngrams_3.get(s2)+1);
					}
					else
					{
						noun_ngrams_3.put(s2,1);
					}
				}
				else if(arr1.length==4)
				{
					if(noun_ngrams_4.containsKey(s2))
					{
						noun_ngrams_4.put(s2,noun_ngrams_4.get(s2)+1);
					}
					else
					{
						noun_ngrams_4.put(s2,1);
					}
				}

			}
		}
	}
	
	public void getPositiveNgrams(ArrayList<String> tweets)
	{
		for(String s:tweets)
		{
			if(s.length()==0)
			{
				continue;
			}
			
			s = s.toLowerCase();
			List<String> words = new ArrayList<>();
			String[] arr = s.split("[^\\S]+");
			for(String s1:arr)
			{
				words.add(s1);
			}
			
			ArrayList<String> list = getNgrams(words,1,4);
			for(String s2:list)
			{
				String[] arr1 = s2.split(" ");
				if(arr1.length==1)
				{
					if(positive_ngrams_1.containsKey(s2))
					{
						positive_ngrams_1.put(s2,positive_ngrams_1.get(s2)+1);
					}
					else
					{
						positive_ngrams_1.put(s2,1);
					}
				}
				else if(arr1.length==2)
				{
					if(positive_ngrams_2.containsKey(s2))
					{
						positive_ngrams_2.put(s2,positive_ngrams_2.get(s2)+1);
					}
					else
					{
						positive_ngrams_2.put(s2,1);
					}
				}
				else if(arr1.length==3)
				{
					if(positive_ngrams_3.containsKey(s2))
					{
						positive_ngrams_3.put(s2,positive_ngrams_3.get(s2)+1);
					}
					else
					{
						positive_ngrams_3.put(s2,1);
					}
				}
				else if(arr1.length==4)
				{
					if(positive_ngrams_4.containsKey(s2))
					{
						positive_ngrams_4.put(s2,positive_ngrams_4.get(s2)+1);
					}
					else
					{
						positive_ngrams_4.put(s2,1);
					}
				}

			}
		}
	}
	
	public void getNegativeNgrams(ArrayList<String> tweets)
	{
		for(String s:tweets)
		{
			if(s.length()==0)
			{
				continue;
			}
			
			s = s.toLowerCase();
			List<String> words = new ArrayList<>();
			String[] arr = s.split("[^\\S]+");
			for(String s1:arr)
			{
				words.add(s1);
			}
			
			ArrayList<String> list = getNgrams(words,1,4);
			for(String s2:list)
			{
				String[] arr1 = s2.split(" ");
				if(arr1.length==1)
				{
					if(negative_ngrams_1.containsKey(s2))
					{
						negative_ngrams_1.put(s2,negative_ngrams_1.get(s2)+1);
					}
					else
					{
						negative_ngrams_1.put(s2,1);
					}
				}
				else if(arr1.length==2)
				{
					if(negative_ngrams_2.containsKey(s2))
					{
						negative_ngrams_2.put(s2,negative_ngrams_2.get(s2)+1);
					}
					else
					{
						negative_ngrams_2.put(s2,1);
					}
				}
				else if(arr1.length==3)
				{
					if(negative_ngrams_3.containsKey(s2))
					{
						negative_ngrams_3.put(s2,negative_ngrams_3.get(s2)+1);
					}
					else
					{
						negative_ngrams_3.put(s2,1);
					}
				}
				else if(arr1.length==4)
				{
					if(negative_ngrams_4.containsKey(s2))
					{
						negative_ngrams_4.put(s2,negative_ngrams_4.get(s2)+1);
					}
					else
					{
						negative_ngrams_4.put(s2,1);
					}
				}

			}
		}
	}
	
	

}
