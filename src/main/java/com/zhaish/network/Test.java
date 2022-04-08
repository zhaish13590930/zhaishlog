package com.zhaish.network;

/**
 * @author zhaish
 * @date 2022/1/11 9:47
 **/
public class Test {
    public static void main(String[] args) {
        String ss = "   maxLen++;";
        int a = 100;
        int b = a++;
        System.out.println(b+"  "+a);
        Test test = new Test();
        int c = test.lengthOfLongestSubstring("abcabcbb");
        System.out.println(c);
    }


    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int size = s.length();
        char[] array = s.toCharArray();
        //Set<String> set = new HashSet<>();
        int max = 0;
        String maxStr = null;
        char[] tempArr = new char[size+1];
        for (int i = 0; i < size; i++) {
            clear(tempArr);
            int maxLen = 0;
            int start = i;
            int tempIndex = 0;
            while (start<size){
                char c = array[start];
                if(contain(tempArr,c)){
                    break;
                }else{
                    tempArr[tempIndex++] = c;
                    maxLen++;
                }
                start++;
            }

            if(maxLen > max){
                max = maxLen;
                maxStr = new String(tempArr);
            }

            if((size - i) < max){
                break;
            }
        }
        System.out.println(max+"        "+new String(tempArr));
        return max;
    }
    private boolean contain(char[] arr,char c){
        for (int i = 0; i < arr.length; i++) {
            if(c != 0 && c == arr[i] && arr[i] != 0 ){
                return true;
            }
        }
        return false;
    }
    private void clear(char[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }
}
