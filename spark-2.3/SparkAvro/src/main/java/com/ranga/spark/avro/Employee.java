package com.ranga.spark.avro;/* rangareddy.avula created on 11/06/20 */

import java.io.Serializable;

public class Employee implements Serializable {
    private long id;
    private String name;
    private float salary;
    private int deptId;

    public Employee() {

    }

    public Employee(long id, String name, float salary, int deptId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.deptId = deptId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
