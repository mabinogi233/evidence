package com.project.evidence.identifyModule;


import com.project.evidence.identifyModule.database.entity.identify;
import com.project.evidence.identifyModule.database.entity.identifyProvide;
import com.project.evidence.identifyModule.database.entity.identifyResult;
import com.project.evidence.identifyModule.database.mapper.identifyMapper;
import com.project.evidence.identifyModule.database.mapper.identifyProvideMapper;
import com.project.evidence.identifyModule.database.mapper.identifyResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("identifyService")
public class Service {
    @Autowired
    identifyMapper identifyMapperObject;

    @Autowired
    identifyProvideMapper identifyProvideMapperObject;

    @Autowired
    identifyResultMapper identifyResultMapperObject;

    /**
     * 主键删除鉴定报表
     * @param bid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteIdentifyByPrimaryKey(int bid){
        try {
            identifyMapperObject.deleteByPrimaryKey(bid);
            if (identifyMapperObject.selectByPrimaryKey(bid) == null) {
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
     * 插入鉴定报表，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insertIdentify(identify record){
        try {
            //设置唯一id
            if(identifyMapperObject.maxId()==null){
                record.setBid(0);
            }else{
                record.setBid(identifyMapperObject.maxId()+1);
            }
            while (identifyMapperObject.selectByPrimaryKey(record.getBid())!=null){
                record.setBid(record.getBid()+1);
            }
            identifyMapperObject.insert(record);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIdentifyByPrimaryKey(identify record){
        try {
            if (record.getBid()!=null && identifyMapperObject.selectByPrimaryKey(record.getBid()) != null) {
                identifyMapperObject.updateByPrimaryKey(record);
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
     * 删除鉴定提供单
     * @param qid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteIdentifyProvideByPrimaryKey(int qid){
        try {
            identifyProvideMapperObject.deleteByPrimaryKey(qid);
            if (identifyProvideMapperObject.selectByPrimaryKey(qid) == null) {
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
     * 插入鉴定提供单，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insertIdentifyProvide(identifyProvide record){
        try {
            //设置唯一id
            if(identifyProvideMapperObject.maxId()==null){
                record.setQid(0);
            }else{
                record.setQid(identifyProvideMapperObject.maxId()+1);
            }
            while (identifyProvideMapperObject.selectByPrimaryKey(record.getQid())!=null){
                record.setQid(record.getQid()+1);
            }
            identifyProvideMapperObject.insert(record);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIdentifyProvideByPrimaryKey(identifyProvide record){
        try {
            if (record.getQid()!=null && identifyProvideMapperObject.selectByPrimaryKey(record.getQid()) != null) {
                identifyProvideMapperObject.updateByPrimaryKey(record);
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
     * 主键删除鉴定结果
     * @param bid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteIdentifyResultByPrimaryKey(int bid){
        try {
            identifyResultMapperObject.deleteByPrimaryKey(bid);
            if (identifyResultMapperObject.selectByPrimaryKey(bid) == null) {
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
     * 插入鉴定结果，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean insertIdentifyResult(identifyResult record){
        try {
            if(identifyMapperObject.selectByPrimaryKey(record.getBid())!=null) {
                identifyResultMapperObject.insert(record);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新，返回是否成功
     * @param record
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateIdentifyResultByPrimaryKey(identifyResult record){
        try {
            if (record.getBid()!=null && identifyResultMapperObject.selectByPrimaryKey(record.getBid()) != null) {
                identifyResultMapperObject.updateByPrimaryKey(record);
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
     * 主键查询鉴定报表
     * @param bid
     * @return
     */
    public identify selectIdentifyByPrimaryKey(Integer bid){
        try{
            return identifyMapperObject.selectByPrimaryKey(bid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查找物证wid对应的报表
     * @param wid
     * @return
     */
    public List<identify> selectIdentifyByWid(Integer wid){
        try{
            return identifyMapperObject.selectByWid(wid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询用户uid提交的报表
     * @param uid
     * @return
     */
    public List<identify> selectIdentifyByUid(Integer uid){
        try{
            return identifyMapperObject.selectByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询鉴定机构jid需要鉴定的鉴定报表
     * @param qid
     * @return
     */
    public List<identify> selectIdentifyByQid(Integer qid){
        try{
            return identifyMapperObject.selectByQid(qid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询机构jid提供的鉴定项目
     * @param jid
     * @return
     */
    public List<identifyProvide> selectIdentifyProvideByJid(Integer jid){
        try{
            return identifyProvideMapperObject.selectByJid(jid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主键查询鉴定项目
     * @param qid
     * @return
     */
    public identifyProvide selectIdentifyProvideByPrimaryKey(Integer qid){
        try{
            return identifyProvideMapperObject.selectByPrimaryKey(qid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询机构jid提供的全部鉴定结果
     * @param jid
     * @return
     */
    public List<identifyResult> selectIdentifyResultByJid(Integer jid){
        try{
            return identifyResultMapperObject.selectByJid(jid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主键查询bid对应的鉴定结果
     * @param bid
     * @return
     */
    public identifyResult selectIdentifyResultByPrimaryKey(Integer bid){
        try{
            return identifyResultMapperObject.selectByPrimaryKey(bid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询全部鉴定提供单
     * @return
     */
    public List<identifyProvide> getAllProvide(){
        try{
            return identifyProvideMapperObject.getAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
