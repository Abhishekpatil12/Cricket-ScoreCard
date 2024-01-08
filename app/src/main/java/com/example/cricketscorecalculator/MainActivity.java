package com.example.cricketscorecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button zero,one,two,three,four,six,wide,noball,wicket,table,undo,redo,othrow,runout,finish;
    TextView run,outtxt,overtxt,runratetxt,targettxt,targetscoretxt;
    int score=0,target=Integer.MAX_VALUE;
    int out=0;
    double ov = 0.0;
    int ball=0;
    double perover=0;
    String action="";
    String over="";

    Dialog dialog;
    Stack<Cricket> undost = new Stack<Cricket>();
    Stack<Cricket> redost = new Stack<Cricket>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zero = findViewById(R.id.dot);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        six = findViewById(R.id.six);
        wide = findViewById(R.id.wide);
        noball = findViewById(R.id.no);
        wicket = findViewById(R.id.out);
        othrow = findViewById(R.id.othrow);
        runout = findViewById(R.id.runout);
        table = findViewById(R.id.button2);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        finish = findViewById(R.id.button);

        run = findViewById(R.id.run);
        outtxt = findViewById(R.id.wicket);
        overtxt = findViewById(R.id.overs);
        runratetxt = findViewById(R.id.runRate);
        targettxt = findViewById(R.id.target);
        targetscoretxt = findViewById(R.id.targetScore);

        takeOversInput(R.layout.overs_input);

        Cricket cd = new Cricket(0,0,"0.0","dot",0.0);
        undost.push(cd);


        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("score",undost);
                startActivity(intent);
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overcount();
                runratecount();
                action="Dot ball";
                save();


            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overcount();
                scorecount(1);
                runratecount();
                action="Single";
                save();

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overcount();
                scorecount(2);
                runratecount();
                action="Double";
                save();

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overcount();
                scorecount(3);
                runratecount();
                action="Three's";
                save();

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overcount();
                scorecount(4);
                runratecount();
                action="four";
                save();

            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overcount();
                scorecount(6);
                runratecount();
                action="six";
                save();

            }
        });

        wide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scorecount(1);
                runratecount();
                action="Wide";
                save();
                showNoBallDialog(R.layout.no_ball,"Wide ");

            }
        });

        noball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = score + 1;
                action="No ball";
                run.setText(score+"");
                showNoBallDialog(R.layout.no_ball,"No ball ");

            }
        });

        wicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out = out + 1;
                overcount();
                runratecount();
                action="Wicket";
                save();
                outtxt.setText(out+"");
            }
        });

        runout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                out = out + 1;
                overcount();
                runratecount();
                action="Wicket";
                save();
                outtxt.setText(out+"");
                showOverThrowDialog(R.layout.overthrow,"Run Out ");


            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(R.layout.alert);
