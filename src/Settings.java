/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Adjust size and color of Snake(head, tail) and Food
 * @author Owner
 */
public class Settings extends GridPane {
    
    // values to be added to color/size lists, which will be used by the Snake and Food class
    private int r;
    private int g;
    private int b;
    private double a;
    private double size;
    private int h;
    private double s;
    private double l;
    
    // color lists
    private ArrayList<Color> headColors;
    private ArrayList<Color> tailColors;
    private ArrayList<Color> foodColors;
    
    // size lists
    private ArrayList<Double> headSizes;
    private ArrayList<Double> tailSizes;
    private ArrayList<Double> foodSizes;
    
    
    // color lists
    private ArrayList<Color> prevHeadColors;
    private ArrayList<Color> prevTailColors;
    private ArrayList<Color> prevFoodColors;
    
    // size lists
    private ArrayList<Double> prevHeadSizes;
    private ArrayList<Double> prevTailSizes;
    private ArrayList<Double> prevFoodSizes;
    
    // color options
    private CheckBox colorGrad;
    private Slider colorStepSlider;
    
    // site options
    private CheckBox sizeGrad;
    private Slider sizeStepSlider;
    
    // gradient (n) options
    private Slider nSlider;
    private TextField nField;
    private int n;
    
    // game options
    private CheckBox headUnique;
    private CheckBox sequence;
    private CheckBox frozen;
    private CheckBox headUniqueSize;
    private CheckBox sequenceSize;
    private CheckBox frozenSize;

    private Game gameApp;
    private MainMenu menu;
    private Stage stage;

    // color sliders
    private Slider rSlider;
    private Slider gSlider;
    private Slider bSlider;
    private Slider aSlider;
    private Slider hSlider;
    private Slider sSlider;
    private Slider lSlider;
    
    // color text fields
    private TextField rField;
    private TextField gField;
    private TextField bField;
    private TextField aField;
    private TextField hField;
    private TextField sField;
    private TextField lField;
    
    // category radio buttons
    private RadioButton headRadio;
    private RadioButton tailRadio;
    private RadioButton foodRadio;
    
    // previews for color/size for head, tail, and food
    private SettingsPreview head;
    private SettingsPreview tail;
    private SettingsPreview food;
            
    // preview radio buttons
    private RadioButton colorRadio;
    private RadioButton sizeRadio;
    
    public Color bg;
    public Color strColor;
    public int strWidth;
    public Color foodStrColor;
    public int foodStrWidth;
    
    private Color currentColor;
    private boolean freezeSliders = false;
    
