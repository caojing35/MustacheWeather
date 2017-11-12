package okhttp3;

import com.mustacheweather.android.util.secure.SecureFileSystem;

import java.io.File;

/**
 * Created by caojing on 2017/11/10.
 */

public class CacheProxy
{
    public static Cache create() {
        Cache secCache = new Cache(new File("cache_path"), 24 * 60 * 60);
        return secCache;
    }
}
