/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4moviereviews;

/**
 *
 * @author ntctk4
 */
public class NYTReview {
    public String webUrl;
    public String display_title;
    public String summary_short;
    public String headline;
    public String publcation_date;
    public String opening_date;
    public String mpaa_rating;
    
    //reworked Mussers NYTNews class so I can hold review data that seemed relevent for this project 
    public NYTReview(String webUrl, String display_title, String summary_short, String headline, String publcation_date, String opening_date, String mpaa_rating ) {
        this.webUrl = webUrl;
        this.display_title = display_title;
        this.summary_short = summary_short;
        this.headline = headline;
        this.publcation_date = publcation_date;
        this.opening_date = opening_date;
        this.mpaa_rating = mpaa_rating;
    }
    
}
