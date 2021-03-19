package KDTree;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model extends Application {
    ArrayList<Nd> nodes = new ArrayList<Nd>();
    ComparatorX compX = new ComparatorX();
    ComparatorY compY = new ComparatorY();
    Group root;
    Canvas canvas;
    int strokeV = 200;
    int strokeH = 200;
    Nd[] nd;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Group();
        Scene scene = new Scene(root);
        canvas = new Canvas(1200, 1000);

        for (int i = 0; i < 10; i++) {
            Random rd = new Random();
            float randomX = rd.nextFloat() * 1000;
            float randomY = rd.nextFloat() * 1000;
            Nd node = new Nd(i, randomX, randomY);
            nodes.add(node);
            makeCircles(canvas, node);
        }

        nd = new Nd[nodes.size()];

        TreeNodes v = buildKDTree(nodes, 0);


        root.getChildren().add(canvas);
        primaryStage.setTitle("KDTree");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeCircles(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillOval(node.getLon(), node.getLat(), 10, 10); //lon = x //lat = y
    }

    private void makeVerticalLine(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.strokeLine(node.getLon() + 5, node.getLat() + (strokeV + 10), node.getLon() + 5, node.getLat() - strokeV);
        strokeV -= 40;
    }

    private void makeHorizontalLine(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.strokeLine(node.getLon() + (strokeH + 10), node.getLat() + 5, node.getLon() - strokeH, node.getLat() + 5);
        strokeH -= 40;
    }

    public TreeNodes buildKDTree(ArrayList<Nd> nodes, int depth) {
        boolean leaf = false;
        ArrayList<Nd> left = null;
        ArrayList<Nd> right = null;
        int median = 0;
        TreeNodes v = null;
        TreeNodes v_left;
        TreeNodes v_right;

        //ptint
        System.out.println();
        System.out.println();
        for (Nd n : nodes) {
            System.out.println(n);
        }
        System.out.println();

        if (nodes.size() == 1) {
            leaf = true;
            v = new TreeNodes(nodes.get(0));
            if (depth % 2 == 0) {
                makeVerticalLine(getCanvas(), nodes.get(0));
            } else {
                makeHorizontalLine(getCanvas(), nodes.get(0));
            }
            System.out.println("Leaf: " + nodes.get(0).toString());
        } else if (depth % 2 == 0) {
            Collections.sort(nodes, compX);
            median = (int) Math.floor(nodes.size() / 2.0);
            makeVerticalLine(getCanvas(), nodes.get(median));
            System.out.println("Median X: " + nodes.get(median)); //print
            left = new ArrayList<Nd>(nodes.subList(0, median));
            right = new ArrayList<Nd>(nodes.subList(median + 1, nodes.size()));
        } else if (depth % 2 == 1) {
            Collections.sort(nodes, compY);
            median = (int) Math.floor(nodes.size() / 2.0);
            makeHorizontalLine(getCanvas(), nodes.get(median));
            System.out.println("Median Y: " + nodes.get(median)); //print
            left = new ArrayList<Nd>(nodes.subList(0, median));
            right = new ArrayList<Nd>(nodes.subList(median + 1, nodes.size()));
        }

        if (!leaf) {
            v = new TreeNodes(nodes.get(median));
            v_left = buildKDTree(left, depth + 1);
            v.setLeft(v_left);

            if (right.size() > 0) {
                v_right = buildKDTree(right, depth + 1);
                v.setRight(v_right);
            }
        }

        return v;
    }

    public Group getRoot() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}