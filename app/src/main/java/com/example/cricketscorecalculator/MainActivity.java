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

    Button zero,one,two,three,four,six,wide,noball,wicket,table,undo,redo,othrow,runout,finish,finish_inning;
    TextView run,outtxt,overtxt,runratetxt,targettxt,targetscoretxt,teamtxt;
    int score=0,target=Integer.MAX_VALUE;
    int out=0;
    double ov = 0.0;
    int ball=0;
    double perover=0;
    String action="";
    String over="",over1;

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
        finish_inning = findViewById(R.id.finish_inning);

        run = findViewById(R.id.run);
        outtxt = findViewById(R.id.wicket);
        overtxt = findViewById(R.id.overs);
        runratetxt = findViewById(R.id.runRate);
        targettxt = findViewById(R.id.target);
        targetscoretxt = findViewById(R.id.targetScore);
        teamtxt = findViewById(R.id.textView);

        takeOversInput(R.layout.overs_input);


        Cricket cd = new Cricket(0,0,0,"0.0","dot",0.0);
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
                check();


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
                check();

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
                check();

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
                check();

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
                check();

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
                check();

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
                check();

            }
        });

        noball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = score + 1;
                action="No ball";
                run.setText(score+"");
                showNoBallDialog(R.layout.no_ball,"No ball ");
                check();

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
                check();
            }
        });

        runout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showOverThrowDialog(R.layout.overthrow,"Run Out ");


            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(R.layout.alert);


            }
        });

        finish_inning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(score>target)
                {
                    showWinner(R.layout.winner,"Team 2");
                    Toast.makeText(MainActivity.this,"Team 2 won",Toast.LENGTH_LONG);
                    System.out.println("Team 2 won");
                }
                else if(target!=Integer.MAX_VALUE && score<target)
                {
                    showWinner(R.layout.winner,"Team 1");
                    Toast.makeText(MainActivity.this,"Team 2 won",Toast.LENGTH_LONG);
                }

                targettxt.setVisibility(TextView.VISIBLE);
                targetscoretxt.setVisibility(TextView.VISIBLE);
                teamtxt.setText("Team 2");
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

            }
        });

        othrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverThrowDialog(R.layout.overthrow,"Overthrow");
                check();
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


                    score= c1.getScore();
                    out=c1.getOut();
                    over = c1.getOver();
                    ball=c1.getBall();
                    perover=c1.getPerover();
                    action=c1.getAction();



                    run.setText(score + "");
                    outtxt.setText(out + "");
                    overtxt.setText(over+"  ("+over1+")");
                    runratetxt.setText("RR : "+perover + "");
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

                    score= c1.getScore();
                    out=c1.getOut();
                    over = c1.getOver();
                    ball=c1.getBall();
                    perover=c1.getPerover();
                    action=c1.getAction();



                    run.setText(score + "");
                    outtxt.setText(out + "");
                    overtxt.setText(over+"  ("+over1+")");
                    runratetxt.setText("RR : "+perover + "");
                }

            }
        });



    }

    private void check() {

        if(score>target)
        {
            showWinner(R.layout.winner,"Team 2");
            Toast.makeText(MainActivity.this,"Team 2 won",Toast.LENGTH_LONG);
            System.out.println("Team 2 won");
        }


        double num1 = Double.parseDouble(over1);
        double num2 = Double.parseDouble(over);

        System.out.println(num1+" "+num2);

        if(num1==num2 && target!=Integer.MAX_VALUE && score<target)
        {
            //here i have called
            showWinner(R.layout.winner,"Team 1");
        }

        if(num1==num2)
        {
            targettxt.setVisibility(TextView.VISIBLE);
            targetscoretxt.setVisibility(TextView.VISIBLE);
            teamtxt.setText("Team 2");
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
        }



    }


    private void save() {
        Cricket c = new Cricket(score,ball,out,over,action,perover);

        undost.push(c);


    }

    private void scorecount(int i) {
        score = score + i;
        run.setText(score+"");

    }

    private void overcount()
    {
        ball++;
        int no1 = ball/6;
        int no2 = ball%6;
        over = no1+""+"."+no2+"";
        overtxt.setText(over+"  ("+over1+")");

    }

    private void runratecount()
    {
        try {
            perover = (score*6)/ball;
            //double perover = perball*6;
            //System.out.println(perover);
            //System.out.println(score+" "+ball);
            runratetxt.setText("RR : "+perover+"");
        }
        catch (Exception e)
        {

        }

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
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(1);
                runratecount();
                action=str+" single";
                save();
                dialog.dismiss();
                check();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(2);
                runratecount();
                action=str+" double";
                save();
                dialog.dismiss();
                check();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(3);
                runratecount();
                action=str+" three";
                save();
                dialog.dismiss();
                check();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(4);
                runratecount();
                action=str+" four";
                save();
                dialog.dismiss();
                check();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(5);
                runratecount();
                action=str+" five";
                save();
                dialog.dismiss();
                check();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write your code here
                if(str.equals("Run Out "))
                {
                    out = out + 1;
                    overcount();
                    outtxt.setText(out+"");

                }
                scorecount(6);
                runratecount();
                action=str+" six";
                save();
                dialog.dismiss();
                check();
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
                int out = Integer.parseInt(outtxt.getText().toString());
                out = out+1;
                action="Wicket";
                save();
                outtxt.setText(out+"");
                check();
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
                    over1 = overs_input.getText().toString();
                    overtxt.setText(ov+"  ("+over1+")");
                    dialog.dismiss();
                }
            }
        });
    }

    private void showAlert(int myLayout){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(myLayout);
        dialog.setCancelable(false);

        Button yes = dialog.findViewById(R.id.ok);
        Button no = dialog.findViewById(R.id.no);

        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
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

    private void showWinner(int mylayout, String str){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(mylayout);
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
                finish();
                startActivity(getIntent());
                dialog.dismiss();
            }
        });
    }

}