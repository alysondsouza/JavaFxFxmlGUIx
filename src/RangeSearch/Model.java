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
    int strokeV = 200;
    int strokeH = 200;
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

        for (int i = 0; i < 10; i++) {
            Random rd = new Random();
            float randomX = rd.nextFloat() * 1000;
            float randomY = rd.nextFloat() * 1000;
            Nd node = new Nd(i, randomX, randomY);
            nodes.add(node);
            makeCircles(canvas, node);
        }
        makeRangeSquare(getCanvas(), nodes.get(2));
        nd = new Nd[nodes.size()];

        TreeNodes v = buildKDTree(nodes, 0);

        System.out.println("\nPRINT REPORTSUBTREE:");
        for (Nd n : reportSubTree(v)) {
            System.out.println(n);
        }

        System.out.println("\nPRINT RANGE:");
        Nd n1 = new Nd(11, 200, 200);
        Nd n2 = new Nd(12, 400, 400);
        searchRange(v, n1, n2);

        root.getChildren().add(canvas);
        primaryStage.setTitle("RangeSearch");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeRangeSquare(Canvas canvas, Nd node) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.fillRect(200, 200, 400, 400); //lon = x //lat = y
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


        if (root.getLeft() == null && root.getRight() == null) { //lon = x; lat = y
            returnedRange.add(root.getNode()); //leaf
            makeRedCircles(getCanvas(), root.getNode());//mark red
        } else if (root.getLeft() != null && root.getRight() == null) {
            boolean rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
            boolean rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();
            if ((rangeX && rangeY)) {
                reportSubTree(root.getLeft());
            }
        } else {
            boolean rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
            boolean rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();
            if ((rangeX && rangeY)) {
                reportSubTree(root);
            } else {
                searchRange(root.getLeft(), n1 , n2);
            }
            rangeX = root.getNode().getLon() > n1.getLon() && root.getNode().getLon() < n2.getLon();
            rangeY = root.getNode().getLat() > n1.getLat() && root.getNode().getLat() < n2.getLat();
            if ((rangeX && rangeY)) {
                reportSubTree(root);
            } else {
                searchRange(root.getRight(), n1 , n2);
            }
        }
        return returnedRange;
    }

    public Group getRoot() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}