    public Settings(Stage stage, MainMenu menu, Game gameApp) {
    
        bg = Color.rgb(244, 244, 244, 1);
        strColor = Color.rgb(0, 0, 0, 1);
        strWidth = 2;
        foodStrColor = Color.rgb(0, 0, 0, 1);
        foodStrWidth = 2;
        
        this.gameApp = gameApp;
        this.menu = menu;
        this.stage = stage;
        
        headColors = new ArrayList<Color>();
        tailColors = new ArrayList<Color>();
        foodColors = new ArrayList<Color>();
        headSizes = new ArrayList<Double>();
        tailSizes = new ArrayList<Double>();
        foodSizes = new ArrayList<Double>();
        prevHeadColors = new ArrayList<Color>();
        prevTailColors = new ArrayList<Color>();
        prevFoodColors = new ArrayList<Color>();
        prevHeadSizes = new ArrayList<Double>();
        prevTailSizes = new ArrayList<Double>();
        prevFoodSizes = new ArrayList<Double>();
        
        // set initial colors and size
        r = 255;
        g = 0;
        b = 0;
        a = 1;
        size = 6;
        
        n = 2;
       
        currentColor = Color.rgb(r, g, b, a);
        
        h = (int) currentColor.getHue();
        s = (int)(currentColor.getSaturation() * 100) / 100.0;
        l = (int)(currentColor.getBrightness() * 100) / 100.0;
        
        /**************************
         * CATEGORY RADIO BUTTONS *
         **************************/
        
        // category label
        Label categoryLabel = new Label("Category");
        categoryLabel.setStyle("-fx-font: 18 monospace; -fx-alignment: center; -fx-underline: true;");
        
        
        // category radio buttons
        HBox categories = new HBox();
        ToggleGroup group = new ToggleGroup();
        headRadio = new RadioButton("HEAD");
        headRadio.setToggleGroup(group);
        tailRadio = new RadioButton("TAIL");
        tailRadio.setToggleGroup(group);
        foodRadio = new RadioButton("FOOD");
        foodRadio.setToggleGroup(group);
        categories.getChildren().addAll(headRadio, tailRadio, foodRadio);
        // set head as beginning category
        headRadio.fire();
        categories.setSpacing(6);

        /************************
         * GRADIENT (N) OPTIONS *
         ************************/
        nSlider = new Slider(2, 40, 2);
        nField = new TextField("2");
        nField.setMaxWidth(40);
        nField.setEditable(false);
        
        /*****************
         * COLOR OPTIONS *
         *****************/
        // color options label
        Label colorLabel = new Label("Color Options");
        colorLabel.setStyle("-fx-font: 18 monospace; -fx-alignment: center; -fx-underline: true;");
        
        
        // color gradient options
        HBox colorGradient = new HBox();
        colorGrad = new CheckBox("Gradient");
        colorStepSlider = new Slider(2, 20, 2);
        TextField colorSliderField = new TextField("2");
        colorSliderField.setMaxWidth(40);
        colorSliderField.setEditable(false);
        
        // adjust color slider field according to color step slider
        colorStepSlider.valueProperty().addListener(l -> {
            colorSliderField.setText("" + (int)colorStepSlider.getValue());
        });
        
        colorGradient.getChildren().addAll(colorGrad, colorStepSlider);
        colorGradient.setSpacing(6);
        
        
        // color buttons (add, remove)
        HBox colorButtons = new HBox();
        Button colorAdd = new Button("Add");
        Button colorRemove = new Button("Remove");
        Button colorAddN = new Button("Add (n)");
        Button colorRemoveN = new Button("Remove (n)");
        colorButtons.getChildren().addAll(colorAdd, colorRemove, colorAddN, colorRemoveN);
        colorButtons.setSpacing(6);
        
        
        /****************
         * COLOR SELECT *
         ****************/
        
        // color select sliders
        rSlider = new Slider(0, 255, r);
        gSlider = new Slider(0, 255, g);
        bSlider = new Slider(0, 255, b);
        aSlider = new Slider(0, 100, a * 100);
        
        hSlider = new Slider(0, 360, 0);
        sSlider = new Slider(0, 100, 100);
        lSlider = new Slider(0, 100, 100);
        
        // color select textfields
        rField = new TextField("255");
        rField.setMaxWidth(40);
        rField.setEditable(false);
        gField = new TextField("0");
        gField.setMaxWidth(40);
        gField.setEditable(false);
        bField = new TextField("0");
        bField.setMaxWidth(40);
        bField.setEditable(false);
        aField = new TextField("100");
        aField.setMaxWidth(40);
        aField.setEditable(false);
        
        hField = new TextField("0");
        hField.setMaxWidth(40);
        hField.setEditable(false);
        sField = new TextField("100");
        sField.setMaxWidth(40);
        sField.setEditable(false);
        lField = new TextField("100");
        lField.setMaxWidth(40);
        lField.setEditable(false);
        
        
        
        /****************
         * SIZE OPTIONS *
         ****************/
        // size options label
        Label sizeLabel = new Label("Size Options");
        sizeLabel.setStyle("-fx-font: 18 monospace; -fx-alignment: center; -fx-underline: true;");
        
        
        // size gradient options
        HBox sizeGradient = new HBox();
        sizeGrad = new CheckBox("Gradient");
        sizeStepSlider = new Slider(2, 20, 2);
        TextField sizeSliderField = new TextField("2");
        sizeSliderField.setMaxWidth(40);
        sizeSliderField.setEditable(false);
        
        // adjust size slider field according to size step slider
        sizeStepSlider.valueProperty().addListener(l -> {
            sizeSliderField.setText("" + (int)sizeStepSlider.getValue());
        });
        
        sizeGradient.getChildren().addAll(sizeGrad, sizeStepSlider);
        sizeGradient.setSpacing(6);
        
        
        // size buttons (add, remove)
        HBox sizeButtons = new HBox();
        Button sizeAdd = new Button("Add");
        Button sizeRemove = new Button("Remove");
        Button sizeAddN = new Button("Add (n)");
        Button sizeRemoveN = new Button("Remove (n)");
        sizeButtons.getChildren().addAll(sizeAdd, sizeRemove, sizeAddN, sizeRemoveN);
        sizeButtons.setSpacing(6);
        
        
        /***************
         * SIZE SELECT *
         ***************/
        Slider sizeSlider = new Slider(0, 40, 6);
        
        
        
        // size select textfield
        TextField sizeField = new TextField("6");
        sizeField.setMaxWidth(40);
        sizeField.setEditable(false);
        
        // add labels and textfields

        
        /****************
         * GAME OPTIONS *
         ****************/
        // game options label
        Label gameLabel = new Label("Game Options");
        gameLabel.setStyle("-fx-font: 18 monospace; -fx-alignment: center; -fx-underline: true;");
        
        
        
        // game options checkboxes
        HBox gameOptions = new HBox();
        headUnique = new CheckBox("Head Unique");
            headUnique.setSelected(true);
        sequence = new CheckBox("Sequence");
        frozen = new CheckBox("Frozen");
        gameOptions.getChildren().addAll(headUnique, sequence, frozen);
        gameOptions.setSpacing(6);
        
        
        // game options checkboxes (size)
        HBox gameOptionsSize = new HBox();
        headUniqueSize = new CheckBox("Head Unique");
            headUniqueSize.setSelected(true);
        sequenceSize = new CheckBox("Sequence");
        frozenSize = new CheckBox("Frozen");
        gameOptionsSize.getChildren().addAll(headUniqueSize, sequenceSize, frozenSize);
        gameOptionsSize.setSpacing(6);
        
        
        /*********************
         * CATEGORY PREVIEWS *
         *********************/
        // add previews for HEAD, TAIL, FOOD
        head = new SettingsPreview("HEAD", headColors, headSizes);
        tail = new SettingsPreview("TAIL", tailColors, tailSizes);
        food = new SettingsPreview("FOOD", foodColors, foodSizes);
        // add previews to options
        
        
        /*************************************
         * PREVIEW FUNCTIONS (CLEAR, MIRROR) *
         *************************************/
        
        // hbox to hold buttons for preview functions
        HBox previewButtons = new HBox();
        previewButtons.setAlignment(Pos.CENTER);
        previewButtons.setSpacing(6);
        
        // buttons for functions
        Button clear = new Button("Clear");
        Button mirror = new Button("Mirror");
        
        previewButtons.getChildren().addAll(clear, mirror);
        
        
        // hbox to hold radio buttons for preview functions
        HBox previewCategories = new HBox();
        previewCategories.setAlignment(Pos.CENTER);
        previewCategories.setSpacing(6);
        
        // radio buttons for functions
        ToggleGroup preview = new ToggleGroup();
        colorRadio = new RadioButton("Color");
            colorRadio.fire();
        colorRadio.setToggleGroup(preview);
        sizeRadio = new RadioButton("Size");
        sizeRadio.setToggleGroup(preview);
        
        previewCategories.getChildren().addAll(colorRadio, sizeRadio);
        
        /*********************************************
         *              ADD EVERYTHING               *
         *********************************************/

        add(categoryLabel, 0, 0);
        add(categories, 0, 1);

        add(nSlider, 0, 2);
        add(new Label("n: "), 1, 2);
        add(nField, 2, 2);

        add(colorLabel, 0, 3);

//        add(colorGradient, 0, 3);
//        add(new Label("n: "), 1, 3);
//        add(colorSliderField, 2, 3);

        add(colorButtons, 0, 4);

        add(rSlider, 0, 5);
        add(new Label("R: "), 1, 5);
        add(rField, 2, 5);

        add(gSlider, 0, 6);
        add(new Label("G: "), 1, 6);
        add(gField, 2, 6);

        add(bSlider, 0, 7);
        add(new Label("B: "), 1, 7);
        add(bField, 2, 7);

        add(hSlider, 0, 8);
        add(new Label("H: "), 1, 8);
        add(hField, 2, 8);

        add(sSlider, 0, 9);
        add(new Label("S: "), 1, 9);
        add(sField, 2, 9);

        add(lSlider, 0, 10);
        add(new Label("L: "), 1, 10);
        add(lField, 2, 10);


        add(aSlider, 0, 11);
        add(new Label("A: "), 1, 11);
        add(aField, 2, 11);



        add(sizeLabel, 0, 12);

//        add(sizeStepSlider, 0, 13);
//        add(new Label("N: "), 1, 13);
//        add(sizeSliderField, 2, 13);

        add(sizeButtons, 0, 13);

        add(sizeSlider, 0, 14);
        add(new Label("#: "), 1, 14);
        add(sizeField, 2, 14);

        add(gameLabel, 0, 15);

        add(gameOptions, 0, 16);
        add(new Label("(Color)"), 2, 16);

        add(gameOptionsSize, 0, 17);
        add(new Label("(Size)"), 2, 17);




        add(head, 3, 2, 1, 100);
        add(tail, 4, 2, 1, 100);
        add(food, 5, 2, 1, 100);
        add(previewButtons, 3, 0, 3, 1);
        add(previewCategories, 3, 1, 3, 1);
        
        /*************************
         * ACTIONS AND LISTENERS *
         *************************/
        
        clear.setOnAction(e -> {
            if(headRadio.isSelected())
                head.clear();
            else if(tailRadio.isSelected())
                tail.clear();
            else
                food.clear();
        });
        
        mirror.setOnAction(e -> {
            if(headRadio.isSelected())
                head.mirror();
            else if(tailRadio.isSelected())
                tail.mirror();
            else
                food.mirror();
        });
        
        // add color to list
        colorAdd.setOnAction(e -> {
            if(headRadio.isSelected())
                head.addColor(false);
            else if(tailRadio.isSelected())
                tail.addColor(false);
            else
                food.addColor(false);
        });
        
        colorAddN.setOnAction(e -> {
            if(headRadio.isSelected())
                head.addColor(true);
            else if(tailRadio.isSelected())
                tail.addColor(true);
            else
                food.addColor(true);
        });
        
        // remove color from list
        colorRemove.setOnAction(e -> {
            if(headRadio.isSelected())
                head.removeColor(false);
            else if(tailRadio.isSelected())
                tail.removeColor(false);
            else
                food.removeColor(false);
        });
        
        // remove color from list
        colorRemoveN.setOnAction(e -> {
            if(headRadio.isSelected())
                head.removeColor(true);
            else if(tailRadio.isSelected())
                tail.removeColor(true);
            else
                food.removeColor(true);
        });
        
        // add size to list
        sizeAdd.setOnAction(e -> {
            if(headRadio.isSelected())
                head.addSize(false);
            else if(tailRadio.isSelected())
                tail.addSize(false);
            else
                food.addSize(false);
        });
        
        // add size to list
        sizeAddN.setOnAction(e -> {
            if(headRadio.isSelected())
                head.addSize(true);
            else if(tailRadio.isSelected())
                tail.addSize(true);
            else
                food.addSize(true);
        });
        
        // remove size from list
        sizeRemove.setOnAction(e -> {
            if(headRadio.isSelected())
                head.removeSize(false);
            else if(tailRadio.isSelected())
                tail.removeSize(false);
            else
                food.removeSize(false);
        });
        
        // remove size from list
        sizeRemoveN.setOnAction(e -> {
            if(headRadio.isSelected())
                head.removeSize(true);
            else if(tailRadio.isSelected())
                tail.removeSize(true);
            else
                food.removeSize(true);
        });
        
        // adjust n textfield when n slider is moved
        nSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            n = newValue.intValue();
            nField.setText(String.valueOf(n));
        });
        
        // adjust size textfield and size preview when size slider is moved
        sizeSlider.valueProperty().addListener(ov -> {
            size = sizeSlider.getValue();
            sizeField.setText("" + (int)size);
            if(headRadio.isSelected())
                head.previewSize();
            else if(tailRadio.isSelected())
                tail.previewSize();
            else
                food.previewSize();
        });
        
        // adjust color textfields and color previews when color slider is moved
        /*
        rSlider.valueProperty().addListener(new ColorListener());
        gSlider.valueProperty().addListener(new ColorListener());
        bSlider.valueProperty().addListener(new ColorListener());
        aSlider.valueProperty().addListener(new ColorListener());
        */
        
        aSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            aField.setText(String.valueOf(newValue.intValue()));
            a = newValue.intValue() / 100.0;
            if(headRadio.isSelected())
                head.previewColor();
            else if(tailRadio.isSelected())
                tail.previewColor();
            else
                food.previewColor();
        });
        
        rSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            rField.setText(String.valueOf(newValue.intValue()));
            if(!freezeSliders) {
                freezeSliders = true;
                r = newValue.intValue();
                currentColor = Color.rgb(r, g, b);
                
                h = (int) currentColor.getHue();
                s = (int)(currentColor.getSaturation() * 100) / 100.0;
                l = (int)(currentColor.getBrightness() * 100) / 100.0;
                hSlider.setValue(h);
                sSlider.setValue(s * 100);
                lSlider.setValue(l * 100);
                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });
        
        gSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            gField.setText(String.valueOf(newValue.intValue()));
            if(!freezeSliders) {
                freezeSliders = true;
                g = newValue.intValue();
                currentColor = Color.rgb(r, g, b);
                
                h = (int) currentColor.getHue();
                s = (int)(currentColor.getSaturation() * 100) / 100.0;
                l = (int)(currentColor.getBrightness() * 100) / 100.0;
                hSlider.setValue(h);
                sSlider.setValue(s * 100);
                lSlider.setValue(l * 100);
                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });
        
        bSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            bField.setText(String.valueOf(newValue.intValue()));
            if(!freezeSliders) {
                freezeSliders = true;
                b = newValue.intValue();
                currentColor = Color.rgb(r, g, b);
                
                h = (int) currentColor.getHue();
                s = (int)(currentColor.getSaturation() * 100) / 100.0;
                l = (int)(currentColor.getBrightness() * 100) / 100.0;
                hSlider.setValue(h);
                sSlider.setValue(s * 100);
                lSlider.setValue(l * 100);
                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });
        
        hSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            hField.setText(String.valueOf(newValue.intValue()));
            if(!freezeSliders) {
                freezeSliders = true;
                
                h = newValue.intValue();
                currentColor = Color.hsb(h, s, l);
                        
                r = (int)(currentColor.getRed() * 255);
                g = (int)(currentColor.getGreen() * 255);
                b = (int)(currentColor.getBlue() * 255);
                rSlider.setValue(r);
                gSlider.setValue(g);
                bSlider.setValue(b);
                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });
        
        sSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            
            sField.setText(String.valueOf(newValue.intValue()));
            
            if(!freezeSliders) {
                freezeSliders = true;
                
                s = newValue.intValue() / 100.0;
                currentColor = Color.hsb(h, s, l);
                
                r = (int)(currentColor.getRed() * 255);
                g = (int)(currentColor.getGreen() * 255);
                b = (int)(currentColor.getBlue() * 255);
                rSlider.setValue(r);
                gSlider.setValue(g);
                bSlider.setValue(b);

                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });
        
        lSlider.valueProperty().addListener((observable, oldValue, newValue) -> 
        {
            lField.setText(String.valueOf(newValue.intValue()));
            if(!freezeSliders) {
                freezeSliders = true;
                
                l = newValue.intValue() / 100.0;
                currentColor = Color.hsb(h, s, l);
                
                r = (int)(currentColor.getRed() * 255);
                g = (int)(currentColor.getGreen() * 255);
                b = (int)(currentColor.getBlue() * 255);
                rSlider.setValue(r);
                gSlider.setValue(g);
                bSlider.setValue(b);
                
                if(headRadio.isSelected())
                    head.previewColor();
                else if(tailRadio.isSelected())
                    tail.previewColor();
                else
                    food.previewColor();
                freezeSliders = false;
            }
        });

    }

    // adjust color textfields and color previews when color slider is moved
    private class ColorListener implements ChangeListener<Number>
    {
        @Override
        public void changed(ObservableValue observable, Number oldValue, Number newValue)
        {
//            System.out.println(observable);
//            System.out.println((Double)newValue);
            r = (int)rSlider.getValue();
            rField.setText("" + r);
            g = (int)gSlider.getValue();
            gField.setText("" + g);
            b = (int)bSlider.getValue();
            bField.setText("" + b);
            a = aSlider.getValue();
            aField.setText("" + (int)(a * 100));
            if(headRadio.isSelected())
                head.previewColor();
            else if(tailRadio.isSelected())
                tail.previewColor();
            else
                food.previewColor();
        }
    }
    
    private class SettingsPreview extends GridPane
    {
        private Label title;
        private Rectangle[] colorPreview;
        private Label[] sizePreview;
        private int colorPosition;
        private int sizePosition;
        private int amount; // amount of previews (color and size)
        
        private ArrayList<Color> colorsList;
        private ArrayList<Double> sizesList;
        
        // previous values used to calculate gradient
        // double for colors for better accuracy despite color still being assigned as int
        private int prevR;
        private int prevG;
        private int prevB;
        private double prevA;
        private double prevSize;
        
        public SettingsPreview(){}
        
        public SettingsPreview(String title, ArrayList<Color> colorsList, ArrayList<Double> sizesList)
        {
            // set title for previews
            this.title = new Label(title);
            this.title.setStyle("-fx-font: 12 monospace;");
            add(this.title, 1, 0);
            
            // set number placeholders above sizes
            Label placeHolder = new Label("##");
            placeHolder.setStyle("-fx-font: 12 monospace");
            add(placeHolder, 0, 0);
            
            // set amount of previews
            amount = 21;
            
            // assign array lists
            this.colorsList = colorsList;
            this.sizesList = sizesList;
            
            colorPreview = new Rectangle[amount];
            sizePreview = new Label[amount];
            
            for(int i = 0; i < amount; i++) {
                
                Rectangle color = new Rectangle(30, 15);
                Label size = new Label("?");
                
                
                
                colorPreview[i] = color;
                sizePreview[i] = size;
                
                size.setStyle("-fx-font: 12 monospace;");
                
                color.setStroke(Color.BLACK);
                color.setStrokeWidth(1);
                
                color.setFill(Color.rgb(0, 0, 0, 0));
                
                if(i == 0) {
                    color.setStrokeWidth(2);
                    size.setUnderline(true);
                }
            
                // i + 1 since title gets 0th position
                add(color, 1, i + 1);
                add(size, 0, i + 1);
                
            }
            
            setVgap(-1);
            setHgap(6);
            setPadding(new Insets(0, 0, 0, 12));
            
            // current color and size
            colorPosition = 0;
            sizePosition = 0;
        }
        
        // clears all values from either colorsList or sizesList
        public void clear()
        {
            
            if(colorRadio.isSelected()) {
                colorsList.clear();
                clearColors();
            }
            else {
                sizesList.clear();
                clearSizes();
            }
            
        }
        
        // reset color previews
        public void clearColors()
        {
                for(int i = 0; i < amount; i++)
                    colorPreview[i].setFill(Color.rgb(0, 0, 0, 0));
                colorPreview[colorPosition % amount].setStrokeWidth(1);
                colorPreview[0].setStrokeWidth(2);
                colorPosition = 0;
        }
        
        // reset size previews
        public void clearSizes()
        {
                for(int i = 0; i < amount; i++)
                    sizePreview[i].setText("?");
                sizePreview[sizePosition % amount].setUnderline(false);
                sizePreview[0].setUnderline(true);
                sizePosition = 0;
        }
        
        // mirrors all values from either colorsList or sizesList
        public void mirror()
        {
            if(colorRadio.isSelected()) {
                
                // exit if no colors in list
                if(colorsList.isEmpty())
                    return;
                
                // add colors to list from list in reverse order
                // {- 2} instead of {-1} so last value isn't mirrored (example list: [R, G, B])
                // [R, G, B, G, R] instead of [R, G, B, B, G, R]
                // {> 0} instead of {>= 0} so user can mirror if only one value
                for(int i = colorsList.size() - 2; i > 0; i--) {
                    colorsList.add(colorsList.get(i));
                }
                
                // add value outside of loop in case of only one value
                colorsList.add(colorsList.get(0));
                
                // update the previews with new list
                updateColorPreviews();
                
            }
            
            else {
            
                // refer to comments from {if(colorRadio.isSelected())}
                
                if(sizesList.isEmpty())
                    return;
                
                for(int i = sizesList.size() - 2; i > 0; i--)
                    sizesList.add(sizesList.get(i));
                
                sizesList.add(sizesList.get(0));
                
                updateSizePreviews();
                
            }
            
        }
        
        // update color previews according to list
        public void updateColorPreviews()
        {
            colorPosition = colorsList.size();
            for(int i = 0; i < colorsList.size(); i++) {
                // set preview value from list
                colorPreview[i % amount].setFill(colorsList.get(i));
                // remove focus from preview
                colorPreview[i % amount].setStrokeWidth(1);
            }
            
            // add focus to current position
            colorPreview[colorPosition % amount].setStrokeWidth(2);
        }
        
        // update size previews according to list
        public void updateSizePreviews()
        {
            sizePosition = sizesList.size();
            for(int i = 0; i < sizesList.size(); i++) {
                // set preview value from list
                sizePreview[i % amount].setText("" + ((int)sizesList.get(i).doubleValue()));
                // remove focus from preview
                sizePreview[i % amount].setUnderline(false);
            }
            
            // add focus to current position
            sizePreview[sizePosition % amount].setUnderline(true);
        }
        
        public void previewColor()
        {
            // in case there are more colors added then there are previews (use modulus)
            colorPreview[colorPosition % amount].setFill(Color.rgb(r, g, b, a));
        }
        
        public void previewColor(int r, int g, int b, double a)
        {
            colorPreview[colorPosition % amount].setFill(Color.rgb(r, g, b, a));
        }
        
        public void previewColor(Color color)
        {
            colorPreview[colorPosition % amount].setFill(color);
        }
        
        public void previewSize()
        {            
            // in case there are more sizes added then there are previews (use modulus)
            sizePreview[sizePosition % amount].setText("" + (int)size);
        }
        
        public void previewSize(double size)
        {
            sizePreview[sizePosition % amount].setText("" + (int)size);
        }
        
        public void addColor(boolean gradient)
        {
            int howMany = gradient ? n : 1;
            
            // variables for gradient
            int gradR;
            int gradG;
            int gradB;
            double gradA;
           
            
            // set previous values to last color in list, else set them to current r, g, b, a values
            if(!colorsList.isEmpty()) {
                prevR = (int)(colorsList.get(colorsList.size() - 1).getRed() * 255);
                prevG = (int)(colorsList.get(colorsList.size() - 1).getGreen() * 255);
                prevB = (int)(colorsList.get(colorsList.size() - 1).getBlue() * 255);
                prevA = colorsList.get(colorsList.size() - 1).getOpacity();
            }
            else {
                prevR = r;
                prevG = g;
                prevB = b;
                prevA = a;
            }

            Color newColor;

            for(int i = 1; i < howMany; i++) {

                newColor = Color.rgb(prevR, prevG, prevB, prevA).interpolate(Color.rgb(r, g, b, a), (double)i / n );
//                newColor = Color.rgb(r, g, b, a).interpolate(Color.rgb(prevR, prevG, prevB, prevA), ((double)(n - i)) / n );

                // update preview
                previewColor(newColor);
                colorPreview[colorPosition % amount].setStrokeWidth(1);
                colorPosition++;
                colorPreview[colorPosition % amount].setStrokeWidth(2);

                // add to list
                colorsList.add(newColor);
            }
            
            previewColor();
            colorPreview[colorPosition % amount].setStrokeWidth(1);
            colorPosition++;
            colorPreview[colorPosition % amount].setStrokeWidth(2);

            // add to list
            colorsList.add(Color.rgb(r, g, b, a));
            
        }
        
        public void removeColor(boolean gradient)
        {
            // how many colors to remove from list and how many previews to reset
            int howMany = gradient ? n : 1;
            
            for(int i = 0; i < howMany; i++) {
                // reset color preview
                // in case there are more colors than amount of previews
                // reset to the preview of the previous set (i.e., colorPosition - amount)
                if(colorPosition >= amount) {
                    colorPreview[colorPosition % amount].setFill(colorsList.get(colorPosition - amount));
                }
                else
                    colorPreview[colorPosition % amount].setFill(Color.rgb(0, 0, 0, 0));

                colorPreview[colorPosition % amount].setStrokeWidth(1);
                colorPosition = colorPosition == 0 ? 0 : colorPosition - 1;
                colorPreview[colorPosition % amount].setStrokeWidth(2);

                // remove from list if not empty
                if(!colorsList.isEmpty())
                    colorsList.remove(colorPosition);
            }
        }
        
        public void addSize(boolean gradient)
        {
            int howMany = gradient ? n : 1;
            
            // variable to calculate gradient
            double gradSize;
            
            // set previous value to last size in list, else set them to current size value
            if(!sizesList.isEmpty()) {
                prevSize = sizesList.get(sizesList.size() - 1);
            }
            else {
                prevSize = size;
            }
            
            for(int i = 1; i < howMany; i++) {
                
                // calculate gradient size using linear interpolation
                gradSize = size == prevSize ? size : size*((double)i/n) + prevSize*((double)(n-i)/n);
                
                previewSize(gradSize);
                sizePreview[sizePosition % amount].setUnderline(false);
                sizePosition++;
                sizePreview[sizePosition % amount].setUnderline(true);
                sizesList.add(gradSize);
            }
            
            previewSize();
            sizePreview[sizePosition % amount].setUnderline(false);
            sizePosition++;
            sizePreview[sizePosition % amount].setUnderline(true);
            sizesList.add(size);
            
        }
        
        public void removeSize(boolean gradient)
        {
            // how many to remove from list and how many previews to reset
            int howMany = gradient ? n : 1;
            
            for(int i = 0; i < howMany; i++) {
                // reset size preview
                // in case there are more sizes than amount of previews
                // reset to the preview of the previous set (i.e., sizePosition - amount)
                if(sizePosition >= amount) {
                    sizePreview[sizePosition % amount].setText("" + ((int)sizesList.get(sizePosition - amount).doubleValue()));
                }
                else
                    sizePreview[sizePosition % amount].setText("?");

                
                sizePreview[sizePosition % amount].setUnderline(false);
                sizePosition = sizePosition == 0 ? 0 : sizePosition - 1;
                sizePreview[sizePosition % amount].setUnderline(true);
                if(!sizesList.isEmpty())
                    sizesList.remove(sizePosition);
            }
        }
        
    }
    
    /****************************************
     * PUBLIC GETTERS FOR LISTS AND OPTIONS *
     ****************************************/
    
    public ArrayList<Color> getHeadColors()
    {
        return headColors;
    }
    
    public ArrayList<Double> getHeadSizes()
    {
        return headSizes;
    }
    
    public ArrayList<Color> getTailColors()
    {
        return tailColors;
    }
    
    public ArrayList<Double> getTailSizes()
    {
        return tailSizes;
    }
    
    public ArrayList<Color> getFoodColors()
    {
        return foodColors;
    }
    
    public ArrayList<Double> getFoodSizes()
    {
        return foodSizes;
    }
    
    public boolean headUnique()
    {
        return headUnique.isSelected();
    }
    
    public boolean sequence()
    {
        return sequence.isSelected();
    }
    
    public boolean frozen()
    {
        return frozen.isSelected();
    }
    
    public boolean headUniqueSize()
    {
        return headUniqueSize.isSelected();
    }
    
    public boolean sequenceSize()
    {
        return sequenceSize.isSelected();
    }
    
    public boolean frozenSize()
    {
        return frozenSize.isSelected();
    }

    // return to main menu
    public void handleKey(KeyEvent ke)
    {
        // assign previous lists to current lists and go to main menu
        if(ke.getCode() == KeyCode.Q) {
            
            headColors.clear();
            tailColors.clear();
            foodColors.clear();
            headSizes.clear();
            tailSizes.clear();
            foodSizes.clear();
            
            for(Color headColor: prevHeadColors)
                headColors.add(headColor);
            for(Color tailColor: prevTailColors)
                tailColors.add(tailColor);
            for(Color foodColor: prevFoodColors)
                foodColors.add(foodColor);
            // use doubleValue so it doesn't just add a pointer in for loop. add(prevHeadSizes.get(i).doubleValue()) (necessary?)
            // do foreach loops create a new object, in which case doubleValue is REALLY not needed now
            for(Double headSize: prevHeadSizes)
                headSizes.add(headSize.doubleValue());
            for(Double tailSize: prevTailSizes)
                tailSizes.add(tailSize.doubleValue());
            for(Double foodSize: prevFoodSizes)
                foodSizes.add(foodSize.doubleValue());
            
            stage.setTitle("Snake");
            gameApp.getScene().setRoot(menu);
            stage.setScene(gameApp.getScene());
        }
        // assign current lists to previous lists and go to main menu
        else if(ke.getCode() == KeyCode.ESCAPE) {
            
            prevHeadColors.clear();
            prevTailColors.clear();
            prevFoodColors.clear();
            prevHeadSizes.clear();
            prevTailSizes.clear();
            prevFoodSizes.clear();
            
            for(Color headColor: headColors)
                prevHeadColors.add(headColor);
            for(Color tailColor: tailColors)
                prevTailColors.add(tailColor);
            for(Color foodColor: foodColors)
                prevFoodColors.add(foodColor);
            for(Double headSize: headSizes)
                prevHeadSizes.add(headSize.doubleValue());
            for(Double tailSize: tailSizes)
                prevTailSizes.add(tailSize.doubleValue());
            for(Double foodSize: foodSizes)
                prevFoodSizes.add(foodSize.doubleValue());
            
            stage.setTitle("Snake");
            gameApp.getScene().setRoot(menu);
            stage.setScene(gameApp.getScene());
        }
        
        else if(ke.getCode() == KeyCode.B) {
            if(ke.isControlDown())
                bg = Color.rgb(244, 244, 244, a);
            else
                bg = Color.rgb(r, g, b, a);
        }
        else if(ke.getCode() == KeyCode.C) {
            if(ke.isControlDown() && ke.isShiftDown())
                foodStrColor = Color.rgb(0, 0, 0, 1);
            else if(ke.isShiftDown())
                foodStrColor = Color.rgb(r, g, b, a);
            else if(ke.isControlDown())
                strColor = Color.rgb(0, 0, 0, 1);
            else
                strColor = Color.rgb(r, g, b, a);
        }
        else if(ke.getCode() == KeyCode.S) {
            if(ke.isControlDown() && ke.isShiftDown())
                foodStrWidth = 2; 
            else if(ke.isShiftDown())
                foodStrWidth = (int)size;
            else if(ke.isControlDown())
                strWidth = 2;
            else
                strWidth = (int)size;
        }
    }
    
    public void updatePreviews()
    {
        // reset previews
        head.clearColors();
        tail.clearColors();
        food.clearColors();
        head.clearSizes();
        tail.clearSizes();
        food.clearSizes();
        
        // update previews
        head.updateColorPreviews();
        tail.updateColorPreviews();
        food.updateColorPreviews();
        head.updateSizePreviews();
        tail.updateSizePreviews();
        food.updateSizePreviews();
    }
}
