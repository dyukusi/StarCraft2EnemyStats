import com.google.common.primitives.Doubles;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.googlecode.charts4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class MatchLogManager {
    List<MatchLog> logs;
    List<League> leagues;

    MatchLogManager(String logPath) throws FileNotFoundException {
        this.logs = new ArrayList<>();

        Scanner sc = new Scanner(new File(logPath));
        while(sc.hasNext()) {
            String line = sc.next();
            String elements[] = line.split(",");
            this.logs.add(new MatchLog(
                    Long.valueOf(elements[0]),
                    Integer.valueOf(elements[1])
            ));
        }

        //JsonArray leagueJsonArray = Util.getJsonByURL(String.format(Constant.BASE_URL_OF_SC2LOGS_API_LEAGUE, Main.g.getRegion().getId())).getAsJsonArray();
        JsonArray leagueJsonArray = Util.getJsonByURL(String.format(Constant.BASE_URL_OF_SC2LOGS_API_LEAGUE, 1)).getAsJsonArray();
        leagues = new ArrayList<>();

        leagueJsonArray.forEach(e -> {
            JsonObject j = e.getAsJsonObject();
            leagues.add(new League(
                    j.get("region_id").getAsInt(),
                    j.get("season_id").getAsInt(),
                    j.get("queue_id").getAsInt(),
                    j.get("league_id").getAsInt(),
                    j.get("tier").getAsInt(),
                    j.get("min_rating").getAsInt(),
                    j.get("max_rating").getAsInt()
            ));
        });
    }

    void generateRatingChart(int width, int height) {
        List<Line> lines = new ArrayList<>();
        double[] ratings = this.logs.stream().mapToDouble(log -> (double)log.getRating()).toArray();
        double maxRating = Doubles.max(ratings);
        double minRating = Doubles.min(ratings);
        double minRange = (int)(minRating / 100) * 100;
        double maxRange = (int)((maxRating + 100) / 100) * 100;

        Line ratingLine = Plots.newLine(DataUtil.scaleWithinRange(minRange, maxRange, ratings));
        ratingLine.setColor(Color.RED);
        ratingLine.setLineStyle(LineStyle.THICK_LINE);
        lines.add(ratingLine);

        this.leagues.forEach(l -> {
            double[] border = {l.getMinRating(), l.getMinRating()};
            Line leagueBorderLine = Plots.newLine(DataUtil.scaleWithinRange(minRange, maxRange, border));
            leagueBorderLine.setColor(Color.BLUE);
            leagueBorderLine.setLineStyle(LineStyle.THICK_LINE);
            String borderLabel = String.format("%s %d", l.getLeague().name(), l.getTier());
            leagueBorderLine.addMarkers(Markers.newTextMarker(borderLabel, Color.BLUE, 15), 0, 1);
            lines.add(leagueBorderLine);
        });

        LineChart chart = GCharts.newLineChart(lines);
        chart.setSize(width, height);

        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels("0", String.valueOf(ratings.length/2), String.valueOf(ratings.length));
        xAxis.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 15, AxisTextAlignment.CENTER));
        chart.addXAxisLabels(xAxis);

        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(minRange, maxRange, (maxRange - minRange)/4);
        yAxis.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 15, AxisTextAlignment.RIGHT));
        chart.addYAxisLabels(yAxis);

        chart.setGrid(9999999, 25, 2, 2);

        System.out.println("mmr chart url: " + chart.toURLString());
        BufferedImage image = null;

        try {
            image = ImageIO.read(new URL(chart.toURLString()));
            ImageIO.write(image, "png", new File(Constant.MMR_CHART_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
