package com.ranga.spark.log.util;

public class Tuple3<V1, V2, V3> extends Tuple2<V1, V2> {

    private V3 v3;

    public Tuple3(V1 v1, V2 v2, V3 v3) {
        super(v1, v2);
        this.v3 = v3;
    }

    public void setV1(V1 v1) {
        super.v1 = v1;
    }

    public V3 _3() {
        return v3;
    }

    @Override
    public String toString() {
        return "Tuple3{" +
                "v3=" + v3 +
                '}';
    }
}
