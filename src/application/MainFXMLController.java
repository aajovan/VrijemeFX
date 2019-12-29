package application;

import java.awt.FlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerTransition;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainFXMLController {
    private double xOffset;
    private double yOffset;
   
    @FXML
    private BorderPane anchorpane;
    
    @FXML
    private ImageView topImg;

    @FXML
    private Label label1;
    
    @FXML
    private BorderPane split;
    

    @FXML
    private Label label2;
    
    @FXML
    private GridPane mypane;
    
    @FXML
    private JFXDrawer drawerTop;

    @FXML
    private JFXHamburger hamburger;
    
    
    
    void mouseClicked(MouseEvent event) {
    	Stage stage = (Stage) anchorpane.getScene().getWindow();
    	 xOffset = stage.getX() - event.getScreenX();
         yOffset = stage.getY() - event.getScreenY();
    }

    void mouseDragged(MouseEvent event) {
    	Stage stage = (Stage) anchorpane.getScene().getWindow();
    	 stage.setX(event.getScreenX() + xOffset);
         stage.setY(event.getScreenY() + yOffset);
    }
    private weather checkedWeather;
    private boolean nightMode=false;
    private void initialize() throws IOException {
    	checkedWeather=test("Vara�din");
    	if(checkedWeather!=null) {
    		setWeatherData();
        	setBackground();
    	}
    	
    	HBox box2 =new HBox();
    	drawerTop.setStyle("-fx-background-color: transparent;");
    	box2.setStyle("-fx-background-color: #3d3d3d;");
      	box2.setPadding(new Insets(0, 10, 0, 10));
      	 box2.setAlignment(Pos.CENTER);
         
         Button changeLocation=menuButt("night.png");
         changeLocation.setOnAction(new EventHandler<ActionEvent>() {
        	    public void handle(ActionEvent e) {
        	    	if(!nightMode) {
        	    		nightMode=true;
        	    		setBackground();
        	    		changeLocation.setStyle("-fx-background-color: #1a1a1a;");
        	    	}
        	    	else {
        	    		nightMode=false;
        	    		setBackground();
        	    		changeLocation.setStyle("-fx-background-color: #3d3d3d;");
        	    	}
        	    	
        	    }
        	});
         changeLocation.setOnMouseExited(new EventHandler<MouseEvent>() {
     		 
             public void handle(MouseEvent event) {
            	 if(!nightMode) {
            		 changeLocation.setStyle("-fx-background-color: #3d3d3d;");
    	        	}
    	        	else {
    	        		 changeLocation.setStyle("-fx-background-color: #1a1a1a;");
    	        	}
             }
         });
         box2.getChildren().addAll(changeLocation,menuButt("icon.png"),menuButt("icon.png"),menuButt("icon.png"),menuButt("icon.png"),toggleButt(box2));
         drawerTop.setSidePane(box2);
         HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
         transition.setRate(-1);
         hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
             transition.setRate(transition.getRate()*-1);
             transition.play();
             
             if(drawerTop.isShown())
             {
                 drawerTop.close();
                 
             }else
                 drawerTop.open();
             
             
         });
    }
 
    private Button toggleButt(HBox box2) {
    	JFXTextField txt=new JFXTextField();
        txt.resize(140, 65);
        txt.setMaxHeight(42);
        txt.setStyle("-fx-text-fill: #ffffff;-jfx-focus-color: #ffffff;-fx-highlight-fill:#1a1a1a;-fx-font-size: 20px;");
        txt.setAlignment(Pos.CENTER);
        txt.setPadding(new Insets(0, 0, 0, 10));
        txt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                    String tfData = txt.getText();
                    try {
						checkedWeather = test(tfData);
						if(checkedWeather!=null) {
							mypane.getChildren().clear();
	        	    		setWeatherData();
	        	        	setBackground();
	        	    	}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
            }
        });
    	Button btn=menuButt("location.png");
	 btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
		 
	        public void handle(MouseEvent event) {
	        	if(box2.getChildren().contains(txt)) {
	        		
	        		box2.getChildren().remove(txt);
	        		drawerTop.setMaxWidth(330);
	        	}
	        	else {
	        		box2.getChildren().add(txt);
	        		btn.setStyle("-fx-background-color: #1a1a1a;");
	        		drawerTop.setMaxWidth(500);
	        	}
	        }
	    });
	 btn.setOnMouseExited(new EventHandler<MouseEvent>() {
 		 
         public void handle(MouseEvent event) {
        	 if(box2.getChildren().contains(txt)) {
        		 btn.setStyle("-fx-background-color: #1a1a1a;");
	        	}
	        	else {
	        		 btn.setStyle("-fx-background-color: #3d3d3d;");
	        	}
         }
     });
	 return btn;


    }
    private Button menuButt(String icon) {
    	 Image image = new Image(icon);
      			 ImageView view=new ImageView(image); 
      			view.setFitHeight(40);
      			view.setFitWidth(40);
      			 
      	 Button btn = new Button(); 
      	 btn.setGraphic(view);
       
        btn.setStyle("-fx-background-color: #3d3d3d;");
        
       
      	//btn.setPrefSize(45, 50);
      	btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
      		 
            public void handle(MouseEvent event) {
            	 btn.setStyle("-fx-background-color: #1a1a1a;");
            	
            }
        });
      	btn.setOnMouseExited(new EventHandler<MouseEvent>() {
     		 
            public void handle(MouseEvent event) {
            	 btn.setStyle("-fx-background-color: #3d3d3d;");
            }
        });
      	 
      	 return btn; 
   } 
    private void setWeatherData() throws IOException {
    	
    	
    	ArrayList<String> days = new ArrayList<String>();
    	ArrayList<String> getDays = new ArrayList<String>();
    	ArrayList<String> getTemps = new ArrayList<String>();
    	ArrayList<String> getImg= new ArrayList<String>();
    	getImg=checkedWeather.getImgNames();
    	getDays=checkedWeather.getDays();
    	getTemps=checkedWeather.getDegrees();
    	days=checkedWeather.getData();
    	ArrayList<String> fixedDegrees = new ArrayList<String>();
    	Image top = new Image(getImg.get(0)+".png");
    	topImg.setImage(top);

    	for(int i =0;i<getTemps.size()-1;i+=2) {
    		String degree=getTemps.get(i)+"�C"+"\n"+getTemps.get(i+1)+"�C";
    		fixedDegrees.add(degree);
    	}
       label1.setText(days.get(0));
       label2.setText(days.get(1)+"�C"+"\n"+days.get(2)+"�C");
      
       for(int i=0;i<6;i++){
    	   Text text=new Text(getDays.get(i+1));
    	   text.setFont(Font.font("Microsoft YaHei UI Light", 16));
            ImageView img=new ImageView(getImg.get(i+1)+".png");
            img.preserveRatioProperty();
            img.setFitHeight(120);
           img.setFitWidth(130);

    	    Text temp=new Text(fixedDegrees.get(i+1));
    	     text.setFill(Color.web("#ffffff"));
    	     temp.setFill(Color.web("#ffffff"));
    	     temp.setFont(Font.font("Microsoft YaHei UI Light", 17));

    	     mypane.add(text, i, 0);
    	     mypane.add(temp, i, 2);
    	     mypane.add(img, i, 1);    	   
    	  
    	     GridPane.setHalignment(text, HPos.CENTER);
    	     GridPane.setHalignment(img, HPos.CENTER);
    	     GridPane.setValignment(text, VPos.BOTTOM);
    	     GridPane.setHalignment(temp, HPos.CENTER);
    	     GridPane.setValignment(temp, VPos.TOP);
       }
    }
    private void setBackground() {
    	ArrayList<String> getImg= new ArrayList<String>();
    	getImg=checkedWeather.getImgNames();
    	 Image imageup = new Image("form"
           		+ ".png");
        Image gridImg = new Image("rainsun"
          		+ ".jpg");
        Image glass = new Image("new2"
         		+ ".png");
        String todayImg = getImg.get(0);
        String Day1=getImg.get(0).substring(0, getImg.get(0).length()-1);
        int dayNum=Integer.parseInt(Day1);
        
        if((dayNum>0&&dayNum<5)||(dayNum>7&&dayNum<10) ) {
        	gridImg = new Image("sunny.jpg");
        }
        else if(dayNum>4&&dayNum<8 ) {
        	if(nightMode) {
        		gridImg = new Image("dcloudy.jpg");
        	}
        	else {
        		gridImg = new Image("cloudy.jpg");
        	}
        	
        }
        else if(dayNum==10&&dayNum==11) {
        	gridImg = new Image("fog.jpg");
        }
        else if(dayNum>11&&dayNum<19||dayNum==39) {
        	gridImg = new Image("rainsun.jpg");
        }
        else if((dayNum>18&&dayNum<22)||(dayNum>32&&dayNum<39)||(dayNum==41)){
        	gridImg = new Image("snow.jpg");
        }
        else if(dayNum>21&&dayNum<26||dayNum==40) {
        	gridImg = new Image("snowsunny.jpg");
        }
        else if(dayNum>25&&dayNum<29||dayNum==42) {
        	gridImg = new Image("rain.jpg");
        }
        else if(dayNum>28&&dayNum<33) {
        	gridImg = new Image("lightning.jpg");
        }
        anchorpane.setBackground(new Background(new BackgroundFill(new ImagePattern(imageup), CornerRadii.EMPTY, Insets.EMPTY)));
        split.setBackground(new Background(new BackgroundFill(new ImagePattern(gridImg), CornerRadii.EMPTY, Insets.EMPTY)));
        mypane.setBackground(new Background(new BackgroundFill(new ImagePattern(glass), CornerRadii.EMPTY, Insets.EMPTY)));
    	
          }
   
   private weather test(String location) throws IOException {
	   weather checkWeather=new weather(location);
	   if(checkWeather.getData()!=null) {
		   return checkWeather;
	   }
	   return null;
   }
}
