import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TwitterException, IOException {
        ConfigurationBuilder config = new ConfigurationBuilder();
        String consumerKey = "eGOMnm8oaH7aQrMDRGZhObz61";
        config.setOAuthConsumerKey(consumerKey);
        String consumerSecret = "McnlQl7rCM8y7VzJBJHopTgrtOU9NOT7Sr0jfGrwvYKjKhcPdI";
        config.setOAuthConsumerSecret(consumerSecret);
        config.setDebugEnabled(true);
        String accessToken = "172445914-XekJoMAZDZznZdr0bC6geaul5P850YvifoqkUY19";
        String accessSecret = "d1lHNfrmgmte75halTAUqvsMfjw7wsvBPJde4VGMF7fbr";
        config.setOAuthAccessToken(accessToken);
        config.setOAuthAccessTokenSecret(accessSecret);
        File file = new File("/home/jtan/KathTweets/log.txt");
        if(!file.exists()) file.createNewFile();
        FileWriter fileWrite = new FileWriter(file);
        final Twitter twitter = new TwitterFactory(config.build()).getInstance();
        Scanner scan = new Scanner(System.in);
        System.out.print("How many pages of tweets would you like to search?");
        int numPages = scan.nextInt();
        Paging paging = new Paging(1,200);
        List<Status> statuses;
        int pg = paging.getPage();
        while(pg < numPages) {
            statuses = twitter.getUserTimeline("x3Katherine",paging);
            for (Status status : statuses) {
                if (status.getText().toUpperCase().contains("I WANT") || status.getText().toUpperCase().contains("I NEED")) {
                    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                    fileWrite.write("@" + status.getUser().getScreenName() + " - " + status.getText() + "\n");
                }
//                    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            pg++;
            paging.setPage(pg);
        }
        fileWrite.close();
    }
}
