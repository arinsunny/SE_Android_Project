package com.example.choi.tour.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ICommService extends Service {
    public ICommService() {
    }

    private final ICommServiceAidlInterface.Stub mBinder = new ICommServiceAidlInterface.Stub() {
        public boolean setUser(String personName, String personEmail, String personId) {
            return false;

        }

        public String getUserName() {
            return "OK";

        }

        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                        double aDouble, String aString) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}
