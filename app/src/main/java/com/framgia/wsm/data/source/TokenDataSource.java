package com.framgia.wsm.data.source;

/**
 * Created by Duong on 6/6/2017.
 */

public interface TokenDataSource {
    interface LocalDataSource extends BaseLocalDataSource {
        void saveToken(String token);

        String getToken();
    }
}
