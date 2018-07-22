$(document).ready(function () {

    loadingPage()
});
/**
 * 加载分页信息
 */
function loadingPage() {

    var totalPage = 0;
    $.ajax({
        url: "/agency/agentList",
        async: false,
        type: 'POST',
        success: function (obj) {
            totalPage = obj.data.pages;
            setMsg(obj.data.list);
        }
    })
    if (totalPage != 0) {
        $('.page').page({
            leng: totalPage,//总页数
            activeClass: 'activP',//active类
            firstPage: '首页',
            lastPage: '末页',
            prv: '«',
            next: '»',
            clickBack: function (pageNum) {
                if (pageNum <= totalPage) {
                    list(pageNum)
                }
            }
        });
    } else {
        $('.page').empty();
    }
}
/**
 * 获取分页参数
 */
function getPageingValue() {
    var type = $('#type').find("option:selected").val();//选中的值
    var name = $('#search-box-property-name').val();//选中的值
    var sorting = $('#sorting').val();//选中的值
    var jsonObj = {};
    if (type.length != 0) {
        jsonObj.type = type;
    }
    if (name.length != 0) {
        jsonObj.name = name;
    }
    if (type.sorting != 0) {
        jsonObj.sorting = sorting;
    }
    return jsonObj;
}
/**
 * 分页请求houselist
 */
function list(pageNum) {
    var data = getPageingValue();
    if (pageNum.length != 0) {
        data.pageNum = pageNum;
    }
    $.ajax({
        url: "/agency/agentList",
        async: false,
        type: 'POST',
        success: function (obj) {
            setMsg(obj.data.list);
        }
    })
}
/**
 * 动态展示数据
 * @param obj
 */
function setMsg(obj) {
    $("#properties").empty();
    $.each(obj, function (index, data) {
        $("#agentList").append(
            '<div class="row">' +
            '<div class="col-md-12 col-lg-6" >' +
            '<div class="agent">' +
            '<a href="/agency/agentDeatil?id=' + data.id + '" class="agent-image"><img alt="" src="' + data.avatar + '"></a>' +
            '<div class="wrapper">' +
            '<header><a href="/agency/agentDetail?id=' + data.id + '"><h2>' + data.name + '</h2></a></header>' +
            '<dl>' +
            '<dt>Phone:</dt>' +
            '<dd>' + data.phone + '</dd>' +
            '<dt>Email:</dt>' +
            '<dd><a href="mailto:#">' + data.email + '</a></dd>' +
            '<dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>' +
            '<dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>' +
            '<dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>' +
            '<dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>' +
            '</dl>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>'
        )
    });
}
