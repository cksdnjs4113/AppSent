package com.example.chanwon.appsent.Analytics;

import android.content.Context;

import com.example.chanwon.appsent.DAO.DatabaseHelper;
import com.example.chanwon.appsent.Holder.AnswerTable;
import com.example.chanwon.appsent.emotion.EmotionHelper;

import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentUtils;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


public class SentimentTool {
    private static DatabaseHelper mydb;

    private static final NumberFormat NF = new DecimalFormat("0.0000");

    static enum Output {

        PENNTREES, VECTORS, ROOT
    }

    /**
     * Sets the labels on the tree (except the leaves) to be the integer value
     * of the sentiment prediction. Makes it easy to print out with
     * Tree.toString()
     */
    static void setSentimentLabels(Tree tree) {
        if (tree.isLeaf()) {
            return;

        }

        for (Tree child : tree.children()) {
            setSentimentLabels(child);

        }

        Label label = tree.label();
        if (!(label instanceof CoreLabel)) {
            throw new IllegalArgumentException("Required a tree with CoreLabels");
        }
        CoreLabel cl = (CoreLabel) label;
        cl.setValue(Integer.toString(RNNCoreAnnotations.getPredictedClass(tree)));
    }

    /**
     * Sets the labels on the tree to be the indices of the nodes. Starts
     * counting at the root and does a postorder traversal.
     */
    static int setIndexLabels(Tree tree, int index) {
        if (tree.isLeaf()) {
            return index;
        }

        tree.label().setValue(Integer.toString(index));
        index++;
        for (Tree child : tree.children()) {
            index = setIndexLabels(child, index);
        }
        return index;

    }

    /**
     * Outputs the vectors from the tree. Counts the tree nodes the same as
     * setIndexLabels.
     */
    static int outputTreeVectors(Tree tree, int index) {
        if (tree.isLeaf()) {
            return index;

        }

        System.out.print("  " + index + ":");
        SimpleMatrix vector = RNNCoreAnnotations.getNodeVector(tree);
        for (int i = 0; i < vector.getNumElements(); ++i) {
            System.out.print("  " + NF.format(vector.get(i)));
        }
        System.out.println();
        index++;
        for (Tree child : tree.children()) {
            index = outputTreeVectors(child, index);
        }
        return index;
    }

    /**
     * Outputs a tree using the output style requested
     */
    static String outputTree(Tree tree, Output output) {
        String outputtt = null;
        switch (output) {
            case PENNTREES: {
                Tree copy = tree.deepCopy();
                setSentimentLabels(copy);
                outputtt = copy.toString();
                break;
            }
            case VECTORS: {
                Tree copy = tree.deepCopy();
                setIndexLabels(copy, 0);
                System.out.println(copy);
                outputtt = copy.toString();
                outputTreeVectors(tree, 0);
                break;
            }
            case ROOT:
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                outputtt = SentimentUtils.sentimentString(sentiment);
                break;
            default:
                throw new IllegalArgumentException("Unknown output format " + output);
        }
        return outputtt;
    }

    public static void getResult(ArrayList array, Context context) throws IOException {
        Output output = Output.ROOT;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        mydb = new DatabaseHelper(context.getApplicationContext());

        if (array.size() > 1) {
            for (int i = 0; i < array.size(); i++) {
                String hel = array.get(i).toString();
                hel = hel.trim();
                Annotation annotation1 = pipeline.process(hel);
                for (CoreMap sentence : annotation1.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    String emotionState = null;
                    String sentimentState = null;
                    String sentencee = sentence.toString();
                    String ohyea = outputTree(tree, output);

                    EmotionHelper hi = new EmotionHelper();
                    int indexEmotion = hi.feel(sentencee);
                    if (indexEmotion == 0) {
                        emotionState = "happy";
                    } else if (indexEmotion == 1) {
                        emotionState = "sad";
                    } else if (indexEmotion == 2) {
                        emotionState = "anger";
                    } else if (indexEmotion == 3) {
                        emotionState = "fear";
                    } else if (indexEmotion == 4) {
                        emotionState = "disgust";
                    } else if (indexEmotion == 5) {
                        emotionState = "surprise";
                    } else if (indexEmotion == 6) {
                        emotionState = "noemo";
                    }

                    if (ohyea.equals("Neutral")) {
                        sentimentState = "neutral";
                    } else if (ohyea.equals("Positive") || ohyea.equals("Very positive")) {
                        sentimentState = "positive";
                    } else if (ohyea.equals("Negative") || ohyea.equals("Very negative")) {
                        sentimentState = "negative";
                    }
                    mydb.insertData(sentencee,sentimentState,emotionState,"2006-05-01");
                }
            }
        }
    }
}