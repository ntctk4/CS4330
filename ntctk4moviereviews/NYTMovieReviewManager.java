/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4moviereviews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.*;

/**
 *
 * @author ntctk4
 */
//It was kind of dificult for me to get the json jar into this project, not sure what my problem was
public class NYTMovieReviewManager {
        private String urlString = "";
   //good example
   //http://api.nytimes.com/svc/movies/v2/reviews/search.json?query=Matrix&api-key=ffce44561b6e44d5a705adabd3fa2a74

    //base url to send    
    private final String baseUrlString = "http://api.nytimes.com/svc/movies/v2/reviews/search.json";
    //my api key
    private final String apiKey = "ffce44561b6e44d5a705adabd3fa2a74";
    private String searchString = "Avengers";
    
    private URL url;
    ArrayList<NYTReview> reviews;
    
    public NYTMovieReviewManager() {
        reviews = new ArrayList<>();
    }
    
        public void load(String searchString) throws Exception {
        if (searchString == null || searchString.equals("")) {
            throw new Exception("The search string was empty.");
        }
        
        this.searchString = searchString;
        
        // create the urlString to be sent over to NYT
        String encodedSearchString;
        try {
            // searchString must be URL encoded to put in URL
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw ex;
        }
        
        //put all parts together to create the url to send over
        urlString = baseUrlString + "?query=" + encodedSearchString + "&api-key=" + apiKey;
        
        try {
            url = new URL(urlString);
        } catch(MalformedURLException muex) {
           throw muex;
        }
        
        String jsonString = "";
        try {
            //get the jason from the response
            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                jsonString += inputLine;
            //close buffer reader when finished
            in.close();
        } catch (IOException ioex) {
            throw ioex;
        }
        
        try {
            parseJsonReviewFeed(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void parseJsonReviewFeed(String jsonString) throws Exception {
        
        // start with clean list
        reviews.clear();
        
        //is jsonString is null or epty just return
        if (jsonString == null || jsonString.equals("")) return;
        
        JSONObject jsonObj;
        try {
            //get the object that will be parsed
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        if (jsonObj == null) return;
        
        String status = "";
        try {
            //get the statue
            status = (String)jsonObj.get("status");
        } catch (Exception ex) {
            throw ex;
        }
        //make sure the status is ok so we can continue to parse
        if (status == null || !status.equals("OK")) {
            throw new Exception("Status returned from API was not OK.");
        }
        
        //the results start off as an array of objects
        //so we need to start off grabing the results from the json
        JSONArray results;
        try {
            results = (JSONArray)jsonObj.get("results");
        } catch (Exception ex) {
            throw ex;
        }
      //for each element in the array grab of the data we want from the response
        for (Object result : results) {
            try {
                JSONObject review = (JSONObject)result;
                JSONObject link = (JSONObject)review.getOrDefault("link", null);
                String webUrl = ""; 
                if (link != null) {
                    webUrl = (String)link.getOrDefault("url", "");
                }
                String display_title = (String)review.getOrDefault("display_title", "");
                String summary_short = (String)review.getOrDefault("summary_short", "");
                String headline = (String)review.getOrDefault("headline", "");
                String mpaa_rating = (String)review.getOrDefault("mpaa_rating", "");
                String publcation_date = (String)review.getOrDefault("publication_date", "");
                String opening_date = (String)review.getOrDefault("opening_date", "");

                //once the data is collected display the results
                System.out.println("display_title: " + display_title + "\n");
                System.out.println("url: " + webUrl + "\n");
                System.out.println("summary_short: " + summary_short + "\n");
                System.out.println("headline: " + headline + "\n");
                System.out.println("publication_date: " + publcation_date + "\n");
                System.out.println("opening_date: " + opening_date + "\n");
                System.out.println("mpaa_rating: " + mpaa_rating + "\n");
                System.out.println("------------------------------------------------------\n");
                
                //create a new review object and put store the data in it
                NYTReview newReview = new NYTReview(webUrl, display_title, summary_short, headline, publcation_date, opening_date, mpaa_rating );
                reviews.add(newReview);
                
            } catch (Exception ex) {
                throw ex;
            }
            
        }
        
    }
    
    public ArrayList<NYTReview> getNewsStories() {
        return reviews;
    }
    
    public int getNumNewsStories() {        
        return reviews.size();
    }
    
}
