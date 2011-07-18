package com.xtremelabs.robolectric.shadows;

import android.webkit.CookieManager;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import org.apache.http.cookie.Cookie;

import java.net.CookieStore;
import java.util.*;

/**
 * Shadows the {@code android.telephony.TelephonyManager} class.
 */
@SuppressWarnings({"UnusedDeclaration"})
@Implements(CookieManager.class)
public class ShadowCookieManager {
    private static CookieManager sRef;
    private Map<String,List<String>> cookies = new HashMap<String, List<String>>();
    private boolean accept;

    @Implementation
    public static CookieManager getInstance() {
        if (sRef == null) {
            sRef = Robolectric.newInstanceOf(CookieManager.class);
        }
        return sRef;
    }

    @Implementation
    public void setCookie(String url, String value) {
        if(!cookies.containsKey(url)) {
            cookies.put(url, new ArrayList<String>());
        }

        cookies.get(url).add(value);
    }

    @Implementation
    public String getCookie(String url) {
        StringBuilder sb = new StringBuilder();
        List cookieList = cookies.get(url);
        if (cookieList != null) {
            Iterator<String> cookieIter = cookieList.iterator();
            while(cookieIter.hasNext()) {
                sb.append(cookieIter.next());
                if (cookieIter.hasNext()) {
                    sb.append(",");
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    @Implementation
    public void setAcceptCookie(boolean accept) {
        this.accept = accept;
    }

    @Implementation
    public boolean acceptCookie() {
        return this.accept;
    }

    @Implementation
    public void removeAllCookie() {
        cookies.clear();
    }
}
