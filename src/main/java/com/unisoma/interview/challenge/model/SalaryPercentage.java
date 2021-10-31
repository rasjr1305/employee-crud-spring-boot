package com.unisoma.interview.challenge.model;

public enum SalaryPercentage {
  TIER_01(0.15), TIER_02(0.12), TIER_03(0.10), TIER_04(0.07), TIER_05(0.04);

  private double value;

  private SalaryPercentage(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }
}