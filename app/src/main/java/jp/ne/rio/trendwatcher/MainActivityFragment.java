package jp.ne.rio.trendwatcher;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    public static class PlaceholderFragment extends android.app.Fragment {

        // Twitterオブジェクト
        private Twitter twitter = null;

        public PlaceholderFragment() {

            // OAuth認証用設定（1）
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

            configurationBuilder.setOAuthConsumerKey("1AjmapUfEy14YY4BsDzpGrdUh");
            configurationBuilder.setOAuthConsumerSecret("xVrTGnFFvAIEFoP6S3EmzHW7rkH8MquEtzbpuQtLYWE9zDMa86");
            configurationBuilder.setOAuthAccessToken("195303617-UOuwQxLF7Ple2s7MVKjBIolL2yOsHZgeYRXdPVOc");
            configurationBuilder.setOAuthAccessTokenSecret("aHvngOayM5RMqfaZo8NNJZOup1Otr1OmBImsirI4mktFc");

            // Twitterオブジェクトの初期化（2）
            this.twitter =
                    new TwitterFactory(configurationBuilder.build()).getInstance();
        }
    }
}
