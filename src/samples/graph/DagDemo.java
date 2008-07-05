/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package samples.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

/**
 * Shows-off DAGLayout :-).
 * 
 * @author Rodrigo Rizzi Starr
 * 
 */
public class DagDemo extends JApplet {
    
    private static final long serialVersionUID = 4050830667009268367L;
    
    /**
     * the graph
     */
    DirectedGraph<String, Integer> graph;
    
    Factory<DirectedGraph<String, Integer>> graphFactory = new Factory<DirectedGraph<String, Integer>>() {
        public DirectedGraph<String, Integer> create() {
            return new DirectedSparseMultigraph<String, Integer>();
        }
    };
    
    Factory<Integer> edgeFactory = new Factory<Integer>() {
        int i = 0;
        
        public Integer create() {
            return i++;
        }
    };
    
    Factory<String> vertexFactory = new Factory<String>() {
        int i = 0;
        
        public String create() {
            return "V" + i++;
        }
    };
    
    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String, Integer> vv;
    
    /**
     * The layout
     */
    DAGLayout<String, Integer> dagLayout;
    
    public DagDemo() {
        
        // create a simple graph for the demo
        graph = new DirectedSparseGraph<String, Integer>();
        
        /* Choose one of these examples */
        // graph1();
        // graph2();
        graph3();
        // graph4();
        
        dagLayout = new DAGLayout<String, Integer>(graph, new Dimension(600,
                600));
        
        vv = new VisualizationViewer<String, Integer>(dagLayout, new Dimension(
                600, 600));
        vv.setBackground(Color.white);
        vv.getRenderContext().setEdgeShapeTransformer(
                new DAGLayout.BentLine<String, Integer>(dagLayout));
        
        vv.getRenderContext().setVertexLabelTransformer(
                new ToStringLabeller<String>());
        
        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller<String>());
        vv.getRenderContext().setArrowFillPaintTransformer(
                new Transformer<Integer, Paint>() {
                    public Paint transform(Integer arg) {
                        return Color.lightGray;
                    }
                });
        
        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        
        vv.setGraphMouse(graphMouse);
        
