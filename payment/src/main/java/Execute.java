/*
 * 文件名：Execute.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import main.java.constant.DataTypeConstant;
import main.java.constant.RunningProjectConstant;
import main.java.domain.Payment;
import main.java.errorcode.DataInvalidErrorCode;
import main.java.exception.DataInvalidException;
import main.java.util.AnalysisUtil;


/**
 * main方法入口类
 * 〈功能详细描述〉
 * @author zwdong90
 * @version 2020年3月15日
 */
public class Execute
{
    /**
     * 存放货币、金额的文件
     */
    private static String paymentAmountFilePath = "src\\main\\resource\\payment_amount.txt";

    /**
     * 存放货币、汇率的文件
     */
    private static String exchangeRateFilePath = "src\\main\\resource\\exchange_rate.txt";

    /**
     * 内存中存放的货币-金额数据
     */
    private static Map<String, String> paymentAmountMap = new HashMap<String, String>();

    /**
     * 内存中存放的货币-汇率数据
     */
    private static Map<String, String> exchangeRateMap = new HashMap<String, String>();

    /**
     * Description: 
     * @param args 
     * @see 
     */
    public static void main(String[] args)
    {
        //1、读取文件里的内容（金额、汇率）并加载到内存。对当前文件里的金额数据进行加和处理
        readFile();

        //2、新起线程，将结果定时输出控制台
        printToConsole();

        //3、新起线程，持续读取控制台输入内容
        readInputFromConsole();
    }

    /**
     * 读取文件里的内容  
     * @see 
     */
    private static void readFile()
    {
        //1、读取货币-金额文件的内容
        try
        {
            File paymentAmountFile = new File(paymentAmountFilePath);
            paymentAmountMap = AnalysisUtil.readFileByLine(paymentAmountFile,
                DataTypeConstant.FILE_PAYMENT_AMOUNT);
        }
        catch (DataInvalidException e)
        {
            String errMsg = "Data format was not right in the file " + paymentAmountFilePath;
            String errorCode = e.getErrCode();
            if (DataInvalidErrorCode.DATA_FORMAT_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", one or more line is not formatted as 2 parts, please check.";
            }
            else if (DataInvalidErrorCode.CURRENCY_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", the CURRENCY is invalid, please check.";
            }
            else if (DataInvalidErrorCode.VALUE_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", the amount is invalid, please check.";
            }
            System.out.println(errMsg);
            return;
        }

        //2、读取货币-汇率文件里的内容
        try
        {
            File exchangeRateFile = new File(exchangeRateFilePath);
            exchangeRateMap = AnalysisUtil.readFileByLine(exchangeRateFile,
                DataTypeConstant.FILE_EXCHANGE_RATE);
        }
        catch (DataInvalidException e)
        {
            String errMsg = "Data format was not right in the file " + exchangeRateFilePath;
            String errorCode = e.getErrCode();
            if (DataInvalidErrorCode.DATA_FORMAT_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", one or more line is not formatted as 2 parts, please check.";
            }
            else if (DataInvalidErrorCode.CURRENCY_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", the CURRENCY is invalid, please check.";
            }
            else if (DataInvalidErrorCode.VALUE_ERROR.equals(errorCode))
            {
                errMsg = errMsg + ", the rate is invalid, please check.";
            }
            System.out.println(errMsg);
            return;
        }
    }

    /**
     * 将结果定时输出控制台 
     * @see 
     */
    private static void printToConsole()
    {
        Timer timer = new Timer(true);
        timer.schedule(new java.util.TimerTask()
        {
            public void run()
            {
                System.out.println();
                System.out.println("The timely result:");
                for (String currentKey : paymentAmountMap.keySet())
                {
                    String currentAmount = paymentAmountMap.get(currentKey);
                    BigDecimal currentAmountDecimal = new BigDecimal(currentAmount);

                    //判断金额是否为0，不是0则展示
                    if (0 != new BigDecimal(0).compareTo(currentAmountDecimal))
                    {
                        StringBuilder showLine = new StringBuilder(32);
                        showLine.append(currentKey).append(" ").append(currentAmount);

                        //进行汇率转换
                        if (exchangeRateMap.containsKey(currentKey))
                        {
                            String rate = exchangeRateMap.get(currentKey);
                            BigDecimal rateDecimal = new BigDecimal(rate);
                            BigDecimal amountUSD = currentAmountDecimal.multiply(
                                rateDecimal).setScale(2, RoundingMode.HALF_UP);
                            showLine.append(" (USD ").append(amountUSD.toPlainString()).append(
                                ")");
                        }
                        System.out.println(showLine);
                    }
                }
            }
        }, 0, RunningProjectConstant.OUTPUT_TIME * 1000);
    }

    /**
     * 持续读取控制台输入内容  
     * @see 
     */
    private static void readInputFromConsole()
    {
        Thread consoleInputThread = new Thread()
        {
            public void run()
            {
                //读取控制台信息
                InputStream is = System.in;
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = null;
                String inputLine = null;
                Payment payment = null;
                try
                {
                    br = new BufferedReader(isr);
                    for (;;)
                    {
                        inputLine = br.readLine();

                        // 控制台输入“quit”则退出程序
                        if (RunningProjectConstant.QUIT_COMMAND.equalsIgnoreCase(inputLine.trim()))
                        {
                            System.out.println("Program quit!");
                            return;
                        }

                        try
                        {
                            payment = AnalysisUtil.analysis(inputLine,
                                DataTypeConstant.INPUT_PAYMENT_AMOUNT);

                            //将控制台输入行合入到内存的map里
                            if (null != payment)
                            {
                                AnalysisUtil.addAmountTogether(paymentAmountMap, payment);
                            }
                        }
                        catch (DataInvalidException e)
                        {
                            String errMsg = "Input invalid: ";
                            String errorCode = e.getErrCode();
                            if (DataInvalidErrorCode.DATA_FORMAT_ERROR.equals(errorCode))
                            {
                                errMsg = errMsg
                                         + "Input line is not formatted as 2 parts, please check.";
                            }
                            else if (DataInvalidErrorCode.CURRENCY_ERROR.equals(errorCode))
                            {
                                errMsg = errMsg + "Input CURRENCY is invalid, please check.";
                            }
                            else if (DataInvalidErrorCode.VALUE_ERROR.equals(errorCode))
                            {
                                errMsg = errMsg + "Input amount is invalid, please check.";
                            }
                            System.out.println(errMsg);
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println("IO Exception happened when read from console");
                    return;
                }
                finally
                {
                    try
                    {
                        br.close();
                    }
                    catch (IOException e)
                    {}
                }
            }
        };
        consoleInputThread.start();
    }
}
