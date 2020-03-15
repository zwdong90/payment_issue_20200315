/*
 * 文件名：DataInvalidException.java
 * 版权：zwdong90
 * 描述：
 * 创建人：zwdong90
 * 创建时间：2020年3月15日
 */

package main.java.exception;

/**
 * 数据非法异常
 * 〈功能详细描述〉
 * @author zwdong90
 * @version 2020年3月15日
 */
public class DataInvalidException extends RuntimeException
{

    private static final long serialVersionUID = -1405709277907984943L;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误描述
     */
    private String errMsg;

    public DataInvalidException(String errCode, String errMsg)
    {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode()
    {
        return errCode;
    }

    public void setErrCode(String errCode)
    {
        this.errCode = errCode;
    }

    public String getErrMsg()
    {
        return errMsg;
    }

    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }

}
