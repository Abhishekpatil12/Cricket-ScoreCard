package com.example.cricketscorecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button zero,one,two,three,four,six,wide,noball,wicket,table,undo,redo;
    TextView run,outtxt,overtxt,runratetxt,ball1,ball2,ball3,ball4,ball5,ball6;
    int score=0;
    int out=0;
    double ov = 0.0;
    int ball=0;
    double perover=0;
    String action="";
    String over="";

    Stack<Cricket> undost = new Stack<Cricket>();
    Stack<Cricket> redost = new Stack<Cricket>();


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
        table = findViewById(R.id.button2);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);

        run = findViewById(R.id.run);
        outtxt = findViewById(R.id.wicket);
        overtxt = findViewById(R.id.overs);
        runratetxt = findViewById(R.id.runRate);

        ball1 = findViewById(R.id.ball1);
        ball2 = findViewById(R.id.ball2);
        ball3 = findViewById(R.id.ball3);
        ball4 = findViewById(R.id.ball4);
        ball5 = findViewById(R.id.ball5);
        ball6 = findViewById(R.id.ball6);

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
                showball(0);
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
                showball(1);
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
                showball(2);
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
                showball(3);
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
                showball(4);
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
                showball(6);
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
            }
        });

        noball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = score + 1;
                action="No ball";
                run.setText(score+"");
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
                    runratetxt.setText(c1.getPerover() + "");
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

    private void save() {
        Cricket c = new Cricket(score,out,over,action,perover);

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

    private void showball(int i)
    {
        int no = ball%6;

        if(no==1)
        {
            ball1.setText(i+"");
            ball2.setText("*");
            ball3.setText("*");
            ball4.setText("*");
            ball5.setText("*");
            ball6.setText("*");
        }
        else if(no==2)
        {
            ball2.setText(i+"");
        }
        else if(no==3)
        {
            ball3.setText(i+"");
        }
        else if(no==4)
        {
            ball4.setText(i+"");
        }
        else if(no==5)
        {
            ball5.setText(i+"");
        }
        else if(no==0)
        {
            ball6.setText(i+"");
        }
    }



}