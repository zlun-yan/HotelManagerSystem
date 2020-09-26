package com.csu.login;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class Main {
    public static void main(String[] args) {
    	BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
  	   try{
  		org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
  	   }
  	   catch(Exception e){
  		e.printStackTrace();
  	   }
        LoginFrame frame = new LoginFrame();
    }
}
