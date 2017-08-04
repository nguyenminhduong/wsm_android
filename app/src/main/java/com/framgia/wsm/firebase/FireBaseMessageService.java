package com.framgia.wsm.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.OffTypeDay;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.binding.ColorManagers;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import static com.framgia.wsm.utils.Constant.EXTRA_NOTIFICATION_REQUEST_TYPE;
import static com.framgia.wsm.utils.Constant.REQUEST_OFFS;

/**
 * Created by ASUS on 14/06/2017.
 */

public class FireBaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "FireBaseMessageService";
    private static final String NOTIFICATION_NUMBER = "NOTIFICATION_NUMBER";
    private static final String MESSAGE = "message";
    private static final String SHOW = "show";
    private static final String TYPE = "type";
    private static final String PERMISSION = "permission";
    private static final int ID_1 = 1;
    private static final String COMPANY = "company";
    private static final String ANNUAL = "Annual";

    @Inject
    UserRepository mUserRepository;
    @Inject
    BaseSchedulerProvider mBaseSchedulerProvider;
    private User mUser = new User();
    private List<OffType> offTypesCompany = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerFireBaseComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .fireBaseModule(new FireBaseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            final String messageResponse = remoteMessage.getData().get(MESSAGE);
            String notificationType = remoteMessage.getData().get(TYPE);
            String requestType =
                    SHOW.equals(notificationType) ? remoteMessage.getData().get(PERMISSION)
                            : Constant.BLANK;
            sendNotification(messageResponse, requestType);
            updateRemainingDayOff(requestType);
        }
    }

    private void updateRemainingDayOff(String requestType) {
        if (!REQUEST_OFFS.equals(requestType)) {
            return;
        }
        mUserRepository.getUser()
                .subscribeOn(mBaseSchedulerProvider.io())
                .flatMap(new Function<User, ObservableSource<List<OffType>>>() {
                    @Override
                    public ObservableSource<List<OffType>> apply(@NonNull User user)
                            throws Exception {
                        mUser = user;
                        return mUserRepository.getListOffType();
                    }
                })
                .flatMap(new Function<List<OffType>, ObservableSource<OffType>>() {
                    @Override
                    public ObservableSource<OffType> apply(@NonNull List<OffType> offTypes)
                            throws Exception {
                        return Observable.fromIterable(offTypes);
                    }
                })
                .filter(new Predicate<OffType>() {
                    @Override
                    public boolean test(@NonNull OffType offType) throws Exception {
                        return offType.getPayType().equals(COMPANY);
                    }
                })
                .toList()
                .toObservable()
                .flatMap(new Function<List<OffType>, ObservableSource<BaseResponse<OffTypeDay>>>() {
                    @Override
                    public ObservableSource<BaseResponse<OffTypeDay>> apply(
                            @NonNull List<OffType> offTypes) throws Exception {
                        offTypesCompany = offTypes;
                        return mUserRepository.getRemainingDayOff();
                    }
                })
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse<OffTypeDay>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<OffTypeDay> offTypeDayBaseResponse)
                            throws Exception {
                        if (offTypeDayBaseResponse.getData().getRemainingDayOff() != null) {
                            offTypesCompany.add(new OffType(ID_1, ANNUAL, COMPANY,
                                    offTypeDayBaseResponse.getData().getRemainingDayOff()));
                        } else {
                            offTypesCompany.add(new OffType(ID_1, ANNUAL, COMPANY,
                                    Float.parseFloat(Constant.DEFAULT_DOUBLE_VALUE)));
                        }
                        mUser.setTypesCompany(offTypesCompany);
                        mUserRepository.saveUser(mUser);
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        Log.e(TAG, "onRequestError: ", error);
                    }
                });
    }

    private void sendNotification(String messageBody, String requestType) {
        SharedPreferences prefs =
                getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationNumber = prefs.getInt(NOTIFICATION_NUMBER, 0);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(EXTRA_NOTIFICATION_REQUEST_TYPE, requestType);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this).setCategory(NotificationCompat.CATEGORY_PROMO)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher_round))
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_view, getString(R.string.view_detail),
                                pendingIntent)
                        .setSmallIcon(R.drawable.ic_notification_w)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setColor(Color.parseColor(ColorManagers.COLOR_GREEN_DARK))
                        .setSound(uri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationNumber, notificationBuilder.build());

        SharedPreferences.Editor editor = prefs.edit();
        notificationNumber++;
        editor.putInt(NOTIFICATION_NUMBER, notificationNumber);
        editor.apply();
    }
}
