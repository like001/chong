package search.impl;

import search.Search;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 618 on 2018/1/12.
 *
 * @author lingfengsan
 */
public class GoogleSearch implements Search {
    private String question;
    private Boolean needOpenBrowser;
    private String path;

    public GoogleSearch(String question, Boolean needOpenBrowser) throws UnsupportedEncodingException {
        this.question = question;
        this.needOpenBrowser = needOpenBrowser;
        this.path = "https://www.google.com.hk/search?safe=strict&hl=zh-CN&ei=w5lhWsGTLsPP0gTHuqCwDQ&q="+question+"&oq"+question +
                "&gs_l=psy-ab.3..0i67k1l2j0l8.14069.15390.0.15489.5.5.0.0.0.0.238.238.2-1.1.0....0...1.1j4.64.psy-ab..4.1.237....0.iWVu2FSFUYw";
    }
    @Override
    public Long search() throws IOException {
        boolean findIt = false;
        String line = null;
        while (!findIt) {
            URL url = new URL(path);
            BufferedReader breaded = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            while ((line = breaded.readLine()) != null) {
                if (line.contains("百度为您找到相关结果约")) {
                    findIt = true;
                    int start = line.indexOf("百度为您找到相关结果约") + 11;

                    line = line.substring(start);
                    int end = line.indexOf("个");
                    line = line.substring(0, end);
                    break;
                }

            }
        }
        line = line.replace(",", "");
        return Long.valueOf(line);
    }

    @Override
    public Long call() throws IOException {
        if (needOpenBrowser) {
            new Utils().openBrowser(path);
        }
        return search();
    }


}
