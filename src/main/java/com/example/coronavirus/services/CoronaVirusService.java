package com.example.coronavirus.services;

import com.example.coronavirus.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusService {

    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private static String VIRUS_DATA_URL_DEATHS= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private List<LocationStats> allstats = new ArrayList<>();

    public List<LocationStats> getAllstats() {
        return allstats;
    }

    @PostConstruct//-->executa logo começa
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int lastCases = (Integer.parseInt(record.get(record.size()-1)));
            int prevDay = (Integer.parseInt(record.get(record.size()-2)));
            locationStats.setLatestTotalcases(lastCases);
            locationStats.setDiffFromLastDay(lastCases-prevDay);
            System.out.println(locationStats);
            newStats.add(locationStats);
        }
                 request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_DEATHS))
                .build();

        response= client.send(request, HttpResponse.BodyHandlers.ofString());
        csvReader = new StringReader(response.body());
        records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
        int i =0;
        for (CSVRecord record : records) {
            LocationStats locationStats =  newStats.get(i);
            int lastCases = (Integer.parseInt(record.get(record.size()-1)));
            locationStats.setDeaths(lastCases);
            System.out.println(locationStats);
            i++;
        }
        this.allstats = newStats;
    }
}
