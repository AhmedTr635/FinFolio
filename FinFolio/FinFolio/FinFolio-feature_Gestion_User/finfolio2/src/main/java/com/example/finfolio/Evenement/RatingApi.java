package com.example.finfolio.Evenement;


import com.example.finfolio.Service.EvennementService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RatingApi {


    public static void saveRating(int eventId, int rating) {
        EvennementService.getInstance().updateRating(eventId, rating);
    }
    public static int loadRating(int eventId) {
        // Load the rating directly from the database
        return EvennementService.getInstance().getRating(eventId);
    }
}
