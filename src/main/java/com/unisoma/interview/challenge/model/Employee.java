package com.unisoma.interview.challenge.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "tax_id", unique = true)
  private String taxId;

  @Column(name = "name")
  private String name;

  @Column(name = "birthday")
  private Date birthday;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "salary")
  private float salary;

  public Employee() {

  }

  public Employee(String name, String taxId, Date birthday, String phone, String address, float salary) {
    this.name = name;
    this.taxId = taxId;
    this.birthday = birthday;
    this.phone = phone;
    this.address = address;
    this.salary = salary;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

  public void raiseSalary() {
    this.salary += this.salary * getSalaryPercentage();
  }

  public double getSalaryPercentage() {
    SalaryPercentage salaryPercentage = null;

    if (this.salary >= 0 && this.salary <= 400) {
      salaryPercentage = SalaryPercentage.TIER_01;
    } else if (this.salary > 400 && this.salary <= 800) {
      salaryPercentage = SalaryPercentage.TIER_02;
    } else if (this.salary > 800 && this.salary <= 1200) {
      salaryPercentage = SalaryPercentage.TIER_03;
    } else if (this.salary > 1200 && this.salary <= 2000) {
      salaryPercentage = SalaryPercentage.TIER_04;
    } else if (this.salary > 2000) {
      salaryPercentage = SalaryPercentage.TIER_05;
    }

    return salaryPercentage.getValue();
  }

  public float getTaxAmount() {
    float totalSalaryTemp = this.salary, taxAmount = 0, taxAmountPerBand = 0;

    if (totalSalaryTemp > 4500) {
      taxAmountPerBand = (totalSalaryTemp - 4500);
      taxAmount += taxAmountPerBand * 0.28;
      totalSalaryTemp -= taxAmountPerBand;
    }

    if (totalSalaryTemp > 3000 && totalSalaryTemp <= 4500) {
      taxAmountPerBand = (totalSalaryTemp - 3000);
      taxAmount += taxAmountPerBand * 0.18;
      totalSalaryTemp -= taxAmountPerBand;
    }

    if (totalSalaryTemp > 2000 && totalSalaryTemp <= 3000) {
      taxAmountPerBand = (totalSalaryTemp - 2000);
      taxAmount += taxAmountPerBand * 0.08;
      totalSalaryTemp -= taxAmountPerBand;
    }

    return taxAmount;
  }
}
