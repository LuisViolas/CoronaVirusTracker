package com.example.coronavirus.controllers;

import com.example.coronavirus.models.LocationStats;
import com.example.coronavirus.services.CoronaVirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Home {

    @Autowired
    CoronaVirusService coronaVirusService;

    @GetMapping("/")
    public String home(Model model){

         List<LocationStats> allStats =  coronaVirusService.getAllstats();
         int totalReportedcases = allStats.stream().mapToInt(stat ->stat.getLatestTotalcases()).sum();
         int totalNewCases = allStats.stream().mapToInt(stat ->stat.getDiffFromLastDay()).sum();
         int totalDeaths = allStats.stream().mapToInt(stat->stat.getDeaths()).sum();
         model.addAttribute("totalcasesReported", totalReportedcases);
         model.addAttribute("locationStats",coronaVirusService.getAllstats());
         model.addAttribute("prevDay",totalNewCases);
         model.addAttribute("totalDeaths",totalDeaths);



        return "home";
    }
}
