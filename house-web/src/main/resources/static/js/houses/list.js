$(document).ready(function () {
    loadingPage()
});
/**
 * 加载分页信息
 */
function loadingPage() {

    var totalPage = 0;
    $.ajax({
        url: "/house/list",
        data: getPageingValue(),
        async: false,
        type: 'POST',
        success: function (obj) {
            totalPage = obj.data.pages;
            setMsg(obj.data.list);
        }
    });
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
        url: "/house/list",
        data: data,
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
        var state = data.type == 1 ? "销售中" : "出租中";
        var src = data.imageList[1];
        $("#properties").append('<div class="property">' +
            '<figure class="tag status">正在销售</figure>' +
            '<div class="property-image">' +
            '<figure class="ribbon">In Hold</figure>' +
            '<a href="/house/detail">' +
            '<img alt="" src="' + src + '" style="width: 260px;height: 195px"></a>' +
            '</div>' +
            '<div class="info">' +
            '<header>' +
            '<a href="/house/detail"><h3>' + data.name + '</h3></a>' +
            '<figure>清河中街</figure>' +
            '</header>' +
            '<div class="tag price">' + data.price + '万</div>' +
            '<aside>' +
            '<p></p>' +
            '<dl>' +
            '<dt>状态:</dt>' +
            '<dd>' + state + '</dd>' +
            '<dt>面积:</dt>' +
            '<dd>' + data.area + 'm<sup>2</sup></dd>' +
            '<dt>卧室:</dt>' +
            '<dd>' + data.beds + '间</dd>' +
            '<dt>卫生间:</dt>' +
            '<dd>' + data.baths + '间</dd>' +
            '</dl>' +
            '</aside>' +
            '<a href="/house/detail" class="link-arrow">Read More</a>' +
            '</div>' +
            '</div>')
    });
}
