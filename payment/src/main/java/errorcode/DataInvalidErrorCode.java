/*
 * 文件名：DataInvalidErrorCode.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.errorcode;

/**
 * 数据非法错误码
 * 〈功能详细描述〉
 * @author zwdong90
 * @version 2020年3月15日
 */
public interface DataInvalidErrorCode
{
    /**
     * 数据的组成不正确（正确的结构行由两部分组成：USD 370）
     */
    String DATA_FORMAT_ERROR = "1";

    /**
     * 货币类型不正确（正确格式：3个英文大写字母）
     */
    String CURRENCY_ERROR = "2";

    /**
     * 金额/汇率数值不正确（正确格式：浮点数）
     */
    String VALUE_ERROR = "3";
}
