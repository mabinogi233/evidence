package com.project.evidence.identifyModule;


import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.identifyModule.database.entity.identify;
import com.project.evidence.identifyModule.database.entity.identifyProvide;
import com.project.evidence.identifyModule.database.entity.identifyResult;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@org.springframework.stereotype.Service("identifyUserFastService")
public class UserFastService extends Service{

    @Autowired
    @Qualifier("userService")
    com.project.evidence.userModule.Service userService;

    @Autowired
    @Qualifier("identifyService")
    Service identifyService;

    @Qualifier("selectThreadPool")
    @Autowired
    ThreadPoolTaskExecutor executor;


    /**
     * 查询提交至此机构的报表
     */
    public List<identify> selectIdentifyFromJid(int jid){
        List<identifyProvide> p = identifyService.selectIdentifyProvideByJid(jid);
        if(p!=null) {
            List<identify> i = new ArrayList<>();
            //多线程优化
            List<Future<List<identify>>> futures = new ArrayList<>();
            for(identifyProvide pi :p){
                if(pi.getQid()!=null){
                    //启动
                    Future<List<identify>> future = executor.submit(new Callable<List<identify>>() {
                        @Override
                        public List<identify> call() throws Exception {
                            return identifyService.selectIdentifyByQid(pi.getQid());
                        }
                    });
                    futures.add(future);
                }
            }
            try {
                //异步轮询获取结果
                for (Future<List<identify>> f : futures) {
                    while (true) {
                        if (f.isDone() && !f.isCancelled()) {
                            List<identify> r =  f.get();
                            if(r!=null){
                                i.addAll(r);
                            }
                            break;
                        }else{
                            Thread.sleep(1);
                        }
                    }
                }
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
                //异常查询则清空
                i.clear();
            }

            if (i.size()>0) {
                return i;
            } else {
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 查询特定uid的机构提交的全部报表
     */
    public List<identify> selectIdentifyByUidSJid(int uid){
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && u.getJid()!=null) {
            List<user> users = userService.selectByJid(u.getJid());
            List<identify> identifies = new ArrayList<>();
            if (users != null) {
                //多线程优化
                List<Future<List<identify>>> futures = new ArrayList<>();
                for (user us : users) {
                    if (us.getUid() != null) {
                        //启动
                        Future<List<identify>> future = executor.submit(new Callable<List<identify>>() {
                            @Override
                            public List<identify> call() throws Exception {
                                return identifyService.selectIdentifyByUid(us.getUid());
                            }
                        });
                        futures.add(future);
                    }
                }

                try {
                    //异步轮询获取结果
                    for (Future<List<identify>> f : futures) {
                        while (true) {
                            if (f.isDone() && !f.isCancelled()) {
                                List<identify> r = f.get();
                                if (r != null) {
                                    identifies.addAll(r);
                                }
                                break;
                            } else {
                                Thread.sleep(1);
                            }
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    //异常查询则清空
                    identifies.clear();
                }

                if (identifies.size() == 0) {
                    return null;
                } else {
                    return identifies;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 查询uid用户提交的报表的全部结果
     * @return
     */
    public List<identifyResult> selectIdentifyResultByUid(int uid){
        List<identifyResult> results = new ArrayList<>();
        if(uid!=-1) {
            //用户提交的全部报表
            List<identify> identifies = identifyService.selectIdentifyByUid(uid);
            if(identifies!=null && identifies.size()!=0){
                //存在报表，添加报表对应的结果
                List<Future<identifyResult>> futures = new ArrayList<>();
                for(identify i:identifies) {
                    //多线程优化
                    if (i.getBid() != null) {
                        //启动
                        Future<identifyResult> future = executor.submit(new Callable<identifyResult>() {
                            @Override
                            public identifyResult call() throws Exception {
                                return identifyService.selectIdentifyResultByPrimaryKey(i.getBid());
                            }
                        });
                        futures.add(future);
                    }
                }
                try {
                    //异步轮询获取结果
                    for (Future<identifyResult> f : futures) {
                        while (true) {
                            if (f.isDone() && !f.isCancelled()) {
                                identifyResult r = f.get();
                                if (r != null) {
                                    results.add(r);
                                }
                                break;
                            } else {
                                Thread.sleep(1);
                            }
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    //异常查询则清空
                    results.clear();
                }

                if(results.size()!=0){
                    //存在结果
                    return results;
                }else{
                    //无鉴定结果
                    return null;
                }
            }else{
                //无鉴定报表
                return null;
            }
        }else{
            //token异常
            return null;
        }
    }
}
