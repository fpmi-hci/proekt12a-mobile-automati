package com.zlatamigas.readme.util;

import java.util.Iterator;
import java.util.List;

public class StringMerger {

    public static final String DEFAULT_DIVIDER = ", ";

    private StringMerger(){}

    public static String mergeStrings(List<String> stringList){

        StringBuilder sb = new StringBuilder("");
        Iterator<String> iterator = stringList.iterator();
        if(iterator.hasNext()){
            sb.append(iterator.next());

            while (iterator.hasNext()){
                sb.append(DEFAULT_DIVIDER).append(iterator.next());
            }
        }

        return sb.toString();
    }

    public static String mergeStrings(List<String> stringList, String divider){

        StringBuilder sb = new StringBuilder("");
        Iterator<String> iterator = stringList.iterator();
        if(iterator.hasNext()){
            sb.append(iterator.next());

            while (iterator.hasNext()){
                sb.append(divider).append(iterator.next());
            }
        }

        return sb.toString();
    }
}
