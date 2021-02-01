package com.example.appw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {
    public static String Userid="1";
    public static Set<String> HandwareId=new HashSet<>();
    public static List<String> HandID= new ArrayList<>();
    public String  getUserid()
    {
        return Userid;
    }
    public void setUserid(String id)
    {
        Userid=id;
    }
    public static ArrayList<String> Handwarenames=new ArrayList<>();
    public Data(){}
    public static  String newuser="";
    public static  String newpwd="";
    public static  String[] user_name={
            "新用户",
            "夏时雨",
            "马宇辰",
            "韩静怡",
            "王瑶"
    };
    public static  String[] user_val={
            "151****5894",
            "181****2513",
            "187****1322",
            "182****5882",
            "135****9682"
    };
    public static int[] userpic = {
            R.mipmap.user,
            R.mipmap.xia,
            R.mipmap.tangshiyi,
            R.mipmap.han,
            R.mipmap.wanyao,
            R.mipmap.user
    };
    public static int getuserwhere()
    {
        int i=Integer.valueOf(Userid);
        if (i>=1 && i<=4) return i;
        return 0;
    }
    public static int getuserpic(int i)
    {
        if (i>=1 && i<=4) return i;
        return 0;
    }

}
