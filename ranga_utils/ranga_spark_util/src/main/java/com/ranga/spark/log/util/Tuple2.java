package com.ranga.spark.log.util;

public class Tuple2<V1, V2> {
    protected V1 v1;
    protected V2 v2;

    public Tuple2(V1 v1, V2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public V1 _1() {
        return v1;
    }

    public V2 _2() {
        return v2;
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
    }
}
