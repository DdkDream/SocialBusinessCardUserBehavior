import com.nuc.dao.AreaDao;
import com.nuc.dao.LoginRecordDao;
import com.nuc.dao.PhoneDao;
import com.nuc.dao.impl.AreaDaoImpl;
import com.nuc.dao.impl.LoginRecordDaoImpl;
import com.nuc.dao.impl.PhoneDaoImpl;
import com.nuc.unit.LoginRecord;

/**
 * @Author：朱瑞敏
 * @Description： TODO
 **/
public class main {
    public static void main(String[] args) {
        LoginRecordDao loginRecord = new LoginRecordDaoImpl();
        LoginRecord loginRecord1 = loginRecord.selectLoginRecordByRecordId("lo202304051614310669");
        System.out.println(loginRecord1);


    }
}
