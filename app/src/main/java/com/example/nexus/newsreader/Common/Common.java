package com.example.nexus.newsreader.Common;

import com.example.nexus.newsreader.Interface.IconBetterIdeaService;
import com.example.nexus.newsreader.Interface.NewsService;
import com.example.nexus.newsreader.Remote.IconBetterIdeaClient;
import com.example.nexus.newsreader.Remote.RetrofitClient;

/**
 * Created by James Sarkar.
 */

public class Common {

    private static final String BASE_URL = "https://newsapi.org/";

    public static final String API_KEY = "6053175218dc4b568bb602c78540d553";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService() {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getApiUrl(String source, String apiKey) {

        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKey)
                .toString();
    }
}
