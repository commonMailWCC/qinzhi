/**
 * Copyright @ 2000 ysten Co. Ltd.
 * All right reserved.
 * @author: wangmin
 * date: 2016-05-16
 */
$.extend($.fn.validatebox.defaults.rules, {
    /*必须和某个字段相等*/
    equalTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: '字段不匹配'
    },
    contentlength: {//验证文本长度
        validator: function (value, param) {
            return value.length == param[0];
        },
        message: '输入内容长度必须为{0}位'
    },
    minLength: {//验证文本最小长度
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少{0}个字符.'
    },
    maxLength: { //验证文本最大长度
        validator: function (value, param) {
            value.replace(/[^\x00-\xff]/g, "**").length;
            return value.replace(/[^\x00-\xff]/g, "**").length <= param[0];
        },
        message: '输入内容长度不能超过{0}位'
    },
    length: {//验证文本长度范围
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    integer: {// 验证整数
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    port: {// 端口号
        validator: function (value) {
            return /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/i.test(value);
        },
        message: '请输入有效的Port！'

    },
    ip: {// 验证IP地址
        validator: function (value) {
            var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
            return reg.test(value);
        },
        message: 'IP地址格式不正确'
    },
    url: {
        validator: function (value) {
            return /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i.test(value);
        },
        message: 'URL地址不正确'
    },
    date:{
    	validator: function (str) {
    		var value = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    	    if (value == null) {
    	        return false;
    	    }
    	    else {
    	        var date = new Date(value[1], value[3] - 1, value[4]);
    	        return (date.getFullYear() == value[1] && (date.getMonth() + 1) == value[3] && date.getDate() == value[4]);
    	    }
    	},
    	message:'请输入正确的日期格式'
    },
    datetime:{
    	validator: function (str) {
    		var value = str.match(/^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/);
    	    if (value == null) {
    	        return false;
    	    }
    	    else {
    	        return true;
    	    }
    	},
    	message:'请输入正确的日期时间格式'
    }

});

//tabs关闭菜单
$.extend($.fn.tabs.methods, {
    allTabs: function (jq) {
        var tabs = $(jq).tabs('tabs');
        var all = [];
        all = $.map(tabs, function (n, i) {
            return $(n).panel('options')
        });
        return all;
    },
    closeCurrent: function (jq) { // 关闭当前
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex', currentTab);
        if (currentTabIndex != 0) {
            $(jq).tabs('close', currentTabIndex);
        }
    },
    closeAll: function (jq) { //关闭全部
        var tabs = $(jq).tabs('allTabs');
        $.each(tabs, function (i, n) {
            if (n.title != "首页") {
                $(jq).tabs('close', n.title);
            }
        })
    },
    closeOther: function (jq) { //关闭除当前标签页外的tab页
        var tabs = $(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex', currentTab);

        $.each(tabs, function (i, n) {
            if (currentTabIndex != i && n.title != "首页") {
                $(jq).tabs('close', n.title);
            }
        })
    },
    closeLeft: function (jq) { // 关闭当前页左侧tab页
        var tabs = $(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex', currentTab);
        var i = currentTabIndex - 1;

        while (i > -1) {
            $(jq).tabs('close', tabs[i].title);
            i--;
        }
    },
    closeRight: function (jq) { //// 关闭当前页右侧tab页
        var tabs = $(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex', currentTab);
        var i = currentTabIndex + 1, len = tabs.length;
        while (i < len) {
            $(jq).tabs('close', tabs[i].title);
            i++;
        }
    }
})