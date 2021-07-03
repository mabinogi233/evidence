package com.project.evidence.organizationModule;


import com.project.evidence.organizationModule.database.entity.organization;
import com.project.evidence.organizationModule.database.mapper.organizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("organizationService")
public class Service {
    @Autowired
    organizationMapper mapper;

    /**
     * 主键删除，返回是否删除成功
     * @param jid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPrimaryKey(int jid){
        try {
            mapper.deleteByPrimaryKey(jid);
            if (mapper.selectByPrimaryKey(jid) == null) {
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
    public boolean insert(organization record){
        try {
            //设置唯一id
            if(mapper.maxId()==null){
                record.setJid(0);
            }else{
                record.setJid(mapper.maxId()+1);
            }
            while (mapper.selectByPrimaryKey(record.getJid())!=null){
                record.setJid(record.getJid()+1);
            }
            mapper.insert(record);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 主键查询
     * @param jid
     * @return
     */
    public organization selectByPrimaryKey(Integer jid){
        try{
            return mapper.selectByPrimaryKey(jid);
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
    public boolean updateByPrimaryKey(organization record){
        try {
            if (record.getJid()!=null && mapper.selectByPrimaryKey(record.getJid()) != null) {
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
    public List<organization> getAll(){
        try {
            return mapper.getAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按机构名称查询
     * @param JName
     * @return
     */
    public List<organization> selectByJName(String JName){
        try {
            if (JName != null) {
                return mapper.selectByJName("%"+JName+"%");
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
