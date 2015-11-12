package jp.ne.rio.trendwatcher;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//import jp.ne.rio.trendwatcher.OsakaTrendAsyncLoader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.layout.content_main, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderCallbacks<List<Status>> {

        // Twitterオブジェクト
        private Twitter twitter = null;

        // ローディング表示用ダイアログ
        private ProgressDialog progressDialog = null;

        public PlaceholderFragment() {

            // OAuth認証用設定
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setOAuthConsumerKey("1AjmapUfEy14YY4BsDzpGrdUh");
            configurationBuilder.setOAuthConsumerSecret("xVrTGnFFvAIEFoP6S3EmzHW7rkH8MquEtzbpuQtLYWE9zDMa86");
            configurationBuilder.setOAuthAccessToken("195303617-UOuwQxLF7Ple2s7MVKjBIolL2yOsHZgeYRXdPVOc");
            configurationBuilder.setOAuthAccessTokenSecret("aHvngOayM5RMqfaZo8NNJZOup1Otr1OmBImsirI4mktFc");

            // Twitterオブジェクトの初期化
            this.twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        }

        // ローディングダイアログの消去
        private void dialogDismiss(){
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);

            // 検索ボタンのイベントリスナーを設定する
            ((Button)getActivity().findViewById(R.id.button1))
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ローダーの開始
                            getLoaderManager().restartLoader(0, null, PlaceholderFragment.this);
                        }
                    });
            setRetainInstance(true);
        }

        @Override
        public Loader<List<Status>> onCreateLoader(int id, Bundle args) {
            // ローディングダイアログの表示
            this.progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading data...");

            OsakaTrendAsyncLoader loader = null;

            switch (id) {
                case 0:
                    // ローダーの初期化
                    loader = new OsakaTrendAsyncLoader(getActivity(), this.twitter);
                    loader.forceLoad();
                    break;
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Status>> loader,	List<Status> data) {

            if (data != null) {

                // アダプターの初期化
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1);

                // アダプターにTweetをセットする
                for (Status tweet : data) {
                    adapter.add(tweet.getText());
                }

                // ListViewにアダプターをセットする
                ((ListView) getView().findViewById(R.id.listView1)).setAdapter(adapter);
            }
            // ローディングダイアログの消去
            dialogDismiss();
        }

        @Override
        public void onLoaderReset(Loader<List<Status>> loader) {
        }

        @Override
        public void onPause() {
            super.onPause();

            // ローディングダイアログの消去
            dialogDismiss();
        }
    }

}
