import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.swing.text.StyledEditorKit;
import java.awt.TextField;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;

public class move extends Application {
    Boolean shield_value=false;
    Boolean wall_shield=false;
    Snake Head;
    int snake_size=5;
    ArrayList<Snake> list=new ArrayList<Snake>();  //Snake
    ArrayList<Block> block=new ArrayList<Block>();  // Block
    ArrayList<Walls> wall_lines=new ArrayList<Walls>();   // walls
    ArrayList<Food> food=new ArrayList<Food>();  // Number food
    ArrayList<Destroy> vanish=new ArrayList<Destroy>();  // Destroy food array
    ArrayList<Magnet> mag=new ArrayList<Magnet>();  //Magnet array
    ArrayList<Shield> sld=new ArrayList<Shield>(); // Shield
    ArrayList<Text> text_array=new ArrayList<Text>(); // Shield


    private static Pane scene=new Pane();
    private Stage stage;
    public int destroy_taken=0;
    public int score=0;
    public Scene gameSize() throws InterruptedException {
        scene.setPrefSize(400,600);
            scene.setStyle("-fx-background-color: black;");


        Random random=new Random();

        start_game();
        AnimationTimer t=new AnimationTimer() {
            private long lastUpdate = 0 ;
            //  @Override

            public void handle(long now) {

                if (now - lastUpdate >= 200_000_0000) {
                    makeblock();
                    // makefood();
                    lastUpdate = now ;
                    //    System.out.println(Head.x);
                }

                try {
                    getfood();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    getblock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    getdestroy();
                } catch (InterruptedException e) {

                }
                try {
                    getmagnet();
                } catch (InterruptedException e) {

                }
if(score>15 && score<30){

                    scene.setStyle("-fx-background-color:#0DFFED ;");

                }
                else if(score >30){
    scene.setStyle("-fx-background-color:violet;");

}


                score_show();

                //System.out.println(snake_size);
            }
        };
        t.start();


        // Bloom bloom = new Bloom();
        //bloom.setThreshold(0.9);
        //  scene.getChildren().add(texxt);
        Image img5 = new Image(getClass().getResourceAsStream("restart.jpg"),30,30,false,false);

        Button buton3 =new Button("RESTART",new ImageView(img5));
        buton3.setPrefSize(120, 35);
        buton3.setLayoutX(290);
        buton3.setLayoutY(565);
        Image img4 = new Image(getClass().getResourceAsStream("exit.jpg"),30,30,false,false);

        Button buton2 =new Button("EXIT",new ImageView(img4));
        buton2.setPrefSize(90, 35);
        buton2.setLayoutX(0);
        buton2.setLayoutY(565);
///////// button ka kam

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                stage.setScene(show_leaders());

            }
        };
        // when button is pressed
        //   button1.setOnAction(event);


        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                stage.setScene(logInScene());

            }
        };
        // when button is pressed
        buton2.setOnAction(event2);

        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //scene.getChildren().removeAll(text);

