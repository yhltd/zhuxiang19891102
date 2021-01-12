package com.zx.pro.util;

import com.zx.pro.constent.HttpStatus;

import java.util.HashMap;

/**
 * @author Eweee
 */
public class ResultInfo extends HashMap<String, Object> {
  private static final long serialVersionUID = 1L;

  /**
   * 状态码
   */
  public static final String CODE_TAG = "code";

  /**
   * 返回内容
   */
  public static final String MSG_TAG = "message";

  /**
   * 数据对象
   */
  public static final String DATA_TAG = "data";

  /**
   * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
   */
  public ResultInfo() {
  }

  /**
   * 初始化一个新创建的 AjaxResult 对象
   *
   * @param code 状态码
   * @param message  返回内容
   */
  public ResultInfo(int code, String message) {
    super.put(CODE_TAG, code);
    super.put(MSG_TAG, message);
  }

  /**
   * 初始化一个新创建的 AjaxResult 对象
   *
   * @param code 状态码
   * @param message  返回内容
   * @param data 数据对象
   */
  public ResultInfo(int code, String message, Object data) {
    super.put(CODE_TAG, code);
    super.put(MSG_TAG, message);
    if (StringUtils.isNotNull(data)) {
      super.put(DATA_TAG, data);
    }
  }

  /**
   * 返回成功消息
   *
   * @return 成功消息
   */
  public static ResultInfo success() {
    return ResultInfo.success("操作成功");
  }

  /**
   * 返回成功数据
   *
   * @return 成功消息
   */
  public static ResultInfo success(Object data) {
    return ResultInfo.success("操作成功", data);
  }

  /**
   * 返回成功消息
   *
   * @param message 返回内容
   * @return 成功消息
   */
  public static ResultInfo success(String message) {
    return ResultInfo.success(message, null);
  }

  /**
   * 返回成功消息
   *
   * @param message  返回内容
   * @param data 数据对象
   * @return 成功消息
   */
  public static ResultInfo success(String message, Object data) {
    return new ResultInfo(HttpStatus.SUCCESS, message, data);
  }

  /**
   * 返回错误消息
   *
   * @return
   */
  public static ResultInfo error() {
    return ResultInfo.error("操作失败");
  }

  /**
   * 返回错误消息
   *
   * @param message 返回内容
   * @return 警告消息
   */
  public static ResultInfo error(String message) {
    return ResultInfo.error(message, null);
  }

  /**
   * 返回错误消息
   *
   * @param message  返回内容
   * @param data 数据对象
   * @return 警告消息
   */
  public static ResultInfo error(String message, Object data) {
    return new ResultInfo(HttpStatus.ERROR, message, data);
  }

  /**
   * 返回错误消息
   *
   * @param code 状态码
   * @param message  返回内容
   * @return 警告消息
   */
  public static ResultInfo error(int code, String message) {
    return new ResultInfo(code, message, null);
  }

}