        JComboBox modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener(graphMouse.getModeListener());
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        
        final ScalingControl scaler = new CrossoverScalingControl();
        
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1 / 1.1f, vv.getCenter());
            }
        });
        
        JPanel scaleGrid = new JPanel(new GridLayout(1, 0));
        scaleGrid.setBorder(BorderFactory.createTitledBorder("Zoom"));
        
        JPanel controls = new JPanel();
        scaleGrid.add(plus);
        scaleGrid.add(minus);
        controls.add(scaleGrid);
        controls.add(modeBox);
        
        content.add(controls, BorderLayout.SOUTH);
    }
    
    /**
     * 
     */
    private void graph1() {
        graph.addVertex("V0");
        graph.addEdge(edgeFactory.create(), "V0", "V1");
        graph.addEdge(edgeFactory.create(), "V0", "V10");
        graph.addEdge(edgeFactory.create(), "V0", "V2");
        graph.addEdge(edgeFactory.create(), "V1", "V4");
        graph.addEdge(edgeFactory.create(), "V1", "V5");
        graph.addEdge(edgeFactory.create(), "V2", "V3");
        graph.addEdge(edgeFactory.create(), "V2", "V5");
        graph.addEdge(edgeFactory.create(), "V4", "V6");
        graph.addEdge(edgeFactory.create(), "V4", "V7");
        graph.addEdge(edgeFactory.create(), "V3", "V8");
        graph.addEdge(edgeFactory.create(), "V6", "V9");
        graph.addEdge(edgeFactory.create(), "V7", "V5");
        graph.addEdge(edgeFactory.create(), "V4", "V10");
        
        /*
         * graph.addVertex("A0"); graph.addEdge(edgeFactory.create(), "A0",
         * "A1"); graph.addEdge(edgeFactory.create(), "A0", "A2");
         * graph.addEdge(edgeFactory.create(), "A0", "A3");
         * 
         * graph.addVertex("B0"); graph.addEdge(edgeFactory.create(), "B0",
         * "B1"); graph.addEdge(edgeFactory.create(), "B0", "B2");
         * graph.addEdge(edgeFactory.create(), "B1", "B4");
         * graph.addEdge(edgeFactory.create(), "B2", "B3");
         * graph.addEdge(edgeFactory.create(), "B2", "B5");
         * graph.addEdge(edgeFactory.create(), "B4", "B6");
         * graph.addEdge(edgeFactory.create(), "B4", "V10");
         * graph.addEdge(edgeFactory.create(), "B4", "B7");
         * graph.addEdge(edgeFactory.create(), "B3", "B8");
         * graph.addEdge(edgeFactory.create(), "B6", "B9");//
         */

    }
    
    private void graph2() {
        graph.addVertex("V0");
        graph.addEdge(edgeFactory.create(), "V0", "V1");
        // graph.addEdge(edgeFactory.create(), "V18", "V8");
        graph.addEdge(edgeFactory.create(), "V15", "V8");
        graph.addEdge(edgeFactory.create(), "V1", "V2");
        graph.addEdge(edgeFactory.create(), "V2", "V3");
        graph.addEdge(edgeFactory.create(), "V3", "V4");
        graph.addEdge(edgeFactory.create(), "V4", "V5");
        graph.addEdge(edgeFactory.create(), "V5", "V6");
        graph.addEdge(edgeFactory.create(), "V6", "V7");
        
        graph.addEdge(edgeFactory.create(), "V2", "V8");
        graph.addEdge(edgeFactory.create(), "V8", "V9");
        graph.addEdge(edgeFactory.create(), "V9", "V10");
        graph.addEdge(edgeFactory.create(), "V10", "V11");
        graph.addEdge(edgeFactory.create(), "V11", "V7");
        
        graph.addEdge(edgeFactory.create(), "V2", "V15");
        graph.addEdge(edgeFactory.create(), "V15", "V16");
        graph.addEdge(edgeFactory.create(), "V16", "V7");
        
        graph.addEdge(edgeFactory.create(), "V7", "V12");
        graph.addEdge(edgeFactory.create(), "V12", "V13");
        graph.addEdge(edgeFactory.create(), "V13", "V14");
        
        /*
         * graph.addVertex("A0"); graph.addEdge(edgeFactory.create(), "A0",
         * "A1"); graph.addEdge(edgeFactory.create(), "A0", "A2");
         * graph.addEdge(edgeFactory.create(), "A0", "A3");
         * 
         * graph.addVertex("B0"); graph.addEdge(edgeFactory.create(), "B0",
         * "B1"); graph.addEdge(edgeFactory.create(), "B0", "B2");
         * graph.addEdge(edgeFactory.create(), "B1", "B4");
         * graph.addEdge(edgeFactory.create(), "B2", "B3");
         * graph.addEdge(edgeFactory.create(), "B2", "B5");
         * graph.addEdge(edgeFactory.create(), "B4", "B6");
         * graph.addEdge(edgeFactory.create(), "B4", "V10");
         * graph.addEdge(edgeFactory.create(), "B4", "B7");
         * graph.addEdge(edgeFactory.create(), "B3", "B8");
         * graph.addEdge(edgeFactory.create(), "B6", "B9");//
         */

    }
    
    private void graph3() {
        graph.addVertex("V0");
        graph.addEdge(edgeFactory.create(), "V0", "V1");
        graph.addEdge(edgeFactory.create(), "V1", "V2");
        graph.addEdge(edgeFactory.create(), "V0", "V2");
        graph.addEdge(edgeFactory.create(), "V2", "V3");
        graph.addEdge(edgeFactory.create(), "V2", "V4");
        graph.addEdge(edgeFactory.create(), "V2", "V5");
        graph.addEdge(edgeFactory.create(), "V5", "V6");
        graph.addEdge(edgeFactory.create(), "V1", "V3");
        graph.addEdge(edgeFactory.create(), "V6", "V7");
    }
    
    private void graph4() {
        graph.addVertex("V0");
        graph.addEdge(edgeFactory.create(), "V0", "V1");
        graph.addEdge(edgeFactory.create(), "V2", "V1");
    }
    
    /**
     * a driver for this demo
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        content.add(new DagDemo());
        frame.pack();
        frame.setVisible(true);
    }
}
