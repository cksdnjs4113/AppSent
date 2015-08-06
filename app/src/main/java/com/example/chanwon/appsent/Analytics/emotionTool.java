//package com.example.chanwon.appsent.Analytics;
//
//import com.example.chanwon.appsent.Holder.emotionTable;
//import com.example.chanwon.appsent.emotion.EmotionHelper;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * Created by CHANWON on 7/31/2015.
// */
//public class emotionTool {
//    public static emotionTable getResult(ArrayList array) throws IOException {
//
//        Integer happyInt = 0;
//        Integer sadInt = 0;
//        Integer angerInt = 0;
//        Integer fearInt = 0;
//        Integer disgustInt = 0;
//        Integer surpirseInt = 0;
//        Integer noemoInt = 0;
//
//        emotionTable result = new emotionTable();
//
//        if (array.size() > 1) {
//            for (int i = 0; i < array.size(); i++) {
//                EmotionHelper hi = new EmotionHelper();
//                String sentence = (String) array.get(i);
//                int indexEmotion = hi.feel(sentence);
//                if (indexEmotion == 0) {
//                    happyInt++;
//                } else if (indexEmotion == 1) {
//                    sadInt++;
//                } else if (indexEmotion == 2) {
//                    angerInt++;
//                } else if (indexEmotion == 3) {
//                    fearInt++;
//                } else if (indexEmotion == 4) {
//                    disgustInt++;
//                } else if (indexEmotion == 5) {
//                    surpirseInt++;
//                } else if (indexEmotion == 6) {
//                    noemoInt++;
//                }
//            }
//            result.setHappy(happyInt.toString());
//            result.setSad(sadInt.toString());
//            result.setAnger(angerInt.toString());
//            result.setFear(fearInt.toString());
//            result.setDisgust(disgustInt.toString());
//            result.setSuprise(surpirseInt.toString());
//            result.setNoemo(noemoInt.toString());
//
//        }
//
//        return result;
//    }
//}
