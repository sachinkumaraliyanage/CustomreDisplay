package com.techbirdssolutions.customerdisplay.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    @Value("${upperLineSize}")
    private int upperLineSize;

    @Value("${downLineSize}")
    private int downLineSize;

    public String upperLine(String pname,String price,String qty,String total){
        String upperLine = " "+price+"x"+qty+"="+total+" ";
        if(upperLine.length()+pname.length()>this.upperLineSize){
            upperLine = pname.substring(0,this.upperLineSize-3-upperLine.length())+"..."+upperLine;
        }else{
            upperLine = pname + upperLine;
        }
        return upperLine;
    }

    public String downLine(String word,String number){
        word = word+" : "+number;
        for (int i=0;i<=(int) Math.ceil((this.downLineSize-word.length())/2.0);i++){
            word = " "+word;
        }
        return word;
    }
    public String downLine(String word){
        for (int i=0;i<=(int) Math.ceil((this.downLineSize-word.length())/2.0);i++){
            word = " "+word;
        }
        return word;
    }
}
