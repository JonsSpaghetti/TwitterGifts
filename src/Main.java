import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TwitterException, IOException {
        ConfigurationBuilder config = new ConfigurationBuilder();
        File keys = new File("~/IdeaProjects/TwitterGifts/keys.txt");
        FileReader reader = new FileReader(keys);

        BufferedReader buffRead = new BufferedReader(reader);

        String consumerKey = buffRead.readLine();
        config.setOAuthConsumerKey(consumerKey);
        String consumerSecret = buffRead.readLine();
        config.setOAuthConsumerSecret(consumerSecret);
        config.setDebugEnabled(true);
        String accessToken = buffRead.readLine();
        String accessSecret = buffRead.readLine();
        config.setOAuthAccessToken(accessToken);
        config.setOAuthAccessTokenSecret(accessSecret);
        File file = new File("/home/jtan/KathTweets/log.txt");
        if(!file.exists()) {
            try{
                file.createNewFile();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
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
