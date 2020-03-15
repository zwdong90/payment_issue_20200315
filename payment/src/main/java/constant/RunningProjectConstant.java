/*
 * 文件名：RunningProjectConstant.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.constant;

/**
 * 工程运行的相关参数
 * 1、“退出”命令 2、控制台输出结果的时间间隔
 * @author zwdong90
 * @version 2020年3月15日
 */
public interface RunningProjectConstant
{
    /**
     * “退出”命令
     */
    String QUIT_COMMAND = "quit";

    /**
     * 控制台输出结果的时间间隔，单位：秒
     */
    int OUTPUT_TIME = 60;
}
