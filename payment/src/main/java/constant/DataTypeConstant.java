/*
 * 文件名：DataTypeConstant.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.constant;

/**
 * 数据类型常量
 * 包括文件里的数据、控制台输入的数据。文件数据包括金额、汇率
 * @author zwdong90
 * @version 2020年3月15日
 */
public interface DataTypeConstant
{

    /**
     * 文件里的数据（金额）
     */
    int FILE_PAYMENT_AMOUNT = 1;

    /**
     * 文件里的数据（汇率）
     */
    int FILE_EXCHANGE_RATE = 2;

    /**
     * 控制台输入的数据（金额）
     */
    int INPUT_PAYMENT_AMOUNT = 3;
}
