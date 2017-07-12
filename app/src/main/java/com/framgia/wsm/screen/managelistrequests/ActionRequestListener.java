package com.framgia.wsm.screen.managelistrequests;

/**
 * Created by tri on 11/07/2017.
 */

public interface ActionRequestListener {

    void onApproveRequest(int requestID);

    void onRejectRequest(int requestID);
}
