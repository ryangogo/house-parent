$(document).ready(function () {

    getCommons();

    /**
     * 添加评论
     */
    $("#form-contact-agent-submit").click(function () {
        $("#form-contact-agent").submit();
    });
    $("#form-contact-agent").ajaxForm({
        beforeSubmit: function () {
            return true;
        },
        success: function (obj) {
            if (obj.status == 0) {
                var data = obj.data;
                addCommon(data)
            } else if (obj.status == 1) {
                layer.alert(obj.msg);
            }
        }
    });

    /**
     * 发送邮件
     */
    $("#sendEmail").click(function () {
        $("#form-contact-agenta").submit();//提交注册表单
    });
    $("#form-contact-agenta").ajaxForm({
        beforeSubmit: function () {
            return true;
        },
        success: function (obj) {
            if (obj.status == 0) {
                layer.alert(obj.msg);
            }
        }
    });
});

/**
 * 添加一个留言
 * @param data
 */
function addCommon(data) {
    $(".comments").append(
        '<li class="comment">' +
        '<figure>' +
        '<div class="image">' +
        '<img alt="" src="' + data.userAvatar + '">' +
        '</div>' +
        '</figure>' +
        '<div class="comment-wrapper">' +
        '<div class="name">' + data.userName + '</div>' +
        '<span class="date"><span class="fa fa-calendar"></span>' + data.createTimeStr + '</span>' +
        '<div class="rating rating-overall" data-score="5"></div>' +
        '<p  style="width: 55em">' + data.msg + '</p>' +
        '<a href="#" class="reply"><span class="fa fa-reply"></span>' + "reply" + '</a>' +
        '<hr>' +
        '</div>' +
        '</li>'
    )
}

/**
 * 获取房产留言信息
 */
function getCommons() {
    var houseId = $("#houseId").val();
    $.ajax({
        url: "/house/commons",
        data: {"houseId": houseId},
        type: "GET",
        async: "false",
        success: function (obj) {
            var data = obj.data;
            $.each(data, function (index, obj) {
                addCommon(obj);
            })
        }
    })
}