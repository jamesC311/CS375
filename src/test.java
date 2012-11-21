import java.util.Collection;

import java.util.Iterator;


public class test {
	public static void main(String[] args){
		Collection<WordFrequency> dog = new WordFrequencyCollection();
		dog.add(new WordFrequency("hello"));
		dog.add(new WordFrequency("hi"));
		dog.add(new WordFrequency("hey"));
		dog.add(new WordFrequency("who"));
		dog.add(new WordFrequency("hi"));
		dog.add(new WordFrequency("hey"));
		dog.add(new WordFrequency("who"));
		dog.add(new WordFrequency("hi"));
		dog.add(new WordFrequency("hey"));
		dog.add(new WordFrequency("who"));
		dog.add(new WordFrequency("hi"));
		dog.add(new WordFrequency("hey"));
		dog.add(new WordFrequency("who"));
		dog.add(new WordFrequency("dog"));
		dog.add(new WordFrequency("dog"));
		
		Collection<WordFrequency> oldList = new WordFrequencyCollection();
		
		Iterator<WordFrequency> itr = dog.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());	
		}
		
	}
	
	public static void selectSort ( Collection<WordFrequency> work )
	{  Collection<WordFrequency> oldList = new WordFrequencyCollection();
	   oldList.addAll(work);
	   Iterator finder;
	   Comparable  maxValue;

	   if ( oldList.size() < 2 )      // Empty or single-element list
	      return;
	   work.clear();     // Empty out the list to receive the new contents
	   // When only one item remains, it is the smallest and goes first
	   while ( oldList.size () > 1 )
	   {  Iterator maxLocation;

	      finder = oldList.iterator();
	      maxValue = (Comparable) finder.next();
	      while ( finder.hasNext() )
	      {  Comparable testValue = (Comparable) finder.next();

	         if ( maxValue.compareTo(testValue) < 0 )
	         {  maxValue = testValue;
	            // We've just stepped PAST the item, so correct for that
	            maxLocation = finder;
	         }
	      }
	      // Note:  this generates an extra traversal
	      finder = oldList.iterator();
	      maxValue = (Comparable) finder.next();
	      finder.remove();  // Eliminate the element returned by finder.next()
	      work.add((WordFrequency)maxValue);    // Add to the FRONT of the new list.
	   }
	   // Move the last value --- the smallest item
	   finder = oldList.iterator();
	   maxValue = (Comparable) finder.next();
	   finder.remove();      // not really required, given garbage collection
	   work.add((WordFrequency) maxValue);
	}
}
