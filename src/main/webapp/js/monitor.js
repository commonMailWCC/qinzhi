/**
 * Copyright @ 2000 ysten Co. Ltd.
 * All right reserved.
 * @author: wangmin
 * date: 2016-05-12
 */
var qinzhi_APP = (function(){
    var APP = {};
    APP.constants = {};
    APP.image_extents = ["jpg","jpeg","png","gif"];
    APP.showTip = function (msg) {
        $.messager.show({
            title: '温馨提示',
            msg: msg,
            showType: 'show'
        });
    }
    APP.showEditWarnFunc = function () {
        $.messager.show({
            title: '温馨提示',
            msg: '修改操作至少且只能选择一行记录',
            showType: 'show'
        });
    };
    APP.showDeleteWarnFunc = function () {
        $.messager.show({
            title: '温馨提示',
            msg: '删除操作请选择一行记录',
            showType: 'show'
        });
    };
    APP.convertIds2Str = function (selections, idName) {
        var toSelectedID = "";
        if (!$.isArray(selections)) return toSelectedID;
        $.each(selections, function (i, value) {
            if (idName) {
                toSelectedID += value[idName] + ",";
            } else {
                toSelectedID += value["id"] + ",";
            }
        });
        return toSelectedID.slice(0, toSelectedID.length - 1);
    };
    return APP;
}())
