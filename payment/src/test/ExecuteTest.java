/*
 * 文件名：ExecuteTest.java
 * 版权：Copyright by www.qingso.com
 * 描述：
 * 修改人：赵卫东
 * 修改时间：2020年3月13日
 */

package test;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import main.java.domain.Payment;
import main.java.util.AnalysisUtil;


class ExecuteTest
{

    @Test
    void analysisTest1()
    {
        String inputPayment = "USD 700";
        Payment payment = AnalysisUtil.analysis(inputPayment, 3);
        Assert.assertEquals("USD", payment.getCurrency());
        Assert.assertEquals("700", payment.getValue());
    }

    @Test
    void analysisTest2()
    {
        String inputPayment = "  \t   USD \t\t   700  \t\t  ";
        Payment payment = AnalysisUtil.analysis(inputPayment, 3);
        Assert.assertEquals("USD", payment.getCurrency());
        Assert.assertEquals("700", payment.getValue());
    }

    @Test
    void analysisTest3()
    {
        String inputPayment = "  \t  \t\t   ";
        Payment payment = AnalysisUtil.analysis(inputPayment, 2);
        Assert.assertEquals(null, payment);
    }

    @Test
    void analysisTest4()
    {
        String inputPayment = "\n\n\n  ";
        Payment payment = AnalysisUtil.analysis(inputPayment, 1);
        Assert.assertEquals(null, payment);
    }

}
