package chat.rocket.android.fragment.oauth;
import android.webkit.WebView;

import chat.rocket.android.log.RCLog;
import chat.rocket.core.models.LoginServiceConfiguration;
import okhttp3.HttpUrl;

/**
 * Created by fiore on 18/09/17.
 */

public class FablabsOAuthFragment extends AbstractOAuthFragment {
    @Override
    protected String getOAuthServiceName() {
        return "fablabs";
    }

    @Override
    protected String generateURL(LoginServiceConfiguration oauthConfig) {
        //String url = "http://" + hostname + "/_oauth/fablabs/"
//                + "?requestTokenAndRedirect=true&state=" + getStateString();
        //RCLog.d(url);
        String url = new HttpUrl.Builder().scheme("https")
                .host("api.fablabs.io")
//                .addPathSegment("home")
                .addPathSegment("oauth")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", oauthConfig.getKey())
                .addQueryParameter("response_type", "code")
//                .addQueryParameter("scope", "profile email")
                .addQueryParameter("state", getStateString())
                .addQueryParameter("redirect_uri", "http://" + hostname + "/_oauth/fablabs")
                .build()
                .toString();
        return url;
    }
    @Override
    protected void onPageLoaded(WebView webview, String url) {
//        super.onPageLoaded(webview, url);

        if (url.contains(hostname) && url.contains("_oauth/" + getOAuthServiceName() + "?code")) {
            final String jsHookUrl = "javascript:"
                    + "window._rocketchet_hook.handleConfig(document.getElementById('config').innerText);";
            webview.loadUrl(jsHookUrl);
        }
    }

    @Override
    protected void onOAuthCompleted() {
        RCLog.d("OAuth Completed");
    }
}

