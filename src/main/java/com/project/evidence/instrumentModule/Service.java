package com.project.evidence.instrumentModule;

import com.project.evidence.instrumentModule.database.entity.instrument;
import com.project.evidence.instrumentModule.database.mapper.instrumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("instrumentService")
public class Service {

    @Autowired
    instrumentMapper mapper;


    /**
     * 主键删除，返回是否删除成功
     * @param yid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPrimaryKey(int yid){
        try {
            mapper.deleteByPrimaryKey(yid);
            if (mapper.selectByPrimaryKey(yid) == null) {
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
    public boolean insert(instrument record){
        try {
            //设置唯一id
            if(mapper.maxId()==null){
                record.setYid(0);
            }else{
                record.setYid(mapper.maxId()+1);
            }
            while (mapper.selectByPrimaryKey(record.getYid())!=null){
                record.setYid(record.getYid()+1);
            }
            mapper.insert(record);
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
    public boolean updateByPrimaryKey(instrument record){
        try {
            if (record.getYid()!=null && mapper.selectByPrimaryKey(record.getYid()) != null) {
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
     * 查询机构的全部仪器
     * @param jid
     * @return
     */
    public List<instrument> selectByJid(int jid){
        try{
            return mapper.selectByJid(jid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主键查询仪器
     * @param yid
     * @return
     */
    public instrument selectByYid(int yid){
        try{
            return mapper.selectByPrimaryKey(yid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
