package com.demo.coronvirus_tracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.demo.coronvirus_tracker.models.LocationStats;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.apache.commons.csv.CSVFormat;

import org.apache.commons.csv.CSVRecord;

@Service
public class CoronaDataService {
    private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> all_stats = new ArrayList<>();

    public List<LocationStats> get_all_stats() {
        return all_stats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetch_data() throws IOException, InterruptedException {
        List<LocationStats> new_stats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csv_body_reader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csv_body_reader);

        for (CSVRecord record : records) {
            LocationStats location_stat = new LocationStats();
            location_stat.setState(record.get("Province/State"));
            location_stat.setCountry(record.get("Country/Region"));
            int latest_cases = Integer.parseInt(record.get(record.size() - 1));
            int prev_day_cases = Integer.parseInt(record.get(record.size() - 2));
            location_stat.setLatestTotalCases(latest_cases);
            location_stat.setDiffFromPrevDay(latest_cases - prev_day_cases);
            new_stats.add(location_stat);
        }
        this.all_stats = new_stats;
    }
}
