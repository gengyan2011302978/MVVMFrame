package com.phjt.module_base.utils;

import androidx.annotation.Nullable;

import static java.lang.String.format;

/**
 * @author: gengyan
 * date:    2021/9/8
 * company: GY
 * description: 检查类 === 断言类（assert）
 */
public class Preconditions {

  private Preconditions() {
    throw new IllegalStateException("you can't instantiate me!");
  }

  /**
   * 空指针检查
   *
   * @param reference 需要检查的参数
   * @param <T> 检查类型
   * @return 通过检查返回实例
   */
  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    } else {
      return reference;
    }
  }

  /**
   * 空指针检查 并提示对应的异常检查信息
   *
   * @param reference 需要检查的参数
   * * @param errorMessage  异常检查信息
   * @param <T> 检查类型
   * @return 通过检查返回实例
   */
  public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    } else {
      return reference;
    }
  }
  public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
    if(reference == null) {
      throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    } else {
      return reference;
    }
  }

  public static void checkState(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
    if(!expression) {
      throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
    }
  }
}
