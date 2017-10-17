/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Penjadwalan;

import demo.CircleDrawer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.ejml.data.D1Matrix64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.MatrixIO;
import org.ejml.ops.RandomMatrices;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;

/**
 *
 * @author Diva
 */
public class Grafik {

    public void PlotAllDataFitness(double[] X_pair, double[] Y_pair) {
        double x, y;

        XYSeriesCollection result = new XYSeriesCollection();

        // membuat marker titik data
        XYSeries series1 = new XYSeries("Nilai Max F(x)");
        
        for (int i = 0; i < X_pair.length; i++) {

            x = X_pair[i] + 1;
            y = Y_pair[i];
            series1.add(x, y);
        
       }

        result.addSeries(series1);

        // Menampilkan plot
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Grafik Nilai Fitness dari f(x)", // chart title
                "Generasi", // x axis label
                "y=f(x)", // y axis label
                result,
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        XYPlot xyPlot = chart.getXYPlot();
        ValueAxis domainAxis = xyPlot.getDomainAxis();
        ValueAxis rangeAxis = xyPlot.getRangeAxis();

        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(1, X_pair.length);
        domain.setTickUnit(new NumberTickUnit(Math.ceil(X_pair.length / 10.0)));
        domain.setVerticalTickLabels(false);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(0, (int) (Math.ceil(Y_pair[Y_pair.length - 1])) + 1);
        range.setTickUnit(new NumberTickUnit(1.0));
        range.setVerticalTickLabels(false);

        // create and display a frame...
        ChartFrame frame = new ChartFrame("Grafik Nilai Fitness dari f(x)", chart);
        frame.setPreferredSize(new Dimension(900, 600)); // set same scale XY
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame); // set center window
        frame.setVisible(true);

        Shape ellips = new Ellipse2D.Double(0, 0, 6, 6);
        Shape rect = new Rectangle2D.Double(0, 0, 4, 4);
        Shape cross = ShapeUtilities.createDiagonalCross(4, 1);
        Shape diamond = ShapeUtilities.createDiamond(1);
        Shape downsegitiga = ShapeUtilities.createDownTriangle(1);
   

        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();

        // set line dan shape untuk series 1
        renderer2.setSeriesLinesVisible(0, true);
        renderer2.setSeriesShapesVisible(0, true);

        // set line dan shape untuk series 2
        renderer2.setSeriesLinesVisible(1, true);
        renderer2.setSeriesShapesVisible(1, true);

        xyPlot.setRenderer(renderer2); // draw plotting all series

        // set only shape of series with index i
        renderer2.setSeriesShape(0, rect); // series 1

        renderer2.setSeriesPaint(0, Color.BLUE); // series 1

        // here we change the line size
        int seriesCount = xyPlot.getSeriesCount();
        for (int i = 0; i < seriesCount; i++) {
            xyPlot.getRenderer().setSeriesStroke(i, new BasicStroke(2));
        }
    }

}
