/*
 * 文件名：AnalysisUtil.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import main.java.constant.DataTypeConstant;
import main.java.domain.Payment;
import main.java.errorcode.DataInvalidErrorCode;
import main.java.exception.DataInvalidException;


/**
 * 解析文件里的数据、控制台输入的数据，并判断其合法性
 * 〈功能详细描述〉
 * @author zwdong90
 * @version 2020年3月15日
 */
public class AnalysisUtil
{

    /**
     * 解析、判断单行数据
     * @param inputLine 单行数据的内容
     * @param dataType 单行数据的类型：
     * 1：文件里的金额数据
     * 2：文件里的汇率数据
     * 3：控制台输入的金额数据
     * @return
     * @throws DataInvalidException 
     * @see 
     */
    public static Payment analysis(String inputLine, int dataType)
        throws DataInvalidException
    {
        // 1、判空。空内容不算非法
        if (null == inputLine || inputLine.matches("^\\s*$"))
        {
            return null;
        }

        // 2、分离货币名称和数值。正确的格式为两部分组成   
        inputLine = inputLine.trim();
        String[] payMentsLine = inputLine.split("\\s+");
        if (2 != payMentsLine.length)
        {
            throw new DataInvalidException(DataInvalidErrorCode.DATA_FORMAT_ERROR, null);
        }

        // 3、判断货币名称是否合规
        String currency = payMentsLine[0];
        if (!currency.matches("[A-Z]{3}"))
        {
            throw new DataInvalidException(DataInvalidErrorCode.CURRENCY_ERROR, null);
        }

        // 4、判断数值(amount/rate)是否合规
        String value = payMentsLine[1];
        try
        {
            Float.parseFloat(value);
        }
        catch (NumberFormatException e)
        {
            throw new DataInvalidException(DataInvalidErrorCode.VALUE_ERROR, null);
        }
        return new Payment(currency, value);
    }

    /**
     * 读取文件里的单行数据 
     * @param file 写有金额/汇率的文件
     * @param fileType 文件类型：
     * 1：存放金额的文件
     * 2：存放汇率的文件
     * @return
     * @throws DataInvalidException 
     * @see 
     */
    public static Map<String, String> readFileByLine(File file, int fileType)
        throws DataInvalidException
    {
        BufferedReader br = null;
        Map<String, String> paymentMap = new HashMap<String, String>();
        try
        {
            br = new BufferedReader(new FileReader(file));
            String currentString = null;
            Payment payment = null;
            while (null != (currentString = br.readLine()))
            {
                payment = analysis(currentString, fileType);
                if (null != payment)
                {
                    // 文件里存放金额、汇率，处理方式不同。金额需要把相通货币的钱数加和，汇率不需要
                    if (DataTypeConstant.FILE_PAYMENT_AMOUNT == fileType)
                    {
                        addAmountTogether(paymentMap, payment);
                    }
                    else if (DataTypeConstant.FILE_EXCHANGE_RATE == fileType)
                    {
                        paymentMap.put(payment.getCurrency(), payment.getValue());
                    }
                }
            }
            br.close();
        }
        catch (IOException e)
        {}
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {}
            }
        }
        return paymentMap;
    }

    /**
     * 对金额的处理
     * 货币存在，则加和；货币不存在，则新增此类型货币
     * @param paymentMap
     * @param payment 
     * @see 
     */
    public static void addAmountTogether(Map<String, String> paymentMap, Payment payment)
    {
        String currency = payment.getCurrency();
        String amount = payment.getValue();
        if (paymentMap.containsKey(currency))
        {
            String preAmount = paymentMap.get(currency);
            BigDecimal preAmountDecimal = new BigDecimal(preAmount);
            BigDecimal currentAmountDecimal = new BigDecimal(amount);
            BigDecimal newAmountDecimal = preAmountDecimal.add(currentAmountDecimal).setScale(2,
                RoundingMode.HALF_UP);
            paymentMap.put(currency, newAmountDecimal.toPlainString());
        }
        else
        {
            paymentMap.put(currency, amount);
        }
    }
}