//                targettxt.setVisibility(TextView.VISIBLE);
//                targetscoretxt.setVisibility(TextView.VISIBLE);
//                target = score;
//                target++;
//                targetscoretxt.setText(target+"");
//                score=0;
//                out=0;
//                ov = 0.0;
//                ball=0;
//                perover=0;
//                action="";
//                over="";
//                run.setText("0");
//                outtxt.setText("0");
//                overtxt.setText("0.0");
//                runratetxt.setText("RR : 0");

            }
        });

        othrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverThrowDialog(R.layout.overthrow,"Overthrow");
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(undost.isEmpty());
                if(undost.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Nothing to undo",Toast.LENGTH_LONG);
                }
                else {


                    Cricket c = undost.peek();

                    undost.pop();
                    redost.push(c);

                    Cricket c1 = undost.peek();

                    System.out.println(c1.getScore());


                    run.setText(c1.getScore() + "");
                    outtxt.setText(c1.getOut() + "");
                    overtxt.setText(c1.getOver() + "");
                    runratetxt.setText("RR : "+c1.getPerover() + "");
                }

            }
        });

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(redost.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Nothing to redo",Toast.LENGTH_LONG);
                }
                else
                {


                    Cricket c = redost.peek();

                    redost.pop();
                    undost.push(c);

                    Cricket c1 = undost.peek();
                    System.out.println(c1.getScore());

                    run.setText(c1.getScore() + "");
                    outtxt.setText(c1.getOut() + "");
                    overtxt.setText(c1.getOver() + "");
                    runratetxt.setText(c1.getPerover() + "");

                }

            }
        });



    }

    private void check() {

        if(score>target)
        {
            Toast.makeText(MainActivity.this,"Team 2 won",Toast.LENGTH_LONG);
            System.out.println("Team 2 won");
        }

    }


    private void save() {
        Cricket c = new Cricket(score,out,over,action,perover);

        undost.push(c);


    }

    private void scorecount(int i) {
        score = score + i;
        run.setText(score+"");
        check();
    }

    private void overcount()
    {
        ball++;
        int no1 = ball/6;
        int no2 = ball%6;
        over = no1+""+"."+no2+"";
        overtxt.setText(over);
    }

    private void runratecount()
    {
        perover = (score*6)/ball;
        //double perover = perball*6;
        //System.out.println(perover);
        //System.out.println(score+" "+ball);
        runratetxt.setText("RR : "+perover+"");
    }

    private void showOverThrowDialog(int myLayout,String str) {

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(myLayout);
        dialog.setCancelable(false);

        Button one = dialog.findViewById(R.id.one_0);
        Button two = dialog.findViewById(R.id.two_0);
        Button three = dialog.findViewById(R.id.three_0);
        Button four = dialog.findViewById(R.id.four_0);
        Button five = dialog.findViewById(R.id.five_0);
        Button six = dialog.findViewById(R.id.six_0);
        Button cancel = dialog.findViewById(R.id.cancel_0);

        dialog.show();

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(1);
                runratecount();
                action=str+" single";
                save();
                dialog.dismiss();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(2);
                runratecount();
                action=str+" double";
                save();
                dialog.dismiss();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(3);
                runratecount();
                action=str+" three";
                save();
                dialog.dismiss();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(4);
                runratecount();
                action=str+" four";
                save();
                dialog.dismiss();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(5);
                runratecount();
                action=str+" five";
                save();
                dialog.dismiss();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(6);
                runratecount();
                action=str+" six";
                save();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                dialog.dismiss();
            }
        });
    }


    private void showNoBallDialog(int myLayout,String str) {

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(myLayout);
        dialog.setCancelable(false);

        Button dot = dialog.findViewById(R.id.dot_1);
        Button one = dialog.findViewById(R.id.one_1);
        Button two = dialog.findViewById(R.id.two_1);
        Button three = dialog.findViewById(R.id.three_1);
        Button four = dialog.findViewById(R.id.four_1);
        Button five = dialog.findViewById(R.id.five_1);
        Button six = dialog.findViewById(R.id.six_1);
        Button out = dialog.findViewById(R.id.out_1);
        Button cancel = dialog.findViewById(R.id.cancel_1);

        dialog.show();

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(0);
                runratecount();
                action=str+"zero";
                save();
                dialog.dismiss();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(1);
                runratecount();
                action=str+"single";
                save();
                dialog.dismiss();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(2);
                runratecount();
                action=str+"two";
                save();
                dialog.dismiss();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(3);
                runratecount();
                action=str+"three";
                save();
                dialog.dismiss();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(4);
                runratecount();
                action=str+"four";
                save();
                dialog.dismiss();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(5);
                runratecount();
                action=str+"five";
                save();
                dialog.dismiss();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                scorecount(6);
                runratecount();
                action=str+"six";
                save();
                dialog.dismiss();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                dialog.dismiss();
            }
        });
    }

    private void takeOversInput(int layout){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(layout);
        dialog.setCancelable(false);

        Button start = dialog.findViewById(R.id.start);
        EditText overs_input = dialog.findViewById(R.id.numberInput);

        dialog.show();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(overs_input.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter the Overs", Toast.LENGTH_SHORT).show();
                }else{
                    // write your code here
                    over = overs_input.getText().toString();
                    dialog.dismiss();
                }
            }
        });
    }

    private void showAlert(int myLayout){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert);
        dialog.setCancelable(false);

        Button yes = dialog.findViewById(R.id.ok);
        Button no = dialog.findViewById(R.id.no);

        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targettxt.setVisibility(TextView.VISIBLE);
                targetscoretxt.setVisibility(TextView.VISIBLE);
                target = score;
                target++;
                targetscoretxt.setText(target+"");
                score=0;
                out=0;
                ov = 0.0;
                ball=0;
                perover=0;
                action="";
                over="";
                run.setText("0");
                outtxt.setText("0");
                overtxt.setText("0.0");
                runratetxt.setText("RR : 0");
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showWinner(int layout, String str){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert);
        dialog.setCancelable(false);

        Button new_game = dialog.findViewById(R.id.new_game);
        TextView winner_team = dialog.findViewById(R.id.team_name);

        winner_team.setText(str);

        dialog.show();

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
//                hello
                dialog.dismiss();
            }
        });
    }

}