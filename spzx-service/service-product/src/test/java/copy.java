import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import org.springframework.beans.BeanUtils;

/**
 * @projectName: spzx-parent
 * @package: PACKAGE_NAME
 * @className: copy
 * @author: XiaoHB
 * @date: 2024/3/7 17:16
 */
public class copy {


    public static void main(String[] args) {
        UserInfoVo a = new UserInfoVo();
        UserInfoVo b = new UserInfoVo();
        a.setAvatar("2");
        a.setNickName("b");
        BeanUtils.copyProperties(a,b);
        System.out.println(b.getAvatar()+b.getNickName());
        System.out.println(a.hashCode()+" "+b.hashCode());
        b.setNickName("c");
        System.out.println("a:"+a.getNickName()+" b:"+b.getNickName());
        System.out.println(a.hashCode()+" "+b.hashCode());

    }
}
