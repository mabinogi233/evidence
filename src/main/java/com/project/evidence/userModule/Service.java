package com.project.evidence.userModule;

import com.project.evidence.userModule.database.entity.user;
import com.project.evidence.userModule.database.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("userService")
public class Service {

    @Autowired
    userMapper mapper;

    /**
     * 主键删除，返回是否删除成功
     * @param uid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPrimaryKey(int uid){
        try {
            mapper.deleteByPrimaryKey(uid);
            if (mapper.selectByPrimaryKey(uid) == null) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入数据，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(user record){
        try {
            //设置唯一id
            if(mapper.maxId()==null){
                record.setUid(0);
            }else{
                record.setUid(mapper.maxId()+1);
            }
            while (mapper.selectByPrimaryKey(record.getUid())!=null){
                record.setUid(record.getUid()+1);
            }
            if(mapper.selectByUserName(record.getUsername())==null &&
                    mapper.selectByIdCardNumber(record.getIdcardnumber())==null) {
                mapper.insert(record);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 主键查询
     * @param uid
     * @return
     */
    public user selectByPrimaryKey(Integer uid){
        try{
            return mapper.selectByPrimaryKey(uid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByPrimaryKey(user record){
        try {
            if (record.getUid()!=null && mapper.selectByPrimaryKey(record.getUid()) != null) {
                mapper.updateByPrimaryKey(record);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询全部
     * @return
     */
    public List<user> getAll(){
        try {
            return mapper.getAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按名称查询
     * @param userName
     * @return
     */
    public user selectByUserName(String userName){
        try {
            if (userName != null) {
                return mapper.selectByUserName(userName);
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 身份证号查询
     * @param idcardNumber
     * @return
     */
    public user selectByIdcardNumber(String idcardNumber){
        try {
            if (idcardNumber != null) {
                return mapper.selectByIdCardNumber(idcardNumber);
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询机构的全部user
     * @param jid
     * @return
     */
    public List<user> selectByJid(int jid){
        try{
            return mapper.selectByJid(jid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}