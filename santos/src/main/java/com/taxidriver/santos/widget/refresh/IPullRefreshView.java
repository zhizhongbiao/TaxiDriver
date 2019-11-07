package com.taxidriver.santos.widget.refresh;
/**
 * Auther by winds on 2016/12/15
 * Email heardown@163.com
 */

public interface IPullRefreshView {

  enum State{
    GONE,MOVE_PULL,MOVE_WAIT_REFRESH,MOVE_REFRESH,MOVE_SRPINGBACK;
  }

  /**
   * 隐藏
   */
  void onPullHided();

  /**
   * 刷新中
   */
  void onPullRefresh();

  /**
   * 提示松手
   */
  void onPullFreeHand();

  /**
   * 下啦中
   */
  void onPullDowning();

  /**
   * 刷新完成
   */
  void onPullFinished();

  /**
   * @param pullDistance 下拉的距离
   * @param pullProgress 下拉的距离的比例
   */
  void onPullProgress(float pullDistance, float pullProgress);
}
