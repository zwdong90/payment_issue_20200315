# payment_issue_20200315

工程名为payment。打开IDE直接导入即可：
File-->import-->Existing Project into Workspace

main方法执行入口所在类：Execute.java

常量定义：RunningProjectConstant。
1、退出命令（默认为“quit”）
2、定时输出时间间隔（默认60秒）

以下两个文件可以没有。如果有的话，会对里面的数据做判断。数据非法则退出程序
存放初始金额的文件：src\\main\\resource\\payment_amount.txt
存放汇率的文件：src\\main\\resource\\exchange_rate.txt

文件通过验证后，会进入控制台定时打印、等待输入的状态。此时可以在控制台输入数据。输入非法会弹出提示，但工程不会终止。除非输入“quit”

文件金额、文件汇率、控制台输入的金额，格式都是相同的，包括以下两部分：
1、货币类型（三个大写字母）
2、数值（浮点数）
两部分用空格或制表符隔开。

对空行、多个空格、多个制表符等“空白”内容，采取放通的态度。

金额数据加和的结果，保留小数点后2位、四舍五入的原则。

