package com.flaringapp.app;

import com.flaringapp.data.storage.CarsSourceModel;
import com.flaringapp.data.storage.CarsSourceModelImpl;

public class InstanceResolver {

    public static CarsSourceModel getCarsSourceModel() {
        return new CarsSourceModelImpl();
    }

}
