package com.bkbklim.Logic;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.bkbklim.Helpers.AssetLoader;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bklim on 01/12/15.
 */
public class QuestionGenerator {
    private int level;
    private String question;
    private ArrayList<Integer> answerOptions;
    private int answerLocation, operator, numOfAnswer;
    private Random rand;
    private GlyphLayout questionTextLayout;
    private int longQuestion;

    public QuestionGenerator() {
        answerOptions = new ArrayList<Integer>();
        answerLocation = 0;
        rand = new Random();
        question = "";
        questionTextLayout = new GlyphLayout();

    }

    public void setLevel(int level) {
        this.level = level;
        if (level <= 5) {
            numOfAnswer = 2;
        } else if (level > 5 && level <= 30) {
            numOfAnswer = 3;
        } else {
            numOfAnswer = 4;
        }
    }

    public void generateQuestion() {

        answerOptions.clear();

        numOfAnswer = calcNumOfAnswer(0);
        answerLocation = rand.nextInt(numOfAnswer);


        //level 1 - 5
        if (level <= 10) {
            //addition, substraction and multiplication for level <= 10
            operator = rand.nextInt(3);

            switch (operator) {
                case 0:
                    generateAddition();
                    break;

                case 1:
                    generateSubstraction();
                    break;

                case 2:
                    generateMultiplication();
                    break;
            }

        } else {
            operator = rand.nextInt(4);

            switch (operator) {
                case 0:
                    generateAddition();
                    break;

                case 1:
                    generateSubstraction();
                    break;

                case 2:
                    generateMultiplication();
                    break;

                case 3:
                    generateDivision();
                    break;
            }


        }


    }

    public int calcNumOfAnswer(int operator) {
        if (level <= 5 ) {
            return 2;
        } else if (level > 5 && level <= 30) {
            return 3;
        } else {
            return 4;
        }

    }

    public void generateAddition() {
        int[] nums;
        int answer = 0;
        question = "";
        int typeOfAddition = 0; //0 - 2 number addition, 1 - 3 numbers addition

        if (level <= 10) {
            nums = new int[2];
            nums[0] = rand.nextInt(5 * level) + level;
            nums[1] = rand.nextInt(5 * level) + level;


        } else if (level > 10 && level <= 30){
            typeOfAddition = rand.nextInt(2);
            switch (typeOfAddition) {
                case 0:
                    nums = new int[2];
                    nums[0] = rand.nextInt(level * 5) + level * 5;
                    nums[1] = rand.nextInt(level * 5) + level * 5;
                    break;

                case 1:
                default:
                    nums = new int[3];
                    nums[0] = rand.nextInt(level * 5) ;
                    nums[1] = rand.nextInt(level * 5) ;
                    nums[2] = rand.nextInt(level * 5) ;
                    break;
            }

        } else { // level > 30
            typeOfAddition = rand.nextInt(2);
            switch (typeOfAddition) {
                case 0:
                    nums = new int[3];
                    nums[0] = rand.nextInt(level * 5) + level * 5;
                    nums[1] = rand.nextInt(level * 5) + level * 5;
                    nums[2] = rand.nextInt(level * 5) + level * 5;
                    break;

                case 1:
                default:
                    nums = new int[4];
                    nums[0] = rand.nextInt(level * 5) + level;
                    nums[1] = rand.nextInt(level * 5) + level;
                    nums[2] = rand.nextInt(level * 5) + level;
                    nums[3] = rand.nextInt(level * 5) + level;
                    break;
            }

        }

        int length = nums.length;
        for (int i = 0 ; i < length; i++ ) {
            if (i == length - 1) {
                question += nums[i];
            } else {
                question += nums[i] + " + ";
            }
            answer += nums[i];
        }

        setQuestionTextLayout(question, nums.length);

        for (int i = 0; i < numOfAnswer; i++) {
            if (answerLocation == i) {
                answerOptions.add(answer);
            } else {
                int option = 0;
                do {
                    if (level <= 10) {
                        option = rand.nextInt(10 * level) + 2 * level;

                    } else if (level > 10 && level <= 30){
                        switch (typeOfAddition) {
                            case 0:
                                option = rand.nextInt(level * 10) + level * 10;
                                break;

                            case 1:
                                option = rand.nextInt(level * 15) + level * 3;
                                break;

                        }
                    } else {
                        switch (typeOfAddition) {
                            case 0:
                                option = rand.nextInt(level * 15) + level * 15;
                                break;

                            case 1:
                                option = rand.nextInt(level * 20) + level * 4;
                                break;

                        }

                    }

                } while (option == answer);
                answerOptions.add(option);
            }
        }
    }

