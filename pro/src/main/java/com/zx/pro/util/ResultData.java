package com.zx.pro.util;

/** @author Eweee */
public class ResultData {

  /** 状态码 */
  private int code;
  /** 信息说明 */
  private String msg;
  /** 数据 */
  private Object data;

  public ResultData(int code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  /**
   * 成功请求
   *
   * @param data 返回数据
   * @return 返回成功信息
   */
  public static ResultData success(Object data) {
    return new ResultData(200, "请求成功", data);
  }

  /**
   * 成功请求
   *
   * @param msg 返回的信息
   * @param data 返回请求数据
   * @return 返回成功信息
   */
  public static ResultData success(String msg, Object data) {
    return new ResultData(200, msg, data);
  }

  /**
   * 成功请求
   *
   * @param msg 返回的信息
   * @param data 返回请求数据
   * @return 返回成功信息
   */
  public static ResultData success(String msg, Object... data) {
    return new ResultData(200, msg, data);
  }

  /**
   * 失败请求
   *
   * @param msg 返回信息
   * @return 返回失败信息
   */
  public static ResultData fail(String msg) {
    return new ResultData(500, msg, null);
  }

  /**
   * 失败请求
   *
   * @param msg 返回信息
   * @param data 返回诗句
   * @return 返回失败信息
   */
  public static ResultData fail(String msg, Object data) {
    return new ResultData(500, msg, data);
  }

  /**
   * 转Json
   * @return json字符串
   */
  public String toJson(){
    return GsonUtil.toJson(this);
  }
}