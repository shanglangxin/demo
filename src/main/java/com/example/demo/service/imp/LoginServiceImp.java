package com.example.demo.service.imp;

import com.example.demo.dto.AccountDTO;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.pojo.Account;
import com.example.demo.service.ILoginService;
import com.example.demo.util.MyException;
import com.example.demo.util.Result;
import com.example.demo.util.ResultUtil;
import com.example.demo.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("loginService")
public class LoginServiceImp implements ILoginService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void comfirmAccount(AccountDTO dto, HttpServletRequest request) throws MyException {
        AccountVO vo = accountMapper.queryAccount(dto);
        if(vo == null){
            throw new MyException(-1, "用户不存在");
        }
        if(!vo.getPassword().equals(dto.getPassword())){
            throw new MyException(-1, "密码错误");
        }
        if(!vo.getType().equals(dto.getType())){
            throw new MyException(-1,"职位不对称");
        }
        if(vo.getPassword().equals(dto.getPassword())){
            Account user = new Account();
            user.setUsername(vo.getUsername());
            user.setStaffId(vo.getStaffId());
            user.setPinyinName(vo.getPinyinName());
            user.setName(vo.getName());
            user.setType(vo.getType());
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
    }

    public void regist(AccountDTO dto) throws MyException {
        AccountVO account = accountMapper.queryAccount(dto);
        if(account != null){
            throw new MyException(-1,"该账号已存在");
        }
        accountMapper.insert(dto);
    }
}
