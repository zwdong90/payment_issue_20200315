/*
 * 文件名：Payment.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.domain;

/**
 * 金额/汇率对象
 * 金额和汇率结构相同，钱数、汇率都可以定义为浮点数。所以这里把金额、汇率用同一对象表示
 * @author zwdong90
 * @version 2020年3月15日
 */
public class Payment
{
    /**
     * 货币类型
     */
    private String currency;

    /**
     * 数值（钱数/汇率）
     */
    private String value;

    public Payment(String currency, String value)
    {
        super();
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