    public void generateSubstraction() {
        int num1, num2, num3, num4;
        int typeOfSubstraction = 0;
        int answer = 0;


        if (level <= 10) {
            num1 = rand.nextInt(level * 3) + level * 5;
            num2 = rand.nextInt(level * 3);
            answer = num1 - num2;
            question = num1 + " - " + num2;
            setQuestionTextLayout(question, 2);

        } else if (level > 10 && level <= 30) {
            typeOfSubstraction = rand.nextInt(2);
            switch (typeOfSubstraction) {
                case 0:
                    num1 = 2 * rand.nextInt(level * 10) ;
                    num2 = 2 * rand.nextInt(level * 10) ;
                    answer = num1 - num2;
                    question = num1 + " - " + num2;
                    setQuestionTextLayout(question, 2);
                    break;

                case 1:
                    num1 = rand.nextInt(level * 3) + level * 10;
                    num2 = rand.nextInt(level * 3) + level;
                    num3 = rand.nextInt(level * 3) + level;
                    answer = num1 - num2 - num3;
                    question = num1 + " - " + num2 + " - " + num3;
                    setQuestionTextLayout(question, 3);
                    break;
            }

        } else {
            typeOfSubstraction = rand.nextInt(2);
            switch (typeOfSubstraction) {
                case 0:
                    num1 = 3 * rand.nextInt(level * 5) ;
                    num2 = 2 * rand.nextInt(level * 5) ;
                    num3 = 2 * rand.nextInt(level * 5) ;
                    answer = num1 - num2 - num3;
                    question = num1 + " - " + num2 + " - " + num3;
                    setQuestionTextLayout(question, 3);
                    break;

                case 1:
                    num1 = rand.nextInt(level * 3) + level * 10;
                    num2 = rand.nextInt(level * 3) ;
                    num3 = rand.nextInt(level * 3) + level;
                    num4 = rand.nextInt(level * 3) ;
                    answer = num1 - num2 - num3 - num4;
                    question = num1 + " - " + num2 + " - " + num3 + " - " + num4;
                    setQuestionTextLayout(question, 4);
                    break;
            }

        }

        for (int i = 0; i < numOfAnswer; i++) {
            if (answerLocation == i) {
                answerOptions.add(answer);
            } else {
                int option = 0;
                do {
                    if (level <= 10) {
                        option = rand.nextInt(level * 6) + level * 2;
                    } else if (level > 10 && level <= 30) {
                        switch (typeOfSubstraction) {
                            case 0:
                                option = rand.nextInt(level * 20) * ((-1) ^ rand.nextInt(2));
                                break;

                            case 1:
                                option = rand.nextInt(level * 3) + level * 8;
                                break;
                        }

                    } else {
                        switch (typeOfSubstraction) {
                            case 0:
                                option = rand.nextInt(level * 20) * ((-1) ^ rand.nextInt(2));
                                break;

                            case 1:
                                option = rand.nextInt(level * 10) + level * 2;
                                break;
                        }

                    }


                } while (option == answer);
                answerOptions.add(option);
            }
        }
    }

