$(document).ready(function () {
    /**
     * 获取url中的参数
     * @param name
     * @returns {null}
     */
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})
function successmsg(title, content) {
    toastr.success(content, title, {
        "timeOut": "1500",
        "iconClass": "toast toast-success",
        "closeButton": true,
        "positionClass": "toast-top-right"
    })
    return false;
}

function errormsg(title, content) {
    toastr.error(content, title, {
        "timeOut": "8000",
        "iconClass": "toast toast-error",
        "closeButton": true,
        "positionClass": "toast-top-right"
    })
    return false;
}


function nextPage(pageNum, pageSize) {

    if ($('#searchForm').length != 0) {
        var url = $('#searchForm').attr('action');
        var data = "?pageNum=" + pageNum + "&pageSize=" + pageSize + "&" + $('#searchForm').serialize();
        window.location.href = url + data;
    } else {
        var url = window.location.href;
        url = url.split('?')[0];
        var data = "?pageNum=" + pageNum + "&pageSize=" + pageSize;
        window.location.href = url + data;
    }
}