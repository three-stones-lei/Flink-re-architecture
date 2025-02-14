package org.lei;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//        System.out.println(isTest());
        //System.out.println(isPerfectSquare(808201));

        assertTrue( true );
    }

    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }

        List<String> slist = Arrays.asList(s.split(""));
        slist.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        List<String> tlist = Arrays.asList(t.split(""));
        tlist.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(slist);
        System.out.println(tlist);
        return slist.equals(tlist);
    }

    public boolean isPerfectSquare(int num) {
        long start = 1;
        long end = num+1;
        long middle = 0;
        while(start<end){
            middle = start + (end-start)/2;
            if(middle*middle == num){
                return true;
            }else if((middle*middle) > num){
                end = middle;
            }else{
                start = middle+1;
            }
        }
        return false;
    }

    public char findTheDifference(String s, String t) {
        char[] tArray = t.toCharArray();
        String result = "";
        for(int i=0;i< tArray.length;i++){
            if(!s.contains(String.valueOf(tArray[i]))){
                return tArray[i];
            }else if(s.length() != t.length()){
                for (int j = 0; j < t.length()-s.length(); j++) {
                    result += String.valueOf(tArray[i]);
                }
            }

        }
        return result.toCharArray()[0];
    }

    public Long thirdMax(Long[] nums) {
        //1.transfer to hashset, remove duplicate
        Set<Long> numsSet = new HashSet<Long>();
        for(int i=0;i< nums.length;i++){
            numsSet.add(nums[i]);
        }

        //2.transfer to arraylist and sort by number descending
        List<Long> numsList = new ArrayList<Long>();
        for(Long i: numsSet){
            numsList.add(i);
        }
        numsList.sort(new Comparator<Long>() {
                          @Override
                          public int compare(Long o1, Long o2) {
                              return 0;
                          }
                      }
        );


        //3.If length of arraylist more than 3, get third element; elsewise, get first element
        if(numsList.size() >=3){
            return numsList.get(2);
        }else{
            return numsList.get(0);
        }

    }
}
