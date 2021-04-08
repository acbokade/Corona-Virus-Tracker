package com.demo.coronvirus_tracker.controllers;

import java.util.List;

import com.demo.coronvirus_tracker.models.LocationStats;
import com.demo.coronvirus_tracker.services.CoronaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class Controller_ {

    @Autowired
    CoronaDataService corona_data_service;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> all_stats = corona_data_service.get_all_stats();
        int total_reported_cases = all_stats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int total_new_cases = all_stats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", all_stats);
        model.addAttribute("totatReportedCases", total_reported_cases);
        model.addAttribute("totalNewCases", total_new_cases);

        return "home";
    }
}