scene.getChildren().removeAll(block);
                scene.getChildren().removeAll(wall_lines);
                scene.getChildren().removeAll(food);
                scene.getChildren().removeAll(mag);
                scene.getChildren().removeAll(sld);
                scene.getChildren().removeAll(vanish);


                score=0;


            }
        };
        // when button is pressed
        buton3.setOnAction(event1);
        scene.getChildren().addAll(buton3,buton2);
        return new Scene(scene);
    }


    ////new
    protected Scene show_leaders() {
        Pane root = new Pane();
        Image img3 = new Image(getClass().getResourceAsStream("leader.jpg"),190,45,false,false);
        root.setMinSize(400, 600);
        Button b = new Button("", new ImageView(img3));
        Button b2 = new Button("EXIT");
        b2.setPrefSize(90, 35);
        b2.setLayoutX(310);
        b2.setLayoutY(0);
        b2.setStyle("-fx-background-color: Red");
        root.setStyle("-fx-background-color: #000000;");

        b2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(logInScene());
            }
        });
        b.setPrefSize(190,45);
        b.setLayoutX(100);
        b.setLayoutY(100);
        b.setStyle("-fx-background-color: Black");
        Text text = new Text(160,180," 1 > 100\n 2 > 90\n 3 > 80 \n 4 > 70\n 5 > 60\n 6 > 50\n 7 > 40\n 8 > 30\n 9 > 20\n 10 > 10");
        text.setFont(javafx.scene.text.Font.font(STYLESHEET_MODENA, 20));
        text.setFill(Color.BROWN);
        root.getChildren().addAll(b,b2,text);
        return new Scene(root);
    }
    public void score_show() {

        Text texxt=new Text();
        String k=Integer.toString(score);
        if(score>0) {
            scene.getChildren().removeAll(text_array);
        }
        texxt.setText(k);
        texxt.setX(10);
        texxt.setY(20);
        texxt.setFill(Color.WHITE);
        texxt.setFont(new Font("Arial",25));
        text_array.add(texxt);
        scene.getChildren().add(texxt);


    }
    protected Scene gameover() {
        Pane root = new Pane();
        Text texxt=new Text();
        String k=Integer.toString(score);
        texxt.setText(k);
        texxt.setX(190);
        texxt.setY(500);
        texxt.setFill(Color.PINK);
        texxt.setFont(new Font("Arial",55));
        //text_array.add(texxt);
        root.getChildren().add(texxt);
        Image im = new Image(getClass().getResourceAsStream("gameover.png"),190,190,false,false);
        //root.setMinHeight(600);
        root.setMinSize(400, 600);
        Button b = new Button("", new ImageView(im));
        b.setLayoutX(100);
        b.setLayoutY(200);


        Button b2 = new Button("EXIT");
        b2.setPrefSize(90, 35);
        b2.setLayoutX(310);
        b2.setLayoutY(0);
        b2.setStyle("-fx-background-color: Red");
        root.setStyle("-fx-background-color: #000000;");

        b2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(logInScene());
            }

        });

        root.getChildren().addAll(b,b2);
        return new Scene(root);
    }
    /////
    public Scene logInScene(){
        Pane root = new Pane();
        root.setMinSize(400, 600);
        Image img1 = new Image(getClass().getResourceAsStream("play.jpg"),40,40,false,false);
        Image img2 = new Image(getClass().getResourceAsStream("resume.jpg"),40,40,false,false);
        Button button1 =new Button("START ",new ImageView(img1));
        Button button2 =new Button("RESUME",new ImageView(img2));
        Button button3 =new Button("LEADERBOARD");
        button1.setPrefSize(130, 40);
        button2.setPrefSize(130, 40);
        button3.setPrefSize(130, 45);
        button1.setLayoutX(50);
        button1.setLayoutY(350);
        button2.setLayoutX(210);
        button2.setLayoutY(350);
        button3.setLayoutX(125);
        button3.setLayoutY(420);
        button1.setStyle("-fx-background-color: Pink");
        button2.setStyle("-fx-background-color: Pink");
        button3.setStyle("-fx-background-color: Pink");
        root.setStyle("-fx-background-color: #000000;");
        ////event handling
        button3.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(show_leaders());
            }
        });
        button1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try {
                    stage.setScene(gameSize());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                try {
                    stage.setScene(gameSize());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        /////////
        root.getChildren().addAll(button1,button2,button3);
        return new Scene(root);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getblock() throws InterruptedException {
        try {
            getshield();
        } catch (InterruptedException e) {

        }

        Iterator<Block> blk = block.iterator();

        while (blk.hasNext()){
            Block clk=blk.next();

            if(clk.getBoundsInParent().intersects(list.get(0).getBoundsInParent())){
                int value_block=clk.value;
                if(value_block>=snake_size && shield_value==false) {
                    System.out.println("margaya");
                    stage.setScene(gameover());
                    Thread.sleep(500);
                }
                else if(shield_value==true){
                    scene.getChildren().remove(clk);
                    score=score+value_block;
                    System.out.println(score+"score");
                    Thread.sleep(500);
                    shield_value=false;

                }
                else if(value_block<=5 && shield_value==false) {
                    //          snake_size=snake_size-value_block;
                    scene.getChildren().remove(clk);
                    snake_size=snake_size-value_block;
                    score=score+value_block;
                    System.out.println(score+"score");
                    remove_snake(value_block);
                    Thread.sleep(500);
                    //   System.out.println(snake_size);


                }
                else{
                    //    System.out.println(value_block);
                    snake_size=snake_size-value_block;
                    score=score+value_block;
                    scene.getChildren().remove(clk);
                    System.out.println(score+"score");
                    remove_snake(value_block);
                    Thread.sleep(1000);

                    System.out.println(snake_size);

                }

            }

        }

    }
    public void remove_snake(int sn){
        for(int i=0;i<sn;i++){
            scene.getChildren().removeAll(list.get(list.size()-1));
            list.remove(list.size()-1);
            System.out.println("REMOVE");
        }
    }

    public void getfood() throws InterruptedException {

        Iterator<Food> f = food.iterator();
        while (f.hasNext()){
            Food mfood=f.next();
            if(list.get(0).getBoundsInParent().intersects(mfood.getBoundsInParent())){
                int mfood_value=mfood.value;
                scene.getChildren().remove(mfood);
                snake_size=snake_size+(mfood_value);
                //  System.out.println(snake_size);
                //     System.out.println(mfood_value);
                create_snake(mfood_value);
                Thread.sleep(300);
            }
        }
    }


    public void create_snake(int inc_snake){
        double sk=list.get(list.size()-1).getTranslateX();
        double sy=list.get(list.size()-1).getTranslateY();
        int number=(int)sk;
        int ny=(int)sy;

        for(int i=0;i<inc_snake;i++){
            Snake sp=new Snake(number,ny+30*(i+1));
            scene.getChildren().addAll(sp);
            list.add(sp);
        }

    }
    public void getmagnet() throws InterruptedException {

        Iterator<Magnet> f = mag.iterator();
        while (f.hasNext()){
            Magnet m_mag=f.next();
            if(list.get(0).getBoundsInParent().intersects(m_mag.getBoundsInParent())){
                scene.getChildren().remove(m_mag);
                scene.getChildren().removeAll(food);
                //  snake_size=snake_size+10;
                score=score+10;
                System.out.println(snake_size);
                //    System.out.println();
                Thread.sleep(300);
            }
        }
    }

    public void getdestroy() throws InterruptedException {

        Iterator<Destroy> dst = vanish.iterator();
        while (dst.hasNext()){
            Destroy d=dst.next();
            if(list.get(0).getBoundsInParent().intersects(d.getBoundsInParent())){
                scene.getChildren().removeAll(block);
                scene.getChildren().remove(d);
                block.removeAll(block);


            }
        }
    }

    public void getshield() throws InterruptedException {

        Iterator<Shield> dst = sld.iterator();
        while (dst.hasNext()){
            Shield d=dst.next();
            if(list.get(0).getBoundsInParent().intersects(d.getBoundsInParent())){
                shield_value=true;
                scene.getChildren().removeAll(d);
               /* for(int i=0;i<5;i++){
                    getblock_shield();
                }*/
                      Thread.sleep(300);


            }
        }
    }





    public void getwalls(){
        Iterator<Walls> wlk = wall_lines.iterator();
        while (wlk.hasNext()){
            Walls clk=wlk.next();
            if(list.get(0).getBoundsInParent().intersects(clk.getBoundsInParent())){
                wall_shield=true;
            }
        }
    }



    public void start_game(){
        for(int i=0;i<snake_size;i++){
            Snake one_snake=new Snake(200,480+i*30);
            if(i==0){
                Head=one_snake;
            }
            list.add(one_snake);
            scene.getChildren().addAll(one_snake);
        }

    }

    public void makeblock () {
        Random ran = new Random();
        int len = 3 + ran.nextInt(3);
        int miss_block = ran.nextInt(6);
        for (int i = 0; i < len; i++) {
            if (i != miss_block) {
                Block myblock = new Block(2 + 0 + 80 * i, -280);

                scene.getChildren().addAll(myblock);

                block.add(myblock);

                TranslateTransition transition=new TranslateTransition();
                transition.setDuration(Duration.seconds(5));
                transition.setToY(600);
                transition.setNode(myblock);
                transition.play();

            }
        }
        makefood();


    }
    public void makefood(){
        Food f1;
        Magnet m1;
        Destroy d1;
        Shield  s1;

        Random ran=new Random();
        int position=ran.nextInt(5);

        if(position==0) {
            f1 = new Food(70, -500);
            food.add(f1);

            int pos=ran.nextInt(2);
            if(pos==0) {
                s1 = new Shield(385, -500);
                sld.add(s1);
            }
            else
            {
                s1 = new Shield(230, -500);
                sld.add(s1);
            }
            scene.getChildren().addAll(s1);
            TranslateTransition transition_s=new TranslateTransition();
            transition_s.setDuration(Duration.seconds(7));
            transition_s.setToY(700);
            transition_s.setNode(s1);
            transition_s.play();


        }
        else if(position==1){
            f1 = new Food(385, -500);
            food.add(f1);




        }
        else if(position==2){
            int pos=ran.nextInt(2);

            f1 = new Food(230, -500);
            food.add(f1);
            if(pos==0) {
                d1 = new Destroy(150, -500);
                vanish.add(d1);
            }
            else {
                d1 = new Destroy(230, -500);
                vanish.add(d1);

            }
            scene.getChildren().addAll(d1);
            TranslateTransition transition_d=new TranslateTransition();
            transition_d.setDuration(Duration.seconds(7));
            transition_d.setToY(700);
            transition_d.setNode(d1);
            transition_d.play();



        }
        else if(position==3){
            f1 = new Food(310, -500);
            food.add(f1);
            m1=new Magnet(150,-500);
            mag.add(m1);
            scene.getChildren().addAll(m1);
            TranslateTransition transition_m=new TranslateTransition();
            transition_m.setDuration(Duration.seconds(7));
            transition_m.setToY(700);
            transition_m.setNode(m1);
            transition_m.play();



        }
        else {
            f1 = new Food(150, -500);
            food.add(f1);


        }


        scene.getChildren().addAll(f1);

        TranslateTransition transition=new TranslateTransition();
        transition.setDuration(Duration.seconds(7));
        transition.setToY(700);
        transition.setNode(f1);
        transition.play();



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        //Scene my_scene=new Scene(gameSize());

        primaryStage.setTitle("Snake Game");
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    getwalls();
                    if(wall_shield==true){
                        System.out.println("No Movment");
                        wall_shield=false;
                    }
                    else {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).Left();
                        }
                    }
                    break;
                case D:
                    getwalls();

                    if(wall_shield==true){
                        System.out.println("No Movment");
                        wall_shield=false;
                    }
                    else{
                        for(int i=0;i<list.size();i++) {
                            list.get(i).Right();
                        }
                    }

                    break;

            }
        });
        primaryStage.setScene(gameSize());
        primaryStage.show();


    }
    //For snake
    public  class Snake extends Circle {
        int x;
        int y;
        boolean dead=false;
        Snake(int x, int y){

            super(15, Color.DEEPPINK);
            this.x=x;
            this.y=y;
            setTranslateX(x);
            setTranslateY(y);

        }

        void Left() {
            setTranslateX(getTranslateX() - 10);
            //   Intersection();

        }

        void Right() {
            setTranslateX(getTranslateX() + 10);
            //      Intersection();

        }

    }

    //For Food
    public  class Food extends Circle {
        Random food_randm=new Random();
        int rand_food=food_randm.nextInt(10)+1;
        int value;
        int x;
        int y;

        Food(int x, int y){
            setRadius(15);
            setFill(Color.GREEN);
            this.value=rand_food;
            this.x=x;
            this.y=y;
            make_text();
            setTranslateX(x);
            setTranslateY(y);
        }
        void make_text(){
            Text text=new Text();
            String vlaue_block=Integer.toString(value);
            text.setText(vlaue_block);
            text.setX(x-10);
            text.setY(y-80);
            text.setFill(Color.WHITE);
            text.setFont(new Font("Arial",25));
            Bloom bloom = new Bloom();
            bloom.setThreshold(0.9);
            scene.getChildren().add(text);

            //For Text Motion
            TranslateTransition transition=new TranslateTransition();
            transition.setDuration(Duration.seconds(6.57));
            transition.setToY(1205);
            transition.setNode(text);
            transition.play();
            //END text Motion

            text.setEffect(bloom);

        }
    }

    //For Magnet
    public  class Magnet extends Circle {
        int x;
        int y;

        Magnet(int x, int y){
            setRadius(15);
            setFill(Color.RED);
            this.x=x;
            this.y=y;
            setTranslateX(x);
            setTranslateY(y);
        }

    }
    //For Shield
    public class Shield extends Circle {
        int x;
        int y;

        Shield(int x, int y){
            setFill(Color.WHITE);
            setRadius(15);
            this.x=x;
            this.y=y;
            setTranslateX(x);
            setTranslateY(y);
        }

    }
    //Destroy everythings
    public class Destroy extends Circle {
        int x;
        int y;
        Destroy(int x, int y){
            setRadius(15);
            this.x=x;
            this.y=y;
            setFill(Color.DEEPSKYBLUE);
            setTranslateX(x);
            setTranslateY(y);
        }

    }

    public  class Block extends Rectangle {

        boolean dead=false;
        int x;
        int y;
        int value;
        Block(int x, int y){
            Random ran=new Random();
            this.value=1+ ran.nextInt(7);
            this.x=x;
            this.y=y;
            setHeight(80);
            setWidth(75);
            setArcHeight(20);
            setArcWidth(20);
            setTranslateX(x);
            setTranslateY(y);
            setFill(Color.YELLOW);
            makeblock();


        }
        void makeblock(){
            Random ran=new Random(); //For random walls
            int ran_wall=ran.nextInt(2);
            Text text=new Text();
            String vlaue_block=Integer.toString(value);
            text.setText(vlaue_block);
            text.setX(x+30);
            text.setY(y-5);
            text.setFill(Color.WHITE);
            text.setFont(new Font("Arial",25));
            Bloom bloom = new Bloom();
            bloom.setThreshold(0.9);
            scene.getChildren().add(text);

            //For Text Motion
            TranslateTransition transition=new TranslateTransition();
            transition.setDuration(Duration.seconds(5.6));
            transition.setToY(1000);
            transition.setNode(text);
            transition.play();
            //END text Motion

            text.setEffect(bloom);
            if(ran_wall==0) {
                Walls wall = new Walls(x + 30, -200);
                wall_lines.add(wall);
                scene.getChildren().add(wall);
                TranslateTransition transition_wall=new TranslateTransition();
                transition_wall.setDuration(Duration.seconds(5));
                transition_wall.setToY(700);
                transition_wall.setNode(wall);
                transition_wall.play();


            }




        }
    }
    public  class Walls extends Rectangle {

        boolean dead=false;
        int x;
        int y;
        Walls(int x, int y){
            //   super(75,80,Color.YELLOW);
            this.x=x;
            this.y=y;
            setHeight(150);
            setWidth(15);
            setTranslateX(x);
            setTranslateY(y);
            setFill(Color.WHITE);


        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}



