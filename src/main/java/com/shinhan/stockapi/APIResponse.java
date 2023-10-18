package com.shinhan.stockapi;

import lombok.Data;

@Data
public class APIResponse {
  private Header header;
  private Body body;
  @Data
  public static class Header {
    private String resultCode;
    private String resultMsg;
  }
  @Data
  public static class Body {
    private Items items;
  }
  @Data
  public static class Items {
    private Item item;
  }
  @Data
  public static class Item {
    private String itmsNm;
    private double fltRt;
    private String clpr;

  }
}