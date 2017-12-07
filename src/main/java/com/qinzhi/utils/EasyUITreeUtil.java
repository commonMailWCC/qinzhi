package com.qinzhi.utils;

import com.qinzhi.bean.ComboTree;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * EasyUI ComboTree工具
 * Created by frank on 16-1-15.
 */
public class EasyUITreeUtil {

    /**
     * 输入无序的ComboTree获得有序的List
     *
     * @param list0 无序ComboTree节点
     * @return List<ComboTree>
     */
    public static List<ComboTree> makeTree(List<ComboTree> list0) throws Exception {

        //以pId为Key,而以pid为相同的ComboTree作为List为Value
        Map<String, List<ComboTree>> mapForPid = Maps.newHashMap();
        //以Id为Tree, 用来快速寻找到RootPID
        Map<String, ComboTree> mapForId = Maps.newHashMap();
        //root pid set
        Set<String> rps = Sets.newHashSet();

        //构造两种类型的map
        for (ComboTree ct : list0) {
            //构造ID Map
            mapForId.put(ct.getId(), ct);

            //PID Map 构造
            String pid = ct.getPid();
            List<ComboTree> lct = mapForPid.get(pid);
            if (lct == null) {
                //构造 Value类型
                lct = new LinkedList<ComboTree>();
                mapForPid.put(pid, lct);
            }
            //添加这个节点
            lct.add(ct);
        }

        //制作rootPidSet
        //已经被寻找过的ID
        Set<String> keySet = new HashSet<String>();
        //寻找RootPID
        for (String key : mapForId.keySet()) {
            //沿pid 直到rootPID
            while (true) {
                //已经处理过这种节点,
                //那么可以肯定该节点所在的rootPid已经被添加
                if (keySet.contains(key)) {
                    break;
                }
                //添加到keySet中, 表示该节点已经被处理
                keySet.add(key);
                ComboTree ct = mapForId.get(key);
                if (ct == null) {
                    //如果ct为null, 则表示该Key就是一个rootPID
                    rps.add(key);
                    break;
                }
                //下一个Pid
                key = ct.getPid();
            }
        }

        //虚拟root结果树
        List<ComboTree> vts = new LinkedList<ComboTree>();
        //对所有的root Pid 进行处理
        for (String key : rps) {
            List<String> queue = new LinkedList<String>();
            ComboTree vt = new ComboTree();
            vt.setId(key);
            vt.setPid(null);
            vt.setText("虚拟节点");
            //添加根Id
            queue.add(key);
            while (!queue.isEmpty()) {
                String pid = queue.remove(0);
                List<ComboTree> list = mapForPid.get(pid);
                //没有pid对应的子树
                if (list == null)
                    continue;
                for (ComboTree ct : list) {
                    //添加到queue中
                    queue.add(ct.getId());
                    //插入到正确的位置
                    if (!vt.addNode(ct)) {
                        throw new RuntimeException(ct.getText() + "无法插入到Tree中");
                    }
                }
            }
            vts.add(vt);
        }
        //整理res结果
        List<ComboTree> res = new LinkedList<ComboTree>();
        for (ComboTree vct : vts) {
            //虚拟PID节点不能作为真实根节点
            for (ComboTree ct : vct.getChildren()) {
                //添加真实节点
                res.add(ct);
            }

        }
        return res;
    }

    /*public static ComboTree getComboTree(String id, String pid, String name) {
        ComboTree t = new ComboTree();
        t.setId(id);
        t.setPid(pid);
        t.setText(name);
        return t;
    }

    public static void main(String[] args) throws Exception {
        List<ComboTree> list = new LinkedList<ComboTree>();
        list.add(getComboTree("0", null, "root"));
        list.add(getComboTree("1", "0", "root"));
        list.add(getComboTree("2", "0", "root"));
        list.add(getComboTree("3", "2", "root"));
        list.add(getComboTree("4", "3", "root"));
        list.add(getComboTree("5", "4", "root"));
        list.add(getComboTree("6", "2", "root"));
        list.add(getComboTree("A", null, "root"));
        list.add(getComboTree("B", "A", "root"));
        list.add(getComboTree("C", "A", "root"));
        list.add(getComboTree("D", "B", "root"));

        list.add(getComboTree("a", "aaaaa", "root"));
        list.add(getComboTree("aa", "a", "root"));
        System.out.println(makeTree(list));

    }*/
}
