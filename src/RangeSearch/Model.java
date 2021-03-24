package RangeSearch;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model extends Application {
    ArrayList<Nd> nodes = new ArrayList<Nd>();
    ComparatorX compX = new ComparatorX();
    ComparatorY compY = new ComparatorY();
    Group root;
    Canvas canvas;
    int strokeV = 220;
    int strokeH = 220;
    Nd[] nd;
    ArrayList<Nd> returnedReportSubtree = new ArrayList<>();
    ArrayList<Nd> returnedRange = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Group();
        Scene scene = new Scene(root);
        canvas = new Canvas(1200, 1000);

        //RANDOM KD-TREE
        /*
        for (int i = 0; i < 10; i++) {
            Random rd = new Random();
            float randomX = rd.nextFloat() * 1000;
            float randomY = rd.nextFloat() * 1000;
            Nd node = new Nd(i, randomX, randomY);
            nodes.add(node);
            makeCircles(canvas, node);
        }
        */

        //FIXED KD-TREE
        Nd node = new Nd(1, 300.0f, 600.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(2, 500.0f, 400.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(3, 700.0f, 900.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(4, 200.0f, 300.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(5, 800.0f, 300.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(6, 200.0f, 800.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(7, 900.0f, 800.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(8, 100.0f, 100.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(9, 200.0f, 500.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(10, 600.0f, 200.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(11, 700.0f, 400.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(12, 400.0f, 700.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(13, 600.0f, 800.0f);
        nodes.add(node);
        makeCircles(canvas, node);
        node = new Nd(14, 800.0f, 700.0f);
        nodes.add(node);
        makeCircles(canvas, node);


        //YELLOW RANGE SQUARE
        makeRangeSquare(getCanvas());
        nd = new Nd[nodes.size()];

        //KD-TREE
        TreeNodes v = buildKDTree(nodes, 0);

        //REPORT()
        System.out.println("\nPRINT REPORTSUBTREE: (ALL LEAVES)");
        for (Nd n : reportSubTree(v)) {
            System.out.println(n);
        }

        //RANGE()
        System.out.println("\nPRINT RANGE:");
        Nd n1 = new Nd(11, 50, 50);
        Nd n2 = new Nd(12, 150, 150);
        searchRange(v, n1, n2);
        for (Nd n : returnedRange) {
            System.out.println(n);
        }


        //STAGE
        root.getChildren().add(canvas);
        primaryStage.setTitle("RangeSearch");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeRangeSquare(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.fillRect(50, 50, 150, 150); //lon = x //lat = y
    }

    private void makeCircles(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillOval(node.getLon(), node.getLat(), 10, 10); //lon = x //lat = y
    }

    private void makeRedCircles(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
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


    private ArrayList<Nd> reportSubTree(TreeNodes root) {
        if (root.getLeft() == null && root.getRight() == null) {
            returnedReportSubtree.add(root.getNode()); //leaf
            makeRedCircles(getCanvas(), root.getNode());//mark red
        } else if (root.getLeft() != null && root.getRight() == null) {
            reportSubTree(root.getLeft());
        } else {
            reportSubTree(root.getLeft());
            reportSubTree(root.getRight());
        }
        return returnedReportSubtree;
    }


    public ArrayList<Nd> searchRange(TreeNodes root, Nd n1, Nd n2) {
        boolean rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
        boolean rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();

        if (rangeX && rangeY) {
            returnedRange = reportSubTree(root);
            for (Nd n : returnedRange) {
                System.out.println(n);
            }

        }
        if (root.getLeft() == null && root.getRight() == null){
            searchRange(root.getLeft(), n1, n2);
        } else if (root.getLeft() != null && root.getRight() == null){
            searchRange(root.getLeft(), n1, n2);
            searchRange(root.getRight(), n1, n2);
        }

        /*

        if (root.getLeft() == null && root.getRight() == null) { //lon = x; lat = y
            returnedRange.add(root.getNode()); //leaf
            makeRedCircles(getCanvas(), root.getNode());//mark red

        } else if (root.getLeft() != null && root.getRight() == null) {
            rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
            rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();

            if ((rangeX && rangeY)) {
                reportSubTree(root.getLeft());
            }
        } else {
            rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
            rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();

            if (rangeX && rangeY) {
                reportSubTree(root);
            } else {
                searchRange(root.getLeft(), n1, n2);
                searchRange(root.getRight(), n1, n2);
            }
        }
        */

        return returnedRange;
    }

    public Group getRoot() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}