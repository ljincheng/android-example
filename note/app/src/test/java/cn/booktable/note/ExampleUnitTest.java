package cn.booktable.note;

import org.junit.Test;

import cn.booktable.uikit.util.StringHelper;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public  void testMoneyStr( )
    {
        try{
            String text="交通信用卡2019年10月20日还款209.18￥";
            String money= StringHelper.moneyStr(text);
            if(money!=null)
            {
                System.out.println("金额："+money);
            }else {
                System.out.println("提取金额失败");
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}