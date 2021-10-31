package com.unisoma.interview.challenge.model;

import java.sql.Date;

public class EmployeeDto {
  private String name;
  private String taxId;
  private Date birthday;
  private String phone;
  private String address;
  private float salary;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTaxId() {
    return this.taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public float getSalary() {
    return this.salary;
  }

  public void setSalary(float salary) {
    this.salary = salary;
  }
}