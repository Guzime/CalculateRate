package ru.liga.util;

import lombok.SneakyThrows;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GraphUtils {

    private static final long serialVersionUID = 1L;
    private final DefaultCategoryDataset dataset;

    /**
     * Creates a new demo instance.
     */
    public GraphUtils() {
        this.dataset = new DefaultCategoryDataset();
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                null,
                "Next days" /* x-axis label*/,
                "RUB" /* y-axis label */,
                dataset);
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        return chart;
    }

    /**
     * Returns a sample dataset.
     */
    public void setData(List<List<Rate>> rateList, List<Currency> currencyList) {
        dataset.clear();
        for (int j = 0; j < currencyList.size(); j++) {
            List<Rate> tempRate = rateList.get(j);
            for (int i = 0; i < tempRate.size(); i++) {
                Rate cur = tempRate.get(i);
                dataset.addValue(cur.getRate(), currencyList.get(j), cur.getDate());
            }
        }
    }

    @SneakyThrows
    public File getCurrencyRatesAsGraph() {
        JFreeChart chart = createChart(dataset);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);

        File lineChart = new File("lineChart.png");
        ImageIO.write(chart.createBufferedImage(1000, 400), "png", lineChart);
        return lineChart;
    }
}