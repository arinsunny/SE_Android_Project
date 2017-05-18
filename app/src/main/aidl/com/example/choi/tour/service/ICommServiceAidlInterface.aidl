// ICommServiceAidlInterface.aidl
package com.example.choi.tour.service;

// Declare any non-default types here with import statements

interface ICommServiceAidlInterface {

    boolean setUser(String personName, String personEmail, String personId);

    String getUserName();
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
