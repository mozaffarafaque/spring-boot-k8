package com.mozafaq.test.sparkapp.job;

import scala.Serializable;
import scala.Tuple2;

import java.util.Comparator;

class TupleComparable implements Comparator<Tuple2<Integer, Integer>>, Serializable {

    @Override
    public int compare(Tuple2<Integer, Integer> o1, Tuple2<Integer, Integer> o2) {
        return  o1._1 - o2._1;
    }
}
