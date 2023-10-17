package com.shinhan.stockapi;

public class StockResponse {
  private String itmsNm;
  private double fltRt;
  private String clpr;

  public StockResponse(String itmsNm, double fltRt, String clpr) {
    this.itmsNm = itmsNm;
    this.fltRt = fltRt;
    this.clpr = clpr;
  }

}

