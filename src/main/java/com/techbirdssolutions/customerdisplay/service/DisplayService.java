package com.techbirdssolutions.customerdisplay.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DisplayService {

    private List <SerialPort> DisplayPorts=new ArrayList<>();
    @Value("${clearDisplay}")
    private byte []clearDisplay;

    @Value("${runningUpperStart}")
    private byte []runningUpperStart;

    @Value("${runningUpperEnd}")
    private byte []runningUpperEnd;

    @Value("${downLineStart}")
    private byte []downLineStart;

    @Value("${downLineEnd}")
    private byte []downLineEnd;

    @Value("${resetdisp}")
    private byte []resetdisp;

    public void findport(String name){
        SerialPort[] portlist =SerialPort.getCommPorts();
        String regex = ".*"+name+".*";
        this.DisplayPorts = new ArrayList<>();
        for (SerialPort port:portlist) {
            if(Pattern.matches(regex,port.getDescriptivePortName())){
                this.DisplayPorts.add(port);
            }
            else if(Pattern.matches(regex,port.getPortDescription())){
                this.DisplayPorts.add(port);
            }
        }
    }

    public void clear() {
        for(SerialPort port:this.DisplayPorts){
            try {
                port.openPort();
                port.writeBytes(this.clearDisplay, this.clearDisplay.length);
                port.closePort();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                port.closePort();
            }
        }
    }

    public void writeRunningUpLine(String text) {
        this.write(this.joinbyte(this.runningUpperStart,text,this.runningUpperEnd));
    }

    public void writeDownLine(String text) {
        this.write(this.joinbyte(this.downLineStart,text,this.downLineEnd));
    }

    private byte[] joinbyte(byte[] start,String text,byte[] end){
        byte[] disText = text.getBytes(StandardCharsets.UTF_8);
        byte[] printText = new byte[start.length + end.length + disText.length];
        System.arraycopy(start, 0, printText, 0, start.length);
        System.arraycopy(disText, 0, printText, start.length, disText.length);
        System.arraycopy(end, 0, printText, start.length + disText.length, end.length);
        return printText;
    }

    private void write(byte[] dis) {
        for(SerialPort port:this.DisplayPorts){
            try {
                port.openPort();
                port.writeBytes(dis, dis.length);
                port.closePort();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                port.closePort();
            }
        }

    }

    public void writeTwoLine(String upper, String down) {
        this.clear();
        this.writeDownLine(down);
        this.writeRunningUpLine(upper);
    }

    public void resetdisplays(){
        for (SerialPort port: this.DisplayPorts) {
            try {
                port.openPort();
                port.writeBytes(this.resetdisp, this.resetdisp.length);
                port.closePort();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                port.closePort();
            }
        }
    }




}
