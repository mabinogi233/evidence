package com.project.evidence.loginModule;

import com.project.evidence.loginModule.database.entity.token;
import com.project.evidence.loginModule.database.mapper.tokenMapper;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@org.springframework.stereotype.Service("loginService")
public class Service {

    //token失效时间
    private static final int tokenDeadMin = 14400;

    //refToken失效时间
    private static final int refTokenDeadMin = 144000;

    @Autowired
    @Qualifier("userService")
    private com.project.evidence.userModule.Service userService;

    @Autowired
    @Qualifier("utils")
    private Utils utils;

    @Autowired
    private tokenMapper mapper;

    /**
     * 登录，返回登录用户的uid，失败则返回-1
     * @param userName
     * @param password
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int login(String userName,String password){
        try {
            if (userName != null && password != null) {
                user u = userService.selectByUserName(userName);
                if (u != null && u.getPassword() != null) {
                    if (u.getPassword().equals(password)) {
                        //登录成功，写入token
                        int uid = u.getUid();
                        String tokenStr;
                        String refToken;
                        Date tokenGetTime = new Date();
                        Date tokenDeadTime = utils.addDateMin(tokenGetTime, tokenDeadMin);
                        Date refTokenDeadTime = utils.addDateMin(tokenGetTime, refTokenDeadMin);
                        do {
                            tokenStr = utils.createToken(u.getUsername());
                            refToken = utils.createRefToken(tokenStr);
                        } while (mapper.selectByToken(tokenStr) != null || mapper.selectByRefToken(refToken) != null);
                        token t = new token();
                        t.setUid(uid);
                        t.setToken(tokenStr);
                        t.setReftoken(refToken);
                        t.setTokendeadtime(tokenDeadTime);
                        t.setReftokendeadtime(refTokenDeadTime);
                        t.setTokengettime(tokenGetTime);
                        if(mapper.selectByPrimaryKey(uid)!=null){
                            mapper.updateByPrimaryKey(t);
                        }else {
                            mapper.insert(t);
                        }
                        return uid;
                    }
                }
            }
            return -1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 退出登录，返回成功与否
     * @param uid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean unlogin(int uid){
        try {
            mapper.deleteByPrimaryKey(uid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据token查询用户uid，失败返回-1
     * @param tokenStr
     * @return
     */
    public int checkToken(String tokenStr){
        try {
            token t = mapper.selectByToken(tokenStr);
            if (t != null && t.getUid() != null) {
                //检验token是否过期
                if((t.getTokendeadtime().after(new Date()))){
                    return t.getUid();
                }else {
                    //token过期
                    return -1;
                }
            } else {
                return -1;
            }
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 通过refToken刷新token，成功则返回刷新后token。
     * @param refToken
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String refToken(String refToken){
        try{
            token t = mapper.selectByRefToken(refToken);
            if(t.getReftokendeadtime().after(new Date())){
                user u = userService.selectByPrimaryKey(t.getUid());
                if(u!=null && u.getUsername()!=null) {
                    //刷新token
                    do {
                        t.setToken(utils.createToken(u.getUsername()));
                    }while (mapper.selectByToken(t.getToken())!=null);
                    //更新
                    mapper.updateByPrimaryKey(t);
                    return t.getToken();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主键查询token
     * @param uid
     * @return
     */
    public token selectByUid(int uid){
        try{
            return mapper.selectByPrimaryKey(uid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
