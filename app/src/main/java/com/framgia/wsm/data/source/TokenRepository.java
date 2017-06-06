package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.local.TokenLocalDataSource;

/**
 * Created by Duong on 6/6/2017.
 */

public class TokenRepository {
    private TokenDataSource.LocalDataSource mLocalDataSource;

    public TokenRepository(TokenLocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public void saveToken(String token) {
        mLocalDataSource.saveToken(token);
    }

    public String getToken() {
        return mLocalDataSource.getToken();
    }
}
