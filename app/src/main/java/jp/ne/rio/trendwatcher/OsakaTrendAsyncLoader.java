package com.rakuraku.android.util;

import java.util.List;
import java.util.Random;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


public class OsakaTrendAsyncLoader extends AsyncTaskLoader<List<Status>> {

	private Twitter twitter;
	
	public OsakaTrendAsyncLoader(Context context, Twitter _twitter ) {
		super(context);
		this.twitter = _twitter;
	}

	@Override
	public List<Status> loadInBackground() {

		try {
			// 大阪のWOEID 
			int osaka = 15015370;

			// トレンドを取得する
			Trend[] trend = this.twitter.getPlaceTrends(osaka).getTrends();
			
			// 取得したトレンドから、ランダムで１つを選択する
			Random rnd = new Random();
			String q = trend[rnd.nextInt(trend.length)].getQuery();
			
			// 検索文字列を設定する
			Query query = new Query(q);
			query.setLocale("ja");	// 日本語のtweetに限定する
			query.setCount(20);		// 最大20tweetにする（デフォルトは15）

			// 検索の実行
			QueryResult result = this.twitter.search(query);
			
			return result.getTweets();
               
		} catch (TwitterException e) {
			Log.d("twitter", e.getMessage());
		}
		
		return null;
	}

}