    public void generateMultiplication() {
        int num1, num2, num3;
        int answer = 0;
        int typeOfMultiplication = 0;

        if (level <= 10) {
            num1 = rand.nextInt(2 * level);
            num2 = rand.nextInt(level) + level;
            answer = num1 * num2;
            question = num1 + " x " + num2;
            setQuestionTextLayout(question, 2);

        } else if (level > 10 && level <= 30) {
            num1 = rand.nextInt(2 * level) + level;
            num2 = rand.nextInt(level) + level;
            answer = num1 * num2;
            question = num1 + " x " + num2;
            setQuestionTextLayout(question, 2);

        } else {
            typeOfMultiplication = rand.nextInt(2);
            switch (typeOfMultiplication) {
                case 0:
                    num1 = rand.nextInt(2 * level) + 2 * level;
                    num2 = rand.nextInt(2 * level) + 2 * level;
                    answer = num1 * num2;
                    question = num1 + " x " + num2;
                    setQuestionTextLayout(question, 2);
                    break;

                case 1:
                    num1 = rand.nextInt(level) + level;
                    num2 = rand.nextInt(8) + 2;
                    num3 = rand.nextInt(level) + level;
                    answer = num1 * num2 * num3;
                    question = num1 + " x " + num2 + " x " + num3;
                    setQuestionTextLayout(question, 3);
                    break;

            }
        }


        for (int i = 0; i < numOfAnswer; i++) {
            if (answerLocation == i) {
                answerOptions.add(answer);
            } else {
                int option = 0;
                do {
                    if (level <= 10) {
                        option = rand.nextInt(4 * level * level);

                    } else if (level > 10 && level <= 30) {
                        option = rand.nextInt(5 * level * level) + level * level;
                    } else {
                        switch (typeOfMultiplication) {
                            case 0:
                                option = rand.nextInt(12 * level * level) + 4 * level * level;
                                break;

                            case 1:
                                option = rand.nextInt(34 * level * level) + 2 * level * level;
                                break;
                        }
                    }


                } while (option == answer);
                answerOptions.add(option);
            }
        }
    }

    public void generateDivision() {
        int num1, num2;
        int answer = 0;

        if (level > 10 && level <= 30) {
            answer = rand.nextInt(2 * level) + level % 5 + 1;
            num2 = rand.nextInt(2 * level) + level % 5 + 1;
            num1 = answer * num2;
            question = num1 + " / " + num2;
            setQuestionTextLayout(question, 2);
        } else {
            answer = rand.nextInt(2 * level) + level;
            num2 = rand.nextInt(2 * level) + level;
            num1 = answer * num2;
            question = num1 + " / " + num2;
            setQuestionTextLayout(question, 2);
        }


        for (int i = 0; i < numOfAnswer; i++) {
            if (answerLocation == i) {
                answerOptions.add(answer);
            } else {
                int option = 0;
                do {
                    if (level > 10 && level <= 30) {
                        option = rand.nextInt(2 * level) + level % 5 + 1;
                    } else {
                        option = rand.nextInt(2 * level) + level;

                    }


                } while (option == answer);
                answerOptions.add(option);
            }
        }


    }

    public int getAnswerLocation() {
        return answerLocation;
    }

    public String getQuestionText() {
        return question;

    }

    public void setQuestionTextLayout(String question, int length) {
        if (length < 3) {
            questionTextLayout.setText(AssetLoader.font, question);
            longQuestion = 0;
        } else if (length == 3) {
            questionTextLayout.setText(AssetLoader.mediumGreenFont, question);
            longQuestion = 1;
        } else {
            questionTextLayout.setText(AssetLoader.smallShadowFont, question);
            longQuestion = 2;
        }
    }

    public int isLongQuestion() {
        return longQuestion;
    }

    public GlyphLayout getQuestionTextLayout() {
        return questionTextLayout;
    }

    public ArrayList<Integer> getAnswers() {
        return answerOptions;

    }

    public int getOptionsCount() {
        return answerOptions.size();

    }

    public int getNumOfAnswer() {
        return numOfAnswer;
    }




}
