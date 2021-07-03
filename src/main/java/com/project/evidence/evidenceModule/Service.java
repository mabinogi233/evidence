package com.project.evidence.evidenceModule;


import com.project.evidence.evidenceModule.database.entity.evidence;
import com.project.evidence.evidenceModule.database.mapper.evidenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("evidenceService")
public class Service {

    @Autowired
    evidenceMapper mapper;

    /**
     * 主键删除，返回是否删除成功
     * @param wid
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPrimaryKey(int wid){
        try {
            mapper.deleteByPrimaryKey(wid);
            if (mapper.selectByPrimaryKey(wid) == null) {
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
    public boolean insert(evidence record){
        try {
            //设置唯一id
            if(mapper.maxId()==null){
                record.setWid(0);
            }else{
                record.setWid(mapper.maxId()+1);
            }
            while (mapper.selectByPrimaryKey(record.getWid())!=null){
                record.setWid(record.getWid()+1);
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
    public boolean updateByPrimaryKey(evidence record){
        try {
            if (record.getWid()!=null && mapper.selectByPrimaryKey(record.getWid()) != null) {
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
     * 查询用户uid提交的全部物证
     * @param uid
     * @return
     */
    public List<evidence> selectByUid(int uid){
        try{
            return mapper.selectByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 主键查询物证
     * @param wid
     * @return
     */
    public evidence selectByWid(int wid){
        try{
            return mapper.selectByPrimaryKey(wid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按照物证描述查询满足描述的全部物证
     * @param text
     * @return
     */
    public List<evidence> selectByText(String text){
        try{
            if(text!=null && text.length()!= 0 &&!text.equals(" ")) {
                String wtext = "%" + text + "%";
                return mapper.selectByText(wtext);
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询全部物证
     * @return
     */
    public List<evidence> selectAll(){
        try{
            return mapper.getAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
